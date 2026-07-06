package com.bmai.myapplication.ui.goals

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bmai.myapplication.data.local.Goal
import com.bmai.myapplication.data.local.GoalType
import com.bmai.myapplication.data.repository.GoalRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GoalsViewModel @Inject constructor(
    private val repository: GoalRepository
) : ViewModel() {

    val allGoals: StateFlow<List<Goal>> = repository.getAllGoals()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun addGoal(goal: Goal) {
        viewModelScope.launch { repository.insertGoal(goal) }
    }

    fun updateGoal(goal: Goal) {
        viewModelScope.launch { repository.updateGoal(goal) }
    }

    fun deleteGoal(goal: Goal) {
        viewModelScope.launch { repository.deleteGoal(goal) }
    }

    fun addDefaultGoalsIfEmpty() {
        viewModelScope.launch {
            if (allGoals.value.isEmpty()) {
                repository.insertGoal(Goal(type = GoalType.WEIGHT, title = "Daily Weight Goal", targetValue = 70.0, currentValue = 75.0, unit = "kg"))
                repository.insertGoal(Goal(type = GoalType.STEPS, title = "Daily Steps Goal", targetValue = 10000.0, currentValue = 4500.0, unit = "steps"))
                repository.insertGoal(Goal(type = GoalType.WATER, title = "Daily Water Goal", targetValue = 3.0, currentValue = 1.5, unit = "L"))
            }
        }
    }
}
