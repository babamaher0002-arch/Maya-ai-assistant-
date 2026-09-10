package com.example.ui.screens

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.AppSettings
import com.example.ui.components.MayaGradientButton
import com.example.ui.theme.MayaBgDark
import com.example.ui.theme.MayaCardBg
import com.example.ui.theme.MayaCardBorder
import com.example.ui.theme.MayaCyan
import com.example.ui.theme.MayaCyanLight
import com.example.ui.theme.MayaEmerald
import com.example.ui.theme.MayaTextSecondary
import com.example.ui.theme.MayaTextWhite
import com.example.ui.theme.MayaViolet

data class AccentColorOption(
    val name: String,
    val colorHex: Long
)

@Composable
fun AppearanceScreen(
    appSettings: AppSettings,
    onBack: () -> Unit,
    onUpdateTheme: (Boolean, Long) -> Unit,
    modifier: Modifier = Modifier
) {
    var isDark by remember { mutableStateOf(appSettings.isDarkMode) }
    var selectedAccent by remember { mutableLongStateOf(appSettings.selectedAccentHex) }
    val context = LocalContext.current

    val accents = listOf(
        AccentColorOption("Maya Violet", 0xFF7C3AED),
        AccentColorOption("Cyber Cyan", 0xFF06B6D4),
        AccentColorOption("Emerald Pulse", 0xFF10B981),
        AccentColorOption("Amber Solar", 0xFFF59E0B),
        AccentColorOption("Rose Neon", 0xFFF43F5E)
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MayaBgDark)
            .padding(16.dp)
            .testTag("appearance_screen")
    ) {
        // App Bar
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = onBack,
                modifier = Modifier.testTag("appearance_back_btn")
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = MayaTextWhite
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Appearance & Theme",
                style = MaterialTheme.typography.titleLarge,
                color = MayaTextWhite,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Dark Mode Card
        Card(
            shape = RoundedCornerShape(18.dp),
            colors = CardDefaults.cardColors(containerColor = MayaCardBg),
            border = BorderStroke(1.dp, MayaCardBorder),
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(18.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = "Obsidian Dark Theme",
                        style = MaterialTheme.typography.titleMedium,
                        color = MayaTextWhite,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        text = "Optimized for high contrast and OLED power efficiency",
                        style = MaterialTheme.typography.bodySmall,
                        color = MayaTextSecondary
                    )
                }

                Switch(
                    checked = isDark,
                    onCheckedChange = { isDark = it },
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = MayaViolet,
                        checkedTrackColor = MayaViolet.copy(alpha = 0.4f)
                    ),
                    modifier = Modifier.testTag("dark_mode_switch")
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Accent Colors
        Text(
            text = "BRAND ACCENT COLOR",
            style = MaterialTheme.typography.labelMedium,
            color = MayaCyanLight,
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.sp
        )

        Spacer(modifier = Modifier.height(12.dp))

        Card(
            shape = RoundedCornerShape(18.dp),
            colors = CardDefaults.cardColors(containerColor = MayaCardBg),
            border = BorderStroke(1.dp, MayaCardBorder),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                accents.forEach { option ->
                    val isSelected = option.colorHex == selectedAccent
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp))
                            .clickable { selectedAccent = option.colorHex }
                            .padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(32.dp)
                                    .clip(CircleShape)
                                    .background(Color(option.colorHex))
                                    .border(
                                        2.dp,
                                        if (isSelected) MayaTextWhite else Color.Transparent,
                                        CircleShape
                                    )
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Text(
                                text = option.name,
                                style = MaterialTheme.typography.titleMedium,
                                color = MayaTextWhite,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                            )
                        }

                        if (isSelected) {
                            Icon(
                                imageVector = Icons.Default.Check,
                                contentDescription = "Selected",
                                tint = MayaCyanLight,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        MayaGradientButton(
            text = "Apply Theme Changes",
            onClick = {
                onUpdateTheme(isDark, selectedAccent)
                Toast.makeText(context, "Theme applied successfully", Toast.LENGTH_SHORT).show()
                onBack()
            },
            modifier = Modifier.fillMaxWidth()
        )
    }
}
