package com.example.pushpull

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pushpull.databinding.SetItemBinding

class SetAdapter(val exerciseSets: MutableList<ExerciseSet>): RecyclerView.Adapter<SetAdapter.SetViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SetViewHolder {
        val binding = SetItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return SetViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: SetViewHolder,
        position: Int
    ) {
        for (set in this.exerciseSets.withIndex()) {
            holder.binding.setNumber.text = "${set.index + 1}"
            holder.binding.weightKg.setText(set.value.weightKg.toString())
            holder.binding.reps.setText(set.value.reps.toString())
        }
    }

    override fun getItemCount(): Int = exerciseSets.size


    inner class SetViewHolder(val binding: SetItemBinding): RecyclerView.ViewHolder(binding.root)
}