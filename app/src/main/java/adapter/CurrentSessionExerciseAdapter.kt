package adapter



import android.content.Context
import android.content.res.ColorStateList
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.shapeminder_appidee.R
import com.example.shapeminder_appidee.databinding.ListItemCurrentSessionExerciseBinding
import com.example.shapeminder_appidee.databinding.ListItemNewSessionExerciseBinding
import model.data.local.model.Content
import ui.viewModel.HomeViewModel

class CurrentSessionExerciseAdapter (
    private val dataset: List<Content>,
    private val viewModel: HomeViewModel,
    private val context: Context
): RecyclerView.Adapter<CurrentSessionExerciseAdapter.ExerciseItemViewHolder>(){

    inner class ExerciseItemViewHolder (val binding: ListItemCurrentSessionExerciseBinding): RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseItemViewHolder {
        val binding = ListItemCurrentSessionExerciseBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ExerciseItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CurrentSessionExerciseAdapter.ExerciseItemViewHolder, position: Int) {
        val exercise = dataset[position]
        var checkBox = holder.binding.saveExerciseCheckbox
        checkBox.buttonTintList = ColorStateList.valueOf(ContextCompat.getColor(context, R.color.tertiary))
        holder.binding.exerciseName.setText(exercise.stringRessourceTitle)
        holder.binding.exerciseBodyPart.text = exercise.bodyPart
        holder.binding.exerciseImage.setImageResource(exercise.imageRessource)
        holder.binding.saveExerciseCheckbox.isChecked = exercise.addedToSession == true
        holder.binding.materialCardView.setOnClickListener {
            viewModel.navigateDetailView(exercise)
            holder.binding.root.findNavController().navigate(R.id.exercisePreviewFragment)
        }


        checkBox.setOnClickListener{
            if (exercise.addedToSession == true){
                viewModel.deleteWorkoutInEditSession(!exercise.addedToSession!!,exercise)
                holder.binding.saveExerciseCheckbox.isChecked = false
                exercise.addedToSession = false
                var tag = "CheckBox??"
                Log.i(tag, "Übung Nicht in der Liste!: ${exercise.addedToSession} ${holder.binding.saveExerciseCheckbox.isChecked}")
            } else{
                viewModel.savedInWorkoutSession(!exercise.addedToSession!!,exercise)
                holder.binding.saveExerciseCheckbox.isChecked = true
                exercise.addedToSession = true
                var tag = "CheckBox??"
                Log.i(tag, "Übung ist in der Liste!: ${exercise.addedToSession} ${holder.binding.saveExerciseCheckbox.isChecked}")
            }
        }
    }

    override fun getItemCount(): Int {
        return dataset.size
    }


}





