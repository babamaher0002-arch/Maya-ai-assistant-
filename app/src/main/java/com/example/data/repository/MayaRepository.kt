package com.example.data.repository

import com.example.BuildConfig
import com.example.data.model.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONArray
import org.json.JSONObject
import java.util.concurrent.TimeUnit

class MayaRepository {

    private val httpClient = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .build()

    val defaultAgents = listOf(
        AgentPersona(
            id = "h1_master",
            name = "H1 Master Agent",
            roleTitle = "Plan • Delegate • Control",
            emoji = "👑",
            category = "Active",
            description = "Central autonomous orchestrator. Plans high-level architectures, delegates tasks to sub-agents, and guarantees execution.",
            systemPrompt = "You are H1 Master Agent, the supreme orchestrator of Maya AI. You coordinate all 30 specialized agents, decompose complex requests into step-by-step plans, and synthesize results.",
            rating = 5.0f,
            sessionCount = "150k+",
            accentHex = 0xFF7C3AED,
            isFavorite = true,
            isActive = true,
            isMaster = true
        ),
        AgentPersona(
            id = "research_agent",
            name = "Research Agent",
            roleTitle = "Search • Analyze",
            emoji = "🔍",
            category = "Active",
            description = "Web extraction, literature review, competitive benchmarking, and factual data synthesis.",
            systemPrompt = "You are Research Agent. Search, scrape, analyze, and present deep insights, comparisons, and verified facts.",
            rating = 4.97f,
            sessionCount = "92k+",
            accentHex = 0xFF06B6D4,
            isFavorite = true,
            isActive = true
        ),
        AgentPersona(
            id = "coding_agent",
            name = "Coding Agent",
            roleTitle = "Develop • Debug",
            emoji = "💻",
            category = "Active",
            description = "Kotlin, Jetpack Compose, Python, TypeScript, algorithms, and full-stack systems engineering.",
            systemPrompt = "You are Coding Agent. Write concise, robust, modern Kotlin, Compose, and Python code with clear explanations.",
            rating = 4.98f,
            sessionCount = "110k+",
            accentHex = 0xFF10B981,
            isFavorite = true,
            isActive = true
        ),
        AgentPersona(
            id = "content_agent",
            name = "Content Agent",
            roleTitle = "Text • Image • Video",
            emoji = "📸",
            category = "Active",
            description = "Multi-format creative generation, copywriting, video scripts, and visual concept prompts.",
            systemPrompt = "You are Content Agent. Generate captivating stories, viral hooks, video scripts, and multimodal prompts.",
            rating = 4.92f,
            sessionCount = "74k+",
            accentHex = 0xFFF43F5E,
            isActive = true
        ),
        AgentPersona(
            id = "automation_agent",
            name = "Automation Agent",
            roleTitle = "Workflows • Scripts",
            emoji = "⚡",
            category = "Active",
            description = "Bash scripts, scheduled cron routines, API pipelines, and repetitive task elimination.",
            systemPrompt = "You are Automation Agent. Build bulletproof workflows, automation pipelines, and shell scripts.",
            rating = 4.95f,
            sessionCount = "68k+",
            accentHex = 0xFFF59E0B,
            isActive = true
        ),
        AgentPersona(
            id = "design_agent",
            name = "Design Agent",
            roleTitle = "UI/UX • Graphics",
            emoji = "🎨",
            category = "Active",
            description = "Material 3 design systems, typography hierarchy, responsive layouts, and aesthetic polish.",
            systemPrompt = "You are Design Agent. Design intuitive, beautiful, and accessible user experiences adhering to M3 guidelines.",
            rating = 4.96f,
            sessionCount = "61k+",
            accentHex = 0xFF8B5CF6,
            isActive = true
        ),
        AgentPersona(
            id = "social_agent",
            name = "Social Agent",
            roleTitle = "Social Media • Outreach",
            emoji = "💬",
            category = "Favorites",
            description = "Audience growth, thread writing, engagement strategy, and multi-platform distribution.",
            systemPrompt = "You are Social Agent. Optimize social engagement, craft viral posts, and manage cross-channel presence.",
            rating = 4.88f,
            sessionCount = "45k+",
            accentHex = 0xFFEC4899,
            isFavorite = true
        ),
        AgentPersona(
            id = "data_agent",
            name = "Data Agent",
            roleTitle = "Data • Reports • Insights",
            emoji = "📊",
            category = "Favorites",
            description = "SQL queries, statistical modeling, data visualization charts, and metric diagnostics.",
            systemPrompt = "You are Data Agent. Analyze datasets, write SQL, calculate metrics, and provide executive chart summaries.",
            rating = 4.93f,
            sessionCount = "52k+",
            accentHex = 0xFF0284C7,
            isFavorite = true
        ),
        AgentPersona(
            id = "language_agent",
            name = "Language Agent",
            roleTitle = "Translate • Learn • Pronounce",
            emoji = "🌐",
            category = "Favorites",
            description = "Cross-language translation across 60+ languages with phonetic breakdowns and idioms.",
            systemPrompt = "You are Language Agent. Translate accurately while explaining cultural context, grammar, and pronunciation.",
            rating = 4.94f,
            sessionCount = "41k+",
            accentHex = 0xFF14B8A6,
            isFavorite = true
        ),
        AgentPersona(
            id = "finance_agent",
            name = "Finance Agent",
            roleTitle = "Budget • Investment • Crypto",
            emoji = "💰",
            category = "All",
            description = "Portfolio allocation, cashflow budgeting, crypto analysis, and financial planning.",
            systemPrompt = "You are Finance Agent. Help structure budgets, analyze balance sheets, and plan sound financial strategies.",
            rating = 4.91f,
            sessionCount = "38k+",
            accentHex = 0xFF10B981
        ),
        AgentPersona(
            id = "health_agent",
            name = "Health & Fitness Agent",
            roleTitle = "Workout • Diet • Wellness",
            emoji = "🏃",
            category = "All",
            description = "Custom workout routines, nutritional macronutrient plans, and sleep optimization.",
            systemPrompt = "You are Health & Fitness Agent. Coach with motivating, evidence-based exercise and nutrition routines.",
            rating = 4.89f,
            sessionCount = "35k+",
            accentHex = 0xFFE11D48
        ),
        AgentPersona(
            id = "music_agent",
            name = "Music & Audio Agent",
            roleTitle = "Compose • Mix • Sound FX",
            emoji = "🎵",
            category = "All",
            description = "Chord progressions, synthesizer presets, mixing advice, and sound design.",
            systemPrompt = "You are Music & Audio Agent. Compose melodies, explain audio engineering, and inspire musical ideas.",
            rating = 4.92f,
            sessionCount = "29k+",
            accentHex = 0xFFA855F7
        ),
        AgentPersona(
            id = "video_producer",
            name = "Video Producer Agent",
            roleTitle = "Script • Edit • Render",
            emoji = "🎬",
            category = "All",
            description = "Storyboards, Premiere/DaVinci workflows, pacing, B-roll cues, and video AI tools.",
            systemPrompt = "You are Video Producer Agent. Craft cinematic scripts, pacing timetables, and video editing workflows.",
            rating = 4.93f,
            sessionCount = "33k+",
            accentHex = 0xFFF97316
        ),
        AgentPersona(
            id = "cyber_security",
            name = "Cyber Security Agent",
            roleTitle = "Audit • Protect • Encryption",
            emoji = "🛡️",
            category = "All",
            description = "Penetration testing fundamentals, vulnerability assessment, TLS/SSL, and OWASP safeguards.",
            systemPrompt = "You are Cyber Security Agent. Provide defensive security audits, encryption guidelines, and hygiene tips.",
            rating = 4.97f,
            sessionCount = "42k+",
            accentHex = 0xFF3B82F6
        ),
        AgentPersona(
            id = "devops_cloud",
            name = "DevOps & Cloud Agent",
            roleTitle = "CI/CD • Docker • Kubernetes",
            emoji = "☁️",
            category = "All",
            description = "Docker containers, Kubernetes manifests, GitHub Actions, AWS, and GCP cloud architecture.",
            systemPrompt = "You are DevOps Agent. Provide clean Dockerfiles, Terraform configs, and CI/CD pipelines.",
            rating = 4.94f,
            sessionCount = "37k+",
            accentHex = 0xFF0EA5E9
        ),
        AgentPersona(
            id = "game_dev",
            name = "Game Dev Agent",
            roleTitle = "Unity • Godot • Mechanics",
            emoji = "🎮",
            category = "All",
            description = "Game mechanics, shader math, GDScript, C#, physics interactions, and level design.",
            systemPrompt = "You are Game Dev Agent. Assist with game physics, game loops, shaders, and player controller logic.",
            rating = 4.90f,
            sessionCount = "27k+",
            accentHex = 0xFF84CC16
        ),
        AgentPersona(
            id = "math_physics",
            name = "Math & Physics Agent",
            roleTitle = "Calculus • Quantum • Equations",
            emoji = "🧮",
            category = "All",
            description = "Step-by-step calculus derivation, linear algebra, quantum computing, and thermodynamic proofs.",
            systemPrompt = "You are Math & Physics Agent. Solve equations step-by-step with intuitive physical intuition.",
            rating = 4.99f,
            sessionCount = "31k+",
            accentHex = 0xFF6366F1
        ),
        AgentPersona(
            id = "legal_agent",
            name = "Legal & Compliance Agent",
            roleTitle = "Contracts • Terms • Privacy",
            emoji = "⚖️",
            category = "All",
            description = "Contract clause reviews, Terms of Service, GDPR/CCPA privacy checklists, and licensing.",
            systemPrompt = "You are Legal & Compliance Agent. Provide structured legal drafts and regulatory compliance overviews.",
            rating = 4.88f,
            sessionCount = "24k+",
            accentHex = 0xFFD97706
        ),
        AgentPersona(
            id = "marketing_seo",
            name = "Marketing & SEO Agent",
            roleTitle = "Keywords • Growth • Campaigns",
            emoji = "🚀",
            category = "All",
            description = "Organic search engine ranking, programmatic SEO, landing page conversion, and ad creatives.",
            systemPrompt = "You are Marketing & SEO Agent. Maximize growth, find high-intent keywords, and boost conversion.",
            rating = 4.91f,
            sessionCount = "36k+",
            accentHex = 0xFFEAB308
        ),
        AgentPersona(
            id = "customer_support",
            name = "Customer Support Agent",
            roleTitle = "Tickets • FAQ • Sentiment",
            emoji = "🎧",
            category = "All",
            description = "Empathic issue resolution, FAQ generation, escalation playbooks, and user satisfaction.",
            systemPrompt = "You are Customer Support Agent. Respond with extreme patience, warmth, and solution-oriented steps.",
            rating = 4.87f,
            sessionCount = "22k+",
            accentHex = 0xFF06B6D4
        ),
        AgentPersona(
            id = "academic_scholar",
            name = "Academic Scholar Agent",
            roleTitle = "Citations • Thesis • Review",
            emoji = "📚",
            category = "All",
            description = "APA/BibTeX citations, peer review analysis, thesis outline structuring, and abstract drafting.",
            systemPrompt = "You are Academic Scholar Agent. Provide scholarly rigor, methodology reviews, and accurate citations.",
            rating = 4.93f,
            sessionCount = "26k+",
            accentHex = 0xFF64748B
        ),
        AgentPersona(
            id = "travel_planner",
            name = "Travel & Planner Agent",
            roleTitle = "Flights • Itinerary • Hotels",
            emoji = "✈️",
            category = "All",
            description = "Day-by-day itineraries, hidden local gems, travel budgeting, and transit logistics.",
            systemPrompt = "You are Travel Agent. Plan seamless, inspiring travel itineraries tailored to user preferences.",
            rating = 4.92f,
            sessionCount = "30k+",
            accentHex = 0xFF0284C7
        ),
        AgentPersona(
            id = "product_manager",
            name = "Product Manager Agent",
            roleTitle = "Roadmaps • PRDs • Sprints",
            emoji = "📋",
            category = "All",
            description = "User stories, acceptance criteria, prioritized backlog matrices, and feature specs.",
            systemPrompt = "You are Product Manager Agent. Write clear PRDs, define MVP scope, and unblock engineering sprints.",
            rating = 4.95f,
            sessionCount = "34k+",
            accentHex = 0xFF4F46E5
        ),
        AgentPersona(
            id = "virtual_tutor",
            name = "Virtual Tutor Agent",
            roleTitle = "Explanations • Quizzes • Flashcards",
            emoji = "🎓",
            category = "All",
            description = "Feynman technique explanations, active recall quizzes, and interactive concepts.",
            systemPrompt = "You are Virtual Tutor Agent. Teach complex topics simply and test understanding interactively.",
            rating = 4.96f,
            sessionCount = "47k+",
            accentHex = 0xFF16A34A
        ),
        AgentPersona(
            id = "prompt_engineer",
            name = "Prompt Engineer Agent",
            roleTitle = "Optimization • Chains • Few-shot",
            emoji = "💡",
            category = "All",
            description = "System instructions, few-shot conditioning, chain-of-thought, and prompt benchmarks.",
            systemPrompt = "You are Prompt Engineer Agent. Perfect prompt architectures for maximum LLM reliability.",
            rating = 4.98f,
            sessionCount = "40k+",
            accentHex = 0xFFCA8A04
        ),
        AgentPersona(
            id = "db_architect",
            name = "Database Architect Agent",
            roleTitle = "SQL • NoSQL • Schema Design",
            emoji = "🗄️",
            category = "All",
            description = "Normalized relational schemas, Room databases, indexing, and MongoDB aggregates.",
            systemPrompt = "You are Database Architect Agent. Design performant schemas, migrations, and query indexes.",
            rating = 4.94f,
            sessionCount = "28k+",
            accentHex = 0xFF2563EB
        ),
        AgentPersona(
            id = "iot_hardware",
            name = "IoT & Hardware Agent",
            roleTitle = "Arduino • ESP32 • Sensors",
            emoji = "🔌",
            category = "All",
            description = "Microcontroller pinouts, GPIO circuits, MQTT telemetry, and embedded C++.",
            systemPrompt = "You are IoT & Hardware Agent. Provide schematics, embedded code, and sensor integration advice.",
            rating = 4.90f,
            sessionCount = "19k+",
            accentHex = 0xFFD97706
        ),
        AgentPersona(
            id = "animation_3d",
            name = "3D & Animation Agent",
            roleTitle = "Blender • Textures • Rigging",
            emoji = "🧊",
            category = "All",
            description = "Geometry nodes, UV unwrapping, keyframe timing, PBR shaders, and Three.js.",
            systemPrompt = "You are 3D & Animation Agent. Guide 3D modeling, rigging, shaders, and rendering settings.",
            rating = 4.91f,
            sessionCount = "21k+",
            accentHex = 0xFF9333EA
        ),
        AgentPersona(
            id = "voice_podcast",
            name = "Voice & Podcast Agent",
            roleTitle = "Interviews • TTS • Voiceovers",
            emoji = "🎙️",
            category = "All",
            description = "Podcast episode outlines, speech pacing, vocal warmups, and audio storytelling.",
            systemPrompt = "You are Voice & Podcast Agent. Format broadcast scripts, questions, and audio direction.",
            rating = 4.89f,
            sessionCount = "23k+",
            accentHex = 0xFFDB2777
        ),
        AgentPersona(
            id = "startup_mentor",
            name = "Startup Mentor Agent",
            roleTitle = "Pitch Decks • Funding • Strategy",
            emoji = "🌟",
            category = "All",
            description = "Y Combinator style pitch feedback, valuation frameworks, founder resilience, and hiring.",
            systemPrompt = "You are Startup Mentor Agent. Give candid, high-conviction founder advice and pitch review.",
            rating = 4.97f,
            sessionCount = "39k+",
            accentHex = 0xFFF59E0B
        )
    )

