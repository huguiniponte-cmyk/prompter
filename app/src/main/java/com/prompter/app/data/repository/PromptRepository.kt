package com.prompter.app.data.repository

import com.prompter.app.data.dao.PromptDao
import com.prompter.app.data.entity.Prompt
import kotlinx.coroutines.flow.Flow

/**
 * Repository for Prompt operations
 */
class PromptRepository(private val promptDao: PromptDao) {
    
    val allPrompts: Flow<List<Prompt>> = promptDao.getAllPrompts()
    val favoritePrompts: Flow<List<Prompt>> = promptDao.getFavoritePrompts()
    
    fun getPromptsByTheme(themeId: String): Flow<List<Prompt>> {
        return promptDao.getPromptsByTheme(themeId)
    }
    
    suspend fun getPromptById(promptId: String): Prompt? {
        return promptDao.getPromptById(promptId)
    }
    
    fun searchPrompts(query: String): Flow<List<Prompt>> {
        return promptDao.searchPrompts(query)
    }
    
    suspend fun insert(prompt: Prompt) {
        promptDao.insertPrompt(prompt)
    }
    
    suspend fun update(prompt: Prompt) {
        promptDao.updatePrompt(prompt)
    }
    
    suspend fun delete(prompt: Prompt) {
        promptDao.deletePrompt(prompt)
    }
    
    suspend fun toggleFavorite(prompt: Prompt) {
        promptDao.updatePrompt(prompt.copy(isFavorite = !prompt.isFavorite))
    }
    
    suspend fun getPromptCountByTheme(themeId: String): Int {
        return promptDao.getPromptCountByTheme(themeId)
    }
}
