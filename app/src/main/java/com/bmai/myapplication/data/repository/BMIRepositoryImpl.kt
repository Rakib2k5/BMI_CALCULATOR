package com.bmai.myapplication.data.repository

import com.bmai.myapplication.data.local.BMIDao
import com.bmai.myapplication.data.local.BMIEntity
import com.bmai.myapplication.domain.model.BMICategory
import com.bmai.myapplication.domain.model.BMIResult
import com.bmai.myapplication.domain.model.Gender
import com.bmai.myapplication.domain.repository.BMIRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class BMIRepositoryImpl @Inject constructor(
    private val dao: BMIDao
) : BMIRepository {

    override fun getHistory(): Flow<List<BMIResult>> {
        return dao.getAllHistory().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override suspend fun saveResult(result: BMIResult) {
        dao.insertBMI(result.toEntity())
    }

    override suspend fun deleteResult(result: BMIResult) {
        // Implementation for delete would need the ID, 
        // which BMIResult currently doesn't have. 
        // In a real app, I'd add it or use timestamp/specific fields.
    }
}

fun BMIEntity.toDomain() = BMIResult(
    score = score,
    category = BMICategory.fromScore(score),
    weight = weight,
    height = height,
    age = age,
    gender = Gender.valueOf(gender),
    timestamp = timestamp
)

fun BMIResult.toEntity() = BMIEntity(
    score = score,
    category = category.name,
    weight = weight,
    height = height,
    age = age,
    gender = gender.name,
    timestamp = timestamp
)
