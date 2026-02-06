package com.prompter.app.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.prompter.app.data.entity.Prompt
import com.prompter.app.data.repository.PromptRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

/**
 * ViewModel for Prompt operations
 */
class PromptViewModel(private val repository: PromptRepository) : ViewModel() {
    
    val allPrompts: StateFlow<List<Prompt>> = repository.allPrompts
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    
    val favoritePrompts: StateFlow<List<Prompt>> = repository.favoritePrompts
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery
    
    val searchResults: StateFlow<List<Prompt>> = _searchQuery
        .flatMapLatest { query ->
            if (query.isBlank()) {
                repository.allPrompts
            } else {
                repository.searchPrompts(query)
            }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    
    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }
    
    fun getPromptsByTheme(themeId: String): StateFlow<List<Prompt>> {
        return repository.getPromptsByTheme(themeId)
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    }
    
    suspend fun getPromptById(promptId: String): Prompt? {
        return repository.getPromptById(promptId)
    }
    
    fun insertPrompt(prompt: Prompt) = viewModelScope.launch {
        repository.insert(prompt)
    }
    
    fun updatePrompt(prompt: Prompt) = viewModelScope.launch {
        repository.update(prompt)
    }
    
    fun deletePrompt(prompt: Prompt) = viewModelScope.launch {
        repository.delete(prompt)
    }
    
    fun toggleFavorite(prompt: Prompt) = viewModelScope.launch {
        repository.toggleFavorite(prompt)
    }
}

class PromptViewModelFactory(private val repository: PromptRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PromptViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PromptViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
