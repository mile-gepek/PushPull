package com.example.pushpull

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pushpull.databinding.ActivityViewWorkoutEntryBinding
import kotlinx.serialization.json.Json

class ViewWorkoutEntry : AppCompatActivity() {
    lateinit var binding: ActivityViewWorkoutEntryBinding

    lateinit var workoutEntry: WorkoutHistoryEntry


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.enableEdgeToEdge()

        this.binding = ActivityViewWorkoutEntryBinding.inflate(this.layoutInflater)
        this.setContentView(this.binding.root)

        val workoutEntryAsJson = this.intent.getStringExtra("workout_entry")!!

        this.workoutEntry = Json.decodeFromString<WorkoutHistoryEntry>(workoutEntryAsJson)

        ViewCompat.setOnApplyWindowInsetsListener(this.findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        this.binding.exerciseList.adapter = WorkoutEntryExerciseListAdapter(this.workoutEntry.routine.exercises)
        this.binding.exerciseList.layoutManager = LinearLayoutManager(this.binding.root.context)
    }
}