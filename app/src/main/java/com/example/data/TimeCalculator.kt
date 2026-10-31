package com.example.data

import com.example.model.CountryTimeSnapshot
import com.example.model.SolarPhase
import com.example.model.SubTimeZoneSnapshot
import com.example.model.UtcBandGroup
import com.example.model.WorldCountry
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.TimeZone
import kotlin.math.abs

object TimeCalculator {

    fun computeSnapshot(
        country: WorldCountry,
        epochMillis: Long,
        anchorCountry: WorldCountry,
        is24Hour: Boolean,
        isPinned: Boolean,
        includeSubZones: Boolean = false
    ): CountryTimeSnapshot {
        val tz = TimeZone.getTimeZone(country.primaryZoneId)
        val anchorTz = TimeZone.getTimeZone(anchorCountry.primaryZoneId)

        val cal = Calendar.getInstance(tz, Locale.ENGLISH).apply {
            timeInMillis = epochMillis
        }
        val anchorCal = Calendar.getInstance(anchorTz, Locale.ENGLISH).apply {
            timeInMillis = epochMillis
        }

        val hour24 = cal.get(Calendar.HOUR_OF_DAY)
        val minute = cal.get(Calendar.MINUTE)
        val second = cal.get(Calendar.SECOND)

        val formattedTime = if (is24Hour) {
            String.format(Locale.ENGLISH, "%02d:%02d", hour24, minute)
        } else {
            val h12 = when {
                hour24 == 0 -> 12
                hour24 > 12 -> hour24 - 12
                else -> hour24
            }
            String.format(Locale.ENGLISH, "%02d:%02d", h12, minute)
        }

        val amPm = if (hour24 < 12) "AM" else "PM"
        val formattedSeconds = String.format(Locale.ENGLISH, ":%02d", second)

        val dateFormat = SimpleDateFormat("EEE, MMM d", Locale.ENGLISH).apply {
            timeZone = tz
        }
        val formattedDate = dateFormat.format(Date(epochMillis))

        val offsetMillis = tz.getOffset(epochMillis)
        val utcOffsetMinutes = offsetMillis / 60000
        val utcOffsetString = formatUtcOffset(utcOffsetMinutes)

        val anchorOffsetMinutes = anchorTz.getOffset(epochMillis) / 60000
        val relativeMinutes = utcOffsetMinutes - anchorOffsetMinutes
        val isAnchor = country.id == anchorCountry.id
        val relativeToAnchorString = when {
            isAnchor -> "ANCHOR"
            relativeMinutes == 0 -> "Same time"
            else -> formatRelativeMinutes(relativeMinutes)
        }

        val dayDelta = computeDayDelta(cal, anchorCal)
        val dayDeltaLabel = when {
            dayDelta < 0 -> "Yesterday"
            dayDelta > 0 -> "Tomorrow"
            else -> "Today"
        }

        val solarPhase = computeSolarPhase(hour24)
        val isBusinessHours = hour24 in 9..17
        val isAwakeHours = hour24 in 7..22
        val isDstActive = tz.inDaylightTime(Date(epochMillis))

        val subSnapshots = if (includeSubZones && country.subTimeZones.isNotEmpty()) {
            country.subTimeZones.map { sub ->
                val subTz = TimeZone.getTimeZone(sub.zoneId)
                val subCal = Calendar.getInstance(subTz, Locale.ENGLISH).apply {
                    timeInMillis = epochMillis
                }
                val sh = subCal.get(Calendar.HOUR_OF_DAY)
                val sm = subCal.get(Calendar.MINUTE)
                val sTime = if (is24Hour) {
                    String.format(Locale.ENGLISH, "%02d:%02d", sh, sm)
                } else {
                    val h12 = if (sh == 0) 12 else if (sh > 12) sh - 12 else sh
                    String.format(Locale.ENGLISH, "%02d:%02d", h12, sm)
                }
                val sAmPm = if (sh < 12) "AM" else "PM"
                val sOffsetMin = subTz.getOffset(epochMillis) / 60000
                SubTimeZoneSnapshot(
                    label = sub.label,
                    zoneId = sub.zoneId,
                    formattedTime = sTime,
                    amPm = sAmPm,
                    utcOffsetString = formatUtcOffset(sOffsetMin),
                    hour24 = sh,
                    minute = sm
                )
            }
        } else {
            emptyList()
        }

        return CountryTimeSnapshot(
            country = country,
            formattedTime = formattedTime,
            formattedSeconds = formattedSeconds,
            amPm = amPm,
            formattedDate = formattedDate,
            utcOffsetString = utcOffsetString,
            utcOffsetMinutes = utcOffsetMinutes,
            relativeToAnchorString = relativeToAnchorString,
            relativeMinutesFromAnchor = relativeMinutes,
            dayDeltaFromAnchor = dayDelta,
            dayDeltaLabel = dayDeltaLabel,
            hour24 = hour24,
            minute = minute,
            second = second,
            solarPhase = solarPhase,
            isBusinessHours = isBusinessHours,
            isAwakeHours = isAwakeHours,
            isDstActive = isDstActive,
            isPinned = isPinned,
            isAnchor = isAnchor,
            subZoneSnapshots = subSnapshots
        )
    }

    fun groupIntoUtcBands(snapshots: List<CountryTimeSnapshot>): List<UtcBandGroup> {
        return snapshots
            .groupBy { it.utcOffsetMinutes }
            .entries
            .sortedBy { it.key }
            .map { (offsetMin, list) ->
                val first = list.first()
                UtcBandGroup(
                    utcOffsetMinutes = offsetMin,
                    utcOffsetLabel = first.utcOffsetString,
                    representativeTime = first.formattedTime,
                    representativeAmPm = first.amPm,
                    solarPhase = first.solarPhase,
                    countries = list.sortedBy { it.country.countryName }
                )
            }
    }

    fun formatUtcOffset(totalMinutes: Int): String {
        if (totalMinutes == 0) return "UTC±00:00"
        val sign = if (totalMinutes >= 0) "+" else "-"
        val absMin = abs(totalMinutes)
        val hours = absMin / 60
        val mins = absMin % 60
        return String.format(Locale.ENGLISH, "UTC%s%02d:%02d", sign, hours, mins)
    }

    fun formatRelativeMinutes(relativeMinutes: Int): String {
        if (relativeMinutes == 0) return "0h"
        val sign = if (relativeMinutes > 0) "+" else "-"
        val absMin = abs(relativeMinutes)
        val hours = absMin / 60
        val mins = absMin % 60
        return if (mins == 0) {
            "$sign${hours}h"
        } else {
            "$sign${hours}h ${mins}m"
        }
    }

    fun computeSolarPhase(hour24: Int): SolarPhase {
        return when (hour24) {
            in 5..7 -> SolarPhase.DAWN
            in 8..17 -> SolarPhase.DAYLIGHT
            in 18..20 -> SolarPhase.DUSK
            else -> SolarPhase.NIGHT
        }
    }

    private fun computeDayDelta(targetCal: Calendar, anchorCal: Calendar): Int {
        val targetYear = targetCal.get(Calendar.YEAR)
        val anchorYear = anchorCal.get(Calendar.YEAR)
        if (targetYear != anchorYear) {
            return if (targetYear > anchorYear) 1 else -1
        }
        val targetDay = targetCal.get(Calendar.DAY_OF_YEAR)
        val anchorDay = anchorCal.get(Calendar.DAY_OF_YEAR)
        return (targetDay - anchorDay).coerceIn(-1, 1)
    }
}