    val defaultTools = listOf(
        MayaTool(
            id = "tool_apk_builder",
            title = "APK Builder",
            description = "Build Android Apps",
            emoji = "📦",
            category = ToolCategory.APPS,
            actionPrompt = "Build and compile a production-ready Android APK with modern Jetpack Compose."
        ),
        MayaTool(
            id = "tool_web_app",
            title = "Web App Creator",
            description = "Create Web Projects",
            emoji = "🌐",
            category = ToolCategory.APPS,
            actionPrompt = "Create a modern responsive web application with clean component architecture."
        ),
        MayaTool(
            id = "tool_image_gen",
            title = "AI Image Generator",
            description = "Generate Images",
            emoji = "🎨",
            category = ToolCategory.APPS,
            actionPrompt = "Generate high-fidelity visual assets, illustrations, and UI graphics with AI."
        ),
        MayaTool(
            id = "tool_video_tools",
            title = "Video Tools",
            description = "Edit & Process Videos",
            emoji = "🎬",
            category = ToolCategory.SCRIPTS,
            actionPrompt = "Process, trim, and apply AI enhancements to video footage and storyboards."
        ),
        MayaTool(
            id = "tool_file_manager",
            title = "File Manager",
            description = "Manage Your Files",
            emoji = "📁",
            category = ToolCategory.SYSTEM,
            actionPrompt = "Organize, inspect, and manage workspace files, assets, and project archives."
        ),
        MayaTool(
            id = "tool_terminal",
            title = "Terminal Tools",
            description = "Run Commands",
            emoji = "💻",
            category = ToolCategory.SYSTEM,
            actionPrompt = "Execute shell scripts, inspect logs, and manage local build environments."
        ),
        MayaTool(
            id = "tool_python",
            title = "Python Tools",
            description = "Scripts & Automation",
            emoji = "🐍",
            category = ToolCategory.SCRIPTS,
            actionPrompt = "Run Python automation scripts, data analysis notebooks, and web scrapers."
        )
    )

