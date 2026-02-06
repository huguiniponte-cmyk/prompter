package com.prompter.app.data.dao

import androidx.room.*
import com.prompter.app.data.entity.Prompt
import kotlinx.coroutines.flow.Flow

/**
 * DAO for Prompt operations
 */
@Dao
interface PromptDao {
    
    @Query("SELECT * FROM prompts ORDER BY created_at DESC")
    fun getAllPrompts(): Flow<List<Prompt>>
    
    @Query("SELECT * FROM prompts WHERE theme_id = :themeId ORDER BY created_at DESC")
    fun getPromptsByTheme(themeId: String): Flow<List<Prompt>>
    
    @Query("SELECT * FROM prompts WHERE is_favorite = 1 ORDER BY created_at DESC")
    fun getFavoritePrompts(): Flow<List<Prompt>>
    
    @Query("SELECT * FROM prompts WHERE id = :promptId")
    suspend fun getPromptById(promptId: String): Prompt?
    
    @Query("""
        SELECT * FROM prompts 
        WHERE title LIKE '%' || :query || '%' 
        OR body LIKE '%' || :query || '%'
        ORDER BY created_at DESC
    """)
    fun searchPrompts(query: String): Flow<List<Prompt>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPrompt(prompt: Prompt)
    
    @Update
    suspend fun updatePrompt(prompt: Prompt)
    
    @Delete
    suspend fun deletePrompt(prompt: Prompt)
    
    @Query("SELECT COUNT(*) FROM prompts WHERE theme_id = :themeId")
    suspend fun getPromptCountByTheme(themeId: String): Int
}
