package com.example.counter

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class CounterViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(CounterState())
    val uiState: StateFlow<CounterState> = _uiState.asStateFlow()

    var targetInputValue by mutableStateOf(0)
        private set

    init {
        _uiState.value = CounterState(currentCount = 0, target = 0)
    }

    fun enteredTargetValue(target: String) {
        targetInputValue = target.toIntOrNull() ?: 0
    }

    fun setTarget() {
        _uiState.update {
            it.copy(target = targetInputValue)
        }
    }

    fun targetReached() {
        if (_uiState.value.target == _uiState.value.currentCount) {
            _uiState.update {
                it.copy(targetReached = true)
            }
        } else {
            _uiState.update {
                it.copy(targetReached = false)
            }
        }
    }

    fun addCount() {
        _uiState.update {
            it.copy(currentCount = it.currentCount.inc())
        }
    }

    fun minusCount() {
        _uiState.update {
            it.copy(currentCount = it.currentCount.dec())
        }
    }
}