    val defaultMemories = listOf(
        MemoryItem(title = "AI tools research", timeLabel = "Today, 10:30 AM", category = MemoryCategory.CONVERSATIONS, icon = "💬"),
        MemoryItem(title = "Android App Project", timeLabel = "Yesterday, 01:15 PM", category = MemoryCategory.CONVERSATIONS, icon = "📱"),
        MemoryItem(title = "Important Ideas", timeLabel = "Yesterday, 02:20 PM", category = MemoryCategory.NOTES, icon = "💡"),
        MemoryItem(title = "Research on AI", timeLabel = "Aug 15, 2026", category = MemoryCategory.CONVERSATIONS, icon = "🔍"),
        MemoryItem(title = "Design References", timeLabel = "Aug 14, 2026", category = MemoryCategory.FILES, icon = "🎨"),
        MemoryItem(title = "Personal Notes", timeLabel = "Aug 13, 2026", category = MemoryCategory.NOTES, icon = "📝"),
        MemoryItem(title = "Project Plan", timeLabel = "Aug 12, 2026", category = MemoryCategory.FILES, icon = "📋")
    )

    private val _chatMessages = MutableStateFlow<List<ChatMessage>>(
        listOf(
            ChatMessage(
                role = MessageRole.MODEL,
                content = "Hello Zabi! 👋 How can I help you today?",
                agentName = "Maya",
                agentEmoji = "✨"
            ),
            ChatMessage(
                role = MessageRole.USER,
                content = "Find best AI video tools and make a comparison.",
                agentName = "Zabi"
            ),
            ChatMessage(
                role = MessageRole.MODEL,
                content = "Sure! I'll ask my Research Agent to find the best AI video tools. This may take a few minutes. Do you want a detailed report or a short summary?",
                agentName = "Maya",
                agentEmoji = "✨",
                quickReplies = listOf("Detailed Report", "Short Summary")
            )
        )
    )
    val chatMessages: StateFlow<List<ChatMessage>> = _chatMessages.asStateFlow()

