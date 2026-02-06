package com.prompter.app.ui.screen

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.prompter.app.data.entity.Output
import com.prompter.app.data.entity.Prompt
import com.prompter.app.util.VariableParser
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.util.UUID

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PromptUseScreen(
    prompt: Prompt,
    onSaveOutput: (Output) -> Unit,
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val variables = remember(prompt.body) { VariableParser.extractVariables(prompt.body) }
    val variableValues = remember { mutableStateMapOf<String, String>() }
    val filledPrompt = remember(prompt.body, variableValues.toMap()) {
        VariableParser.fillVariables(prompt.body, variableValues)
    }
    
    var outputText by remember { mutableStateOf("") }
    var rating by remember { mutableStateOf(0) }
    var showSaveDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(prompt.title) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Voltar")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        }
    ) { padding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Prompt info
            if (prompt.notes != null) {
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.secondaryContainer
                    ),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(
                            Icons.Default.Info,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSecondaryContainer
                        )
                        Text(
                            text = prompt.notes,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSecondaryContainer
                        )
                    }
                }
            }

            // Variable inputs
            if (variables.isNotEmpty()) {
                Text(
                    "Preencher Variáveis",
                    style = MaterialTheme.typography.titleMedium
                )
                
                variables.forEach { variable ->
                    OutlinedTextField(
                        value = variableValues[variable] ?: "",
                        onValueChange = { variableValues[variable] = it },
                        label = { Text(variable) },
                        modifier = Modifier.fillMaxWidth(),
                        supportingText = { Text("Variável: {$variable}") }
                    )
                }
            }

            // Filled prompt preview
            Text(
                "Prompt Preenchido",
                style = MaterialTheme.typography.titleMedium
            )
            
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                ),
                shape = RoundedCornerShape(8.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = filledPrompt,
                        style = MaterialTheme.typography.bodyLarge
                    )
                    
                    Spacer(modifier = Modifier.height(12.dp))
                    
                    Button(
                        onClick = {
                            copyToClipboard(context, filledPrompt)
                            Toast.makeText(context, "Copiado!", Toast.LENGTH_SHORT).show()
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(Icons.Default.ContentCopy, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Copiar Prompt")
                    }
                }
            }

            // Output section
            Text(
                "Guardar Resultado",
                style = MaterialTheme.typography.titleMedium
            )
            
            OutlinedTextField(
                value = outputText,
                onValueChange = { outputText = it },
                label = { Text("Cole aqui o resultado obtido") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                maxLines = 10,
                placeholder = { Text("Após usar o prompt externamente, cole o resultado aqui...") }
            )

            // Rating
            Text(
                "Avaliação (opcional)",
                style = MaterialTheme.typography.labelLarge
            )
            
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                (1..5).forEach { star ->
                    IconButton(
                        onClick = { rating = if (rating == star) 0 else star }
                    ) {
                        Icon(
                            if (star <= rating) Icons.Default.Star else Icons.Default.StarBorder,
                            contentDescription = "Rating $star",
                            tint = if (star <= rating) MaterialTheme.colorScheme.primary
                            else MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.size(32.dp)
                        )
                    }
                }
            }

            // Save button
            Button(
                onClick = { showSaveDialog = true },
                modifier = Modifier.fillMaxWidth(),
                enabled = outputText.isNotBlank()
            ) {
                Icon(Icons.Default.Save, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Guardar Output")
            }
        }
    }

    if (showSaveDialog) {
        AlertDialog(
            onDismissRequest = { showSaveDialog = false },
            title = { Text("Guardar Output") },
            text = { Text("Deseja guardar este output no histórico?") },
            confirmButton = {
                TextButton(
                    onClick = {
                        val output = Output(
                            id = UUID.randomUUID().toString(),
                            promptId = prompt.id,
                            inputFilled = Json.encodeToString(variableValues.toMap()),
                            outputText = outputText,
                            rating = rating,
                            createdAt = System.currentTimeMillis()
                        )
                        onSaveOutput(output)
                        showSaveDialog = false
                        Toast.makeText(context, "Output guardado!", Toast.LENGTH_SHORT).show()
                        onNavigateBack()
                    }
                ) {
                    Text("Guardar")
                }
            },
            dismissButton = {
                TextButton(onClick = { showSaveDialog = false }) {
                    Text("Cancelar")
                }
            }
        )
    }
}

private fun copyToClipboard(context: Context, text: String) {
    val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val clip = ClipData.newPlainText("Prompt", text)
    clipboard.setPrimaryClip(clip)
}
