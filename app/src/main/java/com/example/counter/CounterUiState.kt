package com.example.counter

data class CounterUiState(
    val count: Int = 0,
    val target: Int = 0,
    val targetReached: Boolean = false
)
