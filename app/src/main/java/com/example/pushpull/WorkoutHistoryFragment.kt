package com.example.pushpull

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pushpull.databinding.WorkoutHistoryListBinding

class WorkoutHistoryFragment : Fragment() {
    lateinit var binding: WorkoutHistoryListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        this.binding = WorkoutHistoryListBinding.inflate(inflater, container, false)


        this.binding.list.adapter = WorkoutHistoryAdapter()
        this.binding.list.layoutManager = LinearLayoutManager(this.binding.root.context)

        return this.binding.root
    }
}