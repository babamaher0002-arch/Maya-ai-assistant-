package com.example.ui.screens

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.WorkspacePremium
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.UserProfile
import com.example.ui.components.MayaGradientButton
import com.example.ui.theme.MayaBgDark
import com.example.ui.theme.MayaCardBg
import com.example.ui.theme.MayaCardBorder
import com.example.ui.theme.MayaCyan
import com.example.ui.theme.MayaCyanLight
import com.example.ui.theme.MayaEmerald
import com.example.ui.theme.MayaTextSecondary
import com.example.ui.theme.MayaTextTertiary
import com.example.ui.theme.MayaTextWhite
import com.example.ui.theme.MayaViolet
import com.example.ui.theme.MayaVioletLight

@Composable
fun ProfileScreen(
    userProfile: UserProfile,
    onBack: () -> Unit,
    onSaveProfile: (String, String) -> Unit,
    modifier: Modifier = Modifier
) {
    var name by remember { mutableStateOf(userProfile.name) }
    var email by remember { mutableStateOf(userProfile.email) }
    val context = LocalContext.current

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MayaBgDark)
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
            .testTag("profile_screen")
    ) {
        // Top App Bar
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = onBack,
                modifier = Modifier.testTag("profile_back_btn")
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = MayaTextWhite
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "User Profile",
                style = MaterialTheme.typography.titleLarge,
                color = MayaTextWhite,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Profile Avatar & Tier
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(90.dp)
                    .clip(CircleShape)
                    .background(Brush.linearGradient(listOf(MayaViolet, MayaCyan))),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = name.split(" ").mapNotNull { it.firstOrNull()?.toString() }.joinToString(""),
                    style = MaterialTheme.typography.headlineMedium,
                    color = MayaTextWhite,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(14.dp))

            Surface(
                shape = RoundedCornerShape(20.dp),
                color = MayaCardBg,
                border = BorderStroke(1.dp, MayaEmerald.copy(alpha = 0.5f))
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.WorkspacePremium,
                        contentDescription = null,
                        tint = MayaEmerald,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = userProfile.tier,
                        style = MaterialTheme.typography.labelMedium,
                        color = MayaEmerald,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(28.dp))

        // Telemetry & Usage Stats Grid
        Text(
            text = "USAGE & TELEMETRY",
            style = MaterialTheme.typography.labelMedium,
            color = MayaCyanLight,
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.sp
        )

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            StatCard(
                label = "Queries Today",
                value = "${userProfile.queriesToday}",
                emoji = "⚡",
                modifier = Modifier.weight(1f)
            )
            StatCard(
                label = "Total Prompts",
                value = "${userProfile.totalPrompts}",
                emoji = "💬",
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            StatCard(
                label = "Active Projects",
                value = "${userProfile.projectsCount}",
                emoji = "📁",
                modifier = Modifier.weight(1f)
            )
            StatCard(
                label = "Voice Minutes",
                value = "${userProfile.voiceMinutes}m",
                emoji = "🎙️",
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Edit Profile Form
        Text(
            text = "ACCOUNT DETAILS",
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
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Display Name", color = MayaTextSecondary) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("profile_name_input"),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = MayaCyanLight,
                        unfocusedBorderColor = MayaCardBorder,
                        focusedTextColor = MayaTextWhite,
                        unfocusedTextColor = MayaTextWhite
                    )
                )

                Spacer(modifier = Modifier.height(14.dp))

                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Email Address", color = MayaTextSecondary) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("profile_email_input"),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = MayaCyanLight,
                        unfocusedBorderColor = MayaCardBorder,
                        focusedTextColor = MayaTextWhite,
                        unfocusedTextColor = MayaTextWhite
                    )
                )

                Spacer(modifier = Modifier.height(18.dp))

                MayaGradientButton(
                    text = "Save Profile Changes",
                    icon = Icons.Default.Check,
                    onClick = {
                        onSaveProfile(name, email)
                        Toast.makeText(context, "Profile updated successfully", Toast.LENGTH_SHORT).show()
                    },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@Composable
fun StatCard(
    label: String,
    value: String,
    emoji: String,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = MayaCardBg),
        border = BorderStroke(1.dp, MayaCardBorder),
        modifier = modifier
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Text(text = emoji, fontSize = 20.sp)
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = value,
                style = MaterialTheme.typography.titleLarge,
                color = MayaTextWhite,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = label,
                style = MaterialTheme.typography.labelSmall,
                color = MayaTextSecondary
            )
        }
    }
}
