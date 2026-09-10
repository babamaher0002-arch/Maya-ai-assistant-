package com.example.ui.screens

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
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
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CallEnd
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.MicOff
import androidx.compose.material.icons.filled.VolumeMute
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.model.AgentPersona
import com.example.data.model.MayaEmotion
import com.example.ui.components.MayaGlowingOrb
import com.example.ui.theme.MayaBgDark
import com.example.ui.theme.MayaCardBg
import com.example.ui.theme.MayaCardBorder
import com.example.ui.theme.MayaCyan
import com.example.ui.theme.MayaCyanLight
import com.example.ui.theme.MayaEmerald
import com.example.ui.theme.MayaRose
import com.example.ui.theme.MayaTextSecondary
import com.example.ui.theme.MayaTextWhite
import com.example.ui.theme.MayaViolet
import com.example.ui.theme.MayaVioletLight
import kotlinx.coroutines.delay

enum class VoiceModeTab(val title: String, val icon: String) {
    VOICE_WAVE("Voice", "🎙️"),
    LIVE_CALL("Call", "📞"),
    MULTI_LANG("Translate", "🌐"),
    SCREEN_VISION("Vision", "👁️")
}

@Composable
fun VoiceAssistantScreen(
    currentAgent: AgentPersona,
    onCloseVoice: () -> Unit,
    onVoiceInputCaptured: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedTab by remember { mutableStateOf(VoiceModeTab.VOICE_WAVE) }
    var currentEmotion by remember { mutableStateOf(MayaEmotion.HAPPY) }
    var isMuted by remember { mutableStateOf(false) }
    var isSpeakerOn by remember { mutableStateOf(true) }
    var callSeconds by remember { mutableIntStateOf(168) } // 02:48
    var voiceStateIndex by remember { mutableIntStateOf(0) }
    var selectedLanguage by remember { mutableStateOf("Hindi (हिंदी)") }

    val languages = listOf(
        "Hindi (हिंदी)", "Spanish (Español)", "Japanese (日本語)",
        "French (Français)", "German (Deutsch)", "Arabic (العربية)",
        "Urdu (اردو)", "English (US)"
    )

    val voiceStates = listOf(
        Pair("Listening...", "Say something like 'Analyze the codebase architecture' or 'Summarize recent insights'"),
        Pair("Processing...", "Transcribing audio waveforms and querying neural core..."),
        Pair("Maya Speaking", "\"I'm synthesizing the response for you. All systems are operating smoothly.\"")
    )

    // Increment call duration timer
    LaunchedEffect(selectedTab) {
        if (selectedTab == VoiceModeTab.LIVE_CALL) {
            while (true) {
                delay(1000)
                callSeconds++
            }
        }
    }

    // Simulate animated speech turn-taking
    LaunchedEffect(isMuted, selectedTab) {
        if (!isMuted && selectedTab == VoiceModeTab.VOICE_WAVE) {
            while (true) {
                delay(3200)
                voiceStateIndex = 1
                delay(1800)
                voiceStateIndex = 2
                delay(4200)
                voiceStateIndex = 0
            }
        }
    }

    // Sound wave pulse animation
    val infiniteTransition = rememberInfiniteTransition(label = "wave_anim")
    val wave1 by infiniteTransition.animateFloat(
        initialValue = 0.35f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(400, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "wave1"
    )
    val wave2 by infiniteTransition.animateFloat(
        initialValue = 0.2f,
        targetValue = 0.9f,
        animationSpec = infiniteRepeatable(
            animation = tween(550, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "wave2"
    )
    val wave3 by infiniteTransition.animateFloat(
        initialValue = 0.4f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(320, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "wave3"
    )
    val scanLineAnim by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(2200, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "scan_line"
    )

    val minutes = callSeconds / 60
    val seconds = callSeconds % 60
    val formattedDuration = "%02d:%02d".format(minutes, seconds)

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MayaBgDark)
            .padding(16.dp)
            .testTag("voice_assistant_screen"),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        // 1. Top Bar & Mode Switcher
        Column(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = onCloseVoice,
                    modifier = Modifier.testTag("voice_back_btn")
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Close",
                        tint = MayaTextWhite
                    )
                }

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "Maya AI Core",
                        style = MaterialTheme.typography.titleMedium,
                        color = MayaTextWhite,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Real-time Neural Voice",
                        style = MaterialTheme.typography.labelSmall,
                        color = MayaCyanLight
                    )
                }

                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = MayaEmerald.copy(alpha = 0.2f),
                    border = BorderStroke(1.dp, MayaEmerald.copy(alpha = 0.4f))
                ) {
                    Text(
                        text = "Active",
                        style = MaterialTheme.typography.labelSmall,
                        color = MayaEmerald,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Mode Tabs (Screen 04, 07, 06, 08)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF131A29), RoundedCornerShape(16.dp))
                    .padding(4.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                VoiceModeTab.values().forEach { tab ->
                    val isTabSelected = selectedTab == tab
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = if (isTabSelected) MayaViolet else Color.Transparent,
                        modifier = Modifier
                            .weight(1f)
                            .clickable { selectedTab = tab }
                    ) {
                        Row(
                            modifier = Modifier.padding(vertical = 8.dp),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = tab.icon, fontSize = 14.sp)
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = tab.title,
                                style = MaterialTheme.typography.labelSmall,
                                color = if (isTabSelected) MayaTextWhite else MayaTextSecondary,
                                fontWeight = if (isTabSelected) FontWeight.Bold else FontWeight.Normal
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Screen 05: Emotion Selector Quick Row
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                MayaEmotion.values().forEach { emotion ->
                    val isSelected = emotion == currentEmotion
                    val color = Color(emotion.badgeColorHex)
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = if (isSelected) color.copy(alpha = 0.25f) else MayaCardBg,
                        border = BorderStroke(1.dp, if (isSelected) color else MayaCardBorder),
                        modifier = Modifier.clickable { currentEmotion = emotion }
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = emotion.emoji, fontSize = 13.sp)
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = emotion.title,
                                style = MaterialTheme.typography.labelSmall,
                                color = if (isSelected) MayaTextWhite else MayaTextSecondary
                            )
                        }
                    }
                }
            }
        }

        // 2. Middle Content Area based on Mode
        when (selectedTab) {
            // Screen 04: VOICE ASSISTANT (TALKING)
            VoiceModeTab.VOICE_WAVE -> {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.size(190.dp)
                    ) {
                        MayaGlowingOrb(size = 180.dp)
                    }

                    Spacer(modifier = Modifier.height(18.dp))

                    Text(
                        text = "${currentEmotion.emoji} ${voiceStates[voiceStateIndex].first}",
                        style = MaterialTheme.typography.titleLarge,
                        color = MayaTextWhite,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = voiceStates[voiceStateIndex].second,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MayaTextSecondary,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(horizontal = 24.dp)
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    // Dynamic Sound Waveform Bars
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.height(56.dp)
                    ) {
                        val heights = listOf(
                            wave1 * 34, wave2 * 48, wave3 * 54, wave1 * 40,
                            wave2 * 56, wave3 * 44, wave1 * 50, wave2 * 36
                        )
                        heights.forEach { h ->
                            Box(
                                modifier = Modifier
                                    .width(6.dp)
                                    .height(h.dp.coerceAtLeast(10.dp))
                                    .clip(RoundedCornerShape(3.dp))
                                    .background(
                                        Brush.verticalGradient(
                                            listOf(MayaCyanLight, Color(currentEmotion.badgeColorHex))
                                        )
                                    )
                            )
                        }
                    }
                }
            }

            // Screen 07: REAL TIME VOICE CALL
            VoiceModeTab.LIVE_CALL -> {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Call Avatar with breathing circular border
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.size(170.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size((150 + (wave1 * 20)).dp)
                                .clip(CircleShape)
                                .background(MayaCyan.copy(alpha = 0.15f))
                        )
                        Image(
                            painter = painterResource(id = R.drawable.img_maya_avatar),
                            contentDescription = "Maya AI Caller",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(140.dp)
                                .clip(CircleShape)
                                .border(3.dp, MayaCyanLight, CircleShape)
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "Maya AI Assistant",
                        style = MaterialTheme.typography.headlineSmall,
                        color = MayaTextWhite,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = "Call in progress • $formattedDuration",
                        style = MaterialTheme.typography.titleMedium,
                        color = MayaEmerald,
                        fontWeight = FontWeight.SemiBold
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Emotion: ${currentEmotion.emoji} ${currentEmotion.title} • \"${currentEmotion.quote}\"",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color(currentEmotion.badgeColorHex),
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Audio visualization bars during call
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.height(40.dp)
                    ) {
                        val heights = listOf(wave2 * 30, wave1 * 38, wave3 * 42, wave2 * 26, wave1 * 36)
                        heights.forEach { h ->
                            Box(
                                modifier = Modifier
                                    .width(5.dp)
                                    .height(h.dp.coerceAtLeast(8.dp))
                                    .clip(RoundedCornerShape(2.5.dp))
                                    .background(MayaCyanLight)
                            )
                        }
                    }
                }
            }

            // Screen 06: MULTI-LANGUAGE VOICE TRANSLATION
            VoiceModeTab.MULTI_LANG -> {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Real-Time Language Translation",
                        style = MaterialTheme.typography.titleMedium,
                        color = MayaTextWhite,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    // Language chips
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .horizontalScroll(rememberScrollState()),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        languages.forEach { lang ->
                            val isLangSelected = lang == selectedLanguage
                            Surface(
                                shape = RoundedCornerShape(12.dp),
                                color = if (isLangSelected) MayaCyan else MayaCardBg,
                                border = BorderStroke(1.dp, if (isLangSelected) MayaCyanLight else MayaCardBorder),
                                modifier = Modifier.clickable { selectedLanguage = lang }
                            ) {
                                Text(
                                    text = lang,
                                    style = MaterialTheme.typography.labelMedium,
                                    color = if (isLangSelected) Color.Black else MayaTextWhite,
                                    fontWeight = if (isLangSelected) FontWeight.Bold else FontWeight.Normal,
                                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Dual translation bubble card
                    Card(
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = MayaCardBg),
                        border = BorderStroke(1.dp, MayaCardBorder),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = "YOU SPEAK (English)",
                                style = MaterialTheme.typography.labelSmall,
                                color = MayaCyanLight,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "\"Maya, can you explain the architecture of this Android application?\"",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MayaTextWhite
                            )

                            Spacer(modifier = Modifier.height(14.dp))

                            Text(
                                text = "MAYA TRANSLATES & REPLIES ($selectedLanguage)",
                                style = MaterialTheme.typography.labelSmall,
                                color = MayaEmerald,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = if (selectedLanguage.startsWith("Hindi"))
                                    "\"हाँ, यह एप्लिकेशन Clean Architecture और Jetpack Compose पर बनी है। सारे घटक वास्तविक समय में सिंक होते हैं।\""
                                else
                                    "\"Oui, cette application utilise une architecture moderne avec Jetpack Compose et Gemini AI intégrés en temps réel.\"",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MayaTextWhite,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                }
            }

            // Screen 08: SCREEN & VISION
            VoiceModeTab.SCREEN_VISION -> {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Vision Scanner Viewport Frame
                    Card(
                        shape = RoundedCornerShape(18.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFF0F172A)),
                        border = BorderStroke(1.5.dp, MayaCyanLight),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(180.dp)
                    ) {
                        Box(modifier = Modifier.fillMaxSize()) {
                            // Scanner cyber image
                            Image(
                                painter = painterResource(id = R.drawable.img_maya_cyber),
                                contentDescription = "Screen Scan",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .clip(RoundedCornerShape(18.dp))
                            )

                            // Overlay dark shade
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(Color.Black.copy(alpha = 0.45f))
                            )

                            // Laser scan line
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(2.dp)
                                    .offset(y = (scanLineAnim * 170).dp)
                                    .background(MayaCyanLight)
                            )

                            // Status badge
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = Color.Black.copy(alpha = 0.7f),
                                modifier = Modifier
                                    .align(Alignment.BottomStart)
                                    .padding(10.dp)
                            ) {
                                Row(
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .size(8.dp)
                                            .clip(CircleShape)
                                            .background(MayaEmerald)
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(
                                        text = "Vision AI: Real-Time Analyzing",
                                        style = MaterialTheme.typography.labelSmall,
                                        color = MayaCyanLight
                                    )
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "Maya can see your screen and analyze anything in real-time.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MayaTextSecondary,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    // Action buttons for Vision
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Button(
                            onClick = {
                                onVoiceInputCaptured("Analyze the code and UI on this screen for errors.")
                            },
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = MayaViolet),
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("Detect Bugs", fontSize = 12.sp)
                        }

                        Button(
                            onClick = {
                                onVoiceInputCaptured("Explain the current screen UI and architecture.")
                            },
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = MayaCardBg),
                            border = BorderStroke(1.dp, MayaCardBorder),
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("Explain UI", fontSize = 12.sp, color = MayaTextWhite)
                        }
                    }
                }
            }
        }

        // 3. Bottom Controls (Screen 04 & Screen 07 Call Controls)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Speaker toggle
            IconButton(
                onClick = { isSpeakerOn = !isSpeakerOn },
                modifier = Modifier
                    .size(54.dp)
                    .clip(CircleShape)
                    .background(MayaCardBg)
                    .border(1.dp, MayaCardBorder, CircleShape)
            ) {
                Icon(
                    imageVector = if (isSpeakerOn) Icons.Default.VolumeUp else Icons.Default.VolumeMute,
                    contentDescription = "Speaker",
                    tint = if (isSpeakerOn) MayaCyanLight else MayaTextSecondary
                )
            }

            // Primary Action Button (Mic or End Call in Red)
            if (selectedTab == VoiceModeTab.LIVE_CALL) {
                // End Call Red Button (Screen 07)
                Box(
                    modifier = Modifier
                        .size(72.dp)
                        .clip(CircleShape)
                        .background(MayaRose)
                        .clickable(onClick = onCloseVoice)
                        .testTag("end_call_btn"),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.CallEnd,
                        contentDescription = "End Call",
                        tint = Color.White,
                        modifier = Modifier.size(34.dp)
                    )
                }
            } else {
                // Primary Mute / Talk Button (Screen 04)
                Box(
                    modifier = Modifier
                        .size(72.dp)
                        .clip(CircleShape)
                        .background(if (isMuted) MayaRose else MayaViolet)
                        .clickable { isMuted = !isMuted }
                        .testTag("voice_mute_toggle_btn"),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = if (isMuted) Icons.Default.MicOff else Icons.Default.Mic,
                        contentDescription = if (isMuted) "Unmute" else "Mute",
                        tint = MayaTextWhite,
                        modifier = Modifier.size(32.dp)
                    )
                }
            }

            // Quick Done or Push to Chat Button
            IconButton(
                onClick = {
                    onVoiceInputCaptured("Summarize our voice conversation and provide key takeaways.")
                },
                modifier = Modifier
                    .size(54.dp)
                    .clip(CircleShape)
                    .background(MayaCardBg)
                    .border(1.dp, MayaCardBorder, CircleShape)
                    .testTag("voice_done_to_chat_btn")
            ) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Done",
                    tint = MayaTextWhite
                )
            }
        }
    }
}
