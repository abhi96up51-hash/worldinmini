package com.example.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.data.TimeCalculator
import com.example.data.UserPreferencesRepository
import com.example.data.UserTimePreferences
import com.example.data.WorldCountriesCatalog
import com.example.model.Continent
import com.example.model.CountryTimeSnapshot
import com.example.model.FilterMode
import com.example.model.SortOrder
import com.example.model.UtcBandGroup
import com.example.model.WorldCountry
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.Calendar
import java.util.Locale
import java.util.TimeZone

enum class AppTab(val title: String) {
    WORLD_DIRECTORY("All Countries"),
    TIME_LOCK_MATRIX("Time Lock"),
    UTC_SOLAR_EXPLORER("UTC & Solar")
}

data class WorldTimeUiState(
    val activeTab: AppTab = AppTab.WORLD_DIRECTORY,
    val searchQuery: String = "",
    val selectedContinent: Continent = Continent.ALL,
    val selectedFilterMode: FilterMode = FilterMode.ALL,
    val selectedSortOrder: SortOrder = SortOrder.COUNTRY_A_Z,
    val is24HourFormat: Boolean = true,
    val isTimeLocked: Boolean = false,
    val lockedOffsetMinutes: Int = 0,
    val effectiveEpochMillis: Long = System.currentTimeMillis(),
    val anchorSnapshot: CountryTimeSnapshot? = null,
    val filteredCountries: List<CountryTimeSnapshot> = emptyList(),
    val pinnedSnapshots: List<CountryTimeSnapshot> = emptyList(),
    val utcBandGroups: List<UtcBandGroup> = emptyList(),
    val selectedDetailCountry: CountryTimeSnapshot? = null,
    val totalWorldCountriesCount: Int = 0,
    val daylightCountriesCount: Int = 0,
    val workingHoursCountriesCount: Int = 0,
    val pinnedWorkingOverlapCount: Int = 0
)

private data class FilterQueryState(
    val activeTab: AppTab = AppTab.WORLD_DIRECTORY,
    val searchQuery: String = "",
    val selectedContinent: Continent = Continent.ALL,
    val selectedFilterMode: FilterMode = FilterMode.ALL,
    val selectedSortOrder: SortOrder = SortOrder.COUNTRY_A_Z,
    val selectedDetailCountryId: String? = null
)

