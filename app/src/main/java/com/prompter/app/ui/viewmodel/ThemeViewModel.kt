package com.prompter.app.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.prompter.app.data.entity.Theme
import com.prompter.app.data.repository.ThemeRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

/**
 * ViewModel for Theme operations
 */
class ThemeViewModel(private val repository: ThemeRepository) : ViewModel() {
    
    val allThemes: StateFlow<List<Theme>> = repository.allThemes
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    
    fun insertTheme(theme: Theme) = viewModelScope.launch {
        repository.insert(theme)
    }
    
    fun updateTheme(theme: Theme) = viewModelScope.launch {
        repository.update(theme)
    }
    
    fun deleteTheme(theme: Theme) = viewModelScope.launch {
        repository.delete(theme)
    }
    
    suspend fun getThemeById(themeId: String): Theme? {
        return repository.getThemeById(themeId)
    }
}

class ThemeViewModelFactory(private val repository: ThemeRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ThemeViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ThemeViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
