package adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.shapeminder_appidee.databinding.ListItemEditSessionBinding
import model.data.local.model.myTraining.Exercise
import ui.viewModel.TrainingsessionViewModel


class EditTrainingAdapter (
    private val dataset: List<Exercise>,
    private val sessionViewModel: TrainingsessionViewModel,
    private var context: Context
): RecyclerView.Adapter<EditTrainingAdapter.EditSessionItemViewHolder>() {
    inner class EditSessionItemViewHolder(val binding: ListItemEditSessionBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EditSessionItemViewHolder {
        val binding = ListItemEditSessionBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return EditSessionItemViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

    override fun onBindViewHolder(holder: EditSessionItemViewHolder, position: Int) {
        val exercise = dataset[position]
        var tag = "Delete Btn check??"
        Log.i(tag, "Übung wurde gelöscht: ${exercise.addedToSession} ${dataset.indexOf(exercise)} $position")
        holder.binding.exerciseName.setText(exercise.stringRessourceTitle)
        holder.binding.exerciseBodyPart.setText(exercise.bodyPart)
        holder.binding.exerciseImage.setImageResource(exercise.imageRessource)

        //Um die Trainingsleistungen für jede Übung vom Nutzer abspeichern zu lassen, muss ich auf die EditText views der EditSessionItems zugreifen.

        holder.binding.deleteExercise.setOnClickListener{
            if (exercise.addedToSession == true){
                sessionViewModel.deleteWorkoutInEditSession(!exercise.addedToSession!!,exercise)
                val getPosition = holder.adapterPosition
                notifyItemRemoved(getPosition)
                var tag = "Delete Btn check??"
                Log.i(tag, "Übung wurde gelöscht: ${exercise.addedToSession} ${dataset.indexOf(exercise)} $position")
            }
        }
    }

}




