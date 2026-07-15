package com.example.pushpull

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.addCallback
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pushpull.databinding.ActivityEditRoutinesBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.serialization.json.Json

class EditRoutineActivity: AppCompatActivity() {
    lateinit var binding: ActivityEditRoutinesBinding

    lateinit var routine: RoutineContent.RoutineItem
    var adapter: RoutineEditExercisesAdapter? = null

    var changesMade = false


    fun onChange() {
        this.changesMade = true
    }

    private val addExerciseRegister = this.registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val exerciseIndex = result.data?.getIntExtra("exercise_index", -1)!!
            if (exerciseIndex >= 0) {
                val exercise = RoutineContent.exercises!![exerciseIndex]
                this.routine.exercises.add(exercise)
                this.adapter?.notifyItemInserted(this.routine.exercises.size)
                this.onChange()
            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.enableEdgeToEdge()

        this.binding = ActivityEditRoutinesBinding.inflate(this.layoutInflater)
        this.setContentView(this.binding.root)

        val routineAsJson = this.intent.getStringExtra("routine")!!
        this.routine = Json.decodeFromString<RoutineContent.RoutineItem>(routineAsJson)


        this.binding.editRoutineName.setText(this.routine.name)
        this.binding.editRoutineName.addTextChangedListener{
            this.onChange()
        }

        this.binding.cancelWorkoutButton.setOnClickListener {
            this.confirmCancel()
        }

        this.binding.addExerciseButton.setOnClickListener {
            val intent = Intent(this, ExerciseSelect::class.java)
            this.addExerciseRegister.launch(intent)
        }


        ViewCompat.setOnApplyWindowInsetsListener(this.findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        this.onBackPressedDispatcher.addCallback(this) {
            this@EditRoutineActivity.confirmCancel()
        }

        this.adapter = RoutineEditExercisesAdapter(this.routine.exercises) {
            this.onChange()
        }
        this.binding.routineEditExerciseList.adapter = this.adapter!!
        this.binding.routineEditExerciseList.layoutManager = LinearLayoutManager(this)
    }

    fun confirmCancel() {
        if (this.changesMade) {
            MaterialAlertDialogBuilder(this@EditRoutineActivity)
                .setTitle("Discard edits?")
                .setMessage("You have unsaved changes. Do you want to discard them?")
                .setPositiveButton("Discard") { _, _ ->
                    this.finish()
                }
                .setNegativeButton("Cancel", null)
                .show()
        } else {
            this.finish()
        }
    }
}