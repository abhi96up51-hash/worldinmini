package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Anchor
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.LockClock
import androidx.compose.material.icons.filled.WorkOutline
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.model.CountryTimeSnapshot
import com.example.ui.WorldTimeUiState
import com.example.ui.components.SolarPhaseBadge
import com.example.ui.components.SolarTimelineBar24h
import com.example.ui.components.TimeLockCommandHeader
import com.example.ui.theme.CardBorderSubtle
import com.example.ui.theme.ChronometerGold
import com.example.ui.theme.ChronometerGoldLight
import com.example.ui.theme.DeepObsidianBlue
import com.example.ui.theme.LiveEmerald
import com.example.ui.theme.MeridianCyan
import com.example.ui.theme.MidnightNavy
import com.example.ui.theme.SurfaceElevatedBlue
import com.example.ui.theme.SurfaceSlateBlue
import com.example.ui.theme.TextPrimaryIvory
import com.example.ui.theme.TextSecondarySilver

@Composable
fun TimeLockMatrixScreen(
    uiState: WorldTimeUiState,
    onToggleTimeLock: () -> Unit,
    onAdjustMinutes: (Int) -> Unit,
    onSetOffsetMinutes: (Int) -> Unit,
    onResetLive: () -> Unit,
    onToggle24HourFormat: () -> Unit,
    onLockCountryHour: (String, Int) -> Unit,
    onSetAnchorCountry: (String) -> Unit,
    onTogglePinCountry: (String) -> Unit,
    onSelectCountryDetail: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val anchorId = uiState.anchorSnapshot?.country?.id ?: "GB"

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .testTag("time_lock_matrix_list"),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item(key = "matrix_header") {
            TimeLockCommandHeader(
                anchorSnapshot = uiState.anchorSnapshot,
                isTimeLocked = uiState.isTimeLocked,
                lockedOffsetMinutes = uiState.lockedOffsetMinutes,
                is24HourFormat = uiState.is24HourFormat,
                totalWorldCountriesCount = uiState.totalWorldCountriesCount,
                daylightCountriesCount = uiState.daylightCountriesCount,
                workingHoursCountriesCount = uiState.workingHoursCountriesCount,
                onToggleTimeLock = onToggleTimeLock,
                onAdjustMinutes = onAdjustMinutes,
                onSetOffsetMinutes = onSetOffsetMinutes,
                onResetLive = onResetLive,
                onToggle24HourFormat = onToggle24HourFormat,
                onAnchorClicked = {
                    uiState.anchorSnapshot?.country?.id?.let(onSelectCountryDetail)
                }
            )
        }

        // Global Overlap & Quick Anchor Hour Lock Presets
        item(key = "overlap_planner_card") {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(18.dp),
                color = DeepObsidianBlue,
                border = BorderStroke(1.dp, CardBorderSubtle)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.WorkOutline,
                                contentDescription = null,
                                tint = LiveEmerald,
                                modifier = Modifier.size(18.dp)
                            )
                            Column {
                                Text(
                                    text = "TIME LOCK OVERLAP MATRIX",
                                    style = MaterialTheme.typography.labelMedium,
                                    color = ChronometerGoldLight
                                )
                                Text(
                                    text = "${uiState.pinnedWorkingOverlapCount} of ${uiState.pinnedSnapshots.size} pinned countries in Working Hours (09:00–17:00)",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = TextSecondarySilver
                                )
                            }
                        }

                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .background(LiveEmerald.copy(alpha = 0.16f))
                                .padding(horizontal = 8.dp, vertical = 4.dp)
                        ) {
                            Text(
                                text = "${uiState.pinnedWorkingOverlapCount}/${uiState.pinnedSnapshots.size} Work",
                                style = MaterialTheme.typography.labelMedium,
                                color = LiveEmerald,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = "Quick-Lock Anchor (${uiState.anchorSnapshot?.country?.countryName ?: "Anchor"}) to Specific Hour:",
                        style = MaterialTheme.typography.labelSmall,
                        color = TextSecondarySilver
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .horizontalScroll(rememberScrollState()),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        val quickHours = listOf(
                            "08:00 Morning" to 8,
                            "09:00 Work Start" to 9,
                            "12:00 Noon" to 12,
                            "14:00 Global Sync" to 14,
                            "17:00 Work End" to 17,
                            "20:00 Evening" to 20,
                            "00:00 Midnight" to 0
                        )
                        quickHours.forEach { (label, hour) ->
                            val isSelected = uiState.isTimeLocked && uiState.anchorSnapshot?.hour24 == hour
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(
                                        if (isSelected) ChronometerGold.copy(alpha = 0.25f)
                                        else SurfaceSlateBlue
                                    )
                                    .border(
                                        1.dp,
                                        if (isSelected) ChronometerGold else CardBorderSubtle,
                                        RoundedCornerShape(10.dp)
                                    )
                                    .clickable { onLockCountryHour(anchorId, hour) }
                                    .padding(horizontal = 12.dp, vertical = 7.dp)
                                    .testTag("quick_lock_hour_$hour")
                            ) {
                                Text(
                                    text = label,
                                    style = MaterialTheme.typography.labelSmall,
                                    color = if (isSelected) ChronometerGoldLight else TextPrimaryIvory,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                                )
                            }
                        }
                    }
                }
            }
        }

        // Pinned Countries Interactive 24-Hour Matrix Cards
        items(
            items = uiState.pinnedSnapshots,
            key = { "matrix_${it.country.id}" }
        ) { snapshot ->
            PinnedMatrixCountryCard(
                snapshot = snapshot,
                isTimeLocked = uiState.isTimeLocked,
                is24HourFormat = uiState.is24HourFormat,
                onSelectHour = { hour -> onLockCountryHour(snapshot.country.id, hour) },
                onSetAnchor = { onSetAnchorCountry(snapshot.country.id) },
                onUnpin = { onTogglePinCountry(snapshot.country.id) },
                onCardClick = { onSelectCountryDetail(snapshot.country.id) }
            )
        }
    }
}

