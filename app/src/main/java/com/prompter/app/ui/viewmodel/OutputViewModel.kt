package com.prompter.app.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.prompter.app.data.entity.Output
import com.prompter.app.data.repository.OutputRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

/**
 * ViewModel for Output operations
 */
class OutputViewModel(private val repository: OutputRepository) : ViewModel() {
    
    val allOutputs: StateFlow<List<Output>> = repository.allOutputs
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery
    
    val searchResults: StateFlow<List<Output>> = _searchQuery
        .flatMapLatest { query ->
            if (query.isBlank()) {
                repository.allOutputs
            } else {
                repository.searchOutputs(query)
            }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    
    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }
    
    fun getOutputsByPrompt(promptId: String): StateFlow<List<Output>> {
        return repository.getOutputsByPrompt(promptId)
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    }
    
    suspend fun getOutputById(outputId: String): Output? {
        return repository.getOutputById(outputId)
    }
    
    fun insertOutput(output: Output) = viewModelScope.launch {
        repository.insert(output)
    }
    
    fun updateOutput(output: Output) = viewModelScope.launch {
        repository.update(output)
    }
    
    fun deleteOutput(output: Output) = viewModelScope.launch {
        repository.delete(output)
    }
}

class OutputViewModelFactory(private val repository: OutputRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(OutputViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return OutputViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
