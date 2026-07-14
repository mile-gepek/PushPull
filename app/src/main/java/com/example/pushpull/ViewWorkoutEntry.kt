package com.example.pushpull

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.pushpull.databinding.ActivityViewWorkoutEntryBinding
import kotlinx.serialization.json.Json

class ViewWorkoutEntry : AppCompatActivity() {
    lateinit var binding: ActivityViewWorkoutEntryBinding

    lateinit var routine: RoutineContent.RoutineItem


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.enableEdgeToEdge()

        this.binding = ActivityViewWorkoutEntryBinding.inflate(this.layoutInflater)
        this.setContentView(this.binding.root)

        val routineAsJson = this.intent.getStringExtra("routine")!!
        this.routine = Json.decodeFromString<RoutineContent.RoutineItem>(routineAsJson)

        ViewCompat.setOnApplyWindowInsetsListener(this.findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}