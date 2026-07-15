package com.example.pushpull

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pushpull.databinding.ExerciseEditItemBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class RoutineEditExercisesAdapter(private val exercises: MutableList<Exercise>, val onChanged: (() -> Unit)? = null): RecyclerView.Adapter<RoutineEditExercisesAdapter.EditExercisesView>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EditExercisesView {
        val binding = ExerciseEditItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        binding.setList.layoutManager = LinearLayoutManager(parent.context)

        return EditExercisesView(binding)
    }

    override fun getItemCount(): Int {
        return this.exercises.size
    }

    override fun onBindViewHolder(holder: EditExercisesView, position: Int) {
        val exercise = this.exercises[position]

        holder.binding.exerciseName.text = exercise.name

        val adapter = SetAdapter(exercise.exerciseSets, this.onChanged,)
        holder.binding.setList.adapter = adapter

        holder.binding.addSetButton.setOnClickListener {
            val lastSet = if (exercise.exerciseSets.isEmpty()) {
                ExerciseSet(0.0, 0.0)
            } else {
                exercise.exerciseSets.last().copy()
            }
            exercise.exerciseSets.add(lastSet)
            this.onChanged?.invoke()
            adapter.notifyItemInserted(exercise.exerciseSets.size)
        }

        holder.binding.removeExerciseButton.setOnClickListener { anchorView ->
            MaterialAlertDialogBuilder(anchorView.context)
                .setTitle("Remove exercise?")
                .setMessage("Are you sure you want to remove this exercise?")
                .setPositiveButton("Remove") { _, _ ->
                    this.exercises.removeAt(position)
                    this.notifyItemRemoved(position)
                    this.onChanged?.invoke()
                }
                .setNegativeButton("Cancel", null)
                .show()
        }
    }

    class EditExercisesView(val binding: ExerciseEditItemBinding) : RecyclerView.ViewHolder(binding.root)
}