@Composable
private fun PinnedMatrixCountryCard(
    snapshot: CountryTimeSnapshot,
    isTimeLocked: Boolean,
    is24HourFormat: Boolean,
    onSelectHour: (Int) -> Unit,
    onSetAnchor: () -> Unit,
    onUnpin: () -> Unit,
    onCardClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onCardClick() }
            .testTag("matrix_card_${snapshot.country.id}"),
        shape = RoundedCornerShape(18.dp),
        color = DeepObsidianBlue,
        border = BorderStroke(
            width = if (snapshot.isAnchor) 1.5.dp else 1.dp,
            color = if (snapshot.isAnchor) ChronometerGold else CardBorderSubtle
        )
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = snapshot.country.flagEmoji,
                        style = MaterialTheme.typography.headlineMedium
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Text(
                                text = snapshot.country.countryName,
                                style = MaterialTheme.typography.titleMedium,
                                color = TextPrimaryIvory,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                            if (snapshot.isAnchor) {
                                Text(
                                    text = "ANCHOR",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MidnightNavy,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(4.dp))
                                        .background(ChronometerGoldLight)
                                        .padding(horizontal = 5.dp, vertical = 1.dp)
                                )
                            } else {
                                Text(
                                    text = snapshot.relativeToAnchorString,
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MeridianCyan,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                        Text(
                            text = "${snapshot.country.capitalOrHub} • ${snapshot.utcOffsetString} • ${snapshot.dayDeltaLabel}",
                            style = MaterialTheme.typography.bodySmall,
                            color = TextSecondarySilver,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Column(horizontalAlignment = Alignment.End) {
                        Row(verticalAlignment = Alignment.Bottom) {
                            Text(
                                text = snapshot.formattedTime,
                                style = MaterialTheme.typography.headlineMedium,
                                color = if (isTimeLocked) ChronometerGoldLight else TextPrimaryIvory
                            )
                            if (!is24HourFormat) {
                                Text(
                                    text = snapshot.amPm,
                                    style = MaterialTheme.typography.labelSmall,
                                    color = ChronometerGoldLight,
                                    modifier = Modifier.padding(bottom = 2.dp, start = 2.dp)
                                )
                            }
                        }
                        SolarPhaseBadge(
                            solarPhase = snapshot.solarPhase,
                            isBusinessHours = snapshot.isBusinessHours
                        )
                    }

                    if (!snapshot.isAnchor) {
                        IconButton(
                            onClick = onSetAnchor,
                            modifier = Modifier.size(32.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Anchor,
                                contentDescription = "Set as Anchor",
                                tint = TextSecondarySilver,
                                modifier = Modifier.size(16.dp)
                            )
                        }
                        IconButton(
                            onClick = onUnpin,
                            modifier = Modifier.size(32.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "Unpin country",
                                tint = TextSecondarySilver,
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Interactive 24-hour timeline bar: tapping any hour segment locks that country to that hour!
            SolarTimelineBar24h(
                currentHour24 = snapshot.hour24,
                currentMinute = snapshot.minute,
                isTimeLocked = isTimeLocked,
                onHourSelected = onSelectHour
            )
        }
    }
}
