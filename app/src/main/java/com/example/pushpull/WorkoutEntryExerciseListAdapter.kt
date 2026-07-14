package com.example.pushpull

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pushpull.databinding.ExerciseEditItemBinding

class WorkoutEntryExerciseListAdapter(val exercises: MutableList<Exercise>): RecyclerView.Adapter<WorkoutEntryExerciseListAdapter.EntryViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): EntryViewHolder {
        val binding = ExerciseEditItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return EntryViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: EntryViewHolder,
        position: Int
    ) {
        val exercise = this.exercises[position]

        holder.binding.root.removeView(holder.binding.addSetButton)
        holder.binding.root.removeView(holder.binding.removeExerciseButton)

        holder.binding.exerciseName.text = exercise.name

        holder.binding.setList.adapter = SetAdapter(exercise.exerciseSets, editable=false)
        holder.binding.setList.layoutManager = LinearLayoutManager(holder.binding.root.context)
    }

    override fun getItemCount(): Int = this.exercises.size


    inner class EntryViewHolder(val binding: ExerciseEditItemBinding): RecyclerView.ViewHolder(binding.root)

}
