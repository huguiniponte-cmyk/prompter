package com.prompter.app.data.repository

import com.prompter.app.data.dao.ThemeDao
import com.prompter.app.data.entity.Theme
import kotlinx.coroutines.flow.Flow

/**
 * Repository for Theme operations
 */
class ThemeRepository(private val themeDao: ThemeDao) {
    
    val allThemes: Flow<List<Theme>> = themeDao.getAllThemes()
    
    suspend fun getThemeById(themeId: String): Theme? {
        return themeDao.getThemeById(themeId)
    }
    
    suspend fun insert(theme: Theme) {
        themeDao.insertTheme(theme)
    }
    
    suspend fun update(theme: Theme) {
        themeDao.updateTheme(theme)
    }
    
    suspend fun delete(theme: Theme) {
        themeDao.deleteTheme(theme)
    }
    
    suspend fun getThemeCount(): Int {
        return themeDao.getThemeCount()
    }
}
