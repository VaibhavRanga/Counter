package com.example.counter

data class CounterState(
    val currentCount: Int = 0,
    val target: Int = 0,
    val targetReached: Boolean = false
)
