package com.prompter.app

import android.app.Application
import com.prompter.app.data.PrompterDatabase
import com.prompter.app.data.repository.OutputRepository
import com.prompter.app.data.repository.PromptRepository
import com.prompter.app.data.repository.ThemeRepository

/**
 * Application class for dependency injection
 */
class PrompterApplication : Application() {
    
    private val database by lazy { PrompterDatabase.getDatabase(this) }
    
    val themeRepository by lazy { ThemeRepository(database.themeDao()) }
    val promptRepository by lazy { PromptRepository(database.promptDao()) }
    val outputRepository by lazy { OutputRepository(database.outputDao()) }
}
