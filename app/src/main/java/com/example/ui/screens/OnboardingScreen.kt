package com.example.ui.screens

import androidx.compose.animation.AnimatedContent
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.MayaGlowingOrb
import com.example.ui.components.MayaGradientButton
import com.example.ui.theme.MayaBgDark
import com.example.ui.theme.MayaCardBorder
import com.example.ui.theme.MayaCyan
import com.example.ui.theme.MayaCyanLight
import com.example.ui.theme.MayaTextSecondary
import com.example.ui.theme.MayaTextWhite
import com.example.ui.theme.MayaViolet

data class OnboardingStep(
    val title: String,
    val subtitle: String,
    val description: String,
    val badge: String
)

@Composable
fun OnboardingScreen(
    onFinishOnboarding: () -> Unit,
    modifier: Modifier = Modifier
) {
    val steps = listOf(
        OnboardingStep(
            title = "Meet Maya",
            subtitle = "Autonomous Conversational Intelligence",
            description = "Experience fluid, multimodal interaction with proactive reasoning, code synthesis, and intuitive understanding.",
            badge = "Conversational Partner"
        ),
        OnboardingStep(
            title = "Specialized AI Agents",
            subtitle = "Expertise on Demand",
            description = "Select custom agent personas tailored for Systems Architecture, Deep Research, Creative Writing, Translation, and Business Strategy.",
            badge = "6+ Domain Specialists"
        ),
        OnboardingStep(
            title = "Live Voice & Workspaces",
            subtitle = "Seamless Multimodal Execution",
            description = "Engage in natural hands-free voice dialog with real-time waveform pulsation and organize your missions in Project Workspaces.",
            badge = "Zero Latency Voice"
        )
    )

    var currentStep by remember { mutableIntStateOf(0) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MayaBgDark)
            .padding(horizontal = 24.dp, vertical = 32.dp)
            .testTag("onboarding_screen"),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        // Top skip bar
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            TextButton(
                onClick = onFinishOnboarding,
                modifier = Modifier.testTag("skip_onboarding_btn")
            ) {
                Text(
                    text = "Skip",
                    color = MayaTextSecondary,
                    style = MaterialTheme.typography.labelLarge
                )
            }
        }

        // Center Content
        AnimatedContent(
            targetState = currentStep,
            label = "onboarding_content"
        ) { stepIndex ->
            val step = steps[stepIndex]
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                MayaGlowingOrb(size = 130.dp)

                Spacer(modifier = Modifier.height(28.dp))

                Box(
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(MayaViolet.copy(alpha = 0.2f))
                        .padding(horizontal = 14.dp, vertical = 6.dp)
                ) {
                    Text(
                        text = step.badge,
                        style = MaterialTheme.typography.labelMedium,
                        color = MayaCyanLight,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = step.title,
                    style = MaterialTheme.typography.headlineMedium,
                    color = MayaTextWhite,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = step.subtitle,
                    style = MaterialTheme.typography.titleMedium,
                    color = MayaCyan,
                    fontWeight = FontWeight.Medium,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = step.description,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MayaTextSecondary,
                    textAlign = TextAlign.Center,
                    lineHeight = 24.sp,
                    modifier = Modifier.padding(horizontal = 8.dp)
                )
            }
        }

        // Bottom Navigation Indicators & Action
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            // Pager dots
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = 24.dp)
            ) {
                steps.indices.forEach { index ->
                    val isSelected = index == currentStep
                    Box(
                        modifier = Modifier
                            .padding(horizontal = 4.dp)
                            .height(6.dp)
                            .width(if (isSelected) 24.dp else 6.dp)
                            .clip(CircleShape)
                            .background(if (isSelected) MayaCyan else MayaCardBorder)
                    )
                }
            }

            MayaGradientButton(
                text = if (currentStep < steps.size - 1) "Continue" else "Get Started",
                onClick = {
                    if (currentStep < steps.size - 1) {
                        currentStep++
                    } else {
                        onFinishOnboarding()
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}
