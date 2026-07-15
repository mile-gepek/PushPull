package com.example.pushpull

import ExerciseSelectAdapter
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pushpull.databinding.ActivityExerciseSelectBinding

class ExerciseSelect : AppCompatActivity() {
    lateinit var binding: ActivityExerciseSelectBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        this.binding = ActivityExerciseSelectBinding.inflate(this.layoutInflater)
        this.setContentView(this.binding.root)

        this.enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(this.binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val adapter = ExerciseSelectAdapter(RoutineContent.exercises!!) { exerciseIndex ->
            val exerciseIndexIntent = Intent().apply {
                this.putExtra("exercise_index", exerciseIndex)
            }
            this.setResult(Activity.RESULT_OK, exerciseIndexIntent)
            this.finish()
        }

        this.binding.list.adapter = adapter
        this.binding.list.layoutManager = LinearLayoutManager(this)
    }
}