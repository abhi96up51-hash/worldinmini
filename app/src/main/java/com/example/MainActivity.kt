package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.LockClock
import androidx.compose.material.icons.filled.Public
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material.icons.outlined.LockClock
import androidx.compose.material.icons.outlined.Public
import androidx.compose.material.icons.outlined.WbSunny
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.data.UserPreferencesRepository
import com.example.ui.AppTab
import com.example.ui.WorldTimeViewModel
import com.example.ui.components.CountryDetailSheet
import com.example.ui.screens.TimeLockMatrixScreen
import com.example.ui.screens.UtcSolarExplorerScreen
import com.example.ui.screens.WorldDirectoryScreen
import com.example.ui.theme.CardBorderSubtle
import com.example.ui.theme.ChronometerGold
import com.example.ui.theme.ChronometerGoldLight
import com.example.ui.theme.DeepObsidianBlue
import com.example.ui.theme.LiveEmerald
import com.example.ui.theme.MeridianCyan
import com.example.ui.theme.MidnightNavy
import com.example.ui.theme.SurfaceElevatedBlue
import com.example.ui.theme.TextPrimaryIvory
import com.example.ui.theme.TextSecondarySilver
import com.example.ui.theme.WorldTimeLockTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WorldTimeLockTheme {
                val context = LocalContext.current
                val repository = remember(context) {
                    UserPreferencesRepository(context.applicationContext)
                }
                val viewModel: WorldTimeViewModel = viewModel(
                    factory = WorldTimeViewModel.Factory(repository)
                )
                WorldTimeLockApp(viewModel = viewModel)
            }
        }
    }
}

