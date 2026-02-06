package com.prompter.app.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.UUID

/**
 * Output entity - Represents a historical execution of a prompt with its result
 */
@Entity(
    tableName = "outputs",
    foreignKeys = [
        ForeignKey(
            entity = Prompt::class,
            parentColumns = ["id"],
            childColumns = ["prompt_id"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["prompt_id"])]
)
data class Output(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: String = UUID.randomUUID().toString(),
    
    @ColumnInfo(name = "prompt_id")
    val promptId: String,
    
    @ColumnInfo(name = "input_filled")
    val inputFilled: String, // JSON with variable values
    
    @ColumnInfo(name = "output_text")
    val outputText: String, // Raw pasted result
    
    @ColumnInfo(name = "rating")
    val rating: Int = 0, // 1-5
    
    @ColumnInfo(name = "created_at")
    val createdAt: Long = System.currentTimeMillis()
)
