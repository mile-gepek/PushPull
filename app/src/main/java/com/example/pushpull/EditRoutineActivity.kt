package com.example.pushpull

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pushpull.databinding.ActivityEditRoutinesBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.serialization.json.Json

class EditRoutineActivity: AppCompatActivity() {
    lateinit var binding: ActivityEditRoutinesBinding

    lateinit var routine: RoutineContent.RoutineItem

    var changesMade = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.enableEdgeToEdge()

        this.binding = ActivityEditRoutinesBinding.inflate(this.layoutInflater)
        this.setContentView(this.binding.root)

        val routineAsJson = this.intent.getStringExtra("routine")!!
        this.routine = Json.decodeFromString<RoutineContent.RoutineItem>(routineAsJson)


        this.binding.editRoutineName.setText(this.routine.name)

        this.binding.cancelWorkoutButton.setOnClickListener {
            if (this.changesMade) {
                this.confirmCancel()
            } else {
                this.finish()
            }
        }


        ViewCompat.setOnApplyWindowInsetsListener(this.findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val adapter = RoutineEditExercisesAdapter(this.routine.exercises)
        this.binding.routineEditExerciseList.adapter = adapter
        this.binding.routineEditExerciseList.layoutManager = LinearLayoutManager(this)
    }

    fun confirmCancel() {
        MaterialAlertDialogBuilder(this@EditRoutineActivity)
            .setTitle("Discard edits?")
            .setMessage("You have unsaved changes. Do you want to discard them?")
            .setPositiveButton("Discard") { _, _ ->
                this.finish()
            }
            .setNegativeButton("Cancel", null)
            .show()
    }
}