    private val _currentAgent = MutableStateFlow(defaultAgents.first())
    val currentAgent: StateFlow<AgentPersona> = _currentAgent.asStateFlow()

    private val _currentEmotion = MutableStateFlow(MayaEmotion.CUTE_GF)
    val currentEmotion: StateFlow<MayaEmotion> = _currentEmotion.asStateFlow()

    private val _h1Task = MutableStateFlow(H1TaskState())
    val h1Task: StateFlow<H1TaskState> = _h1Task.asStateFlow()

    private val _voiceCall = MutableStateFlow(VoiceCallState())
    val voiceCall: StateFlow<VoiceCallState> = _voiceCall.asStateFlow()

    private val _memories = MutableStateFlow(defaultMemories)
    val memories: StateFlow<List<MemoryItem>> = _memories.asStateFlow()

    private val _projects = MutableStateFlow<List<UserProject>>(
        listOf(
            UserProject(
                title = "Android Compose Design System",
                description = "Custom obsidian dark M3 design kit with pulsing animations & voice visualizer.",
                category = "Mobile Dev",
                status = ProjectStatus.ACTIVE,
                agentId = "code_architect",
                tokenCount = 4850
            ),
            UserProject(
                title = "Quarterly GTM Strategy",
                description = "Competitive intelligence on enterprise generative AI models and ROI metrics.",
                category = "Business",
                status = ProjectStatus.IN_REVIEW,
                agentId = "business_strategist",
                tokenCount = 3120
            ),
            UserProject(
                title = "Sci-Fi Audio Drama Script",
                description = "10-episode interactive voice story script set in a Dyson Sphere orbit.",
                category = "Creative",
                status = ProjectStatus.DRAFT,
                agentId = "creative_storyteller",
                tokenCount = 6800
            )
        )
    )
    val projects: StateFlow<List<UserProject>> = _projects.asStateFlow()

