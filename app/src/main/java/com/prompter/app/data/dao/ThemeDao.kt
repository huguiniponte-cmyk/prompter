package com.prompter.app.data.dao

import androidx.room.*
import com.prompter.app.data.entity.Theme
import kotlinx.coroutines.flow.Flow

/**
 * DAO for Theme operations
 */
@Dao
interface ThemeDao {
    
    @Query("SELECT * FROM themes ORDER BY name ASC")
    fun getAllThemes(): Flow<List<Theme>>
    
    @Query("SELECT * FROM themes WHERE id = :themeId")
    suspend fun getThemeById(themeId: String): Theme?
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTheme(theme: Theme)
    
    @Update
    suspend fun updateTheme(theme: Theme)
    
    @Delete
    suspend fun deleteTheme(theme: Theme)
    
    @Query("SELECT COUNT(*) FROM themes")
    suspend fun getThemeCount(): Int
}
