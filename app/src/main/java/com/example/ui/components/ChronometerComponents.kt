package com.example.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Brightness3
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material.icons.filled.WbTwilight
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.model.SolarPhase
import com.example.ui.theme.CardBorderSubtle
import com.example.ui.theme.ChronometerGold
import com.example.ui.theme.ChronometerGoldLight
import com.example.ui.theme.DawnRose
import com.example.ui.theme.DeepObsidianBlue
import com.example.ui.theme.DuskViolet
import com.example.ui.theme.LiveEmerald
import com.example.ui.theme.MeridianCyan
import com.example.ui.theme.MidnightNavy
import com.example.ui.theme.SurfaceElevatedBlue
import com.example.ui.theme.TextMutedSlate
import com.example.ui.theme.TextPrimaryIvory
import com.example.ui.theme.TextSecondarySilver
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.sin

@Composable
fun AnalogChronometerDial(
    hour24: Int,
    minute: Int,
    second: Int,
    isTimeLocked: Boolean,
    modifier: Modifier = Modifier,
    dialSize: Dp = 84.dp
) {
    val isDaylight = hour24 in 6..18
    val accentColor = if (isTimeLocked) ChronometerGold else MeridianCyan

    Box(
        modifier = modifier.size(dialSize),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val radius = min(size.width, size.height) / 2f
            val center = Offset(size.width / 2f, size.height / 2f)

            // Outer bezel ring
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(
                        if (isDaylight) Color(0xFF172A4F) else Color(0xFF0A1124),
                        MidnightNavy
                    ),
                    center = center,
                    radius = radius
                ),
                radius = radius,
                center = center
            )

            drawCircle(
                color = accentColor.copy(alpha = 0.55f),
                radius = radius - 1.5.dp.toPx(),
                center = center,
                style = Stroke(width = 2.dp.toPx())
            )

            // 60 minute / 12 hour indices
            for (i in 0 until 60) {
                val angleRad = ((i * 6f - 90f) * (PI / 180f)).toFloat()
                val isMajorHour = i % 5 == 0
                val isQuarter = i % 15 == 0
                val outerR = radius - 5.dp.toPx()
                val innerR = when {
                    isQuarter -> outerR - 7.dp.toPx()
                    isMajorHour -> outerR - 5.dp.toPx()
                    else -> outerR - 2.dp.toPx()
                }
                val start = Offset(
                    x = center.x + innerR * cos(angleRad),
                    y = center.y + innerR * sin(angleRad)
                )
                val end = Offset(
                    x = center.x + outerR * cos(angleRad),
                    y = center.y + outerR * sin(angleRad)
                )
                drawLine(
                    color = when {
                        isQuarter -> ChronometerGoldLight
                        isMajorHour -> TextPrimaryIvory.copy(alpha = 0.85f)
                        else -> TextMutedSlate.copy(alpha = 0.4f)
                    },
                    start = start,
                    end = end,
                    strokeWidth = if (isQuarter) 2.2.dp.toPx() else if (isMajorHour) 1.5.dp.toPx() else 0.8.dp.toPx(),
                    cap = StrokeCap.Round
                )
            }

            // Hour hand
            val hourAngleDeg = ((hour24 % 12) + minute / 60f) * 30f - 90f
            val hourRad = (hourAngleDeg * (PI / 180f)).toFloat()
            val hourLength = radius * 0.48f
            drawLine(
                color = TextPrimaryIvory,
                start = center,
                end = Offset(
                    x = center.x + hourLength * cos(hourRad),
                    y = center.y + hourLength * sin(hourRad)
                ),
                strokeWidth = 3.2.dp.toPx(),
                cap = StrokeCap.Round
            )

            // Minute hand
            val minuteAngleDeg = (minute + second / 60f) * 6f - 90f
            val minuteRad = (minuteAngleDeg * (PI / 180f)).toFloat()
            val minuteLength = radius * 0.70f
            drawLine(
                color = ChronometerGoldLight,
                start = center,
                end = Offset(
                    x = center.x + minuteLength * cos(minuteRad),
                    y = center.y + minuteLength * sin(minuteRad)
                ),
                strokeWidth = 2.2.dp.toPx(),
                cap = StrokeCap.Round
            )

            // Second hand (only sweep when live, or point to 12 when locked)
            val secAngleDeg = (if (isTimeLocked) 0f else second * 6f) - 90f
            val secRad = (secAngleDeg * (PI / 180f)).toFloat()
            val secLength = radius * 0.78f
            drawLine(
                color = if (isTimeLocked) ChronometerGold else MeridianCyan,
                start = Offset(
                    x = center.x - (radius * 0.16f) * cos(secRad),
                    y = center.y - (radius * 0.16f) * sin(secRad)
                ),
                end = Offset(
                    x = center.x + secLength * cos(secRad),
                    y = center.y + secLength * sin(secRad)
                ),
                strokeWidth = 1.2.dp.toPx(),
                cap = StrokeCap.Round
            )

            // Center cap jewel
            drawCircle(
                color = accentColor,
                radius = 3.8.dp.toPx(),
                center = center
            )
            drawCircle(
                color = MidnightNavy,
                radius = 1.5.dp.toPx(),
                center = center
            )
        }
    }
}

