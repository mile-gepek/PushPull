package com.example.pushpull

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.pushpull.databinding.RoutineEntryListBinding
import com.example.pushpull.placeholder.PlaceholderContent

class RoutineListFragment : Fragment() {

    lateinit var binding: RoutineEntryListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        this.binding = RoutineEntryListBinding.inflate(inflater, container, false)


        this.binding.list.adapter = RoutineListAdapter()
        this.binding.list.layoutManager = LinearLayoutManager(this.binding.root.context)

        return this.binding.root
    }
}