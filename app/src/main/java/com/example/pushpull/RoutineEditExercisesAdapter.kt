package com.example.pushpull

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pushpull.databinding.ExerciseEditItemBinding

class RoutineEditExercisesAdapter(private val exercises: MutableList<Exercise>): RecyclerView.Adapter<RoutineEditExercisesAdapter.EditExercisesView>() {


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

        holder.binding.setList.adapter = SetAdapter(exercise.exerciseSets)
    }

    inner class EditExercisesView(val binding: ExerciseEditItemBinding) : RecyclerView.ViewHolder(binding.root)
}