@Composable
fun WorldTimeLockApp(
    viewModel: WorldTimeViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    // BackHandler for secondary tabs or detail modal
    BackHandler(enabled = uiState.selectedDetailCountry != null || uiState.activeTab != AppTab.WORLD_DIRECTORY) {
        if (uiState.selectedDetailCountry != null) {
            viewModel.selectCountryForDetail(null)
        } else {
            viewModel.selectTab(AppTab.WORLD_DIRECTORY)
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = MidnightNavy,
        contentWindowInsets = WindowInsets.safeDrawing,
        topBar = {
            WorldTimeTopBar(
                totalCountries = uiState.totalWorldCountriesCount,
                isTimeLocked = uiState.isTimeLocked
            )
        },
        bottomBar = {
            NavigationBar(
                containerColor = DeepObsidianBlue,
                tonalElevation = 8.dp,
                modifier = Modifier.testTag("bottom_navigation_bar")
            ) {
                AppTab.entries.forEach { tab ->
                    val selected = uiState.activeTab == tab
                    val icon = when (tab) {
                        AppTab.WORLD_DIRECTORY -> if (selected) Icons.Filled.Public else Icons.Outlined.Public
                        AppTab.TIME_LOCK_MATRIX -> if (selected) Icons.Filled.LockClock else Icons.Outlined.LockClock
                        AppTab.UTC_SOLAR_EXPLORER -> if (selected) Icons.Filled.WbSunny else Icons.Outlined.WbSunny
                    }
                    NavigationBarItem(
                        selected = selected,
                        onClick = { viewModel.selectTab(tab) },
                        icon = {
                            Icon(
                                imageVector = icon,
                                contentDescription = tab.title
                            )
                        },
                        label = {
                            Text(
                                text = tab.title,
                                style = MaterialTheme.typography.labelMedium
                            )
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = ChronometerGoldLight,
                            selectedTextColor = ChronometerGoldLight,
                            indicatorColor = SurfaceElevatedBlue,
                            unselectedIconColor = TextSecondarySilver,
                            unselectedTextColor = TextSecondarySilver
                        ),
                        modifier = Modifier.testTag("nav_tab_${tab.name}")
                    )
                }
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (uiState.activeTab) {
                AppTab.WORLD_DIRECTORY -> {
                    WorldDirectoryScreen(
                        uiState = uiState,
                        onSearchQueryChange = viewModel::updateSearchQuery,
                        onSelectContinent = viewModel::selectContinent,
                        onSelectFilterMode = viewModel::selectFilterMode,
                        onSelectSortOrder = viewModel::selectSortOrder,
                        onToggleTimeLock = viewModel::toggleTimeLock,
                        onAdjustMinutes = viewModel::adjustLockedTimeMinutes,
                        onSetOffsetMinutes = viewModel::setLockedOffsetMinutes,
                        onResetLive = viewModel::resetToLiveTime,
                        onToggle24HourFormat = viewModel::toggle24HourFormat,
                        onTogglePinCountry = viewModel::togglePinCountry,
                        onSetAnchorCountry = viewModel::setAnchorCountry,
                        onSelectCountryDetail = viewModel::selectCountryForDetail
                    )
                }

                AppTab.TIME_LOCK_MATRIX -> {
                    TimeLockMatrixScreen(
                        uiState = uiState,
                        onToggleTimeLock = viewModel::toggleTimeLock,
                        onAdjustMinutes = viewModel::adjustLockedTimeMinutes,
                        onSetOffsetMinutes = viewModel::setLockedOffsetMinutes,
                        onResetLive = viewModel::resetToLiveTime,
                        onToggle24HourFormat = viewModel::toggle24HourFormat,
                        onLockCountryHour = viewModel::lockCountryToHourMinute,
                        onSetAnchorCountry = viewModel::setAnchorCountry,
                        onTogglePinCountry = viewModel::togglePinCountry,
                        onSelectCountryDetail = viewModel::selectCountryForDetail
                    )
                }

                AppTab.UTC_SOLAR_EXPLORER -> {
                    UtcSolarExplorerScreen(
                        uiState = uiState,
                        onSelectCountryDetail = viewModel::selectCountryForDetail
                    )
                }
            }

            // Country Horological Detail Bottom Sheet
            val detailCountry = uiState.selectedDetailCountry
            if (detailCountry != null) {
                CountryDetailSheet(
                    snapshot = detailCountry,
                    isTimeLocked = uiState.isTimeLocked,
                    is24HourFormat = uiState.is24HourFormat,
                    onDismiss = { viewModel.selectCountryForDetail(null) },
                    onTogglePin = viewModel::togglePinCountry,
                    onSetAnchor = viewModel::setAnchorCountry,
                    onLockToHour = { id, hour ->
                        viewModel.lockCountryToHourMinute(id, hour)
                    }
                )
            }
        }
    }
}

@Composable
private fun WorldTimeTopBar(
    totalCountries: Int,
    isTimeLocked: Boolean
) {
    Surface(
        color = MidnightNavy,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()
                .padding(horizontal = 16.dp, vertical = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(DeepObsidianBlue)
                        .border(
                            1.dp,
                            if (isTimeLocked) ChronometerGold else MeridianCyan,
                            RoundedCornerShape(10.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Language,
                        contentDescription = null,
                        tint = if (isTimeLocked) ChronometerGoldLight else MeridianCyan,
                        modifier = Modifier.size(20.dp)
                    )
                }
                Spacer(modifier = Modifier.width(10.dp))
                Column {
                    Text(
                        text = "World Time Lock",
                        style = MaterialTheme.typography.titleLarge,
                        color = TextPrimaryIvory,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Global Horology • All $totalCountries Countries & Territories",
                        style = MaterialTheme.typography.labelSmall,
                        color = TextSecondarySilver
                    )
                }
            }

            Row(
                modifier = Modifier
                    .clip(CircleShape)
                    .background(
                        if (isTimeLocked) ChronometerGold.copy(alpha = 0.16f)
                        else LiveEmerald.copy(alpha = 0.16f)
                    )
                    .border(
                        0.8.dp,
                        if (isTimeLocked) ChronometerGold else LiveEmerald,
                        CircleShape
                    )
                    .padding(horizontal = 10.dp, vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(7.dp)
                        .clip(CircleShape)
                        .background(if (isTimeLocked) ChronometerGoldLight else LiveEmerald)
                )
                Text(
                    text = if (isTimeLocked) "LOCKED" else "LIVE",
                    style = MaterialTheme.typography.labelSmall,
                    color = if (isTimeLocked) ChronometerGoldLight else LiveEmerald,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}
