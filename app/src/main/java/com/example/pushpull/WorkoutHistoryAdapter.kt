package com.example.pushpull

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView

import com.example.pushpull.placeholder.PlaceholderContent.PlaceholderItem
import com.example.pushpull.databinding.WorkoutHistoryEntryBinding
import com.example.pushpull.placeholder.PlaceholderContent

/**
 * [RecyclerView.Adapter] that can display a [PlaceholderItem].
 * TODO: Replace the implementation with code for your data type.
 */

class WorkoutHistoryAdapter(
) : RecyclerView.Adapter<WorkoutHistoryAdapter.ViewHolder>() {
    private val values: List<PlaceholderItem> = listOf(PlaceholderContent.PlaceholderItem("a", "b", "c"), PlaceholderContent.PlaceholderItem("d", "e", "f"))

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            WorkoutHistoryEntryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.idView.text = item.id
        holder.contentView.text = item.content
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(binding: WorkoutHistoryEntryBinding) : RecyclerView.ViewHolder(binding.root) {
        val idView: TextView = binding.itemNumber
        val contentView: TextView = binding.content

        override fun toString(): String {
            return super.toString() + " '" + contentView.text + "'"
        }
    }

}