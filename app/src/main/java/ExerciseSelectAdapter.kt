import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pushpull.Exercise
import com.example.pushpull.databinding.ExerciseSelectItemBinding

class ExerciseSelectAdapter(val exercises: MutableList<Exercise>, val onExerciseSelect: (Int) -> Unit): RecyclerView.Adapter<ExerciseSelectAdapter.ExerciseViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ExerciseViewHolder {
        val binding = ExerciseSelectItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ExerciseViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: ExerciseViewHolder,
        position: Int
    ) {
        val exercise = this.exercises[position]
        holder.binding.exerciseName.text = exercise.name

        holder.binding.root.setOnClickListener {
            this.onExerciseSelect(position)
        }
    }

    override fun getItemCount(): Int = this.exercises.size

    class ExerciseViewHolder(val binding: ExerciseSelectItemBinding): RecyclerView.ViewHolder(binding.root)
}