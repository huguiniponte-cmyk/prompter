package com.prompter.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.prompter.app.security.SecurityManager
import com.prompter.app.ui.navigation.Screen
import com.prompter.app.ui.screen.*
import com.prompter.app.ui.theme.PrompterTheme
import com.prompter.app.ui.viewmodel.*
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        val securityManager = SecurityManager(applicationContext)
        
        setContent {
            PrompterTheme {
                PrompterApp(securityManager)
            }
        }
    }
}

@Composable
fun PrompterApp(securityManager: SecurityManager) {
    val application = androidx.compose.ui.platform.LocalContext.current.applicationContext as PrompterApplication
    
    val themeViewModel: ThemeViewModel = viewModel(
        factory = ThemeViewModelFactory(application.themeRepository)
    )
    val promptViewModel: PromptViewModel = viewModel(
        factory = PromptViewModelFactory(application.promptRepository)
    )
    val outputViewModel: OutputViewModel = viewModel(
        factory = OutputViewModelFactory(application.outputRepository)
    )
    
    val isAppLockEnabled by securityManager.isAppLockEnabled.collectAsState(initial = false)
    var isUnlocked by remember { mutableStateOf(false) }
    var showPinSetup by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    
    if (isAppLockEnabled && !isUnlocked) {
        PinLockScreen(
            onPinVerified = { isUnlocked = true },
            onVerifyPin = { pin -> securityManager.verifyPin(pin) }
        )
    } else if (showPinSetup) {
        PinSetupScreen(
            onPinSet = { pin ->
                scope.launch {
                    securityManager.setPin(pin)
                    showPinSetup = false
                }
            },
            onCancel = { showPinSetup = false }
        )
    } else {
        MainScreen(
            themeViewModel = themeViewModel,
            promptViewModel = promptViewModel,
            outputViewModel = outputViewModel,
            securityManager = securityManager,
            onSetupPin = { showPinSetup = true }
        )
    }
}

@Composable
fun MainScreen(
    themeViewModel: ThemeViewModel,
    promptViewModel: PromptViewModel,
    outputViewModel: OutputViewModel,
    securityManager: SecurityManager,
    onSetupPin: () -> Unit
) {
    val navController = rememberNavController()
    val themes by themeViewModel.allThemes.collectAsState()
    val prompts by promptViewModel.searchResults.collectAsState()
    val outputs by outputViewModel.searchResults.collectAsState()
    val promptSearchQuery by promptViewModel.searchQuery.collectAsState()
    val outputSearchQuery by outputViewModel.searchQuery.collectAsState()
    val isAppLockEnabled by securityManager.isAppLockEnabled.collectAsState(initial = false)
    val scope = rememberCoroutineScope()

    Scaffold(
        bottomBar = {
            NavigationBar {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination

                NavigationBarItem(
                    icon = { Icon(Icons.Default.Folder, contentDescription = null) },
                    label = { Text("Temas") },
                    selected = currentDestination?.hierarchy?.any { it.route == Screen.Themes.route } == true,
                    onClick = {
                        navController.navigate(Screen.Themes.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )

                NavigationBarItem(
                    icon = { Icon(Icons.Default.Description, contentDescription = null) },
                    label = { Text("Prompts") },
                    selected = currentDestination?.hierarchy?.any { it.route == Screen.Prompts.route } == true,
                    onClick = {
                        navController.navigate(Screen.Prompts.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )

                NavigationBarItem(
                    icon = { Icon(Icons.Default.History, contentDescription = null) },
                    label = { Text("Histórico") },
                    selected = currentDestination?.hierarchy?.any { it.route == Screen.History.route } == true,
                    onClick = {
                        navController.navigate(Screen.History.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )

                NavigationBarItem(
                    icon = { Icon(Icons.Default.Settings, contentDescription = null) },
                    label = { Text("Definições") },
                    selected = currentDestination?.hierarchy?.any { it.route == Screen.Settings.route } == true,
                    onClick = {
                        navController.navigate(Screen.Settings.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Themes.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Themes.route) {
                ThemesScreen(
                    themes = themes,
                    onThemeClick = { themeId ->
                        navController.navigate(Screen.ThemeDetail.createRoute(themeId))
                    },
                    onAddTheme = { theme ->
                        themeViewModel.insertTheme(theme)
                    },
                    onDeleteTheme = { theme ->
                        themeViewModel.deleteTheme(theme)
                    }
                )
            }

            composable(Screen.Prompts.route) {
                PromptsScreen(
                    prompts = prompts,
                    themes = themes,
                    searchQuery = promptSearchQuery,
                    onSearchQueryChange = { promptViewModel.setSearchQuery(it) },
                    onPromptClick = { promptId ->
                        navController.navigate(Screen.PromptUse.createRoute(promptId))
                    },
                    onAddPrompt = { prompt ->
                        promptViewModel.insertPrompt(prompt)
                    },
                    onToggleFavorite = { prompt ->
                        promptViewModel.toggleFavorite(prompt)
                    },
                    onDeletePrompt = { prompt ->
                        promptViewModel.deletePrompt(prompt)
                    }
                )
            }

            composable(Screen.History.route) {
                HistoryScreen(
                    outputs = outputs,
                    searchQuery = outputSearchQuery,
                    onSearchQueryChange = { outputViewModel.setSearchQuery(it) },
                    onOutputClick = { outputId ->
                        navController.navigate(Screen.OutputDetail.createRoute(outputId))
                    },
                    onDeleteOutput = { output ->
                        outputViewModel.deleteOutput(output)
                    }
                )
            }

            composable(Screen.Settings.route) {
                SettingsScreen(
                    appLockEnabled = isAppLockEnabled,
                    onToggleAppLock = { enabled ->
                        scope.launch {
                            securityManager.setAppLockEnabled(enabled)
                        }
                    },
                    onSetupPin = onSetupPin
                )
            }

            composable(
                route = Screen.PromptUse.route,
                arguments = listOf(navArgument("promptId") { type = NavType.StringType })
            ) { backStackEntry ->
                val promptId = backStackEntry.arguments?.getString("promptId") ?: return@composable
                var prompt by remember { mutableStateOf<com.prompter.app.data.entity.Prompt?>(null) }

                LaunchedEffect(promptId) {
                    prompt = promptViewModel.getPromptById(promptId)
                }

                prompt?.let {
                    PromptUseScreen(
                        prompt = it,
                        onSaveOutput = { output ->
                            outputViewModel.insertOutput(output)
                        },
                        onNavigateBack = { navController.navigateUp() }
                    )
                }
            }
        }
    }
}
