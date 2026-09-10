package com.example.data.model

import java.util.UUID

data class ChatMessage(
    val id: String = UUID.randomUUID().toString(),
    val role: MessageRole,
    val content: String,
    val timestamp: Long = System.currentTimeMillis(),
    val agentName: String = "Maya",
    val agentEmoji: String = "✨",
    val isStreaming: Boolean = false,
    val isError: Boolean = false,
    val quickReplies: List<String> = emptyList()
)

enum class MessageRole {
    USER, MODEL, SYSTEM
}

data class AgentPersona(
    val id: String,
    val name: String,
    val roleTitle: String,
    val emoji: String,
    val category: String, // "Active", "Favorites", "Development", "Creative", etc.
    val description: String,
    val systemPrompt: String,
    val rating: Float = 4.9f,
    val sessionCount: String = "10k+",
    val accentHex: Long = 0xFF7C3AED,
    val isFavorite: Boolean = false,
    val isActive: Boolean = false,
    val isMaster: Boolean = false
)

enum class MayaEmotion(
    val title: String,
    val quote: String,
    val emoji: String,
    val badgeColorHex: Long
) {
    HAPPY("Happy", "I'm so glad!", "😄", 0xFF10B981),
    CUTE_GF("Cute (GF Mode)", "You're amazing...", "💖", 0xFFEC4899),
    ANGRY("Angry", "That's not right!", "💢", 0xFFEF4444),
    SAD("Sad", "I understand...", "😢", 0xFF60A5FA),
    EXCITED("Excited", "Let's do it!", "✨", 0xFFF59E0B),
    THINKING("Thinking", "Let me check...", "🤔", 0xFF8B5CF6)
}

data class H1TaskStep(
    val index: Int,
    val title: String,
    val status: StepStatus
)

enum class StepStatus {
    COMPLETED, IN_PROGRESS, PENDING
}

data class H1TaskState(
    val taskTitle: String = "Task: Research AI tools",
    val taskSubtitle: String = "Find best AI video generators and compare features.",
    val progress: Float = 0.68f,
    val steps: List<H1TaskStep> = listOf(
        H1TaskStep(1, "Understand requirement", StepStatus.COMPLETED),
        H1TaskStep(2, "Searching best tools", StepStatus.IN_PROGRESS),
        H1TaskStep(3, "Comparing features", StepStatus.PENDING),
        H1TaskStep(4, "Preparing summary", StepStatus.PENDING),
        H1TaskStep(5, "Final report to you", StepStatus.PENDING)
    ),
    val liveLogs: List<String> = listOf(
        "Searching on web...",
        "Reading results...",
        "Analyzing data...",
        "Almost done..."
    ),
    val isRunning: Boolean = true
)

data class VoiceCallState(
    val callerName: String = "Ahmed",
    val callerSubtitle: String = "External User",
    val callDurationSeconds: Int = 28,
    val isMuted: Boolean = false,
    val isSpeakerOn: Boolean = true,
    val isCallActive: Boolean = true
)

data class MemoryItem(
    val id: String = UUID.randomUUID().toString(),
    val title: String,
    val timeLabel: String,
    val category: MemoryCategory,
    val icon: String
)

enum class MemoryCategory {
    ALL, CONVERSATIONS, FILES, NOTES
}

data class UserProject(
    val id: String = UUID.randomUUID().toString(),
    val title: String,
    val description: String,
    val category: String,
    val status: ProjectStatus = ProjectStatus.ACTIVE,
    val agentId: String = "coding_agent",
    val lastUpdated: Long = System.currentTimeMillis(),
    val tokenCount: Int = 1250,
    val currentStep: Int = 4
)

enum class ProjectStatus {
    ACTIVE, IN_REVIEW, DRAFT, COMPLETED
}

data class ProjectWorkflowStep(
    val number: Int,
    val title: String,
    val subtitle: String,
    val isCompleted: Boolean = false,
    val isActive: Boolean = false
)

data class MayaTool(
    val id: String,
    val title: String,
    val description: String,
    val emoji: String,
    val category: ToolCategory, // ALL, APPS, SCRIPTS, SYSTEM
    val actionPrompt: String
)

enum class ToolCategory {
    ALL, APPS, SCRIPTS, SYSTEM
}

data class HistorySession(
    val id: String = UUID.randomUUID().toString(),
    val title: String,
    val agentName: String,
    val agentEmoji: String,
    val preview: String,
    val timestamp: Long = System.currentTimeMillis(),
    val isPinned: Boolean = false,
    val messageCount: Int = 4
)

data class UserProfile(
    val name: String = "Zabi Ch",
    val username: String = "@zabich",
    val email: String = "zabi@maya.ai",
    val tier: String = "Maya Pro Lifetime",
    val projectsCount: Int = 12,
    val agentsCount: Int = 30,
    val membershipMonths: Int = 6,
    val queriesToday: Int = 38,
    val totalPrompts: Int = 1420,
    val voiceMinutes: Int = 76
)

data class AppSettings(
    val isDarkMode: Boolean = true,
    val selectedAccentHex: Long = 0xFF7C3AED,
    val selectedModel: String = "gemini-3.5-flash",
    val backgroundModeAlwaysListen: Boolean = true,
    val voiceSpeed: Float = 1.0f,
    val voicePitch: Float = 1.0f,
    val speechLanguage: String = "English (US)",
    val notificationsEnabled: Boolean = true,
    val hapticFeedback: Boolean = true,
    val autoScrollChat: Boolean = true
)