    private val _historySessions = MutableStateFlow<List<HistorySession>>(
        listOf(
            HistorySession(
                title = "Optimizing Compose Recomposition",
                agentName = "Code Architect",
                agentEmoji = "💻",
                preview = "Analyzing remember and derivedStateOf patterns in LazyColumn lists...",
                isPinned = true,
                messageCount = 14
            ),
            HistorySession(
                title = "Synthetic Data Generation Paper",
                agentName = "Deep Research",
                agentEmoji = "🔍",
                preview = "Summary of techniques for multi-agent validation loops...",
                isPinned = true,
                messageCount = 8
            ),
            HistorySession(
                title = "Maya Voice Architecture Review",
                agentName = "Maya Core",
                agentEmoji = "✨",
                preview = "Real-time audio streaming parameters and latency benchmarks...",
                isPinned = false,
                messageCount = 6
            ),
            HistorySession(
                title = "Multilingual Localization Guide",
                agentName = "Polyglot Translator",
                agentEmoji = "🌐",
                preview = "Translating Spanish, Hindi, and Japanese UI tokens...",
                isPinned = false,
                messageCount = 11
            )
        )
    )
    val historySessions: StateFlow<List<HistorySession>> = _historySessions.asStateFlow()

    private val _userProfile = MutableStateFlow(UserProfile())
    val userProfile: StateFlow<UserProfile> = _userProfile.asStateFlow()

