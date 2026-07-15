package com.example.pushpull

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pushpull.databinding.WorkoutHistoryEntryBinding
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.format
import kotlinx.datetime.format.char
import kotlinx.serialization.json.Json


class WorkoutHistoryAdapter(
) : RecyclerView.Adapter<WorkoutHistoryAdapter.ViewHolder>() {
    private val values: MutableList<WorkoutHistoryEntry> = RoutineContent.workoutHistory

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
        val workoutEntry = this.values[position]

        holder.binding.routineName.text = workoutEntry.routine.name

        val datetimeFormat = LocalDateTime.Format {
            this.year(); this.char('-'); this.monthNumber(); this.char('-'); this.day()
            this.char(' ')
            this.hour(); this.char(':'); this.minute()
        }
        holder.binding.startDatetime.text = workoutEntry.startDateTime.format(datetimeFormat)


        holder.binding.duration.text = workoutEntry.duration.toComponents { hours, minutes, seconds, _ ->
            if (hours > 0) {
                "%02d:%02d:%02d".format(hours, minutes, seconds)
            } else {
                "%02d:%02d".format(minutes, seconds)
            }
        }

        holder.binding.root.setOnClickListener { anchorView ->
            val workoutEntryAsJson = Json.encodeToString(workoutEntry)
            val intent = Intent(anchorView.context, ViewWorkoutEntry::class.java).apply {
                this.putExtra("workout_entry", workoutEntryAsJson)
            }
            anchorView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = this.values.size

    inner class ViewHolder(val binding: WorkoutHistoryEntryBinding) : RecyclerView.ViewHolder(binding.root)

}