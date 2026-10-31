package com.example.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Anchor
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.LockClock
import androidx.compose.material.icons.filled.PushPin
import androidx.compose.material.icons.outlined.PushPin
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CountryDetailSheet(
    snapshot: CountryTimeSnapshot,
    isTimeLocked: Boolean,
    is24HourFormat: Boolean,
    onDismiss: () -> Unit,
    onTogglePin: (String) -> Unit,
    onSetAnchor: (String) -> Unit,
    onLockToHour: (String, Int) -> Unit
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = DeepObsidianBlue,
        contentColor = TextPrimaryIvory,
        modifier = Modifier.testTag("country_detail_sheet")
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 8.dp)
        ) {
            // Header Row: Flag + Country Name + Close Button
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
                        style = MaterialTheme.typography.displayMedium
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(
                            text = snapshot.country.countryName,
                            style = MaterialTheme.typography.headlineMedium,
                            color = TextPrimaryIvory
                        )
                        Text(
                            text = "${snapshot.country.capitalOrHub} • ${snapshot.country.continent.displayName}",
                            style = MaterialTheme.typography.bodyMedium,
                            color = TextSecondarySilver
                        )
                    }
                }

                IconButton(onClick = onDismiss) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close details",
                        tint = TextSecondarySilver
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Main Horology Readout Card (Analog Chronometer + Tabular Time)
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(18.dp),
                color = SurfaceSlateBlue,
                border = BorderStroke(1.dp, CardBorderSubtle)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    AnalogChronometerDial(
                        hour24 = snapshot.hour24,
                        minute = snapshot.minute,
                        second = snapshot.second,
                        isTimeLocked = isTimeLocked,
                        dialSize = 92.dp
                    )

                    Column(horizontalAlignment = Alignment.End) {
                        Row(verticalAlignment = Alignment.Bottom) {
                            Text(
                                text = snapshot.formattedTime,
                                style = MaterialTheme.typography.displayLarge,
                                color = if (isTimeLocked) ChronometerGoldLight else TextPrimaryIvory
                            )
                            Text(
                                text = if (isTimeLocked) ":00" else snapshot.formattedSeconds,
                                style = MaterialTheme.typography.titleMedium,
                                color = MeridianCyan,
                                modifier = Modifier.padding(bottom = 6.dp, start = 3.dp)
                            )
                        }
                        if (!is24HourFormat) {
                            Text(
                                text = snapshot.amPm,
                                style = MaterialTheme.typography.titleSmall,
                                color = ChronometerGoldLight
                            )
                        }
                        Text(
                            text = "${snapshot.formattedDate} (${snapshot.dayDeltaLabel})",
                            style = MaterialTheme.typography.bodyMedium,
                            color = TextSecondarySilver
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            SolarPhaseBadge(
                                solarPhase = snapshot.solarPhase,
                                isBusinessHours = snapshot.isBusinessHours
                            )
                            Text(
                                text = snapshot.utcOffsetString,
                                style = MaterialTheme.typography.labelMedium,
                                color = MeridianCyan,
                                modifier = Modifier
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(MeridianCyan.copy(alpha = 0.15f))
                                    .padding(horizontal = 8.dp, vertical = 3.dp)
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Primary Actions: Set as Anchor Reference & Pin/Unpin
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Button(
                    onClick = { onSetAnchor(snapshot.country.id) },
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (snapshot.isAnchor) ChronometerGold else SurfaceElevatedBlue,
                        contentColor = if (snapshot.isAnchor) MidnightNavy else TextPrimaryIvory
                    ),
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(
                        imageVector = Icons.Default.Anchor,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = if (snapshot.isAnchor) "Current Anchor" else "Set as Anchor",
                        style = MaterialTheme.typography.labelLarge
                    )
                }

                FilledTonalButton(
                    onClick = { onTogglePin(snapshot.country.id) },
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.filledTonalButtonColors(
                        containerColor = if (snapshot.isPinned) MeridianCyan.copy(alpha = 0.22f) else SurfaceElevatedBlue,
                        contentColor = if (snapshot.isPinned) MeridianCyan else TextPrimaryIvory
                    ),
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(
                        imageVector = if (snapshot.isPinned) Icons.Default.PushPin else Icons.Outlined.PushPin,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = if (snapshot.isPinned) "Pinned in Matrix" else "Pin to Matrix",
                        style = MaterialTheme.typography.labelLarge
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Quick Time Lock Presets for this Country
            Text(
                text = "LOCK WORLD TIME TO ${snapshot.country.countryName.uppercase()} HOUR",
                style = MaterialTheme.typography.labelMedium,
                color = ChronometerGoldLight
            )
            Spacer(modifier = Modifier.height(6.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                val hours = listOf(
                    "08:00 Morning" to 8,
                    "09:00 Work Start" to 9,
                    "12:00 Noon" to 12,
                    "15:00 Afternoon" to 15,
                    "17:00 Work End" to 17,
                    "21:00 Night" to 21
                )
                hours.forEach { (label, h) ->
                    val active = isTimeLocked && snapshot.hour24 == h
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(10.dp))
                            .background(
                                if (active) ChronometerGold.copy(alpha = 0.25f)
                                else SurfaceSlateBlue
                            )
                            .border(
                                1.dp,
                                if (active) ChronometerGold else CardBorderSubtle,
                                RoundedCornerShape(10.dp)
                            )
                            .clickable { onLockToHour(snapshot.country.id, h) }
                            .padding(horizontal = 12.dp, vertical = 8.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.LockClock,
                                contentDescription = null,
                                tint = if (active) ChronometerGoldLight else MeridianCyan,
                                modifier = Modifier.size(14.dp)
                            )
                            Text(
                                text = label,
                                style = MaterialTheme.typography.labelSmall,
                                color = if (active) ChronometerGoldLight else TextPrimaryIvory
                            )
                        }
                    }
                }
            }

            // Multi-Timezone Breakdown (if country spans multiple timezones)
            if (snapshot.subZoneSnapshots.isNotEmpty()) {
                Spacer(modifier = Modifier.height(18.dp))
                Text(
                    text = "ALL TIMEZONES IN ${snapshot.country.countryName.uppercase()} (${snapshot.subZoneSnapshots.size})",
                    style = MaterialTheme.typography.labelMedium,
                    color = MeridianCyan
                )
                Spacer(modifier = Modifier.height(8.dp))
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    snapshot.subZoneSnapshots.forEach { sub ->
                        Surface(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp),
                            color = SurfaceSlateBlue,
                            border = BorderStroke(0.8.dp, CardBorderSubtle)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 12.dp, vertical = 10.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = sub.label,
                                        style = MaterialTheme.typography.titleSmall,
                                        color = TextPrimaryIvory
                                    )
                                    Text(
                                        text = "${sub.zoneId} • ${sub.utcOffsetString}",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = TextSecondarySilver
                                    )
                                }
                                Row(verticalAlignment = Alignment.Bottom) {
                                    Text(
                                        text = sub.formattedTime,
                                        style = MaterialTheme.typography.headlineMedium,
                                        color = if (isTimeLocked) ChronometerGoldLight else TextPrimaryIvory
                                    )
                                    if (!is24HourFormat) {
                                        Spacer(modifier = Modifier.width(3.dp))
                                        Text(
                                            text = sub.amPm,
                                            style = MaterialTheme.typography.labelSmall,
                                            color = ChronometerGoldLight,
                                            modifier = Modifier.padding(bottom = 2.dp)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            // Country Horological & Geographic Metadata Grid
            Text(
                text = "COUNTRY TELEMETRY & METADATA",
                style = MaterialTheme.typography.labelMedium,
                color = TextSecondarySilver
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                MetadataTile(
                    label = "ISO Code",
                    value = snapshot.country.isoCode,
                    modifier = Modifier.weight(1f)
                )
                MetadataTile(
                    label = "Dialing Code",
                    value = snapshot.country.callingCode,
                    modifier = Modifier.weight(1f)
                )
                MetadataTile(
                    label = "Currency",
                    value = snapshot.country.currencyCode,
                    modifier = Modifier.weight(1f)
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                MetadataTile(
                    label = "IANA Zone",
                    value = snapshot.country.primaryZoneId,
                    modifier = Modifier.weight(1f)
                )
                MetadataTile(
                    label = "DST Status",
                    value = if (snapshot.isDstActive) "Active (Summer)" else "Standard Time",
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(28.dp))
        }
    }
}

@Composable
private fun MetadataTile(
    label: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(10.dp),
        color = SurfaceSlateBlue,
        border = BorderStroke(0.8.dp, CardBorderSubtle)
    ) {
        Column(modifier = Modifier.padding(10.dp)) {
            Text(
                text = label,
                style = MaterialTheme.typography.labelSmall,
                color = TextSecondarySilver
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = value,
                style = MaterialTheme.typography.bodyMedium,
                color = TextPrimaryIvory,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}
