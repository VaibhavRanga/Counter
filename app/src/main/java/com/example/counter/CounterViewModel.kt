package com.example.counter

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class CounterViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(CounterUiState())
    val uiState: StateFlow<CounterUiState> = _uiState.asStateFlow()

    fun updateTargetValue(enteredText: String) {
        _uiState.update {
            it.copy(target = enteredText.toIntOrNull() ?: 0)
        }
    }

    fun addCount() {
        _uiState.update {
            it.copy(count = _uiState.value.count.inc())
        }
        targetReached()
    }

    fun minusCount() {
        _uiState.update {
            it.copy(count = _uiState.value.count.dec())
        }
        targetReached()
    }

    private fun targetReached() {
        _uiState.update {
            it.copy(targetReached = _uiState.value.count == _uiState.value.target)
        }
    }
}
