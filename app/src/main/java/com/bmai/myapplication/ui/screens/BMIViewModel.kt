package com.bmai.myapplication.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bmai.myapplication.domain.model.BMIResult
import com.bmai.myapplication.domain.repository.BMIRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BMIViewModel @Inject constructor(
    private val repository: BMIRepository
) : ViewModel() {

    val history: StateFlow<List<BMIResult>> = repository.getHistory()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun saveResult(result: BMIResult) {
        viewModelScope.launch {
            repository.saveResult(result)
        }
    }
}