@Composable
fun SolarPhaseBadge(
    solarPhase: SolarPhase,
    isBusinessHours: Boolean,
    modifier: Modifier = Modifier
) {
    val (phaseColor, icon) = when (solarPhase) {
        SolarPhase.DAWN -> DawnRose to Icons.Default.WbTwilight
        SolarPhase.DAYLIGHT -> ChronometerGoldLight to Icons.Default.WbSunny
        SolarPhase.DUSK -> DuskViolet to Icons.Default.WbTwilight
        SolarPhase.NIGHT -> MeridianCyan to Icons.Default.Brightness3
    }

    Row(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .background(phaseColor.copy(alpha = 0.14f))
            .border(0.8.dp, phaseColor.copy(alpha = 0.35f), RoundedCornerShape(8.dp))
            .padding(horizontal = 8.dp, vertical = 3.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = solarPhase.label,
            tint = phaseColor,
            modifier = Modifier.size(13.dp)
        )
        Text(
            text = if (isBusinessHours) "Work 09–17" else solarPhase.label,
            style = MaterialTheme.typography.labelSmall,
            color = phaseColor
        )
    }
}

@Composable
fun SolarTimelineBar24h(
    currentHour24: Int,
    currentMinute: Int,
    isTimeLocked: Boolean,
    modifier: Modifier = Modifier,
    onHourSelected: ((Int) -> Unit)? = null
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(22.dp)
                .clip(RoundedCornerShape(6.dp))
                .background(MidnightNavy)
                .border(0.8.dp, CardBorderSubtle, RoundedCornerShape(6.dp))
        ) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                val cellWidth = size.width / 24f
                for (h in 0 until 24) {
                    val cellColor = when (h) {
                        in 9..17 -> LiveEmerald.copy(alpha = 0.36f) // Working hours
                        in 6..8, 18 -> ChronometerGold.copy(alpha = 0.24f) // Daylight / transition
                        in 19..21 -> DuskViolet.copy(alpha = 0.22f) // Evening
                        else -> Color(0xFF0A1328) // Night
                    }
                    drawRect(
                        color = cellColor,
                        topLeft = Offset(h * cellWidth, 0f),
                        size = Size(cellWidth - 1.dp.toPx().coerceAtLeast(0.5f), size.height)
                    )
                }

                // Current / Locked Time Needle
                val progress = ((currentHour24 * 60 + currentMinute) / 1440f).coerceIn(0f, 1f)
                val needleX = progress * size.width
                val needleColor = if (isTimeLocked) ChronometerGoldLight else MeridianCyan

                drawLine(
                    color = needleColor,
                    start = Offset(needleX, 0f),
                    end = Offset(needleX, size.height),
                    strokeWidth = 2.8.dp.toPx()
                )
                drawCircle(
                    color = needleColor,
                    radius = 4.dp.toPx(),
                    center = Offset(needleX, size.height / 2f)
                )
            }

            if (onHourSelected != null) {
                Row(modifier = Modifier.fillMaxSize()) {
                    for (h in 0 until 24) {
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxSize()
                                .clickable { onHourSelected(h) }
                        )
                    }
                }
            }
        }

        if (onHourSelected != null) {
            Spacer(modifier = Modifier.height(3.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                listOf("00:00", "06:00", "09:00 Work", "12:00", "17:00", "23:00").forEach { mark ->
                    Text(
                        text = mark,
                        style = MaterialTheme.typography.labelSmall,
                        color = TextSecondarySilver
                    )
                }
            }
        }
    }
}

