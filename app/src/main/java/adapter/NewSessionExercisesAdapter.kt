package adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.shapeminder_appidee.R
import com.example.shapeminder_appidee.databinding.ListItemNewSessionExerciseBinding
import model.data.local.model.Content
import ui.viewModel.HomeViewModel

class NewSessionExercisesAdapter (
    private val dataset: List<Content>,
    private val viewModel: HomeViewModel
): RecyclerView.Adapter<NewSessionExercisesAdapter.ExerciseItemViewHolder>(){

    inner class ExerciseItemViewHolder (val binding: ListItemNewSessionExerciseBinding): RecyclerView.ViewHolder(binding.root)



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseItemViewHolder {
        val binding = ListItemNewSessionExerciseBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ExerciseItemViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

    override fun onBindViewHolder(holder: NewSessionExercisesAdapter.ExerciseItemViewHolder, position: Int) {
        val exercise = dataset[position]
        var checkBox = holder.binding.saveExerciseCheckbox
        holder.binding.exerciseName.setText(exercise.stringRessourceTitle)
        holder.binding.exerciseBodyPart.setText(exercise.bodyPart)
        holder.binding.exerciseImage.setImageResource(exercise.imageRessource)
        if (exercise.addedToSession == true){
            holder.binding.saveExerciseCheckbox.isChecked = true
        } else{
            holder.binding.saveExerciseCheckbox.isChecked = false
        }
        holder.binding.materialCardView.setOnClickListener {
            viewModel.navigateDetailView(exercise)
            holder.binding.root.findNavController().navigate(R.id.exercisePreviewFragment)
        }


        checkBox.setOnClickListener{
            if (exercise.addedToSession == true){
                viewModel.savedInWorkoutSession(!exercise.addedToSession!!,exercise)
                holder.binding.saveExerciseCheckbox.isChecked = true
                exercise.addedToSession = false
            } else{
                viewModel.savedInWorkoutSession(!exercise.addedToSession!!,exercise)
                holder.binding.saveExerciseCheckbox.isChecked = true
                exercise.addedToSession = true
            }
        }
    }
}





