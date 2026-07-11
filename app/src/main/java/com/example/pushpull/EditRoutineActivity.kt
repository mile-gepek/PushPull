package com.example.pushpull

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pushpull.databinding.ActivityEditRoutinesBinding
import kotlinx.serialization.json.Json

class EditRoutineActivity: AppCompatActivity() {
    lateinit var binding: ActivityEditRoutinesBinding

    lateinit var routine: RoutineContent.RoutineItem


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        this.binding = ActivityEditRoutinesBinding.inflate(layoutInflater)
        setContentView(this.binding.root)

        val routineAsJson = intent.getStringExtra("routine")!!
        this.routine = Json.decodeFromString<RoutineContent.RoutineItem>(routineAsJson)


        this.binding.editRoutineName.setText(routine.name)


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val adapter = RoutineEditExercisesAdapter(this.routine.exercises)
        this.binding.routineEditExerciseList.adapter = adapter
        this.binding.routineEditExerciseList.layoutManager = LinearLayoutManager(this)
    }
}