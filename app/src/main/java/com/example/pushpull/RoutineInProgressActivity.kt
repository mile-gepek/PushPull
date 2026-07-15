package com.example.pushpull

import android.content.Context
import android.os.Bundle
import androidx.activity.addCallback
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pushpull.databinding.ActivityRoutineInProgressBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.seconds

class RoutineInProgressActivity : AppCompatActivity() {
    lateinit var binding: ActivityRoutineInProgressBinding

    lateinit var routine: RoutineContent.RoutineItem

    var duration: Duration = Duration.ZERO
    var timerJob: Job? = null

    var routineChanged = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.enableEdgeToEdge()

        this.binding = ActivityRoutineInProgressBinding.inflate(this.layoutInflater)
        this.setContentView(this.binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(this.findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val routineAsJson = this.intent.getStringExtra("routine")!!
        this.routine = Json.decodeFromString<RoutineContent.RoutineItem>(routineAsJson)

        val adapter = RoutineEditExercisesAdapter(this.routine.exercises) {
            this.routineChanged = true
        }
        this.binding.routineEditExerciseList.adapter = adapter
        this.binding.routineEditExerciseList.layoutManager = LinearLayoutManager(this)

        this.binding.cancelWorkoutButton.setOnClickListener { anchorView ->
            this.confirmCancelWorkout(anchorView.context)
        }

        this.onBackPressedDispatcher.addCallback(this) {
            this@RoutineInProgressActivity.confirmCancelWorkout(this@RoutineInProgressActivity)
        }

        this.startTimer()
    }

    fun startTimer() {
        fun updateUI() {
            var timerText: String = ""
            val hours = this.duration.inWholeHours
            val minutes = this.duration.inWholeMinutes
            val seconds = this.duration.inWholeSeconds
            if (hours > 0) {
                timerText = "%02d:".format(hours)
            }
            timerText += "%02d:%02d".format(minutes, seconds)
            this.binding.workoutProgressTimer.text = timerText
        }

        this.timerJob = this.lifecycleScope.launch {
            while (true) {
                updateUI()
                delay(1000.milliseconds)
                this@RoutineInProgressActivity.duration += 1.seconds
            }
        }
    }

    fun confirmCancelWorkout(context: Context) {
        MaterialAlertDialogBuilder(context)
            .setTitle("Discard workout?")
            .setPositiveButton("Discard") { _, _ ->
                this.finish()
            }
            .setNegativeButton("Resume", null)
            .show()
    }
}