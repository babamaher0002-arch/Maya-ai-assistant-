package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.data.repository.MayaRepository
import com.example.ui.components.MayaBottomBar
import com.example.ui.components.OfflineErrorBanner
import com.example.ui.screens.AgentsScreen
import com.example.ui.screens.AppearanceScreen
import com.example.ui.screens.AuthScreen
import com.example.ui.screens.ChatScreen
import com.example.ui.screens.HistoryScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.OnboardingScreen
import com.example.ui.screens.ProfileScreen
import com.example.ui.screens.SettingsScreen
import com.example.ui.screens.SplashScreen
import com.example.ui.screens.ToolsProjectsScreen
import com.example.ui.screens.VoiceAssistantScreen
import com.example.ui.theme.MayaAITheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    private val repository = MayaRepository()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val appSettings by repository.appSettings.collectAsState()

            MayaAITheme(
                darkTheme = appSettings.isDarkMode,
                accentHex = appSettings.selectedAccentHex
            ) {
                MayaApp(repository = repository)
            }
        }
    }
}

@Composable
fun MayaApp(repository: MayaRepository) {
    val navController = rememberNavController()
    val scope = rememberCoroutineScope()

    val chatMessages by repository.chatMessages.collectAsState()
    val currentAgent by repository.currentAgent.collectAsState()
    val projects by repository.projects.collectAsState()
    val historySessions by repository.historySessions.collectAsState()
    val userProfile by repository.userProfile.collectAsState()
    val appSettings by repository.appSettings.collectAsState()
    val isOffline by repository.isOffline.collectAsState()
    val currentEmotion by repository.currentEmotion.collectAsState()
    val h1Task by repository.h1Task.collectAsState()
    val memories by repository.memories.collectAsState()

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route ?: "splash"

    val mainNavigationRoutes = setOf("home", "chat", "agents", "projects", "tools", "profile", "settings")
    val showBottomBar = currentRoute in mainNavigationRoutes

    var pendingChatPrompt by remember { mutableStateOf<String?>(null) }

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                MayaBottomBar(
                    currentRoute = currentRoute,
                    onNavigate = { targetRoute ->
                        if (targetRoute == "voice") {
                            navController.navigate("voice")
                        } else {
                            val resolvedRoute = if (targetRoute == "tools") "projects" else targetRoute
                            navController.navigate(resolvedRoute) {
                                popUpTo("home") {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    }
                )
            }
        },
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            NavHost(
                navController = navController,
                startDestination = "splash"
            ) {
                composable("splash") {
                    SplashScreen(
                        onSplashFinished = {
                            navController.navigate("onboarding") {
                                popUpTo("splash") { inclusive = true }
                            }
                        }
                    )
                }

                composable("onboarding") {
                    OnboardingScreen(
                        onFinishOnboarding = {
                            navController.navigate("auth") {
                                popUpTo("onboarding") { inclusive = true }
                            }
                        }
                    )
                }

                composable("auth") {
                    AuthScreen(
                        onAuthSuccess = {
                            navController.navigate("home") {
                                popUpTo("auth") { inclusive = true }
                            }
                        }
                    )
                }

                composable("home") {
                    Column(modifier = Modifier.fillMaxSize()) {
                        OfflineErrorBanner(
                            isOffline = isOffline,
                            onRetry = { repository.setOfflineMode(false) }
                        )
                        HomeScreen(
                            userProfile = userProfile,
                            agents = repository.defaultAgents,
                            recentHistory = historySessions,
                            isOffline = isOffline,
                            currentEmotion = currentEmotion,
                            onSelectEmotion = { repository.setEmotion(it) },
                            h1Task = h1Task,
                            onToggleH1Task = { repository.toggleH1Task() },
                            memories = memories,
                            onOpenVoice = { navController.navigate("voice") },
                            onOpenChatWithPrompt = { prompt ->
                                pendingChatPrompt = prompt
                                navController.navigate("chat")
                            },
                            onSelectAgent = { agent ->
                                repository.setAgent(agent)
                                navController.navigate("chat")
                            },
                            onOpenAgents = { navController.navigate("agents") },
                            onOpenTools = { navController.navigate("projects") },
                            onOpenProfile = { navController.navigate("profile") },
                            onOpenAllHistory = { navController.navigate("history") }
                        )
                    }
                }

                composable("chat") {
                    val promptToUse = pendingChatPrompt
                    pendingChatPrompt = null

                    ChatScreen(
                        messages = chatMessages,
                        currentAgent = currentAgent,
                        agents = repository.defaultAgents,
                        onSelectAgent = { agent -> repository.setAgent(agent) },
                        onSendMessage = { text ->
                            scope.launch {
                                repository.sendMessage(text)
                            }
                        },
                        onClearChat = { repository.clearChat() },
                        onOpenVoice = { navController.navigate("voice") },
                        initialPrompt = promptToUse
                    )
                }

                composable("agents") {
                    AgentsScreen(
                        agents = repository.defaultAgents,
                        selectedAgent = currentAgent,
                        onLaunchAgent = { agent ->
                            repository.setAgent(agent)
                            navController.navigate("chat")
                        }
                    )
                }

                composable("projects") {
                    ToolsProjectsScreen(
                        projects = projects,
                        tools = repository.defaultTools,
                        onCreateProject = { title, desc, cat, agentId ->
                            repository.addProject(title, desc, cat, agentId)
                        },
                        onDeleteProject = { id -> repository.deleteProject(id) },
                        onLaunchToolPrompt = { toolPrompt ->
                            pendingChatPrompt = toolPrompt
                            navController.navigate("chat")
                        }
                    )
                }

                composable("tools") {
                    ToolsProjectsScreen(
                        projects = projects,
                        tools = repository.defaultTools,
                        onCreateProject = { title, desc, cat, agentId ->
                            repository.addProject(title, desc, cat, agentId)
                        },
                        onDeleteProject = { id -> repository.deleteProject(id) },
                        onLaunchToolPrompt = { toolPrompt ->
                            pendingChatPrompt = toolPrompt
                            navController.navigate("chat")
                        }
                    )
                }

                composable("settings") {
                    SettingsScreen(
                        userProfile = userProfile,
                        appSettings = appSettings,
                        isOffline = isOffline,
                        onToggleOffline = { repository.setOfflineMode(it) },
                        onNavigateToProfile = { navController.navigate("profile") },
                        onNavigateToAppearance = { navController.navigate("appearance") },
                        onNavigateToVoice = { navController.navigate("voice") },
                        onNavigateToHistory = { navController.navigate("history") }
                    )
                }

                composable("voice") {
                    VoiceAssistantScreen(
                        currentAgent = currentAgent,
                        onCloseVoice = { navController.popBackStack() },
                        onVoiceInputCaptured = { voicePrompt ->
                            pendingChatPrompt = voicePrompt
                            navController.navigate("chat")
                        }
                    )
                }

                composable("profile") {
                    ProfileScreen(
                        userProfile = userProfile,
                        onBack = { navController.popBackStack() },
                        onSaveProfile = { name, email ->
                            repository.updateProfile(name, email)
                        }
                    )
                }

                composable("appearance") {
                    AppearanceScreen(
                        appSettings = appSettings,
                        onBack = { navController.popBackStack() },
                        onUpdateTheme = { isDark, accent ->
                            repository.updateTheme(isDark, accent)
                        }
                    )
                }

                composable("history") {
                    HistoryScreen(
                        sessions = historySessions,
                        onSelectSession = { session ->
                            pendingChatPrompt = session.title
                            navController.navigate("chat")
                        },
                        onTogglePin = { id -> repository.togglePinHistory(id) },
                        onDeleteSession = { id -> repository.deleteHistory(id) },
                        onBack = { navController.popBackStack() }
                    )
                }
            }
        }
    }
}

