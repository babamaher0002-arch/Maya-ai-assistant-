package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Apps
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.SmartToy
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.R
import com.example.data.model.AgentPersona
import com.example.data.model.H1TaskState
import com.example.data.model.HistorySession
import com.example.data.model.MayaEmotion
import com.example.data.model.MemoryItem
import com.example.data.model.UserProfile
import com.example.ui.components.MayaGlowingOrb
import com.example.ui.components.MayaStatusBadge
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
import com.example.ui.theme.MayaVioletLight

@Composable
fun HomeScreen(
    userProfile: UserProfile,
    agents: List<AgentPersona>,
    recentHistory: List<HistorySession>,
    isOffline: Boolean,
    currentEmotion: MayaEmotion = MayaEmotion.HAPPY,
    onSelectEmotion: (MayaEmotion) -> Unit = {},
    h1Task: H1TaskState = H1TaskState(),
    onToggleH1Task: () -> Unit = {},
    memories: List<MemoryItem> = emptyList(),
    onOpenVoice: () -> Unit,
    onOpenChatWithPrompt: (String) -> Unit,
    onSelectAgent: (AgentPersona) -> Unit,
    onOpenAgents: () -> Unit = {},
    onOpenTools: () -> Unit = {},
    onOpenProfile: () -> Unit,
    onOpenAllHistory: () -> Unit,
    modifier: Modifier = Modifier
) {
    var quickPromptText by remember { mutableStateOf("") }
    var showOtherAppsDialog by remember { mutableStateOf(false) }
    var backgroundListenEnabled by remember { mutableStateOf(true) }

    val promptSuggestions = listOf(
        "⚡ Debug Jetpack Compose code",
        "🔬 Deep research quantum AI",
        "📝 Draft executive pitch",
        "🌐 Translate to Spanish & Hindi",
        "💡 Architecture review"
    )

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(MayaBgDark)
            .testTag("home_screen"),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(18.dp)
    ) {
        // 1. Header Section with User Avatar & Online Status
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Hello, ${userProfile.name.split(" ").firstOrNull() ?: "Alex"}",
                        style = MaterialTheme.typography.headlineMedium,
                        color = MayaTextWhite,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        MayaStatusBadge(isOnline = !isOffline)
                        Spacer(modifier = Modifier.width(8.dp))
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = Color(currentEmotion.badgeColorHex).copy(alpha = 0.2f),
                            border = BorderStroke(1.dp, Color(currentEmotion.badgeColorHex).copy(alpha = 0.4f))
                        ) {
                            Text(
                                text = "${currentEmotion.emoji} ${currentEmotion.title}",
                                style = MaterialTheme.typography.labelSmall,
                                color = Color(currentEmotion.badgeColorHex),
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp),
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }

                // Profile Avatar Button
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .border(1.5.dp, MayaCyanLight, CircleShape)
                        .clickable(onClick = onOpenProfile)
                        .testTag("profile_avatar_header"),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.img_user_zabi),
                        contentDescription = "User Profile",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }

        // 2. Screen 05: EMOTIONAL STATES SELECTOR ROW
        item {
            Column {
                Text(
                    text = "Maya Emotional Mood",
                    style = MaterialTheme.typography.labelMedium,
                    color = MayaTextSecondary,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    MayaEmotion.values().forEach { emotion ->
                        val isSelected = emotion == currentEmotion
                        val badgeColor = Color(emotion.badgeColorHex)
                        Surface(
                            shape = RoundedCornerShape(14.dp),
                            color = if (isSelected) badgeColor.copy(alpha = 0.25f) else MayaCardBg,
                            border = BorderStroke(
                                1.dp,
                                if (isSelected) badgeColor else MayaCardBorder
                            ),
                            modifier = Modifier
                                .clickable { onSelectEmotion(emotion) }
                                .testTag("emotion_${emotion.name}")
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(text = emotion.emoji, fontSize = 16.sp)
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = emotion.title,
                                    style = MaterialTheme.typography.labelMedium,
                                    color = if (isSelected) MayaTextWhite else MayaTextSecondary,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                                )
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "\"${currentEmotion.quote}\"",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color(currentEmotion.badgeColorHex),
                    fontWeight = FontWeight.Medium
                )
            }
        }

        // 3. Hero Assistant Orb & Avatar Card (Screen 03 + 04)
        item {
            Card(
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = MayaCardBg),
                border = BorderStroke(1.dp, MayaCardBorder),
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("hero_orb_card")
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            Brush.verticalGradient(
                                listOf(Color(0xFF1E283D), Color(0xFF131825))
                            )
                        )
                        .padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "VOICE COMPANION",
                            style = MaterialTheme.typography.labelMedium,
                            color = MayaCyanLight,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.sp
                        )
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = MayaViolet.copy(alpha = 0.25f)
                        ) {
                            Text(
                                text = "Gemini 3.5 Flash",
                                style = MaterialTheme.typography.labelSmall,
                                color = MayaVioletLight,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Character Avatar with Pulsing Glowing Ring
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.size(120.dp)
                    ) {
                        MayaGlowingOrb(
                            size = 120.dp,
                            onClick = onOpenVoice
                        )
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    Text(
                        text = "Tap to talk with Maya",
                        style = MaterialTheme.typography.titleMedium,
                        color = MayaTextWhite,
                        fontWeight = FontWeight.SemiBold
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = "Ultra-low latency audio • Real-time interaction",
                        style = MaterialTheme.typography.bodySmall,
                        color = MayaTextSecondary
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Suggestion chips inside hero
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .horizontalScroll(rememberScrollState()),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        promptSuggestions.forEach { prompt ->
                            Surface(
                                shape = RoundedCornerShape(12.dp),
                                color = Color(0xFF0F1420),
                                border = BorderStroke(1.dp, MayaCardBorder),
                                modifier = Modifier.clickable { onOpenChatWithPrompt(prompt) }
                            ) {
                                Text(
                                    text = prompt,
                                    style = MaterialTheme.typography.labelMedium,
                                    color = MayaTextWhite,
                                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
                                )
                            }
                        }
                    }
                }
            }
        }

        // 4. Screen 09: H1 TASK SYSTEM (Autonomous Mode Active)
        item {
            Card(
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = MayaCardBg),
                border = BorderStroke(1.dp, MayaCyan.copy(alpha = 0.4f)),
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("h1_task_card")
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(10.dp)
                                    .clip(CircleShape)
                                    .background(if (h1Task.isRunning) MayaEmerald else MayaRose)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "H1 AUTONOMOUS TASK",
                                style = MaterialTheme.typography.labelSmall,
                                color = MayaCyanLight,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 1.sp
                            )
                        }

                        IconButton(
                            onClick = onToggleH1Task,
                            modifier = Modifier.size(32.dp)
                        ) {
                            Icon(
                                imageVector = if (h1Task.isRunning) Icons.Default.Pause else Icons.Default.PlayArrow,
                                contentDescription = if (h1Task.isRunning) "Pause Task" else "Resume Task",
                                tint = if (h1Task.isRunning) MayaCyanLight else MayaEmerald,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = h1Task.taskTitle,
                        style = MaterialTheme.typography.titleMedium,
                        color = MayaTextWhite,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = h1Task.taskSubtitle,
                        style = MaterialTheme.typography.bodySmall,
                        color = MayaTextSecondary
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // Progress Bar
                    Column {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "Step 2 of 5: Searching best tools",
                                style = MaterialTheme.typography.labelSmall,
                                color = MayaCyanLight
                            )
                            Text(
                                text = "${(h1Task.progress * 100).toInt()}%",
                                style = MaterialTheme.typography.labelSmall,
                                color = MayaTextWhite,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        Spacer(modifier = Modifier.height(6.dp))
                        LinearProgressIndicator(
                            progress = { h1Task.progress },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(6.dp)
                                .clip(RoundedCornerShape(3.dp)),
                            color = MayaCyanLight,
                            trackColor = Color(0xFF1E293B),
                        )
                    }
                }
            }
        }

        // 5. Quick Actions Grid (Screen 03 & 15)
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                // Voice Call Shortcut
                QuickActionButton(
                    icon = Icons.Default.Mic,
                    title = "Voice Call",
                    subtitle = "Real-time",
                    accentColor = MayaCyan,
                    onClick = onOpenVoice,
                    modifier = Modifier.weight(1f)
                )

                // 30+ Agents Shortcut
                QuickActionButton(
                    icon = Icons.Default.Group,
                    title = "30+ Agents",
                    subtitle = "Specialists",
                    accentColor = MayaViolet,
                    onClick = onOpenAgents,
                    modifier = Modifier.weight(1f)
                )

                // Connected Apps Shortcut (Screen 15)
                QuickActionButton(
                    icon = Icons.Default.Apps,
                    title = "Other Apps",
                    subtitle = "Say 'Maya'",
                    accentColor = MayaEmerald,
                    onClick = { showOtherAppsDialog = true },
                    modifier = Modifier.weight(1f)
                )
            }
        }

        // 6. Quick Ask Input Bar
        item {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MayaCardBg),
                border = BorderStroke(1.dp, MayaCardBorder),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp, vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(
                        value = quickPromptText,
                        onValueChange = { quickPromptText = it },
                        placeholder = {
                            Text("Ask Maya anything...", color = MayaTextTertiary, style = MaterialTheme.typography.bodyMedium)
                        },
                        modifier = Modifier
                            .weight(1f)
                            .testTag("home_quick_prompt_input"),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color.Transparent,
                            unfocusedBorderColor = Color.Transparent,
                            focusedTextColor = MayaTextWhite,
                            unfocusedTextColor = MayaTextWhite
                        ),
                        singleLine = true
                    )

                    IconButton(
                        onClick = onOpenVoice,
                        modifier = Modifier.testTag("home_voice_quick_btn")
                    ) {
                        Icon(imageVector = Icons.Default.Mic, contentDescription = "Voice input", tint = MayaCyanLight)
                    }

                    IconButton(
                        onClick = {
                            if (quickPromptText.isNotBlank()) {
                                onOpenChatWithPrompt(quickPromptText)
                                quickPromptText = ""
                            }
                        },
                        modifier = Modifier.testTag("home_send_quick_btn")
                    ) {
                        Icon(imageVector = Icons.Default.Send, contentDescription = "Send", tint = MayaViolet)
                    }
                }
            }
        }

        // 7. Featured Specialists Carousel (Screen 10)
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Specialized AI Agents (30+)",
                    style = MaterialTheme.typography.titleLarge,
                    color = MayaTextWhite,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "See all",
                    style = MaterialTheme.typography.labelMedium,
                    color = MayaCyanLight,
                    modifier = Modifier.clickable { onOpenAgents() }
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                items(agents) { agent ->
                    Card(
                        shape = RoundedCornerShape(18.dp),
                        colors = CardDefaults.cardColors(containerColor = MayaCardBg),
                        border = BorderStroke(1.dp, MayaCardBorder),
                        modifier = Modifier
                            .width(210.dp)
                            .clickable { onSelectAgent(agent) }
                            .testTag("agent_card_${agent.id}")
                    ) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(40.dp)
                                        .clip(CircleShape)
                                        .background(Color(agent.accentHex).copy(alpha = 0.2f)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(text = agent.emoji, fontSize = 20.sp)
                                }
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        imageVector = Icons.Default.Star,
                                        contentDescription = null,
                                        tint = Color(0xFFF59E0B),
                                        modifier = Modifier.size(14.dp)
                                    )
                                    Spacer(modifier = Modifier.width(3.dp))
                                    Text(
                                        text = "${agent.rating}",
                                        style = MaterialTheme.typography.labelSmall,
                                        color = MayaTextSecondary
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(12.dp))

                            Text(
                                text = agent.name,
                                style = MaterialTheme.typography.titleMedium,
                                color = MayaTextWhite,
                                fontWeight = FontWeight.Bold
                            )

                            Spacer(modifier = Modifier.height(2.dp))

                            Text(
                                text = agent.roleTitle,
                                style = MaterialTheme.typography.labelSmall,
                                color = MayaCyanLight
                            )

                            Spacer(modifier = Modifier.height(6.dp))

                            Text(
                                text = agent.description,
                                style = MaterialTheme.typography.bodySmall,
                                color = MayaTextSecondary,
                                maxLines = 2,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    }
                }
            }
        }

        // 8. Screen 11: Memory Bank Quick Preview
        if (memories.isNotEmpty()) {
            item {
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MayaCardBg),
                    border = BorderStroke(1.dp, MayaCardBorder),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.Psychology,
                                    contentDescription = null,
                                    tint = MayaVioletLight,
                                    modifier = Modifier.size(18.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "Maya's Memory Bank",
                                    style = MaterialTheme.typography.labelMedium,
                                    color = MayaVioletLight,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                            Text(
                                text = "${memories.size} saved",
                                style = MaterialTheme.typography.labelSmall,
                                color = MayaTextSecondary
                            )
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .horizontalScroll(rememberScrollState()),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            memories.take(4).forEach { memory ->
                                Surface(
                                    shape = RoundedCornerShape(10.dp),
                                    color = Color(0xFF131A29),
                                    border = BorderStroke(1.dp, MayaCardBorder)
                                ) {
                                    Row(
                                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(text = memory.icon, fontSize = 12.sp)
                                        Spacer(modifier = Modifier.width(6.dp))
                                        Text(
                                            text = memory.title,
                                            style = MaterialTheme.typography.labelSmall,
                                            color = MayaTextWhite
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        // 9. Recent Conversations
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Recent Sessions",
                    style = MaterialTheme.typography.titleLarge,
                    color = MayaTextWhite,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "View history",
                    style = MaterialTheme.typography.labelMedium,
                    color = MayaCyanLight,
                    modifier = Modifier.clickable(onClick = onOpenAllHistory)
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                recentHistory.take(3).forEach { session ->
                    Card(
                        shape = RoundedCornerShape(14.dp),
                        colors = CardDefaults.cardColors(containerColor = MayaCardBg),
                        border = BorderStroke(1.dp, MayaCardBorder),
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onOpenChatWithPrompt(session.title) }
                            .testTag("recent_session_${session.id}")
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(14.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(
                                modifier = Modifier.weight(1f),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(text = session.agentEmoji, fontSize = 20.sp)
                                Spacer(modifier = Modifier.width(12.dp))
                                Column {
                                    Text(
                                        text = session.title,
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = MayaTextWhite,
                                        fontWeight = FontWeight.SemiBold,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                    Text(
                                        text = session.preview,
                                        style = MaterialTheme.typography.bodySmall,
                                        color = MayaTextSecondary,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                }
                            }
                            Icon(
                                imageVector = Icons.Default.ArrowForward,
                                contentDescription = null,
                                tint = MayaTextTertiary,
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    }
                }
            }
        }
    }

    // Screen 15: WORKS WITH OTHER APPS DIALOG
    if (showOtherAppsDialog) {
        Dialog(onDismissRequest = { showOtherAppsDialog = false }) {
            Card(
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = MayaCardBg),
                border = BorderStroke(1.dp, MayaCyan.copy(alpha = 0.5f)),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .testTag("works_with_other_apps_dialog")
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Say 'Maya' Anywhere",
                            style = MaterialTheme.typography.titleLarge,
                            color = MayaTextWhite,
                            fontWeight = FontWeight.Bold
                        )
                        IconButton(
                            onClick = { showOtherAppsDialog = false },
                            modifier = Modifier.size(32.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "Close",
                                tint = MayaTextSecondary
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Maya listens even when the app is closed. Just say 'Maya' to activate in any application.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MayaTextSecondary
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    // App grid
                    val connectedApps = listOf(
                        "TikTok", "WhatsApp", "YouTube", "Instagram",
                        "Facebook", "Chrome", "Telegram", "Any App"
                    )

                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        connectedApps.chunked(4).forEach { rowApps ->
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                rowApps.forEach { appName ->
                                    Surface(
                                        shape = RoundedCornerShape(12.dp),
                                        color = Color(0xFF131A29),
                                        border = BorderStroke(1.dp, MayaCardBorder),
                                        modifier = Modifier.weight(1f)
                                    ) {
                                        Column(
                                            modifier = Modifier.padding(vertical = 10.dp),
                                            horizontalAlignment = Alignment.CenterHorizontally
                                        ) {
                                            Text(
                                                text = when (appName) {
                                                    "TikTok" -> "🎵"
                                                    "WhatsApp" -> "💬"
                                                    "YouTube" -> "▶️"
                                                    "Instagram" -> "📷"
                                                    "Facebook" -> "👥"
                                                    "Chrome" -> "🌐"
                                                    "Telegram" -> "✈️"
                                                    else -> "⚡"
                                                },
                                                fontSize = 20.sp
                                            )
                                            Spacer(modifier = Modifier.height(4.dp))
                                            Text(
                                                text = appName,
                                                style = MaterialTheme.typography.labelSmall,
                                                color = MayaTextWhite,
                                                maxLines = 1
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "Always Listen in Background",
                                style = MaterialTheme.typography.labelLarge,
                                color = MayaTextWhite,
                                fontWeight = FontWeight.SemiBold
                            )
                            Text(
                                text = "Wake word 'Maya' active",
                                style = MaterialTheme.typography.bodySmall,
                                color = MayaEmerald
                            )
                        }
                        Switch(
                            checked = backgroundListenEnabled,
                            onCheckedChange = { backgroundListenEnabled = it },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = Color.White,
                                checkedTrackColor = MayaCyan,
                                uncheckedThumbColor = MayaTextSecondary,
                                uncheckedTrackColor = MayaCardBorder
                            )
                        )
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    Button(
                        onClick = { showOtherAppsDialog = false },
                        colors = ButtonDefaults.buttonColors(containerColor = MayaCyan),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Done", color = Color.Black, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}

@Composable
private fun QuickActionButton(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    subtitle: String,
    accentColor: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MayaCardBg),
        border = BorderStroke(1.dp, MayaCardBorder),
        modifier = modifier
            .clickable(onClick = onClick)
            .testTag("quick_action_$title")
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(38.dp)
                    .clip(CircleShape)
                    .background(accentColor.copy(alpha = 0.2f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = accentColor,
                    modifier = Modifier.size(20.dp)
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = title,
                style = MaterialTheme.typography.labelMedium,
                color = MayaTextWhite,
                fontWeight = FontWeight.Bold,
                maxLines = 1
            )
            Text(
                text = subtitle,
                style = MaterialTheme.typography.labelSmall,
                color = MayaTextSecondary,
                maxLines = 1
            )
        }
    }
}

