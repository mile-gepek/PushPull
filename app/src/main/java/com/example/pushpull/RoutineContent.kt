package com.example.pushpull

object RoutineContent {
    val defaultRoutines =
        listOf(RoutineItem("Push", mutableListOf()), RoutineItem("Pull", mutableListOf()))

    data class RoutineItem(var name: String, var exercises: MutableList<Exercise>)
}