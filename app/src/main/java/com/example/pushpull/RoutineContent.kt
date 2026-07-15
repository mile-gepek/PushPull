package com.example.pushpull

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlin.time.Duration


@SuppressLint("UnsafeOptInUsageError")
@Serializable
data class ExerciseSet(var weightKg: Double, var reps: Double)

@SuppressLint("UnsafeOptInUsageError")
@Serializable
data class Exercise(var name: String, val exerciseSets: MutableList<ExerciseSet> = mutableListOf()) {
    fun deepCopy(): Exercise {
        val sets = mutableListOf<ExerciseSet>()
        for (set in this.exerciseSets) {
            sets.add(set.copy())
        }
        return Exercise(
            this.name,
            sets,
        )
    }
}

@SuppressLint("UnsafeOptInUsageError")
@Serializable
data class WorkoutHistoryEntry(
    val routine: RoutineContent.RoutineItem,
    val startDateTime: LocalDateTime,
    val duration: Duration
) {
}


object RoutineContent {
    var exercises: MutableList<Exercise> = mutableListOf()
    var routines: MutableList<RoutineItem> = mutableListOf()

    var workoutHistory: MutableList<WorkoutHistoryEntry> = mutableListOf()
    var workoutHistoryAdapter: WorkoutHistoryAdapter? = null

    fun loadWorkoutHistory(preferences: SharedPreferences) {
        val exercisesAsJson = preferences.getString("workout_history", "[]")!!
        this.workoutHistory = Json.decodeFromString(exercisesAsJson)
        this.workoutHistory.sortByDescending { entry -> entry.startDateTime }
    }

    fun saveWorkout(workoutEntry: WorkoutHistoryEntry, preferences: SharedPreferences) {
        this.workoutHistory.add(0, workoutEntry)
        this.workoutHistory.sortByDescending { entry -> entry.startDateTime } // Znam da bi ovdje bilo bolje koristiti drugu strukturu podataka koja odma dodaje na tocno mjesto
        val workoutHistoryAsJson = Json.encodeToString(this.workoutHistory)
        preferences.edit() { this.putString("workout_history", workoutHistoryAsJson) }
        this.workoutHistoryAdapter?.notifyItemInserted(0)
    }

    fun loadTextFromAssetFile(context: Context, filename: String): String {
        return context.assets.open(filename).bufferedReader().use { it.readText() }
    }

    fun loadDefaultExercises(context: Context, preferences: SharedPreferences) {
        val exercisesAsJson = this.loadTextFromAssetFile(context, "default_exercises.json")
        this.exercises = Json.decodeFromString(exercisesAsJson)
        preferences.edit { this.putString("exercises", exercisesAsJson) }
    }

    fun loadDefaultRoutines(context: Context, preferences: SharedPreferences) {
        val routinesAsJson = this.loadTextFromAssetFile(context, "default_routines.json")
        this.routines = Json.decodeFromString(routinesAsJson)
        preferences.edit { this.putString("routines", routinesAsJson) }
    }

    fun loadExercises(preferences: SharedPreferences) {
        val exercisesAsJson = preferences.getString("exercises", "[]")!!
        this.exercises = Json.decodeFromString(exercisesAsJson)
    }

    fun loadRoutines(preferences: SharedPreferences) {
        val routinesAsJson = preferences.getString("routines", "[]")!!
        this.routines = Json.decodeFromString(routinesAsJson)
    }

    fun saveRoutine(routineIndex: Int, routine: RoutineItem, preferences: SharedPreferences) {
        this.routines[routineIndex] = routine
        val routinesAsJson = Json.encodeToString(this.routines)
        this.saveRoutinesToPreferences(preferences)
    }

    fun addRoutine(routine: RoutineItem, preferences: SharedPreferences) {
        this.routines.add(routine)
        this.saveRoutinesToPreferences(preferences)
    }

    fun deleteRoutine(routineIndex: Int, preferences: SharedPreferences) {
        this.routines.removeAt(routineIndex)
        this.saveRoutinesToPreferences(preferences)
    }

    fun saveRoutinesToPreferences(preferences: SharedPreferences) {
        val routinesAsJson = Json.encodeToString(this.routines)
        preferences.edit() { this.putString("routines", routinesAsJson) }
    }

    @SuppressLint("UnsafeOptInUsageError")
    @Serializable
    data class RoutineItem(var name: String, var exercises: MutableList<Exercise> = mutableListOf()) {
        fun deepCopy(): RoutineItem {
            val exercises: MutableList<Exercise> = mutableListOf()
            for (exercise in this.exercises) {
                exercises.add(exercise.deepCopy())
            }
            return RoutineItem(
                this.name,
                exercises
            )
        }
    }
}