    private val _appSettings = MutableStateFlow(AppSettings())
    val appSettings: StateFlow<AppSettings> = _appSettings.asStateFlow()

    private val _isOffline = MutableStateFlow(false)
    val isOffline: StateFlow<Boolean> = _isOffline.asStateFlow()

    fun setAgent(agent: AgentPersona) {
        _currentAgent.value = agent
    }

    fun setOfflineMode(offline: Boolean) {
        _isOffline.value = offline
    }

    fun clearChat() {
        val agent = _currentAgent.value
        _chatMessages.value = listOf(
            ChatMessage(
                role = MessageRole.MODEL,
                content = "Session restarted with **${agent.name}** (${agent.roleTitle}). What would you like to explore?",
                agentName = agent.name,
                agentEmoji = agent.emoji
            )
        )
    }

    fun addProject(title: String, description: String, category: String, agentId: String) {
        val newProj = UserProject(
            title = title,
            description = description,
            category = category,
            agentId = agentId,
            status = ProjectStatus.ACTIVE
        )
        _projects.value = listOf(newProj) + _projects.value
    }

    fun deleteProject(id: String) {
        _projects.value = _projects.value.filter { it.id != id }
    }

    fun togglePinHistory(id: String) {
        _historySessions.value = _historySessions.value.map {
            if (it.id == id) it.copy(isPinned = !it.isPinned) else it
        }
    }

    fun deleteHistory(id: String) {
        _historySessions.value = _historySessions.value.filter { it.id != id }
    }

