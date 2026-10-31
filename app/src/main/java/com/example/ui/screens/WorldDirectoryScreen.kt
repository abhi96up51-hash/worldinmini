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
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Public
import androidx.compose.material.icons.filled.PushPin
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Sort
import androidx.compose.material.icons.outlined.PushPin
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.model.Continent
import com.example.model.CountryTimeSnapshot
import com.example.model.FilterMode
import com.example.model.SortOrder
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
import com.example.ui.theme.TextMutedSlate
import com.example.ui.theme.TextPrimaryIvory
import com.example.ui.theme.TextSecondarySilver

@Composable
fun WorldDirectoryScreen(
    uiState: WorldTimeUiState,
    onSearchQueryChange: (String) -> Unit,
    onSelectContinent: (Continent) -> Unit,
    onSelectFilterMode: (FilterMode) -> Unit,
    onSelectSortOrder: (SortOrder) -> Unit,
    onToggleTimeLock: () -> Unit,
    onAdjustMinutes: (Int) -> Unit,
    onSetOffsetMinutes: (Int) -> Unit,
    onResetLive: () -> Unit,
    onToggle24HourFormat: () -> Unit,
    onTogglePinCountry: (String) -> Unit,
    onSetAnchorCountry: (String) -> Unit,
    onSelectCountryDetail: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var sortMenuExpanded by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .testTag("world_directory_list"),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // 1. Time Lock Command Header
        item(key = "command_header") {
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

        // 2. Search Bar, Continent Chips & Filter Pills
        item(key = "search_and_filters") {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedTextField(
                        value = uiState.searchQuery,
                        onValueChange = onSearchQueryChange,
                        placeholder = {
                            Text(
                                text = "Search country, capital, ISO, or UTC…",
                                style = MaterialTheme.typography.bodyMedium,
                                color = TextMutedSlate
                            )
                        },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = "Search countries",
                                tint = MeridianCyan
                            )
                        },
                        trailingIcon = {
                            if (uiState.searchQuery.isNotEmpty()) {
                                IconButton(
                                    onClick = { onSearchQueryChange("") },
                                    modifier = Modifier.testTag("clear_search_button")
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Clear,
                                        contentDescription = "Clear search",
                                        tint = TextSecondarySilver
                                    )
                                }
                            }
                        },
                        singleLine = true,
                        shape = RoundedCornerShape(14.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = DeepObsidianBlue,
                            unfocusedContainerColor = DeepObsidianBlue,
                            focusedBorderColor = ChronometerGold,
                            unfocusedBorderColor = CardBorderSubtle,
                            focusedTextColor = TextPrimaryIvory,
                            unfocusedTextColor = TextPrimaryIvory
                        ),
                        modifier = Modifier
                            .weight(1f)
                            .testTag("search_countries_input")
                    )

                    // Sort Button + Dropdown
                    Box {
                        Surface(
                            shape = RoundedCornerShape(14.dp),
                            color = DeepObsidianBlue,
                            border = BorderStroke(1.dp, CardBorderSubtle),
                            modifier = Modifier
                                .height(56.dp)
                                .clickable { sortMenuExpanded = true }
                                .padding(horizontal = 12.dp)
                                .testTag("sort_menu_button")
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Sort,
                                    contentDescription = "Sort countries",
                                    tint = ChronometerGoldLight,
                                    modifier = Modifier.size(20.dp)
                                )
                                Text(
                                    text = when (uiState.selectedSortOrder) {
                                        SortOrder.COUNTRY_A_Z -> "A–Z"
                                        SortOrder.UTC_WEST_TO_EAST -> "UTC↑"
                                        SortOrder.UTC_EAST_TO_WEST -> "UTC↓"
                                        SortOrder.CONTINENT -> "Region"
                                    },
                                    style = MaterialTheme.typography.labelMedium,
                                    color = TextPrimaryIvory
                                )
                            }
                        }

                        DropdownMenu(
                            expanded = sortMenuExpanded,
                            onDismissRequest = { sortMenuExpanded = false },
                            modifier = Modifier.background(SurfaceSlateBlue)
                        ) {
                            SortOrder.entries.forEach { order ->
                                DropdownMenuItem(
                                    text = {
                                        Text(
                                            text = order.label,
                                            style = MaterialTheme.typography.bodyMedium,
                                            color = if (uiState.selectedSortOrder == order) {
                                                ChronometerGoldLight
                                            } else {
                                                TextPrimaryIvory
                                            }
                                        )
                                    },
                                    onClick = {
                                        onSelectSortOrder(order)
                                        sortMenuExpanded = false
                                    }
                                )
                            }
                        }
                    }
                }

                // Continent Filter Row
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Continent.entries.forEach { continent ->
                        val selected = uiState.selectedContinent == continent
                        FilterChip(
                            selected = selected,
                            onClick = { onSelectContinent(continent) },
                            label = {
                                Text(
                                    text = continent.shortLabel,
                                    style = MaterialTheme.typography.labelMedium
                                )
                            },
                            colors = FilterChipDefaults.filterChipColors(
                                containerColor = DeepObsidianBlue,
                                labelColor = TextSecondarySilver,
                                selectedContainerColor = ChronometerGold.copy(alpha = 0.22f),
                                selectedLabelColor = ChronometerGoldLight
                            ),
                            border = FilterChipDefaults.filterChipBorder(
                                enabled = true,
                                selected = selected,
                                borderColor = CardBorderSubtle,
                                selectedBorderColor = ChronometerGold
                            ),
                            modifier = Modifier.testTag("continent_chip_${continent.name}")
                        )
                    }
                }

                // Secondary Status Filter Row (All, Pinned, Working Hours, Daylight, Multi-Zone, DST)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    FilterMode.entries.forEach { mode ->
                        val selected = uiState.selectedFilterMode == mode
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(10.dp))
                                .background(
                                    if (selected) MeridianCyan.copy(alpha = 0.20f)
                                    else SurfaceSlateBlue.copy(alpha = 0.65f)
                                )
                                .border(
                                    width = 1.dp,
                                    color = if (selected) MeridianCyan else CardBorderSubtle,
                                    shape = RoundedCornerShape(10.dp)
                                )
                                .clickable { onSelectFilterMode(mode) }
                                .padding(horizontal = 12.dp, vertical = 6.dp)
                                .testTag("filter_mode_${mode.name}")
                        ) {
                            Text(
                                text = mode.label,
                                style = MaterialTheme.typography.labelSmall,
                                color = if (selected) MeridianCyan else TextSecondarySilver,
                                fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal
                            )
                        }
                    }
                }

                // Results Header Count
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Showing ${uiState.filteredCountries.size} of ${uiState.totalWorldCountriesCount} countries & territories",
                        style = MaterialTheme.typography.bodySmall,
                        color = TextSecondarySilver
                    )
                    Text(
                        text = if (uiState.isTimeLocked) "Tap card for multi-zone & lock" else "Tap card to inspect",
                        style = MaterialTheme.typography.labelSmall,
                        color = ChronometerGoldLight
                    )
                }
            }
        }

        // 3. Empty State or Countries List
        if (uiState.filteredCountries.isEmpty()) {
            item(key = "empty_state") {
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 24.dp),
                    shape = RoundedCornerShape(18.dp),
                    color = DeepObsidianBlue,
                    border = BorderStroke(1.dp, CardBorderSubtle)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(28.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            imageVector = Icons.Default.Public,
                            contentDescription = null,
                            tint = MeridianCyan,
                            modifier = Modifier.size(44.dp)
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = "No matching countries found",
                            style = MaterialTheme.typography.titleMedium,
                            color = TextPrimaryIvory
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Try searching by country name, capital city, ISO code, or UTC offset.",
                            style = MaterialTheme.typography.bodySmall,
                            color = TextSecondarySilver
                        )
                    }
                }
            }
        } else {
            items(
                items = uiState.filteredCountries,
                key = { it.country.id }
            ) { snapshot ->
                WorldCountryTimeCard(
                    snapshot = snapshot,
                    isTimeLocked = uiState.isTimeLocked,
                    is24HourFormat = uiState.is24HourFormat,
                    onCardClick = { onSelectCountryDetail(snapshot.country.id) },
                    onTogglePin = { onTogglePinCountry(snapshot.country.id) },
                    onSetAnchor = { onSetAnchorCountry(snapshot.country.id) }
                )
            }
        }
    }
}

