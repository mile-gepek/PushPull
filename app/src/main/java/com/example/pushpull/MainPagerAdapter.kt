package com.example.pushpull

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class MainPagerAdapter(fa: FragmentActivity): FragmentStateAdapter(fa) {
    private val fragments = listOf(
        RoutineListFragment(),
        WorkoutHistoryFragment(),
    )

    override fun getItemCount() = fragments.size
    override fun createFragment(position: Int) = fragments[position]
}