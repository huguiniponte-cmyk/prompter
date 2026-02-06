package com.prompter.app.data.dao

import androidx.room.*
import com.prompter.app.data.entity.Output
import kotlinx.coroutines.flow.Flow

/**
 * DAO for Output operations
 */
@Dao
interface OutputDao {
    
    @Query("SELECT * FROM outputs ORDER BY created_at DESC")
    fun getAllOutputs(): Flow<List<Output>>
    
    @Query("SELECT * FROM outputs WHERE prompt_id = :promptId ORDER BY created_at DESC")
    fun getOutputsByPrompt(promptId: String): Flow<List<Output>>
    
    @Query("SELECT * FROM outputs WHERE id = :outputId")
    suspend fun getOutputById(outputId: String): Output?
    
    @Query("""
        SELECT * FROM outputs 
        WHERE output_text LIKE '%' || :query || '%'
        ORDER BY created_at DESC
    """)
    fun searchOutputs(query: String): Flow<List<Output>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOutput(output: Output)
    
    @Update
    suspend fun updateOutput(output: Output)
    
    @Delete
    suspend fun deleteOutput(output: Output)
    
    @Query("SELECT COUNT(*) FROM outputs WHERE prompt_id = :promptId")
    suspend fun getOutputCountByPrompt(promptId: String): Int
    
    @Query("DELETE FROM outputs WHERE prompt_id = :promptId")
    suspend fun deleteOutputsByPrompt(promptId: String)
}
