package com.example.pushpull

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.pushpull.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    val tabTitles = listOf("Routines", "Workouts")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        this.binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(this.binding.root)

        this.binding.viewPager2.adapter = MainPagerAdapter(this)
//        this.binding.viewPager2.offscreenPageLimit = 1

        TabLayoutMediator(
            this.binding.tabLayout, this.binding.viewPager2, false, true
        ) { tab, position ->
            tab.text = this.tabTitles[position]
        }.attach()
    }
}
