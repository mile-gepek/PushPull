package com.example.pushpull

import android.content.Context
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.pushpull.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayoutMediator
import androidx.core.content.edit

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    val tabTitles = listOf("Routines", "Workouts")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        this.enableEdgeToEdge()

        this.binding = ActivityMainBinding.inflate(this.layoutInflater)
        this.setContentView(this.binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(this.binding.main) { view, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        val preferences = this.getSharedPreferences("routine_exercise_prefs", Context.MODE_PRIVATE)
        val isFirstLaunch = preferences.getBoolean("is_first_launch", true)
        if (isFirstLaunch) {
            RoutineContent.loadDefaultExercises(this, preferences)
            RoutineContent.loadDefaultRoutines(this, preferences)
            preferences.edit { this.putBoolean("is_first_launch", false) }
        } else {
            RoutineContent.loadExercises(preferences)
            RoutineContent.loadRoutines(preferences)
        }

        this.binding.viewPager2.adapter = MainPagerAdapter(this)

        TabLayoutMediator(
            this.binding.tabLayout, this.binding.viewPager2, false, true
        ) { tab, position ->
            tab.text = this.tabTitles[position]
        }.attach()
    }
}
