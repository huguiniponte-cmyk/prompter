package com.prompter.app.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.prompter.app.data.entity.Prompt
import com.prompter.app.data.entity.Theme
import com.prompter.app.ui.viewmodel.PromptViewModel
import com.prompter.app.ui.viewmodel.ThemeViewModel
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ThemeDetailScreen(
    themeId: String,
    themeViewModel: ThemeViewModel,
    promptViewModel: PromptViewModel,
    onPromptClick: (String) -> Unit,
    onNavigateBack: () -> Unit
) {
    var theme by remember { mutableStateOf<Theme?>(null) }
    val allPrompts by promptViewModel.searchResults.collectAsState()
    val themePrompts = allPrompts.filter { it.themeId == themeId }
    
    var showAddDialog by remember { mutableStateOf(false) }

    LaunchedEffect(themeId) {
        theme = themeViewModel.getThemeById(themeId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(theme?.name ?: "Detalhes do Tema") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Voltar")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showAddDialog = true },
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(Icons.Default.Add, contentDescription = "Adicionar Prompt")
            }
        }
    ) { padding ->
        if (themePrompts.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        Icons.Default.Description,
                        contentDescription = null,
                        modifier = Modifier.size(64.dp),
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        "Nenhum prompt neste tema",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(themePrompts, key = { it.id }) { prompt ->
                    PromptCard(
                        prompt = prompt,
                        themeName = theme?.name ?: "",
                        onClick = { onPromptClick(prompt.id) },
                        onToggleFavorite = { promptViewModel.toggleFavorite(prompt) },
                        onDelete = { promptViewModel.deletePrompt(prompt) }
                    )
                }
            }
        }
    }

    if (showAddDialog && theme != null) {
        AddPromptDialog(
            themes = listOf(theme!!),
            onDismiss = { showAddDialog = false },
            onConfirm = { title, body, notes, tId ->
                promptViewModel.insertPrompt(
                    Prompt(
                        id = UUID.randomUUID().toString(),
                        themeId = tId,
                        title = title,
                        body = body,
                        notes = notes,
                        isFavorite = false,
                        createdAt = System.currentTimeMillis()
                    )
                )
                showAddDialog = false
            }
        )
    }
}
