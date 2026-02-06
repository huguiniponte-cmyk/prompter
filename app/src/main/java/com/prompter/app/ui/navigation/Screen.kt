package com.prompter.app.ui.navigation

/**
 * Navigation routes for the app
 */
sealed class Screen(val route: String) {
    object Themes : Screen("themes")
    object Prompts : Screen("prompts")
    object History : Screen("history")
    object Settings : Screen("settings")
    object ThemeDetail : Screen("theme/{themeId}") {
        fun createRoute(themeId: String) = "theme/$themeId"
    }
    object PromptDetail : Screen("prompt/{promptId}") {
        fun createRoute(promptId: String) = "prompt/$promptId"
    }
    object PromptUse : Screen("prompt/{promptId}/use") {
        fun createRoute(promptId: String) = "prompt/$promptId/use"
    }
    object OutputDetail : Screen("output/{outputId}") {
        fun createRoute(outputId: String) = "output/$outputId"
    }
}
