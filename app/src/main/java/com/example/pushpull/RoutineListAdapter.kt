package com.example.pushpull

import android.content.Context
import android.content.Intent
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu

import com.example.pushpull.databinding.RoutineEntryBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.serialization.json.Json

class RoutineListAdapter(
    private val routines: MutableList<RoutineContent.RoutineItem> = RoutineContent.getDefaultRoutines()
) : RecyclerView.Adapter<RoutineListAdapter.RoutineViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoutineViewHolder {
        val binding = RoutineEntryBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return RoutineViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RoutineViewHolder, position: Int) {
        val routine = routines[position]
        holder.binding.routineName.text = routine.name

        holder.binding.layout.setOnClickListener { anchorView ->
            editRoutine(routine, anchorView.context)
        }

        holder.binding.menuButton.setOnClickListener { anchorView ->
            val menu = PopupMenu(anchorView.context, anchorView)
            menu.menuInflater.inflate(R.menu.routine_menu, menu.menu)
            menu.setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.routine_edit -> {
                        editRoutine(routine, anchorView.context)
                        true
                    }
                    R.id.routine_delete -> {
                        confirmDelete(anchorView.context,routine)
                        true
                    }
                    else -> false
                }
            }
            menu.show()
        }

        holder.binding.startRoutineButton.setOnClickListener { anchorView ->
            startRoutine(routine, anchorView.context)
        }
    }


    fun startRoutine(routine: RoutineContent.RoutineItem, context: Context) {
        val routineAsJson = Json.encodeToString(routine)
        val intent = Intent(context, RoutineInProgressActivity::class.java).apply {
            putExtra("routine", routineAsJson)
        }
        context.startActivity(intent)
    }


    fun editRoutine(routine: RoutineContent.RoutineItem, context: Context) {
        val routineAsJson = Json.encodeToString(routine)
        val intent = Intent(context, EditRoutineActivity::class.java).apply {
            putExtra("routine", routineAsJson)
        }
        context.startActivity(intent)
    }

    private fun confirmDelete(context: Context, routine: RoutineContent.RoutineItem) {
        MaterialAlertDialogBuilder(context)
            .setTitle("Delete routine?")
            .setMessage("This will permanently delete the workout routine \"${routine.name}\". This can't be undone.")
            .setPositiveButton("Delete") { _, _ ->
                println("Deleting routine")
                // actual deletion logic here
                // e.g. mutableRoutines.remove(routine); recyclerView.adapter?.notifyDataSetChanged()
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    override fun getItemCount(): Int = routines.size

    inner class RoutineViewHolder(val binding: RoutineEntryBinding) : RecyclerView.ViewHolder(binding.root)
}