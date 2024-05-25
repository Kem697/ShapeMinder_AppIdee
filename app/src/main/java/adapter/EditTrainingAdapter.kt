package adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.RecyclerView
import de.kem697.shapeminder.R
import de.kem697.shapeminder.databinding.ListItemEditSessionBinding
import de.kem697.shapeminder.model.data.local.model.myTraining.Exercise
import de.kem697.shapeminder.model.data.local.model.myTraining.TrainingsSession
import de.kem697.shapeminder.ui.viewModel.TrainingsessionViewModel


class EditTrainingAdapter (
    private val trainingsSession: TrainingsSession,
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

        var userInputReps = holder.binding.editReps
        var userInputSets = holder.binding.editSets
        var userInputWeight = holder.binding.editWeight

        var tag = "Delete Btn check??"
        Log.i(tag, "Übung wurde gelöscht: ${exercise.addedToSession} ${dataset.indexOf(exercise)} $position")
        holder.binding.exerciseName.setText(exercise.stringRessourceTitle)
        holder.binding.exerciseBodyPart.setText(exercise.bodyPart)
        holder.binding.exerciseImage.setImageResource(exercise.imageRessource)


        Log.i("Session", "Übungen: ${trainingsSession.trainingsSession.size} Trainingsdaten: ${trainingsSession.performance.size}")
        holder.binding.deleteExercise.setOnClickListener{
            if (exercise.addedToSession == true){
                val getElementIndexPosition = holder.adapterPosition
                sessionViewModel.deleteWorkoutInEditSession(!exercise.addedToSession!!,exercise)
                notifyItemRemoved(getElementIndexPosition)
                var tag = "Delete Btn check??"
                Log.i(tag, "Übung wurde gelöscht: ${exercise.addedToSession} ${dataset.indexOf(exercise)} $position | Größe Performance Liste: ${trainingsSession.performance.size}")
            }
        }

        //Um die Trainingsleistungen für jede Übung vom Nutzer abspeichern zu lassen, muss ich auf die EditText views der EditSessionItems zugreifen.


         try {
             editPerformance(userInputReps,userInputSets,userInputWeight,holder)
         } catch (e:Exception){
             Log.e("EditTrainingAdapter", "Error in editPerformance: ${e.message}", e)
             Toast.makeText(context,context.getString(R.string.toastUnknownFailure),Toast.LENGTH_SHORT).show()
        }
    }

    var tag = "Performance Save"


    fun editPerformance(userInputReps: EditText, userInputSets: EditText, userInputWeight: EditText, holder: EditSessionItemViewHolder){


            val getElementIndexPosition = holder.adapterPosition

            userInputReps.addTextChangedListener { userInput ->
                userInput.toString()
                sessionViewModel.editTrainingPerformance(getElementIndexPosition,context,userInput.toString(),userInputReps.id,trainingsSession)
                Log.i("User Input","Reps Input: ${userInput.toString()} | Adapter Position: $getElementIndexPosition")
            }
            userInputSets.addTextChangedListener { userInput ->
                sessionViewModel.editTrainingPerformance(getElementIndexPosition,context,userInput.toString(),userInputSets.id,trainingsSession)
                Log.i("User Input","Sets Input: ${userInput.toString()} | Adapter Position: $getElementIndexPosition")
            }
            userInputWeight.addTextChangedListener { userInput ->
                userInput.toString()
                sessionViewModel.editTrainingPerformance(getElementIndexPosition,context,userInput.toString(),userInputWeight.id,trainingsSession)
                Log.i("User Input","Weight Input: ${userInput.toString()} | Adapter Position: $getElementIndexPosition")
            }

        //This ensures a safely access to the performance data to avoid potential IndexOutOfBoundsExceptions

        trainingsSession.performance.getOrNull(getElementIndexPosition)?.let {
            holder.binding.editSets.setText(it.sets)
            holder.binding.editReps.setText(it.reps)
            holder.binding.editWeight.setText(it.weight)
        } ?: run {
            Log.e("Performance Save", "Invalid index: $getElementIndexPosition")
        }
            Log.i(tag,"Sets: ${trainingsSession.performance.firstOrNull()?.sets} | Reps: ${trainingsSession.performance.firstOrNull()?.reps}  Weight: ${trainingsSession.performance.firstOrNull()?.weight}|")
    }
}




