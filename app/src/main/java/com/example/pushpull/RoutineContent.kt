package com.example.pushpull

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.plus
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlin.time.Clock
import kotlin.time.Duration
import kotlin.time.DurationUnit
import kotlin.time.toDuration


@SuppressLint("UnsafeOptInUsageError")
@Serializable
data class ExerciseSet(var weightKg: Double, var reps: Double)

@SuppressLint("UnsafeOptInUsageError")
@Serializable
data class Exercise(var name: String, val exerciseSets: MutableList<ExerciseSet> = mutableListOf())

@SuppressLint("UnsafeOptInUsageError")
@Serializable
data class WorkoutHistoryEntry(
    val routine: RoutineContent.RoutineItem,
    val datetime: LocalDateTime,
    val duration: Duration
) {
    companion object {
        fun getPlaceholders(): MutableList<WorkoutHistoryEntry> {
            val list = mutableListOf<WorkoutHistoryEntry>()

            val routines = RoutineContent.routines!!
            val timezone = TimeZone.currentSystemDefault()
            val now = Clock.System.now().toLocalDateTime(timezone)
            for ((i, routine) in routines.withIndex()) {
                val routine = routine
                val startDateTime = now.toInstant(timezone)
                    .plus(i, DateTimeUnit.DAY, timezone)
                    .toLocalDateTime(timezone)
                val duration =
                    (((i.toDouble() + 15) * 100.0 % 103.0) / 103.0 + 1).toDuration(DurationUnit.HOURS)
                val entry = WorkoutHistoryEntry(routine, startDateTime, duration)
                list.add(entry)
            }

            return list
        }
    }
}


object RoutineContent {
    var exercises: MutableList<Exercise>? = null
    var routines: MutableList<RoutineItem>? = null

    fun  loadTextFromAssetFile(context: Context, filename: String): String {
        return context.assets.open(filename).bufferedReader().use { it.readText() }
    }

    fun loadDefaultExercises(context: Context, preferences: SharedPreferences) {
        val exercisesAsJson = this.loadTextFromAssetFile(context, "default_exercises.json")
        this.exercises = Json.decodeFromString<MutableList<Exercise>>(exercisesAsJson)
        preferences.edit{ this.putString("exercises", exercisesAsJson) }
    }

    fun loadDefaultRoutines(context: Context, preferences: SharedPreferences) {
        val routinesAsJson = this.loadTextFromAssetFile(context, "default_routines.json")
        this.routines = Json.decodeFromString<MutableList<RoutineItem>>(routinesAsJson)
        preferences.edit{ this.putString("routines", routinesAsJson) }
    }

    fun loadExercises(preferences: SharedPreferences) {
        val exercisesAsJson = preferences.getString("exercises", "")!!
        this.exercises = Json.decodeFromString<MutableList<Exercise>>(exercisesAsJson)
    }

    fun loadRoutines(preferences: SharedPreferences) {
        val routinesAsJson = preferences.getString("routines", "")!!
        this.routines = Json.decodeFromString<MutableList<RoutineItem>>(routinesAsJson)
    }

    @SuppressLint("UnsafeOptInUsageError")
    @Serializable
    data class RoutineItem(var name: String, var exercises: MutableList<Exercise> = mutableListOf())
}