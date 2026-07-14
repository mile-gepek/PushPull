package com.example.pushpull

import android.annotation.SuppressLint
import android.content.Intent
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView

import com.example.pushpull.placeholder.PlaceholderContent.PlaceholderItem
import com.example.pushpull.databinding.WorkoutHistoryEntryBinding
import com.example.pushpull.placeholder.PlaceholderContent
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.plus
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlin.time.Clock
import kotlin.time.Duration
import kotlin.time.DurationUnit
import kotlin.time.toDuration


@SuppressLint("UnsafeOptInUsageError")
@Serializable
data class WorkoutHistoryEntry(val routine: RoutineContent.RoutineItem, val datetime: LocalDateTime, val duration: Duration) {
    companion object {
        fun getPlaceholders(): MutableList<WorkoutHistoryEntry> {
            val list = mutableListOf<WorkoutHistoryEntry>()

            val routines = RoutineContent.getDefaultRoutines()
            val timezone = TimeZone.currentSystemDefault()
            val now = Clock.System.now().toLocalDateTime(timezone)
            for (i in 0..10) {
                val routine = routines[i % 2]
                val startDateTime = now.toInstant(timezone)
                    .plus(i, DateTimeUnit.DAY, timezone)
                    .toLocalDateTime(timezone)
                val duration = (((i.toDouble() + 15) * 100.0 % 103.0) / 103.0 + 1).toDuration(DurationUnit.HOURS)
                val entry = WorkoutHistoryEntry(routine, startDateTime, duration)
                list.add(entry)
            }

            return list
        }
    }
}

class WorkoutHistoryAdapter(
) : RecyclerView.Adapter<WorkoutHistoryAdapter.ViewHolder>() {
    private val values: MutableList<WorkoutHistoryEntry> = WorkoutHistoryEntry.getPlaceholders()

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
        holder.binding.startDatetime.text = workoutEntry.datetime.toString()
        holder.binding.duration.text = workoutEntry.duration.toString()

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