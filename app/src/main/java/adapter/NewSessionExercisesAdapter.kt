package adapter

import android.content.Context
import android.content.res.ColorStateList
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import de.kem697.shapeminder.R
import de.kem697.shapeminder.databinding.ListItemNewSessionExerciseBinding
import de.kem697.shapeminder.model.data.local.model.myTraining.Exercise
import de.kem697.shapeminder.ui.viewModel.ExercisesViewModel


class NewSessionExercisesAdapter (
    private val dataset: List<Exercise>,
    private val viewModel: ExercisesViewModel,
    private val context: Context
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
        checkBox.buttonTintList = ColorStateList.valueOf(ContextCompat.getColor(context, R.color.tertiary))
        holder.binding.exerciseName.setText(exercise.stringRessourceTitle)
        holder.binding.exerciseBodyPart.text = context.getString(exercise.bodyPart)
        holder.binding.exerciseImage.setImageResource(exercise.imageRessource)
        holder.binding.saveExerciseCheckbox.isChecked = exercise.addedToSession == true
        holder.binding.materialCardView.setOnClickListener {
            viewModel.navigateSelectedExercises(exercise)
            holder.binding.root.findNavController().navigate(R.id.exercisePreviewFragment)
        }


        checkBox.setOnClickListener{
            if (exercise.addedToSession == true){
                viewModel.savedInWorkoutSession(!exercise.addedToSession!!,exercise)
                holder.binding.saveExerciseCheckbox.isChecked = false
                exercise.addedToSession = false
                var tag = "Radiocheck??"
                Log.i(tag, "Übung Nicht in der Liste!: ${exercise.addedToSession} ${holder.binding.saveExerciseCheckbox.isChecked}")
            } else{
                viewModel.savedInWorkoutSession(!exercise.addedToSession!!,exercise)
                holder.binding.saveExerciseCheckbox.isChecked = true
                exercise.addedToSession = true
                var tag = "Radiocheck??"
                Log.i(tag, "Übung ist in der Liste!: ${exercise.addedToSession} ${holder.binding.saveExerciseCheckbox.isChecked}")
            }
        }
    }
}





