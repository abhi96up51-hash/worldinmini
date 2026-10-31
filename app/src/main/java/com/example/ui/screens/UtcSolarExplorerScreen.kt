package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.model.UtcBandGroup
import com.example.ui.WorldTimeUiState
import com.example.ui.components.SolarPhaseBadge
import com.example.ui.components.WorldSolarTerminatorCanvas
import com.example.ui.theme.CardBorderSubtle
import com.example.ui.theme.ChronometerGold
import com.example.ui.theme.ChronometerGoldLight
import com.example.ui.theme.DeepObsidianBlue
import com.example.ui.theme.MeridianCyan
import com.example.ui.theme.SurfaceElevatedBlue
import com.example.ui.theme.SurfaceSlateBlue
import com.example.ui.theme.TextPrimaryIvory
import com.example.ui.theme.TextSecondarySilver

@Composable
fun UtcSolarExplorerScreen(
    uiState: WorldTimeUiState,
    onSelectCountryDetail: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val anchor = uiState.anchorSnapshot

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .testTag("utc_solar_explorer_list"),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // 1. Solar Terminator & Meridian Wave Visualizer
        if (anchor != null) {
            item(key = "solar_terminator") {
                WorldSolarTerminatorCanvas(
                    anchorUtcOffsetMinutes = anchor.utcOffsetMinutes,
                    anchorHour24 = anchor.hour24,
                    anchorMinute = anchor.minute,
                    isTimeLocked = uiState.isTimeLocked
                )
            }
        }

        // 2. Section Header
        item(key = "utc_bands_header") {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 4.dp, vertical = 4.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "WORLD UTC LONGITUDINAL TIME BANDS",
                        style = MaterialTheme.typography.titleMedium,
                        color = TextPrimaryIvory
                    )
                    Text(
                        text = "${uiState.utcBandGroups.size} distinct official UTC offsets across ${uiState.totalWorldCountriesCount} countries",
                        style = MaterialTheme.typography.bodySmall,
                        color = TextSecondarySilver
                    )
                }
            }
        }

        // 3. Longitudinal UTC Bands (West UTC-11 to East UTC+14)
        items(
            items = uiState.utcBandGroups,
            key = { "utc_band_${it.utcOffsetMinutes}" }
        ) { band ->
            UtcBandCard(
                band = band,
                is24HourFormat = uiState.is24HourFormat,
                isTimeLocked = uiState.isTimeLocked,
                onCountryClick = onSelectCountryDetail
            )
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun UtcBandCard(
    band: UtcBandGroup,
    is24HourFormat: Boolean,
    isTimeLocked: Boolean,
    onCountryClick: (String) -> Unit
) {
    val isPrimeMeridian = band.utcOffsetMinutes == 0

    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        color = DeepObsidianBlue,
        border = BorderStroke(
            width = if (isPrimeMeridian) 1.5.dp else 1.dp,
            color = if (isPrimeMeridian) MeridianCyan else CardBorderSubtle
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
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .background(
                                if (isPrimeMeridian) MeridianCyan.copy(alpha = 0.22f)
                                else SurfaceElevatedBlue
                            )
                            .padding(horizontal = 10.dp, vertical = 5.dp)
                    ) {
                        Text(
                            text = band.utcOffsetLabel,
                            style = MaterialTheme.typography.labelLarge,
                            color = if (isPrimeMeridian) MeridianCyan else ChronometerGoldLight,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Column {
                        Text(
                            text = if (isPrimeMeridian) {
                                "Prime Meridian • ${band.countries.size} Countries"
                            } else {
                                "${band.countries.size} ${if (band.countries.size == 1) "Country" else "Countries"}"
                            },
                            style = MaterialTheme.typography.titleSmall,
                            color = TextPrimaryIvory
                        )
                        SolarPhaseBadge(
                            solarPhase = band.solarPhase,
                            isBusinessHours = band.countries.firstOrNull()?.isBusinessHours == true
                        )
                    }
                }

                Row(verticalAlignment = Alignment.Bottom) {
                    Text(
                        text = band.representativeTime,
                        style = MaterialTheme.typography.displaySmall,
                        color = if (isTimeLocked) ChronometerGoldLight else TextPrimaryIvory
                    )
                    if (!is24HourFormat) {
                        Spacer(modifier = Modifier.width(3.dp))
                        Text(
                            text = band.representativeAmPm,
                            style = MaterialTheme.typography.labelMedium,
                            color = ChronometerGoldLight,
                            modifier = Modifier.padding(bottom = 3.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                band.countries.forEach { snap ->
                    Row(
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .background(SurfaceSlateBlue)
                            .border(0.8.dp, CardBorderSubtle, RoundedCornerShape(8.dp))
                            .clickable { onCountryClick(snap.country.id) }
                            .padding(horizontal = 9.dp, vertical = 5.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(5.dp)
                    ) {
                        Text(
                            text = snap.country.flagEmoji,
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Text(
                            text = snap.country.countryName,
                            style = MaterialTheme.typography.bodySmall,
                            color = TextPrimaryIvory
                        )
                    }
                }
            }
        }
    }
}
