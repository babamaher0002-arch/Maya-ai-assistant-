package com.example.ui.screens

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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.AttachFile
import androidx.compose.material.icons.filled.DeleteSweep
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.AgentPersona
import com.example.data.model.ChatMessage
import com.example.ui.components.MessageBubbleView
import com.example.ui.theme.MayaBgDark
import com.example.ui.theme.MayaCardBg
import com.example.ui.theme.MayaCardBorder
import com.example.ui.theme.MayaCyanLight
import com.example.ui.theme.MayaTextSecondary
import com.example.ui.theme.MayaTextTertiary
import com.example.ui.theme.MayaTextWhite
import com.example.ui.theme.MayaViolet
import com.example.ui.theme.MayaVioletLight
import kotlinx.coroutines.launch

@Composable
fun ChatScreen(
    messages: List<ChatMessage>,
    currentAgent: AgentPersona,
    agents: List<AgentPersona>,
    onSelectAgent: (AgentPersona) -> Unit,
    onSendMessage: (String) -> Unit,
    onClearChat: () -> Unit,
    onOpenVoice: () -> Unit,
    initialPrompt: String? = null,
    modifier: Modifier = Modifier
) {
    var inputText by remember { mutableStateOf(initialPrompt ?: "") }
    var showAgentMenu by remember { mutableStateOf(false) }
    val listState = rememberLazyListState()
    val scope = rememberCoroutineScope()

    val followUpChips = listOf(
        "Explain step-by-step",
        "Provide code example",
        "Alternative approaches?",
        "Summarize in 3 bullets"
    )

    LaunchedEffect(messages.size) {
        if (messages.isNotEmpty()) {
            listState.animateScrollToItem(messages.size - 1)
        }
    }

    LaunchedEffect(initialPrompt) {
        if (!initialPrompt.isNullOrBlank()) {
            onSendMessage(initialPrompt)
            inputText = ""
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MayaBgDark)
            .testTag("chat_screen")
    ) {
        // Chat Header with Persona Selector
        Surface(
            color = MayaCardBg,
            border = BorderStroke(1.dp, MayaCardBorder),
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Agent dropdown trigger
                Box {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .clip(RoundedCornerShape(20.dp))
                            .background(Color(0xFF101522))
                            .border(1.dp, MayaCardBorder, RoundedCornerShape(20.dp))
                            .clickable { showAgentMenu = true }
                            .padding(horizontal = 10.dp, vertical = 6.dp)
                            .testTag("chat_agent_selector")
                    ) {
                        Text(text = currentAgent.emoji, fontSize = 18.sp)
                        Spacer(modifier = Modifier.width(6.dp))
                        Column {
                            Text(
                                text = currentAgent.name,
                                style = MaterialTheme.typography.labelMedium,
                                color = MayaTextWhite,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = currentAgent.roleTitle,
                                style = MaterialTheme.typography.labelSmall,
                                color = MayaCyanLight,
                                fontSize = 10.sp
                            )
                        }
                        Spacer(modifier = Modifier.width(4.dp))
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowDown,
                            contentDescription = "Switch Agent",
                            tint = MayaTextSecondary,
                            modifier = Modifier.size(16.dp)
                        )
                    }

                    DropdownMenu(
                        expanded = showAgentMenu,
                        onDismissRequest = { showAgentMenu = false },
                        modifier = Modifier
                            .background(MayaCardBg)
                            .border(1.dp, MayaCardBorder, RoundedCornerShape(8.dp))
                    ) {
                        agents.forEach { agent ->
                            DropdownMenuItem(
                                text = {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Text(text = agent.emoji, fontSize = 18.sp)
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Column {
                                            Text(
                                                text = agent.name,
                                                color = MayaTextWhite,
                                                fontWeight = FontWeight.SemiBold
                                            )
                                            Text(
                                                text = agent.roleTitle,
                                                color = MayaTextSecondary,
                                                style = MaterialTheme.typography.bodySmall
                                            )
                                        }
                                    }
                                },
                                onClick = {
                                    onSelectAgent(agent)
                                    showAgentMenu = false
                                }
                            )
                        }
                    }
                }

                // Action Icons
                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(
                        onClick = onOpenVoice,
                        modifier = Modifier.testTag("chat_voice_header_btn")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Mic,
                            contentDescription = "Voice Mode",
                            tint = MayaCyanLight
                        )
                    }
                    IconButton(
                        onClick = onClearChat,
                        modifier = Modifier.testTag("chat_clear_btn")
                    ) {
                        Icon(
                            imageVector = Icons.Default.DeleteSweep,
                            contentDescription = "Clear Chat",
                            tint = MayaTextTertiary
                        )
                    }
                }
            }
        }

        // Messages List
        LazyColumn(
            state = listState,
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(horizontal = 14.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(messages) { message ->
                MessageBubbleView(message = message)
            }
        }

        // Suggested Follow-up chips
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 14.dp, vertical = 6.dp)
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            followUpChips.forEach { chip ->
                Surface(
                    shape = RoundedCornerShape(14.dp),
                    color = Color(0xFF141A28),
                    border = BorderStroke(1.dp, MayaCardBorder),
                    modifier = Modifier.clickable {
                        onSendMessage(chip)
                    }
                ) {
                    Text(
                        text = chip,
                        style = MaterialTheme.typography.labelSmall,
                        color = MayaCyanLight,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                    )
                }
            }
        }

        // Bottom Input Bar
        Card(
            shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
            colors = CardDefaults.cardColors(containerColor = MayaCardBg),
            border = BorderStroke(1.dp, MayaCardBorder),
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = inputText,
                    onValueChange = { inputText = it },
                    placeholder = {
                        Text(
                            text = "Message ${currentAgent.name}...",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MayaTextTertiary
                        )
                    },
                    modifier = Modifier
                        .weight(1f)
                        .testTag("chat_text_input"),
                    shape = RoundedCornerShape(16.dp),
                    maxLines = 4,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = MayaViolet,
                        unfocusedBorderColor = MayaCardBorder,
                        focusedTextColor = MayaTextWhite,
                        unfocusedTextColor = MayaTextWhite,
                        focusedContainerColor = Color(0xFF0F1420),
                        unfocusedContainerColor = Color(0xFF0F1420)
                    )
                )

                Spacer(modifier = Modifier.width(6.dp))

                IconButton(
                    onClick = onOpenVoice,
                    modifier = Modifier
                        .size(44.dp)
                        .clip(CircleShape)
                        .background(Color(0xFF101624))
                        .border(1.dp, MayaCardBorder, CircleShape)
                        .testTag("chat_voice_input_btn")
                ) {
                    Icon(
                        imageVector = Icons.Default.Mic,
                        contentDescription = "Dictate",
                        tint = MayaCyanLight,
                        modifier = Modifier.size(22.dp)
                    )
                }

                Spacer(modifier = Modifier.width(6.dp))

                IconButton(
                    onClick = {
                        if (inputText.isNotBlank()) {
                            val textToSend = inputText
                            inputText = ""
                            onSendMessage(textToSend)
                        }
                    },
                    modifier = Modifier
                        .size(44.dp)
                        .clip(CircleShape)
                        .background(if (inputText.isNotBlank()) MayaViolet else MayaCardBorder)
                        .testTag("chat_send_btn")
                ) {
                    Icon(
                        imageVector = Icons.Default.Send,
                        contentDescription = "Send",
                        tint = MayaTextWhite,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }
    }
}