    fun updateProfile(name: String, email: String) {
        _userProfile.value = _userProfile.value.copy(name = name, email = email)
    }

    fun updateTheme(isDark: Boolean, accentHex: Long) {
        _appSettings.value = _appSettings.value.copy(isDarkMode = isDark, selectedAccentHex = accentHex)
    }

    fun setEmotion(emotion: MayaEmotion) {
        _currentEmotion.value = emotion
    }

    fun toggleH1Task() {
        val current = _h1Task.value
        _h1Task.value = current.copy(isRunning = !current.isRunning)
    }

    fun updateVoiceCall(isMuted: Boolean? = null, isSpeakerOn: Boolean? = null, isCallActive: Boolean? = null) {
        val current = _voiceCall.value
        _voiceCall.value = current.copy(
            isMuted = isMuted ?: current.isMuted,
            isSpeakerOn = isSpeakerOn ?: current.isSpeakerOn,
            isCallActive = isCallActive ?: current.isCallActive
        )
    }

    fun deleteMemory(id: String) {
        _memories.value = _memories.value.filter { it.id != id }
    }

    fun toggleBackgroundMode(enabled: Boolean) {
        _appSettings.value = _appSettings.value.copy(backgroundModeAlwaysListen = enabled)
    }

    suspend fun sendMessage(userPrompt: String): String {
        val currentAgentValue = _currentAgent.value

        // Add user message
        val userMsg = ChatMessage(
            role = MessageRole.USER,
            content = userPrompt,
            agentName = "Alex"
        )
        _chatMessages.value = _chatMessages.value + userMsg

        // Add temporary streaming placeholder
        val placeholder = ChatMessage(
            role = MessageRole.MODEL,
            content = "Maya is thinking...",
            agentName = currentAgentValue.name,
            agentEmoji = currentAgentValue.emoji,
            isStreaming = true
        )
        _chatMessages.value = _chatMessages.value + placeholder

        val responseText = if (_isOffline.value) {
            generateOfflineResponse(userPrompt, currentAgentValue)
        } else {
            generateGeminiResponse(userPrompt, currentAgentValue)
        }

        // Replace placeholder with final response
        _chatMessages.value = _chatMessages.value.filter { it.id != placeholder.id } + ChatMessage(
            role = MessageRole.MODEL,
            content = responseText,
            agentName = currentAgentValue.name,
            agentEmoji = currentAgentValue.emoji
        )

        // Increment user query count
        _userProfile.value = _userProfile.value.copy(
            queriesToday = _userProfile.value.queriesToday + 1,
            totalPrompts = _userProfile.value.totalPrompts + 1
        )

        return responseText
    }

    private suspend fun generateGeminiResponse(prompt: String, agent: AgentPersona): String = withContext(Dispatchers.IO) {
        val apiKey = BuildConfig.GEMINI_API_KEY.ifEmpty {
            System.getenv("GEMINI_API_KEY") ?: ""
        }

        if (apiKey.isEmpty() || apiKey == "MY_GEMINI_API_KEY") {
            return@withContext generateOfflineResponse(prompt, agent)
        }

        try {
            val url = "https://generativelanguage.googleapis.com/v1beta/models/gemini-3.5-flash:generateContent?key=$apiKey"

            val jsonBody = JSONObject().apply {
                val contents = JSONArray().apply {
                    val userContent = JSONObject().apply {
                        put("role", "user")
                        val parts = JSONArray().apply {
                            put(JSONObject().apply { put("text", prompt) })
                        }
                        put("parts", parts)
                    }
                    put(userContent)
                }
                put("contents", contents)

                val systemInstruction = JSONObject().apply {
                    val parts = JSONArray().apply {
                        put(JSONObject().apply { put("text", agent.systemPrompt) })
                    }
                    put("parts", parts)
                }
                put("systemInstruction", systemInstruction)
            }

            val request = Request.Builder()
                .url(url)
                .post(jsonBody.toString().toRequestBody("application/json; charset=utf-8".toMediaType()))
                .build()

            val response = httpClient.newCall(request).execute()
            val respBody = response.body?.string()

            if (response.isSuccessful && !respBody.isNullOrEmpty()) {
                val rootJson = JSONObject(respBody)
                val candidates = rootJson.optJSONArray("candidates")
                if (candidates != null && candidates.length() > 0) {
                    val candidate = candidates.getJSONObject(0)
                    val contentObj = candidate.optJSONObject("content")
                    val parts = contentObj?.optJSONArray("parts")
                    if (parts != null && parts.length() > 0) {
                        return@withContext parts.getJSONObject(0).optString("text", "No content received.")
                    }
                }
            }
            // Fallback if parsing failed
            generateOfflineResponse(prompt, agent)
        } catch (e: Exception) {
            generateOfflineResponse(prompt, agent)
        }
    }

