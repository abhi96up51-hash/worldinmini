package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.model.Continent
import com.example.ui.WorldTimeViewModel
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class ExampleRobolectricTest {

    @Test
    fun `read string from context`() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val appName = context.getString(R.string.app_name)
        assertEquals("World Time Lock", appName)
    }

    @Test
    fun `viewModel supports time lock and country filtering`() {
        val vm = WorldTimeViewModel(preferencesRepository = null)
        assertFalse(vm.uiState.value.isTimeLocked)
        assertTrue(vm.uiState.value.totalWorldCountriesCount >= 200)

        vm.adjustLockedTimeMinutes(120)
        assertTrue(vm.uiState.value.isTimeLocked)
        assertEquals(120, vm.uiState.value.lockedOffsetMinutes)

        vm.selectContinent(Continent.ASIA)
        assertTrue(vm.uiState.value.filteredCountries.all { it.country.continent == Continent.ASIA })

        vm.resetToLiveTime()
        assertFalse(vm.uiState.value.isTimeLocked)
        assertEquals(0, vm.uiState.value.lockedOffsetMinutes)
    }
}
