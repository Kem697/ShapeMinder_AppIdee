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
import de.kem697.shapeminder.databinding.ListItemCurrentSessionExerciseBinding
import de.kem697.shapeminder.model.data.local.model.myTraining.Exercise
import de.kem697.shapeminder.ui.viewModel.ExercisesViewModel
import de.kem697.shapeminder.ui.viewModel.TrainingsessionViewModel


class CurrentSessionExerciseAdapter (
    private val dataset: List<Exercise>,
    private val viewModel: ExercisesViewModel,
    private val sessionViewModel: TrainingsessionViewModel,
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
        holder.binding.exerciseBodyPart.text = context.getString(exercise.bodyPart)
        holder.binding.exerciseImage.setImageResource(exercise.imageRessource)
        holder.binding.saveExerciseCheckbox.isChecked = exercise.addedToSession == true
        holder.binding.materialCardView.setOnClickListener {
            viewModel.navigateSelectedExercises(exercise)
            holder.binding.root.findNavController().navigate(R.id.action_allExerciseListForEditSessionFragment_to_exercisePreviewFragment)
        }


        checkBox.setOnClickListener{
            if (exercise.addedToSession == true){
               sessionViewModel.deleteWorkoutInEditSession(!exercise.addedToSession!!,exercise)
                holder.binding.saveExerciseCheckbox.isChecked = false
                exercise.addedToSession = false
                var tag = "CheckBox??"
                Log.i(tag, "Übung Nicht in der Liste!: ${exercise.addedToSession} ${holder.binding.saveExerciseCheckbox.isChecked}")
            } else{
                sessionViewModel.deleteWorkoutInEditSession(!exercise.addedToSession!!,exercise)
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





