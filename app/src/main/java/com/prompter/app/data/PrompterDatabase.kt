package com.prompter.app.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.prompter.app.data.dao.OutputDao
import com.prompter.app.data.dao.PromptDao
import com.prompter.app.data.dao.ThemeDao
import com.prompter.app.data.entity.Output
import com.prompter.app.data.entity.Prompt
import com.prompter.app.data.entity.Theme

/**
 * Main Room Database for Prompter app
 * Version 1 - Initial schema with Theme, Prompt, Output
 */
@Database(
    entities = [Theme::class, Prompt::class, Output::class],
    version = 1,
    exportSchema = false
)
abstract class PrompterDatabase : RoomDatabase() {
    
    abstract fun themeDao(): ThemeDao
    abstract fun promptDao(): PromptDao
    abstract fun outputDao(): OutputDao
    
    companion object {
        @Volatile
        private var INSTANCE: PrompterDatabase? = null
        
        fun getDatabase(context: Context): PrompterDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    PrompterDatabase::class.java,
                    "prompter_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
