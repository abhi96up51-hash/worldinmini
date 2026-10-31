package com.example.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.LockOpen
import androidx.compose.material.icons.filled.Public
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.R
import com.example.data.TimeCalculator
import com.example.model.CountryTimeSnapshot
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
import kotlin.math.roundToInt

@Composable
fun TimeLockCommandHeader(
    anchorSnapshot: CountryTimeSnapshot?,
    isTimeLocked: Boolean,
    lockedOffsetMinutes: Int,
    is24HourFormat: Boolean,
    totalWorldCountriesCount: Int,
    daylightCountriesCount: Int,
    workingHoursCountriesCount: Int,
    onToggleTimeLock: () -> Unit,
    onAdjustMinutes: (Int) -> Unit,
    onSetOffsetMinutes: (Int) -> Unit,
    onResetLive: () -> Unit,
    onToggle24HourFormat: () -> Unit,
    onAnchorClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    val borderColor by animateColorAsState(
        targetValue = if (isTimeLocked) ChronometerGold else CardBorderSubtle,
        label = "header_border"
    )

    Surface(
        modifier = modifier
            .fillMaxWidth()
            .testTag("time_lock_command_header"),
        shape = RoundedCornerShape(22.dp),
        color = DeepObsidianBlue,
        border = BorderStroke(1.5.dp, borderColor),
        tonalElevation = 6.dp
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            // Generated Hero Art Background with rich dark gradient scrim
            Image(
                painter = painterResource(id = R.drawable.img_world_horology_banner),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                alpha = 0.28f,
                modifier = Modifier
                    .matchParentSize()
                    .clip(RoundedCornerShape(22.dp))
            )

            Box(
                modifier = Modifier
                    .matchParentSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                MidnightNavy.copy(alpha = 0.68f),
                                DeepObsidianBlue.copy(alpha = 0.92f),
                                DeepObsidianBlue.copy(alpha = 0.98f)
                            )
                        )
                    )
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                // Top Status Bar: Mode Badge + 12H/24H Toggle + Lock/Unlock Button
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Live vs Time Locked Status Pill
                    Row(
                        modifier = Modifier
                            .clip(CircleShape)
                            .background(
                                if (isTimeLocked) ChronometerGold.copy(alpha = 0.20f)
                                else LiveEmerald.copy(alpha = 0.18f)
                            )
                            .border(
                                1.dp,
                                if (isTimeLocked) ChronometerGold else LiveEmerald,
                                CircleShape
                            )
                            .clickable { onToggleTimeLock() }
                            .padding(horizontal = 12.dp, vertical = 6.dp)
                            .testTag("time_lock_status_pill"),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .clip(CircleShape)
                                .background(if (isTimeLocked) ChronometerGoldLight else LiveEmerald)
                        )
                        Text(
                            text = if (isTimeLocked) {
                                val deltaText = TimeCalculator.formatRelativeMinutes(lockedOffsetMinutes)
                                "TIME LOCKED ($deltaText)"
                            } else {
                                "LIVE WORLD CLOCK"
                            },
                            style = MaterialTheme.typography.labelMedium,
                            color = if (isTimeLocked) ChronometerGoldLight else LiveEmerald,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        // 12H / 24H Format Chip
                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            color = SurfaceSlateBlue,
                            border = BorderStroke(1.dp, CardBorderSubtle),
                            modifier = Modifier
                                .clickable { onToggle24HourFormat() }
                                .testTag("toggle_24h_button")
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Schedule,
                                    contentDescription = "Toggle 12H or 24H format",
                                    tint = MeridianCyan,
                                    modifier = Modifier.size(14.dp)
                                )
                                Text(
                                    text = if (is24HourFormat) "24H" else "12H",
                                    style = MaterialTheme.typography.labelMedium,
                                    color = TextPrimaryIvory
                                )
                            }
                        }

                        // Primary Time Lock Toggle Button
                        Button(
                            onClick = onToggleTimeLock,
                            shape = RoundedCornerShape(10.dp),
                            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (isTimeLocked) ChronometerGold else SurfaceElevatedBlue,
                                contentColor = if (isTimeLocked) MidnightNavy else TextPrimaryIvory
                            ),
                            modifier = Modifier
                                .height(34.dp)
                                .testTag("toggle_time_lock_button")
                        ) {
                            Icon(
                                imageVector = if (isTimeLocked) Icons.Default.Lock else Icons.Default.LockOpen,
                                contentDescription = if (isTimeLocked) "Unlock Time" else "Lock Time",
                                modifier = Modifier.size(15.dp)
                            )
                            Spacer(modifier = Modifier.width(5.dp))
                            Text(
                                text = if (isTimeLocked) "Locked" else "Lock Time",
                                style = MaterialTheme.typography.labelMedium,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Anchor Country Main Chronometer Display
                if (anchorSnapshot != null) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(16.dp))
                            .background(SurfaceSlateBlue.copy(alpha = 0.72f))
                            .border(1.dp, CardBorderSubtle, RoundedCornerShape(16.dp))
                            .clickable { onAnchorClicked() }
                            .padding(12.dp)
                            .testTag("anchor_country_card"),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.weight(1f)
                        ) {
                            AnalogChronometerDial(
                                hour24 = anchorSnapshot.hour24,
                                minute = anchorSnapshot.minute,
                                second = anchorSnapshot.second,
                                isTimeLocked = isTimeLocked,
                                dialSize = 68.dp
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                                ) {
                                    Text(
                                        text = "ANCHOR REFERENCE",
                                        style = MaterialTheme.typography.labelSmall,
                                        color = ChronometerGoldLight
                                    )
                                    Text(
                                        text = "• ${anchorSnapshot.utcOffsetString}",
                                        style = MaterialTheme.typography.labelSmall,
                                        color = MeridianCyan
                                    )
                                }
                                Spacer(modifier = Modifier.height(2.dp))
                                Text(
                                    text = "${anchorSnapshot.country.flagEmoji} ${anchorSnapshot.country.countryName}",
                                    style = MaterialTheme.typography.titleMedium,
                                    color = TextPrimaryIvory,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                                Text(
                                    text = "${anchorSnapshot.country.capitalOrHub} • ${anchorSnapshot.formattedDate}",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = TextSecondarySilver,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }
                        }

                        Column(horizontalAlignment = Alignment.End) {
                            Row(verticalAlignment = Alignment.Bottom) {
                                Text(
                                    text = anchorSnapshot.formattedTime,
                                    style = MaterialTheme.typography.displaySmall,
                                    color = if (isTimeLocked) ChronometerGoldLight else TextPrimaryIvory
                                )
                                Text(
                                    text = if (isTimeLocked) ":00" else anchorSnapshot.formattedSeconds,
                                    style = MaterialTheme.typography.labelLarge,
                                    color = if (isTimeLocked) ChronometerGold else MeridianCyan,
                                    modifier = Modifier.padding(bottom = 3.dp, start = 2.dp)
                                )
                            }
                            if (!is24HourFormat) {
                                Text(
                                    text = anchorSnapshot.amPm,
                                    style = MaterialTheme.typography.labelMedium,
                                    color = ChronometerGoldLight
                                )
                            } else {
                                SolarPhaseBadge(
                                    solarPhase = anchorSnapshot.solarPhase,
                                    isBusinessHours = anchorSnapshot.isBusinessHours
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Interactive Time Lock Scrubber & Quick Step Controls
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(14.dp))
                        .background(MidnightNavy.copy(alpha = 0.78f))
                        .border(1.dp, CardBorderSubtle.copy(alpha = 0.8f), RoundedCornerShape(14.dp))
                        .padding(horizontal = 12.dp, vertical = 10.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Public,
                                contentDescription = null,
                                tint = if (isTimeLocked) ChronometerGoldLight else MeridianCyan,
                                modifier = Modifier.size(15.dp)
                            )
                            Text(
                                text = if (isTimeLocked) {
                                    "Global Time Lock Scrubber (All $totalWorldCountriesCount Countries Locked)"
                                } else {
                                    "Slide or step to lock time across all $totalWorldCountriesCount countries"
                                },
                                style = MaterialTheme.typography.labelSmall,
                                color = TextSecondarySilver
                            )
                        }

                        if (isTimeLocked || lockedOffsetMinutes != 0) {
                            Row(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(6.dp))
                                    .background(LiveEmerald.copy(alpha = 0.16f))
                                    .clickable { onResetLive() }
                                    .padding(horizontal = 8.dp, vertical = 3.dp)
                                    .testTag("reset_live_button"),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Refresh,
                                    contentDescription = "Reset to Live Time",
                                    tint = LiveEmerald,
                                    modifier = Modifier.size(12.dp)
                                )
                                Text(
                                    text = "LIVE NOW",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = LiveEmerald,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }

                    Slider(
                        value = lockedOffsetMinutes.toFloat(),
                        onValueChange = { newVal ->
                            // Snap to 15-minute increments for clean horological comparison
                            val snapped = ((newVal / 15f).roundToInt() * 15).coerceIn(-720, 720)
                            onSetOffsetMinutes(snapped)
                        },
                        valueRange = -720f..720f,
                        colors = SliderDefaults.colors(
                            thumbColor = ChronometerGoldLight,
                            activeTrackColor = ChronometerGold,
                            inactiveTrackColor = SurfaceElevatedBlue
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(28.dp)
                            .testTag("time_lock_slider")
                    )

                    // Quick Time Lock Stepper Buttons
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        val steps = listOf(
                            "-3h" to -180,
                            "-1h" to -60,
                            "-15m" to -15,
                            "+15m" to 15,
                            "+1h" to 60,
                            "+3h" to 180
                        )
                        steps.forEach { (label, delta) ->
                            FilledTonalButton(
                                onClick = { onAdjustMinutes(delta) },
                                shape = RoundedCornerShape(8.dp),
                                contentPadding = PaddingValues(horizontal = 4.dp, vertical = 2.dp),
                                colors = ButtonDefaults.filledTonalButtonColors(
                                    containerColor = SurfaceElevatedBlue,
                                    contentColor = TextPrimaryIvory
                                ),
                                modifier = Modifier
                                    .weight(1f)
                                    .height(30.dp)
                                    .testTag("step_button_$label")
                            ) {
                                Text(
                                    text = label,
                                    style = MaterialTheme.typography.labelSmall
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Global Live Telemetry Summary Strip
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    TelemetryMiniPill(
                        label = "Countries",
                        value = "$totalWorldCountriesCount",
                        accentColor = MeridianCyan,
                        modifier = Modifier.weight(1f)
                    )
                    TelemetryMiniPill(
                        label = "In Daylight",
                        value = "$daylightCountriesCount",
                        accentColor = ChronometerGoldLight,
                        modifier = Modifier.weight(1f)
                    )
                    TelemetryMiniPill(
                        label = "Work Hours",
                        value = "$workingHoursCountriesCount",
                        accentColor = LiveEmerald,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }
}

@Composable
private fun TelemetryMiniPill(
    label: String,
    value: String,
    accentColor: Color,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(10.dp))
            .background(SurfaceSlateBlue.copy(alpha = 0.7f))
            .border(0.8.dp, CardBorderSubtle, RoundedCornerShape(10.dp))
            .padding(horizontal = 10.dp, vertical = 6.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = TextSecondarySilver,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Text(
            text = value,
            style = MaterialTheme.typography.labelLarge,
            color = accentColor,
            fontWeight = FontWeight.Bold
        )
    }
}
