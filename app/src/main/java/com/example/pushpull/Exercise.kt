package com.example.pushpull

import kotlinx.serialization.Serializable


@Serializable
data class ExerciseSet(var weightKg: Double, var reps: Double)

@Serializable
data class Exercise(var name: String, val exerciseSets: MutableList<ExerciseSet>)