class WorldTimeViewModel(
    private val preferencesRepository: UserPreferencesRepository? = null
) : ViewModel() {

    private val allCountries: List<WorldCountry> = WorldCountriesCatalog.allCountries

    private var liveEpochMillis: Long = System.currentTimeMillis()
    private var localPrefs: UserTimePreferences = UserTimePreferences()
    private var filterState: FilterQueryState = FilterQueryState()

    private val _uiState = MutableStateFlow(buildUiState(liveEpochMillis, localPrefs, filterState))
    val uiState: StateFlow<WorldTimeUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            while (true) {
                delay(1000L)
                liveEpochMillis = System.currentTimeMillis()
                refreshState()
            }
        }
        if (preferencesRepository != null) {
            viewModelScope.launch {
                preferencesRepository.preferencesFlow.collect { prefs ->
                    localPrefs = prefs
                    refreshState()
                }
            }
        }
    }

    private fun refreshState() {
        _uiState.value = buildUiState(liveEpochMillis, localPrefs, filterState)
    }

    private fun buildUiState(
        liveMillis: Long,
        prefs: UserTimePreferences,
        filter: FilterQueryState
    ): WorldTimeUiState {
        val effectiveMillis = if (prefs.isTimeLocked) {
            val baseMinuteMillis = (liveMillis / 60000L) * 60000L
            baseMinuteMillis + (prefs.lockedOffsetMinutes * 60000L)
        } else {
            liveMillis
        }

        val anchorCountry = allCountries.find { it.id == prefs.anchorCountryId }
            ?: allCountries.find { it.id == "GB" }
            ?: allCountries.first()

        val anchorSnapshot = TimeCalculator.computeSnapshot(
            country = anchorCountry,
            epochMillis = effectiveMillis,
            anchorCountry = anchorCountry,
            is24Hour = prefs.is24HourFormat,
            isPinned = prefs.pinnedCountryIds.contains(anchorCountry.id),
            includeSubZones = true
        )

        val allSnapshots = allCountries.map { country ->
            TimeCalculator.computeSnapshot(
                country = country,
                epochMillis = effectiveMillis,
                anchorCountry = anchorCountry,
                is24Hour = prefs.is24HourFormat,
                isPinned = prefs.pinnedCountryIds.contains(country.id),
                includeSubZones = (country.id == filter.selectedDetailCountryId)
            )
        }

        val daylightCount = allSnapshots.count { it.hour24 in 6..18 }
        val workingCount = allSnapshots.count { it.isBusinessHours }

        val q = filter.searchQuery.trim().lowercase(Locale.ENGLISH)
        val filtered = allSnapshots.filter { snap ->
            val matchesContinent = filter.selectedContinent == Continent.ALL ||
                snap.country.continent == filter.selectedContinent

            val matchesFilterMode = when (filter.selectedFilterMode) {
                FilterMode.ALL -> true
                FilterMode.PINNED -> snap.isPinned
                FilterMode.WORKING_HOURS -> snap.isBusinessHours
                FilterMode.DAYLIGHT_NOW -> snap.hour24 in 6..18
                FilterMode.MULTI_ZONE -> snap.country.subTimeZones.size > 1
                FilterMode.DST_ACTIVE -> snap.isDstActive
            }

            val matchesQuery = if (q.isEmpty()) {
                true
            } else {
                snap.country.countryName.lowercase(Locale.ENGLISH).contains(q) ||
                    snap.country.capitalOrHub.lowercase(Locale.ENGLISH).contains(q) ||
                    snap.country.isoCode.lowercase(Locale.ENGLISH).contains(q) ||
                    snap.utcOffsetString.lowercase(Locale.ENGLISH).contains(q) ||
                    snap.country.callingCode.lowercase(Locale.ENGLISH).contains(q) ||
                    snap.country.continent.displayName.lowercase(Locale.ENGLISH).contains(q)
            }

            matchesContinent && matchesFilterMode && matchesQuery
        }

        val sorted = when (filter.selectedSortOrder) {
            SortOrder.COUNTRY_A_Z -> filtered.sortedBy { it.country.countryName }
            SortOrder.UTC_WEST_TO_EAST -> filtered.sortedWith(
                compareBy<CountryTimeSnapshot> { it.utcOffsetMinutes }
                    .thenBy { it.country.countryName }
            )
            SortOrder.UTC_EAST_TO_WEST -> filtered.sortedWith(
                compareByDescending<CountryTimeSnapshot> { it.utcOffsetMinutes }
                    .thenBy { it.country.countryName }
            )
            SortOrder.CONTINENT -> filtered.sortedWith(
                compareBy<CountryTimeSnapshot> { it.country.continent.ordinal }
                    .thenBy { it.country.countryName }
            )
        }

        val pinnedList = buildList {
            add(anchorSnapshot)
            allSnapshots
                .filter { it.isPinned && it.country.id != anchorCountry.id }
                .sortedBy { it.utcOffsetMinutes }
                .forEach { add(it) }
        }

        val pinnedWorkingOverlap = pinnedList.count { it.isBusinessHours }
        val utcBands = TimeCalculator.groupIntoUtcBands(allSnapshots)
        val detailSnapshot = filter.selectedDetailCountryId?.let { id ->
            allSnapshots.find { it.country.id == id }
        }

        return WorldTimeUiState(
            activeTab = filter.activeTab,
            searchQuery = filter.searchQuery,
            selectedContinent = filter.selectedContinent,
            selectedFilterMode = filter.selectedFilterMode,
            selectedSortOrder = filter.selectedSortOrder,
            is24HourFormat = prefs.is24HourFormat,
            isTimeLocked = prefs.isTimeLocked,
            lockedOffsetMinutes = prefs.lockedOffsetMinutes,
            effectiveEpochMillis = effectiveMillis,
            anchorSnapshot = anchorSnapshot,
            filteredCountries = sorted,
            pinnedSnapshots = pinnedList,
            utcBandGroups = utcBands,
            selectedDetailCountry = detailSnapshot,
            totalWorldCountriesCount = allSnapshots.size,
            daylightCountriesCount = daylightCount,
            workingHoursCountriesCount = workingCount,
            pinnedWorkingOverlapCount = pinnedWorkingOverlap
        )
    }

    fun selectTab(tab: AppTab) {
        filterState = filterState.copy(activeTab = tab)
        refreshState()
    }

    fun updateSearchQuery(query: String) {
        filterState = filterState.copy(searchQuery = query)
        refreshState()
    }

    fun selectContinent(continent: Continent) {
        filterState = filterState.copy(selectedContinent = continent)
        refreshState()
    }

    fun selectFilterMode(mode: FilterMode) {
        filterState = filterState.copy(selectedFilterMode = mode)
        refreshState()
    }

    fun selectSortOrder(order: SortOrder) {
        filterState = filterState.copy(selectedSortOrder = order)
        refreshState()
    }

    fun selectCountryForDetail(countryId: String?) {
        filterState = filterState.copy(selectedDetailCountryId = countryId)
        refreshState()
    }

    fun toggle24HourFormat() {
        val next = !localPrefs.is24HourFormat
        localPrefs = localPrefs.copy(is24HourFormat = next)
        refreshState()
        viewModelScope.launch {
            preferencesRepository?.toggle24HourFormat()
        }
    }

    fun togglePinCountry(countryId: String) {
        val current = localPrefs.pinnedCountryIds
        val updated = if (current.contains(countryId)) current - countryId else current + countryId
        localPrefs = localPrefs.copy(pinnedCountryIds = updated)
        refreshState()
        viewModelScope.launch {
            preferencesRepository?.togglePinCountry(countryId)
        }
    }

    fun setAnchorCountry(countryId: String) {
        localPrefs = localPrefs.copy(anchorCountryId = countryId)
        refreshState()
        viewModelScope.launch {
            preferencesRepository?.setAnchorCountry(countryId)
        }
    }

    fun toggleTimeLock() {
        val currentlyLocked = localPrefs.isTimeLocked
        val nextLocked = !currentlyLocked
        val nextOffset = if (nextLocked) localPrefs.lockedOffsetMinutes else 0
        localPrefs = localPrefs.copy(
            isTimeLocked = nextLocked,
            lockedOffsetMinutes = nextOffset
        )
        refreshState()
        viewModelScope.launch {
            preferencesRepository?.setTimeLockState(nextLocked, nextOffset)
        }
    }

    fun adjustLockedTimeMinutes(deltaMinutes: Int) {
        val nextOffset = (localPrefs.lockedOffsetMinutes + deltaMinutes).coerceIn(-1440, 1440)
        localPrefs = localPrefs.copy(
            isTimeLocked = true,
            lockedOffsetMinutes = nextOffset
        )
        refreshState()
        viewModelScope.launch {
            preferencesRepository?.setTimeLockState(true, nextOffset)
        }
    }

    fun setLockedOffsetMinutes(offsetMinutes: Int) {
        val clamped = offsetMinutes.coerceIn(-1440, 1440)
        localPrefs = localPrefs.copy(
            isTimeLocked = true,
            lockedOffsetMinutes = clamped
        )
        refreshState()
        viewModelScope.launch {
            preferencesRepository?.setTimeLockState(true, clamped)
        }
    }

    fun lockCountryToHourMinute(countryId: String, targetHour24: Int, targetMinute: Int = 0) {
        val targetCountry = allCountries.find { it.id == countryId } ?: return
        val tz = TimeZone.getTimeZone(targetCountry.primaryZoneId)
        val liveCal = Calendar.getInstance(tz, Locale.ENGLISH).apply {
            timeInMillis = liveEpochMillis
        }
        val currentMinuteOfDay = liveCal.get(Calendar.HOUR_OF_DAY) * 60 + liveCal.get(Calendar.MINUTE)
        val desiredMinuteOfDay = targetHour24.coerceIn(0, 23) * 60 + targetMinute.coerceIn(0, 59)
        val deltaMinutes = desiredMinuteOfDay - currentMinuteOfDay
        setLockedOffsetMinutes(deltaMinutes)
    }

    fun resetToLiveTime() {
        localPrefs = localPrefs.copy(
            isTimeLocked = false,
            lockedOffsetMinutes = 0
        )
        refreshState()
        viewModelScope.launch {
            preferencesRepository?.setTimeLockState(false, 0)
        }
    }

    class Factory(private val repository: UserPreferencesRepository) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return WorldTimeViewModel(repository) as T
        }
    }
}
