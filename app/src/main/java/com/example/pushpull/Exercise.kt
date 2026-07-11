package com.example.pushpull

import android.annotation.SuppressLint
import kotlinx.serialization.Serializable


@SuppressLint("UnsafeOptInUsageError")
@Serializable
data class ExerciseSet(var weightKg: Double, var reps: Double)

@SuppressLint("UnsafeOptInUsageError")
@Serializable
data class Exercise(var name: String, val exerciseSets: MutableList<ExerciseSet>)
