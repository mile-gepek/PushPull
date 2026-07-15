package com.example.pushpull

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
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

    fun formatWithoutTrailingDecimals(value: Double): String {
        if (value == value.toLong().toDouble()) {
            return value.toLong().toString()
        }
        return value.toString()
    }

    override fun onBindViewHolder(
        holder: SetViewHolder,
        position: Int
    ) {
        val set = this.exerciseSets[position]
        holder.binding.setNumber.text = (position + 1).toString()
        val weightFormatted = this.formatWithoutTrailingDecimals(set.weightKg)
        val repsFormatted = this.formatWithoutTrailingDecimals(set.reps)

        if (this.editable) {
            holder.binding.weightKg.setHint(this.formatWithoutTrailingDecimals(set.weightKg))
            holder.binding.reps.setHint(this.formatWithoutTrailingDecimals(set.reps))
        } else {
            holder.binding.weightKg.setText(weightFormatted)
            holder.binding.reps.setText(repsFormatted)
        }

        val initialWeight = set.weightKg
        val initialReps = set.reps

        if (this.editable) {
            holder.binding.weightKg.addTextChangedListener { newWeightString ->
                this.onChanged?.invoke()
                if (newWeightString.isNullOrBlank()) {
                    set.weightKg = initialWeight
                    return@addTextChangedListener
                }
                val newWeight = newWeightString.toString().toDoubleOrNull()
                set.weightKg = newWeight ?: 0.0
            }

            holder.binding.reps.addTextChangedListener { newRepString ->
                this.onChanged?.invoke()
                if (newRepString.isNullOrBlank()) {
                    set.reps = initialReps
                    return@addTextChangedListener
                }
                val newReps = newRepString.toString().toDoubleOrNull()
                set.reps = newReps ?: 0.0
            }

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