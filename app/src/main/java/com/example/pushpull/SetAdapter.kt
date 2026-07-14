package com.example.pushpull

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pushpull.databinding.SetItemBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class SetAdapter(
    val exerciseSets: MutableList<ExerciseSet>,
    val onChanged: (() -> Unit)? = null,
    val editable: Boolean = true
): RecyclerView.Adapter<SetAdapter.SetViewHolder>() {
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
        val set = this.exerciseSets[position]
        holder.binding.setNumber.text = (position + 1).toString()
        holder.binding.weightKg.setText(set.weightKg.toString())
        holder.binding.reps.setText(set.reps.toString())

        if (this.editable) {
            holder.binding.setNumber.setOnClickListener { anchorView ->
                MaterialAlertDialogBuilder(anchorView.context)
                    .setTitle("Remove set?")
                    .setMessage("Are you sure you want to remove this set?")
                    .setPositiveButton("Remove") { _, _ ->
                        this.exerciseSets.removeAt(position)
                        this.notifyItemRemoved(position)
                        this.onChanged?.invoke()
                    }
                    .setNegativeButton("Cancel", null)
                    .show()
                true
            }
        }
    }

    override fun getItemCount(): Int = this.exerciseSets.size


    inner class SetViewHolder(val binding: SetItemBinding): RecyclerView.ViewHolder(binding.root)
}