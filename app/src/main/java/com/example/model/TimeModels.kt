package com.example.model

enum class Continent(val displayName: String, val shortLabel: String) {
    ALL("All World", "All"),
    ASIA("Asia", "Asia"),
    EUROPE("Europe", "Europe"),
    AFRICA("Africa", "Africa"),
    AMERICAS("Americas", "Americas"),
    OCEANIA("Oceania", "Oceania"),
    ANTARCTICA("Antarctica & Islands", "Polar")
}

enum class SolarPhase(val label: String) {
    DAWN("Dawn"),
    DAYLIGHT("Daylight"),
    DUSK("Twilight"),
    NIGHT("Night")
}

enum class SortOrder(val label: String) {
    COUNTRY_A_Z("Country (A–Z)"),
    UTC_WEST_TO_EAST("UTC Offset (West → East)"),
    UTC_EAST_TO_WEST("UTC Offset (East → West)"),
    CONTINENT("By Continent")
}

enum class FilterMode(val label: String) {
    ALL("All Countries"),
    PINNED("Pinned"),
    WORKING_HOURS("Working Hours (09–17)"),
    DAYLIGHT_NOW("Daylight (06–18)"),
    MULTI_ZONE("Multi-Timezone"),
    DST_ACTIVE("DST Active")
}

data class SubTimeZoneInfo(
    val label: String,
    val zoneId: String
)

data class WorldCountry(
    val id: String,
    val countryName: String,
    val isoCode: String,
    val capitalOrHub: String,
    val continent: Continent,
    val primaryZoneId: String,
    val callingCode: String,
    val currencyCode: String,
    val subTimeZones: List<SubTimeZoneInfo> = emptyList()
) {
    val flagEmoji: String
        get() = isoCodeToFlagEmoji(isoCode)
}

data class SubTimeZoneSnapshot(
    val label: String,
    val zoneId: String,
    val formattedTime: String,
    val amPm: String,
    val utcOffsetString: String,
    val hour24: Int,
    val minute: Int
)

data class CountryTimeSnapshot(
    val country: WorldCountry,
    val formattedTime: String,
    val formattedSeconds: String,
    val amPm: String,
    val formattedDate: String,
    val utcOffsetString: String,
    val utcOffsetMinutes: Int,
    val relativeToAnchorString: String,
    val relativeMinutesFromAnchor: Int,
    val dayDeltaFromAnchor: Int,
    val dayDeltaLabel: String,
    val hour24: Int,
    val minute: Int,
    val second: Int,
    val solarPhase: SolarPhase,
    val isBusinessHours: Boolean,
    val isAwakeHours: Boolean,
    val isDstActive: Boolean,
    val isPinned: Boolean,
    val isAnchor: Boolean,
    val subZoneSnapshots: List<SubTimeZoneSnapshot> = emptyList()
)

data class UtcBandGroup(
    val utcOffsetMinutes: Int,
    val utcOffsetLabel: String,
    val representativeTime: String,
    val representativeAmPm: String,
    val solarPhase: SolarPhase,
    val countries: List<CountryTimeSnapshot>
)

fun isoCodeToFlagEmoji(isoCode: String): String {
    val clean = isoCode.trim().uppercase()
    if (clean.length != 2 || !clean[0].isLetter() || !clean[1].isLetter()) {
        return "🌍"
    }
    val firstChar = Character.codePointAt(clean, 0) - 0x41 + 0x1F1E6
    val secondChar = Character.codePointAt(clean, 1) - 0x41 + 0x1F1E6
    return String(Character.toChars(firstChar)) + String(Character.toChars(secondChar))
}
