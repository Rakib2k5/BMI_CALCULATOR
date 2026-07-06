package com.bmai.myapplication.domain.repository

import com.bmai.myapplication.domain.model.BMIResult
import kotlinx.coroutines.flow.Flow

interface BMIRepository {
    fun getHistory(): Flow<List<BMIResult>>
    suspend fun saveResult(result: BMIResult)
    suspend fun deleteResult(result: BMIResult)
}
