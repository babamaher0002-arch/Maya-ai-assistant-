package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Wifi
import androidx.compose.material.icons.filled.WifiOff
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.AppSettings
import com.example.data.model.UserProfile
import com.example.ui.theme.MayaBgDark
import com.example.ui.theme.MayaCardBg
import com.example.ui.theme.MayaCardBorder
import com.example.ui.theme.MayaCyan
import com.example.ui.theme.MayaCyanLight
import com.example.ui.theme.MayaEmerald
import com.example.ui.theme.MayaRose
import com.example.ui.theme.MayaTextSecondary
import com.example.ui.theme.MayaTextTertiary
import com.example.ui.theme.MayaTextWhite
import com.example.ui.theme.MayaViolet

@Composable
fun SettingsScreen(
    userProfile: UserProfile,
    appSettings: AppSettings,
    isOffline: Boolean,
    onToggleOffline: (Boolean) -> Unit,
    onNavigateToProfile: () -> Unit,
    onNavigateToAppearance: () -> Unit,
    onNavigateToVoice: () -> Unit,
    onNavigateToHistory: () -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(MayaBgDark)
            .testTag("settings_screen"),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Title
        item {
            Text(
                text = "Settings",
                style = MaterialTheme.typography.headlineMedium,
                color = MayaTextWhite,
                fontWeight = FontWeight.Bold
            )
        }

        // Profile Banner Card
        item {
            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = MayaCardBg),
                border = BorderStroke(1.dp, MayaCardBorder),
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(onClick = onNavigateToProfile)
                    .testTag("settings_profile_card")
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(18.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(56.dp)
                                .clip(CircleShape)
                                .background(Brush.linearGradient(listOf(MayaViolet, MayaCyan))),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "AM",
                                style = MaterialTheme.typography.titleLarge,
                                color = MayaTextWhite,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        Spacer(modifier = Modifier.width(14.dp))
                        Column {
                            Text(
                                text = userProfile.name,
                                style = MaterialTheme.typography.titleMedium,
                                color = MayaTextWhite,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = userProfile.email,
                                style = MaterialTheme.typography.bodySmall,
                                color = MayaTextSecondary
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Surface(
                                shape = RoundedCornerShape(6.dp),
                                color = MayaEmerald.copy(alpha = 0.2f)
                            ) {
                                Text(
                                    text = userProfile.tier,
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MayaEmerald,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                )
                            }
                        }
                    }

                    Icon(
                        imageVector = Icons.Default.ArrowForwardIos,
                        contentDescription = "Open Profile",
                        tint = MayaTextTertiary,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
        }

        // Section: Configuration
        item {
            Text(
                text = "PREFERENCES",
                style = MaterialTheme.typography.labelMedium,
                color = MayaCyanLight,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.sp
            )
        }

        item {
            Card(
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = MayaCardBg),
                border = BorderStroke(1.dp, MayaCardBorder),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column {
                    SettingsRow(
                        icon = Icons.Default.Palette,
                        title = "Appearance & Theme",
                        subtitle = if (appSettings.isDarkMode) "Obsidian Dark Mode" else "Light Mode",
                        onClick = onNavigateToAppearance,
                        tag = "settings_appearance_row"
                    )

                    SettingsDivider()

                    SettingsRow(
                        icon = Icons.Default.Mic,
                        title = "Voice Assistant Mode",
                        subtitle = "Waveform visualizer & microphone input",
                        onClick = onNavigateToVoice,
                        tag = "settings_voice_row"
                    )

                    SettingsDivider()

                    SettingsRow(
                        icon = Icons.Default.History,
                        title = "Chat History & Sessions",
                        subtitle = "Saved prompts, pinned sessions & logs",
                        onClick = onNavigateToHistory,
                        tag = "settings_history_row"
                    )

                    SettingsDivider()

                    SettingsRow(
                        icon = Icons.Default.Psychology,
                        title = "AI Neural Engine",
                        subtitle = "Google Gemini 3.5 Flash (Default)",
                        onClick = {},
                        tag = "settings_engine_row"
                    )
                }
            }
        }

        // Section: System & Diagnostics
        item {
            Text(
                text = "NETWORK & DIAGNOSTICS",
                style = MaterialTheme.typography.labelMedium,
                color = MayaCyanLight,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.sp
            )
        }

        item {
            Card(
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = MayaCardBg),
                border = BorderStroke(1.dp, MayaCardBorder),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.weight(1f)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(40.dp)
                                    .clip(CircleShape)
                                    .background(if (isOffline) MayaRose.copy(alpha = 0.2f) else MayaEmerald.copy(alpha = 0.2f)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = if (isOffline) Icons.Default.WifiOff else Icons.Default.Wifi,
                                    contentDescription = null,
                                    tint = if (isOffline) MayaRose else MayaEmerald,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                            Spacer(modifier = Modifier.width(12.dp))
                            Column {
                                Text(
                                    text = if (isOffline) "Simulate Offline Mode (On)" else "Network Status (Online)",
                                    style = MaterialTheme.typography.titleMedium,
                                    color = MayaTextWhite,
                                    fontWeight = FontWeight.SemiBold
                                )
                                Text(
                                    text = if (isOffline) "Local intelligence fallback active" else "Connected to Gemini Cloud Core",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MayaTextSecondary
                                )
                            }
                        }

                        Switch(
                            checked = isOffline,
                            onCheckedChange = onToggleOffline,
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = MayaRose,
                                checkedTrackColor = MayaRose.copy(alpha = 0.3f),
                                uncheckedThumbColor = MayaEmerald,
                                uncheckedTrackColor = MayaEmerald.copy(alpha = 0.3f)
                            ),
                            modifier = Modifier.testTag("offline_toggle_switch")
                        )
                    }

                    SettingsDivider()

                    SettingsRow(
                        icon = Icons.Default.Shield,
                        title = "Privacy & Encryption",
                        subtitle = "Zero data retention on device",
                        onClick = {}
                    )

                    SettingsDivider()

                    SettingsRow(
                        icon = Icons.Default.Info,
                        title = "About Maya AI Assistant",
                        subtitle = "v2.5.0 Pro Neural Engine (Build 420)",
                        onClick = {}
                    )
                }
            }
        }
    }
}

@Composable
fun SettingsRow(
    icon: ImageVector,
    title: String,
    subtitle: String,
    onClick: () -> Unit,
    tag: String = "settings_row_$title"
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(16.dp)
            .testTag(tag),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.weight(1f)
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(Color(0xFF101624))
                    .border(1.dp, MayaCardBorder, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = MayaCyanLight,
                    modifier = Modifier.size(20.dp)
                )
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    color = MayaTextWhite,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodySmall,
                    color = MayaTextSecondary
                )
            }
        }

        Icon(
            imageVector = Icons.Default.ArrowForwardIos,
            contentDescription = null,
            tint = MayaTextTertiary,
            modifier = Modifier.size(14.dp)
        )
    }
}

@Composable
fun SettingsDivider() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(1.dp)
            .background(MayaCardBorder.copy(alpha = 0.5f))
    )
}
