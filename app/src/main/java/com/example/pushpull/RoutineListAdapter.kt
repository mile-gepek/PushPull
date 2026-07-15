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
    private val routines: MutableList<RoutineContent.RoutineItem> = RoutineContent.routines!!
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
        val routine = this.routines[position]
        holder.binding.routineName.text = routine.name

        holder.binding.layout.setOnClickListener { anchorView ->
            this.editRoutine(position, anchorView.context)
        }

        holder.binding.menuButton.setOnClickListener { anchorView ->
            val menu = PopupMenu(anchorView.context, anchorView)
            menu.menuInflater.inflate(R.menu.routine_menu, menu.menu)
            menu.setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.routine_edit -> {
                        this.editRoutine(position, anchorView.context)
                        true
                    }

                    R.id.routine_delete -> {
                        this.confirmDelete(anchorView.context, position)
                        true
                    }

                    else -> false
                }
            }
            menu.show()
        }

        holder.binding.startRoutineButton.setOnClickListener { anchorView ->
            this.startRoutine(position, anchorView.context)
        }
    }

    fun startRoutine(routineIndex: Int, context: Context) {
        val intent = Intent(context, RoutineInProgressActivity::class.java).apply {
            this.putExtra("routine_index", routineIndex)
        }
        context.startActivity(intent)
    }

    fun editRoutine(routineIndex: Int, context: Context) {
        val intent = Intent(context, EditRoutineActivity::class.java).apply {
            this.putExtra("routine_index", routineIndex)
        }
        context.startActivity(intent)
    }

    private fun confirmDelete(context: Context, routineIndex: Int) {
        val routine = RoutineContent.routines[routineIndex]
        MaterialAlertDialogBuilder(context)
            .setTitle("Delete routine?")
            .setMessage("This will permanently delete the workout routine \"${routine.name}\". This can't be undone.")
            .setPositiveButton("Delete") { _, _ ->
                val preferences =
                    context.getSharedPreferences("routine_exercise_prefs", Context.MODE_PRIVATE)
                RoutineContent.deleteRoutine(routineIndex, preferences)
                this.notifyItemRemoved(routineIndex)
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    override fun getItemCount(): Int = this.routines.size

    inner class RoutineViewHolder(val binding: RoutineEntryBinding) :
        RecyclerView.ViewHolder(binding.root)
}