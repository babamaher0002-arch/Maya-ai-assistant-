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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.data.model.MayaTool
import com.example.data.model.ProjectStatus
import com.example.data.model.UserProject
import com.example.ui.components.MayaGradientButton
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
fun ToolsProjectsScreen(
    projects: List<UserProject>,
    tools: List<MayaTool>,
    onCreateProject: (String, String, String, String) -> Unit,
    onDeleteProject: (String) -> Unit,
    onLaunchToolPrompt: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedTab by remember { mutableIntStateOf(0) }
    var showCreateDialog by remember { mutableStateOf(false) }

    Scaffold(
        floatingActionButton = {
            if (selectedTab == 0) {
                ExtendedFloatingActionButton(
                    onClick = { showCreateDialog = true },
                    containerColor = MayaViolet,
                    contentColor = MayaTextWhite,
                    icon = { Icon(Icons.Default.Add, contentDescription = null) },
                    text = { Text("New Project", fontWeight = FontWeight.Bold) },
                    modifier = Modifier.testTag("fab_new_project")
                )
            }
        },
        containerColor = MayaBgDark,
        modifier = modifier.testTag("tools_projects_screen")
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp, vertical = 12.dp)
        ) {
            // Header
            Text(
                text = "Workspace & Tools",
                style = MaterialTheme.typography.headlineMedium,
                color = MayaTextWhite,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Manage mission projects and launch accelerated AI utilities.",
                style = MaterialTheme.typography.bodyMedium,
                color = MayaTextSecondary
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Tab Selector
            Surface(
                shape = RoundedCornerShape(14.dp),
                color = MayaCardBg,
                border = BorderStroke(1.dp, MayaCardBorder),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(modifier = Modifier.padding(4.dp)) {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(10.dp))
                            .background(if (selectedTab == 0) MayaViolet else Color.Transparent)
                            .clickable { selectedTab = 0 }
                            .padding(vertical = 10.dp)
                            .testTag("tab_projects"),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Projects (${projects.size})",
                            style = MaterialTheme.typography.labelLarge,
                            color = MayaTextWhite,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(10.dp))
                            .background(if (selectedTab == 1) MayaViolet else Color.Transparent)
                            .clickable { selectedTab = 1 }
                            .padding(vertical = 10.dp)
                            .testTag("tab_tools"),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "AI Tools (${tools.size})",
                            style = MaterialTheme.typography.labelLarge,
                            color = MayaTextWhite,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (selectedTab == 0) {
                // Projects List
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    contentPadding = PaddingValues(bottom = 80.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(projects) { project ->
                        ProjectCard(
                            project = project,
                            onDelete = { onDeleteProject(project.id) },
                            onOpen = { onLaunchToolPrompt("Project workspace: ${project.title}. ${project.description}") }
                        )
                    }
                }
            } else {
                // AI Tools List
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(14.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(tools) { tool ->
                        ToolCard(
                            tool = tool,
                            onLaunch = { onLaunchToolPrompt("Execute ${tool.title} task with Maya AI.") }
                        )
                    }
                }
            }
        }
    }

    if (showCreateDialog) {
        ProjectCreationDialog(
            onDismiss = { showCreateDialog = false },
            onConfirm = { title, desc, cat, agentId ->
                onCreateProject(title, desc, cat, agentId)
                showCreateDialog = false
            }
        )
    }
}

