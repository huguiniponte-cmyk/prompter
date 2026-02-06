package com.prompter.app.data.repository

import com.prompter.app.data.dao.OutputDao
import com.prompter.app.data.entity.Output
import kotlinx.coroutines.flow.Flow

/**
 * Repository for Output operations
 */
class OutputRepository(private val outputDao: OutputDao) {
    
    val allOutputs: Flow<List<Output>> = outputDao.getAllOutputs()
    
    fun getOutputsByPrompt(promptId: String): Flow<List<Output>> {
        return outputDao.getOutputsByPrompt(promptId)
    }
    
    suspend fun getOutputById(outputId: String): Output? {
        return outputDao.getOutputById(outputId)
    }
    
    fun searchOutputs(query: String): Flow<List<Output>> {
        return outputDao.searchOutputs(query)
    }
    
    suspend fun insert(output: Output) {
        outputDao.insertOutput(output)
    }
    
    suspend fun update(output: Output) {
        outputDao.updateOutput(output)
    }
    
    suspend fun delete(output: Output) {
        outputDao.deleteOutput(output)
    }
    
    suspend fun getOutputCountByPrompt(promptId: String): Int {
        return outputDao.getOutputCountByPrompt(promptId)
    }
    
    suspend fun deleteOutputsByPrompt(promptId: String) {
        outputDao.deleteOutputsByPrompt(promptId)
    }
}
