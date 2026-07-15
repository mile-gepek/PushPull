package com.example.pushpull

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pushpull.databinding.RoutineEntryListBinding
import kotlinx.serialization.json.Json

class RoutineListFragment : Fragment() {

    lateinit var binding: RoutineEntryListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        this.binding = RoutineEntryListBinding.inflate(inflater, container, false)


        val adapter = RoutineListAdapter()
        this.binding.list.adapter = adapter
        this.binding.list.layoutManager = LinearLayoutManager(this.binding.root.context)


        this.binding.addRoutineButton.setOnClickListener {
            val intent = Intent(this.binding.root.context, RoutineInProgressActivity::class.java)
            this.startActivity(intent)
        }

        return this.binding.root
    }
}