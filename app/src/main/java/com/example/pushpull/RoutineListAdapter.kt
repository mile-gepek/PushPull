package com.example.pushpull

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView

import com.example.pushpull.placeholder.PlaceholderContent.PlaceholderItem
import com.example.pushpull.databinding.RoutineEntryBinding

/**
 * [RecyclerView.Adapter] that can display a [PlaceholderItem].
 * TODO: Replace the implementation with code for your data type.
 */
class RoutineListAdapter(
    private val routines: List<RoutineContent.RoutineItem> = RoutineContent.defaultRoutines
) : RecyclerView.Adapter<RoutineListAdapter.RoutineViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoutineViewHolder {

        return RoutineViewHolder(
            RoutineEntryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }

    override fun onBindViewHolder(holder: RoutineViewHolder, position: Int) {
        val routine = routines[position]
        holder.name.text = routine.name
    }

    override fun getItemCount(): Int = routines.size

    inner class RoutineViewHolder(binding: RoutineEntryBinding) : RecyclerView.ViewHolder(binding.root) {
        val name: TextView = binding.routineName

        override fun toString(): String {
            return super.toString() + " '" + name + "'"
        }
    }

}