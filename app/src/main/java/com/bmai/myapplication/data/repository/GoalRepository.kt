package com.bmai.myapplication.data.repository

import com.bmai.myapplication.data.local.Goal
import com.bmai.myapplication.data.local.GoalDao
import com.bmai.myapplication.data.local.GoalType
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

interface GoalRepository {
    fun getAllGoals(): Flow<List<Goal>>
    suspend fun getGoalByType(type: GoalType): Goal?
    suspend fun insertGoal(goal: Goal)
    suspend fun updateGoal(goal: Goal)
    suspend fun deleteGoal(goal: Goal)
    suspend fun deleteAllGoals()
}

@Singleton
class GoalRepositoryImpl @Inject constructor(
    private val goalDao: GoalDao
) : GoalRepository {
    override fun getAllGoals(): Flow<List<Goal>> = goalDao.getAllGoals()
    override suspend fun getGoalByType(type: GoalType): Goal? = goalDao.getGoalByType(type)
    override suspend fun insertGoal(goal: Goal) = goalDao.insertGoal(goal)
    override suspend fun updateGoal(goal: Goal) = goalDao.updateGoal(goal)
    override suspend fun deleteGoal(goal: Goal) = goalDao.deleteGoal(goal)
    override suspend fun deleteAllGoals() = goalDao.deleteAllGoals()
}