@Composable
fun WorldSolarTerminatorCanvas(
    anchorUtcOffsetMinutes: Int,
    anchorHour24: Int,
    anchorMinute: Int,
    isTimeLocked: Boolean,
    modifier: Modifier = Modifier
) {
    // Calculate UTC decimal hour (0..24)
    val totalUtcMinutes = ((anchorHour24 * 60 + anchorMinute - anchorUtcOffsetMinutes) % 1440 + 1440) % 1440
    val utcDecimalHour = totalUtcMinutes / 60f
    // Solar noon occurs where local time is 12:00 -> UTC offset = 12 - utcDecimalHour
    val rawNoonOffset = 12f - utcDecimalHour
    val solarNoonOffsetHours = when {
        rawNoonOffset > 12f -> rawNoonOffset - 24f
        rawNoonOffset < -12f -> rawNoonOffset + 24f
        else -> rawNoonOffset
    }

    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        color = DeepObsidianBlue,
        border = androidx.compose.foundation.BorderStroke(1.dp, CardBorderSubtle)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "GLOBAL SOLAR TERMINATOR & MERIDIANS",
                        style = MaterialTheme.typography.labelMedium,
                        color = if (isTimeLocked) ChronometerGoldLight else MeridianCyan
                    )
                    Text(
                        text = String.format(
                            java.util.Locale.ENGLISH,
                            "UTC %02d:%02d • Solar Zenith at UTC%s%.1fh",
                            totalUtcMinutes / 60,
                            totalUtcMinutes % 60,
                            if (solarNoonOffsetHours >= 0) "+" else "",
                            solarNoonOffsetHours
                        ),
                        style = MaterialTheme.typography.bodySmall,
                        color = TextSecondarySilver
                    )
                }

                Box(
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(
                            if (isTimeLocked) ChronometerGold.copy(alpha = 0.18f)
                            else LiveEmerald.copy(alpha = 0.18f)
                        )
                        .padding(horizontal = 10.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = if (isTimeLocked) "LOCKED WAVE" else "LIVE ORBIT",
                        style = MaterialTheme.typography.labelSmall,
                        color = if (isTimeLocked) ChronometerGoldLight else LiveEmerald
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(110.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(MidnightNavy)
            ) {
                Canvas(modifier = Modifier.fillMaxSize()) {
                    val w = size.width
                    val h = size.height

                    // Draw 24 longitudinal meridian lines (-12 to +12)
                    for (i in 0..24) {
                        val x = (i / 24f) * w
                        drawLine(
                            color = CardBorderSubtle.copy(alpha = if (i == 12) 0.85f else 0.3f),
                            start = Offset(x, 0f),
                            end = Offset(x, h),
                            strokeWidth = if (i == 12) 1.6.dp.toPx() else 0.7.dp.toPx()
                        )
                    }

                    // Equator line
                    drawLine(
                        color = CardBorderSubtle.copy(alpha = 0.45f),
                        start = Offset(0f, h / 2f),
                        end = Offset(w, h / 2f),
                        strokeWidth = 1.dp.toPx()
                    )

                    // Draw sinusoidal Day/Night terminator wave
                    val noonX = ((solarNoonOffsetHours + 12f) / 24f).coerceIn(0f, 1f) * w
                    val wavePath = Path()
                    val fillPath = Path()
                    val steps = 80
                    for (s in 0..steps) {
                        val frac = s / steps.toFloat()
                        val x = frac * w
                        val lonOffsetHours = frac * 24f - 12f
                        val phase = ((lonOffsetHours - solarNoonOffsetHours) / 24f) * (2f * PI.toFloat())
                        val y = (h / 2f) - (h * 0.34f) * cos(phase)
                        if (s == 0) {
                            wavePath.moveTo(x, y)
                            fillPath.moveTo(x, h)
                            fillPath.lineTo(x, y)
                        } else {
                            wavePath.lineTo(x, y)
                            fillPath.lineTo(x, y)
                        }
                    }
                    fillPath.lineTo(w, h)
                    fillPath.close()

                    drawPath(
                        path = fillPath,
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                ChronometerGold.copy(alpha = 0.30f),
                                MeridianCyan.copy(alpha = 0.05f)
                            )
                        )
                    )

                    drawPath(
                        path = wavePath,
                        color = ChronometerGoldLight,
                        style = Stroke(width = 2.2.dp.toPx())
                    )

                    // Draw Sun Zenith orb at Solar Noon
                    val sunY = (h / 2f) - (h * 0.34f)
                    drawCircle(
                        color = ChronometerGold.copy(alpha = 0.30f),
                        radius = 14.dp.toPx(),
                        center = Offset(noonX, sunY)
                    )
                    drawCircle(
                        color = ChronometerGoldLight,
                        radius = 6.dp.toPx(),
                        center = Offset(noonX, sunY)
                    )
                }
            }

            Spacer(modifier = Modifier.height(6.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("UTC-12", style = MaterialTheme.typography.labelSmall, color = TextMutedSlate)
                Text("UTC-06", style = MaterialTheme.typography.labelSmall, color = TextMutedSlate)
                Text("UTC±00 (Prime)", style = MaterialTheme.typography.labelSmall, color = MeridianCyan)
                Text("UTC+06", style = MaterialTheme.typography.labelSmall, color = TextMutedSlate)
                Text("UTC+12/+14", style = MaterialTheme.typography.labelSmall, color = TextMutedSlate)
            }
        }
    }
}