@Composable
fun ProjectCard(
    project: UserProject,
    onDelete: () -> Unit,
    onOpen: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MayaCardBg),
        border = BorderStroke(1.dp, MayaCardBorder),
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onOpen)
            .testTag("project_item_${project.id}")
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
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(MayaViolet.copy(alpha = 0.2f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Default.Folder, contentDescription = null, tint = MayaCyanLight, modifier = Modifier.size(18.dp))
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(
                            text = project.title,
                            style = MaterialTheme.typography.titleMedium,
                            color = MayaTextWhite,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = project.category,
                            style = MaterialTheme.typography.labelSmall,
                            color = MayaCyanLight
                        )
                    }
                }

                // Status Badge
                Surface(
                    shape = RoundedCornerShape(6.dp),
                    color = when (project.status) {
                        ProjectStatus.ACTIVE -> MayaEmerald.copy(alpha = 0.2f)
                        ProjectStatus.IN_REVIEW -> Color(0xFFF59E0B).copy(alpha = 0.2f)
                        ProjectStatus.DRAFT -> MayaCardBorder
                        ProjectStatus.COMPLETED -> MayaCyan.copy(alpha = 0.2f)
                    }
                ) {
                    Text(
                        text = project.status.name,
                        style = MaterialTheme.typography.labelSmall,
                        color = when (project.status) {
                            ProjectStatus.ACTIVE -> MayaEmerald
                            ProjectStatus.IN_REVIEW -> Color(0xFFF59E0B)
                            ProjectStatus.DRAFT -> MayaTextSecondary
                            ProjectStatus.COMPLETED -> MayaCyanLight
                        },
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = project.description,
                style = MaterialTheme.typography.bodyMedium,
                color = MayaTextSecondary,
                lineHeight = 20.sp
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "📊 ${project.tokenCount} tokens allocated",
                    style = MaterialTheme.typography.labelSmall,
                    color = MayaTextTertiary
                )

                IconButton(
                    onClick = onDelete,
                    modifier = Modifier.size(32.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete",
                        tint = MayaRose,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun ToolCard(
    tool: MayaTool,
    onLaunch: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = MayaCardBg),
        border = BorderStroke(1.dp, MayaCardBorder),
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onLaunch)
            .testTag("tool_card_${tool.id}")
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier.weight(1f),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color(0xFF101624))
                        .border(1.dp, MayaCardBorder, RoundedCornerShape(12.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = tool.emoji, fontSize = 24.sp)
                }
                Spacer(modifier = Modifier.width(14.dp))
                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = tool.title,
                            style = MaterialTheme.typography.titleMedium,
                            color = MayaTextWhite,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = MayaViolet.copy(alpha = 0.2f)
                        ) {
                            Text(
                                text = tool.category.name,
                                style = MaterialTheme.typography.labelSmall,
                                color = MayaCyanLight,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(3.dp))
                    Text(
                        text = tool.description,
                        style = MaterialTheme.typography.bodySmall,
                        color = MayaTextSecondary,
                        lineHeight = 18.sp
                    )
                }
            }

            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(MayaViolet),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.AutoAwesome,
                    contentDescription = "Run tool",
                    tint = MayaTextWhite,
                    modifier = Modifier.size(18.dp)
                )
            }
        }
    }
}

@Composable
fun ProjectCreationDialog(
    onDismiss: () -> Unit,
    onConfirm: (title: String, desc: String, category: String, agentId: String) -> Unit
) {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("Development") }
    var agentId by remember { mutableStateOf("code_architect") }

    val categories = listOf("Development", "Research", "Creative", "Business")

    Dialog(onDismissRequest = onDismiss) {
        Card(
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = MayaCardBg),
            border = BorderStroke(1.dp, MayaViolet),
            modifier = Modifier.padding(12.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            ) {
                Text(
                    text = "Create New Project",
                    style = MaterialTheme.typography.titleLarge,
                    color = MayaTextWhite,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Project Title", color = MayaTextSecondary) },
                    placeholder = { Text("e.g., E-Commerce Architecture", color = MayaTextTertiary) },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = MayaCyanLight,
                        unfocusedBorderColor = MayaCardBorder,
                        focusedTextColor = MayaTextWhite,
                        unfocusedTextColor = MayaTextWhite
                    )
                )

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Mission Description", color = MayaTextSecondary) },
                    placeholder = { Text("Goals and expected deliverables...", color = MayaTextTertiary) },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    maxLines = 3,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = MayaCyanLight,
                        unfocusedBorderColor = MayaCardBorder,
                        focusedTextColor = MayaTextWhite,
                        unfocusedTextColor = MayaTextWhite
                    )
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "Select Category",
                    style = MaterialTheme.typography.labelMedium,
                    color = MayaTextSecondary
                )

                Spacer(modifier = Modifier.height(6.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    categories.forEach { cat ->
                        val isSel = cat == category
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = if (isSel) MayaViolet else Color(0xFF101624),
                            border = BorderStroke(1.dp, if (isSel) MayaCyanLight else MayaCardBorder),
                            modifier = Modifier
                                .weight(1f)
                                .clickable { category = cat }
                                .padding(vertical = 2.dp)
                        ) {
                            Text(
                                text = cat,
                                style = MaterialTheme.typography.labelSmall,
                                color = MayaTextWhite,
                                fontWeight = if (isSel) FontWeight.Bold else FontWeight.Normal,
                                modifier = Modifier.padding(horizontal = 4.dp, vertical = 6.dp),
                                maxLines = 1
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                MayaGradientButton(
                    text = "Initialize Project",
                    onClick = {
                        if (title.isNotBlank()) {
                            onConfirm(title, description, category, agentId)
                        }
                    },
                    enabled = title.isNotBlank(),
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}
