package com.example.pushpull


data class Set(var weightKg: Double, var reps: Float)

data class Exercise(var name: String, val sets: MutableList<Set>)
