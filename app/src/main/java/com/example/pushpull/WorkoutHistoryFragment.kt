package com.example.pushpull

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.pushpull.databinding.RoutineEntryListBinding
import com.example.pushpull.databinding.WorkoutHistoryListBinding
import com.example.pushpull.placeholder.PlaceholderContent

/**
 * A fragment representing a list of Items.
 */
class WorkoutHistoryFragment : Fragment() {
    lateinit var binding: WorkoutHistoryListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        this.binding = WorkoutHistoryListBinding.inflate(inflater, container, false)


        this.binding.list.adapter = WorkoutHistoryAdapter()
        this.binding.list.layoutManager = LinearLayoutManager(this.binding.root.context)

        return this.binding.root
    }

    companion object {

        // TODO: Customize parameter argument names
        const val ARG_COLUMN_COUNT = "column-count"

        // TODO: Customize parameter initialization
        @JvmStatic
        fun newInstance(columnCount: Int) =
            WorkoutHistoryFragment().apply {
                this.arguments = Bundle().apply {
                    this.putInt(this@Companion.ARG_COLUMN_COUNT, columnCount)
                }
            }
    }
}