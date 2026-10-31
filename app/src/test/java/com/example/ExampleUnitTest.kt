package com.example

import com.example.data.TimeCalculator
import com.example.data.WorldCountriesCatalog
import com.example.model.Continent
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class ExampleUnitTest {
    @Test
    fun worldCatalog_containsAllMajorContinentsAndOver200Countries() {
        val countries = WorldCountriesCatalog.allCountries
        assertTrue("Catalog should contain over 200 countries", countries.size >= 200)
        assertTrue(countries.any { it.continent == Continent.ASIA })
        assertTrue(countries.any { it.continent == Continent.EUROPE })
        assertTrue(countries.any { it.continent == Continent.AFRICA })
        assertTrue(countries.any { it.continent == Continent.AMERICAS })
        assertTrue(countries.any { it.continent == Continent.OCEANIA })
    }

    @Test
    fun timeCalculator_computesAccurateSnapshotsAndOffsets() {
        val countries = WorldCountriesCatalog.allCountries
        val uk = countries.first { it.isoCode == "GB" }
        val india = countries.first { it.isoCode == "IN" }
        val epoch = 1735689600000L // Jan 1, 2025 00:00:00 UTC

        val ukSnap = TimeCalculator.computeSnapshot(uk, epoch, uk, is24Hour = true, isPinned = true)
        val indiaSnap = TimeCalculator.computeSnapshot(india, epoch, uk, is24Hour = true, isPinned = true)

        assertEquals("00:00", ukSnap.formattedTime)
        assertEquals("05:30", indiaSnap.formattedTime)
        assertEquals("+5h 30m", indiaSnap.relativeToAnchorString)
    }
}