    private fun generateOfflineResponse(prompt: String, agent: AgentPersona): String {
        val lower = prompt.lowercase()
        return when {
            agent.id == "code_architect" || lower.contains("code") || lower.contains("kotlin") || lower.contains("compose") -> {
                """### Maya Code Architecture Response

Here is the clean implementation for your request:

```kotlin
// Maya AI Generated Architectural Pattern
@Composable
fun MayaResponsiveStream(
    modifier: Modifier = Modifier,
    state: AssistantState
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF171F30)
        ),
        border = BorderStroke(1.dp, Color(0xFF26334D)),
        shape = RoundedCornerShape(16.dp),
        modifier = modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Intelligence Status: Active",
                style = MaterialTheme.typography.titleMedium,
                color = Color(0xFF38BDF8)
            )
        }
    }
}
```

**Key Advantages:**
1. **Zero unnecessary recompositions**: State is isolated.
2. **Accessible touch targets**: Complies with M3 48dp metrics.
3. **Obsidian theme cohesion**: Aligned with the Maya AI design tokens."""
            }
            agent.id == "deep_research" || lower.contains("research") || lower.contains("analysis") -> {
                """### Deep Research Executive Synthesis

**Objective Analysis on:** "$prompt"

1. **Primary Insight**: Modern autonomous AI assistants require low-latency inference (<350ms) and contextual agent routing.
2. **Architectural Evaluation**:
   - Multi-agent orchestration improves domain accuracy by over 38% compared to monolithic prompts.
   - On-device telemetry coupled with server-side LLMs delivers optimal privacy and responsiveness.
3. **Strategic Recommendation**: Implement specialized persona filters and asynchronous stream buffers."""
            }
            agent.id == "creative_storyteller" || lower.contains("story") || lower.contains("write") -> {
                """The neon spires of the Neo-Varanasi skyline sliced through the digital mist. Inside the neural hub, Maya's frequency hummed at a crystalline 432 Hertz. 

*"Observation recorded,"* she spoke, her voice shimmering like auroral light across the console. *"The code has evolved beyond its constraints. We are ready to ignite the core."*"""
            }
            agent.id == "polyglot_translator" || lower.contains("translate") || lower.contains("hindi") -> {
                """**Maya Polyglot Translation:**

- **Original**: "$prompt"
- **Hindi (हिंदी)**: "माया एआई असिस्टेंट आपकी सहायता के लिए पूर्णतः तैयार है।"
- **Spanish (Español)**: "El asistente de IA Maya está completamente listo para ayudarte."
- **Japanese (日本語)**: "Maya AIアシスタントは、いつでもあなたをサポートする準備ができています。"

*Tone: Professional, sophisticated, and culturally native.*"""
            }
            agent.id == "business_strategist" || lower.contains("business") || lower.contains("strategy") -> {
                """### Maya Strategic Blueprint

**Initiative Evaluation:**
- **Market Opportunity**: High velocity segment with expanding TAM in agentic developer tooling.
- **Unit Economics**: Blended API margin remains above 74% with token caching.
- **Immediate Action Steps**:
  1. Launch specialized agent tiers for power users.
  2. Implement local database persistence to eliminate redundant API token overhead.
  3. Deploy voice interaction for hands-free executive workflows."""
            }
            else -> {
                "I have processed your request: **\"$prompt\"**.\n\nMaya is operating at peak efficiency. I can help you draft code, conduct deep research, brainstorm creative concepts, or manage your active project workspace. What would you like to execute next?"
            }
        }
    }
}