@Composable
fun WorldCountryTimeCard(
    snapshot: CountryTimeSnapshot,
    isTimeLocked: Boolean,
    is24HourFormat: Boolean,
    onCardClick: () -> Unit,
    onTogglePin: () -> Unit,
    onSetAnchor: () -> Unit,
    modifier: Modifier = Modifier
) {
    val borderColor = when {
        snapshot.isAnchor -> ChronometerGold
        snapshot.isPinned -> MeridianCyan.copy(alpha = 0.65f)
        else -> CardBorderSubtle
    }

    Surface(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onCardClick() }
            .testTag("country_card_${snapshot.country.id}"),
        shape = RoundedCornerShape(18.dp),
        color = DeepObsidianBlue,
        border = BorderStroke(if (snapshot.isAnchor) 1.5.dp else 1.dp, borderColor),
        tonalElevation = 3.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 14.dp, vertical = 12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                // Left column: Flag + Country Name + Capital + Metadata badges
                Row(
                    modifier = Modifier.weight(1f),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(44.dp)
                            .clip(CircleShape)
                            .background(SurfaceSlateBlue)
                            .border(1.dp, CardBorderSubtle, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = snapshot.country.flagEmoji,
                            style = MaterialTheme.typography.headlineSmall
                        )
                    }

                    Spacer(modifier = Modifier.width(12.dp))

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
                                overflow = TextOverflow.Ellipsis,
                                modifier = Modifier.weight(1f, fill = false)
                            )
                            Text(
                                text = snapshot.country.isoCode,
                                style = MaterialTheme.typography.labelSmall,
                                color = MeridianCyan,
                                modifier = Modifier
                                    .clip(RoundedCornerShape(4.dp))
                                    .background(MeridianCyan.copy(alpha = 0.14f))
                                    .padding(horizontal = 5.dp, vertical = 1.dp)
                            )
                            if (snapshot.country.subTimeZones.size > 1) {
                                Text(
                                    text = "${snapshot.country.subTimeZones.size} Zones",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = ChronometerGoldLight,
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(4.dp))
                                        .background(ChronometerGold.copy(alpha = 0.16f))
                                        .padding(horizontal = 5.dp, vertical = 1.dp)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(2.dp))

                        Text(
                            text = "${snapshot.country.capitalOrHub} • ${snapshot.country.continent.shortLabel} (${snapshot.country.callingCode})",
                            style = MaterialTheme.typography.bodySmall,
                            color = TextSecondarySilver,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )

                        Spacer(modifier = Modifier.height(6.dp))

                        // Badges Row: UTC Offset + Relative to Anchor + Day Delta + Solar Phase
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Text(
                                text = snapshot.utcOffsetString,
                                style = MaterialTheme.typography.labelSmall,
                                color = TextPrimaryIvory,
                                modifier = Modifier
                                    .clip(RoundedCornerShape(6.dp))
                                    .background(SurfaceElevatedBlue)
                                    .padding(horizontal = 6.dp, vertical = 2.dp)
                            )

                            Text(
                                text = snapshot.relativeToAnchorString,
                                style = MaterialTheme.typography.labelSmall,
                                color = if (snapshot.isAnchor) ChronometerGoldLight else MeridianCyan,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier
                                    .clip(RoundedCornerShape(6.dp))
                                    .background(
                                        if (snapshot.isAnchor) ChronometerGold.copy(alpha = 0.2f)
                                        else MeridianCyan.copy(alpha = 0.14f)
                                    )
                                    .padding(horizontal = 6.dp, vertical = 2.dp)
                            )

                            if (snapshot.dayDeltaFromAnchor != 0) {
                                Text(
                                    text = snapshot.dayDeltaLabel,
                                    style = MaterialTheme.typography.labelSmall,
                                    color = ChronometerGoldLight,
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(6.dp))
                                        .background(ChronometerGold.copy(alpha = 0.16f))
                                        .padding(horizontal = 6.dp, vertical = 2.dp)
                                )
                            }
                        }
                    }
                }

                // Right column: Digital Clock + Date + Solar Phase + Quick Actions
                Column(horizontalAlignment = Alignment.End) {
                    Row(verticalAlignment = Alignment.Bottom) {
                        Text(
                            text = snapshot.formattedTime,
                            style = MaterialTheme.typography.displaySmall,
                            color = if (isTimeLocked) ChronometerGoldLight else TextPrimaryIvory
                        )
                        if (!is24HourFormat) {
                            Text(
                                text = snapshot.amPm,
                                style = MaterialTheme.typography.labelMedium,
                                color = ChronometerGoldLight,
                                modifier = Modifier.padding(bottom = 3.dp, start = 3.dp)
                            )
                        } else {
                            Text(
                                text = if (isTimeLocked) ":00" else snapshot.formattedSeconds,
                                style = MaterialTheme.typography.labelSmall,
                                color = TextMutedSlate,
                                modifier = Modifier.padding(bottom = 4.dp, start = 2.dp)
                            )
                        }
                    }

                    Text(
                        text = snapshot.formattedDate,
                        style = MaterialTheme.typography.bodySmall,
                        color = TextSecondarySilver
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        SolarPhaseBadge(
                            solarPhase = snapshot.solarPhase,
                            isBusinessHours = snapshot.isBusinessHours
                        )

                        // Set Anchor Button
                        Box(
                            modifier = Modifier
                                .size(28.dp)
                                .clip(CircleShape)
                                .background(
                                    if (snapshot.isAnchor) ChronometerGold.copy(alpha = 0.25f)
                                    else SurfaceSlateBlue
                                )
                                .clickable { onSetAnchor() }
                                .testTag("anchor_button_${snapshot.country.id}"),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Anchor,
                                contentDescription = "Set ${snapshot.country.countryName} as Anchor Reference",
                                tint = if (snapshot.isAnchor) ChronometerGoldLight else TextSecondarySilver,
                                modifier = Modifier.size(15.dp)
                            )
                        }

                        // Pin to Matrix Button
                        Box(
                            modifier = Modifier
                                .size(28.dp)
                                .clip(CircleShape)
                                .background(
                                    if (snapshot.isPinned) MeridianCyan.copy(alpha = 0.22f)
                                    else SurfaceSlateBlue
                                )
                                .clickable { onTogglePin() }
                                .testTag("pin_button_${snapshot.country.id}"),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = if (snapshot.isPinned) Icons.Default.PushPin else Icons.Outlined.PushPin,
                                contentDescription = if (snapshot.isPinned) "Unpin ${snapshot.country.countryName}" else "Pin ${snapshot.country.countryName}",
                                tint = if (snapshot.isPinned) MeridianCyan else TextSecondarySilver,
                                modifier = Modifier.size(15.dp)
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // 24-Hour Mini Solar & Working Hours Strip
            SolarTimelineBar24h(
                currentHour24 = snapshot.hour24,
                currentMinute = snapshot.minute,
                isTimeLocked = isTimeLocked
            )
        }
    }
}
