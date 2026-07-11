package com.example.pushpull

import android.annotation.SuppressLint
import kotlinx.serialization.Serializable

object RoutineContent {
    fun getDefaultRoutines(): MutableList<RoutineItem> {
        val pushRoutine = RoutineItem("Push")

        val bench = Exercise("Bench press", mutableListOf())
        bench.exerciseSets.add(ExerciseSet(60.0, 10.0))
        bench.exerciseSets.add(ExerciseSet(60.0, 9.0))
        bench.exerciseSets.add(ExerciseSet(60.0, 8.5))
        pushRoutine.exercises.add(bench)

        val incline_bench = Exercise("Incline bench press", mutableListOf())
        incline_bench.exerciseSets.add(ExerciseSet(60.0, 10.0))
        incline_bench.exerciseSets.add(ExerciseSet(60.0, 9.0))
        incline_bench.exerciseSets.add(ExerciseSet(60.0, 8.5))

        pushRoutine.exercises.add(incline_bench)

        val pushdown = Exercise("Tricep pushdown", mutableListOf())
        pushdown.exerciseSets.add(ExerciseSet(30.0, 9.0))
        pushdown.exerciseSets.add(ExerciseSet(30.0, 8.0))
        pushdown.exerciseSets.add(ExerciseSet(30.0, 6.5))
        pushRoutine.exercises.add(pushdown)

        val pullRoutine = RoutineItem("Pull")

        val curl = Exercise("Bicep curl", mutableListOf())
        curl.exerciseSets.add(ExerciseSet(12.0, 10.0))
        curl.exerciseSets.add(ExerciseSet(12.0, 9.0))
        curl.exerciseSets.add(ExerciseSet(12.0, 8.5))
        pullRoutine.exercises.add(curl)

        val pulldown = Exercise("Lat pulldown", mutableListOf())
        pulldown.exerciseSets.add(ExerciseSet(60.0, 11.0))
        pulldown.exerciseSets.add(ExerciseSet(60.0, 10.5))
        pulldown.exerciseSets.add(ExerciseSet(60.0, 10.0))
        pullRoutine.exercises.add(pulldown)

        return mutableListOf(pushRoutine, pullRoutine)
    }

    @SuppressLint("UnsafeOptInUsageError")
    @Serializable
    data class RoutineItem(var name: String, var exercises: MutableList<Exercise> = mutableListOf())
}