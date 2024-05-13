package adapter

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.EditText
import androidx.core.view.isInvisible
import androidx.core.widget.addTextChangedListener
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

        holder.binding.deleteExercise.setOnClickListener{
            if (exercise.addedToSession == true){
                sessionViewModel.deleteWorkoutInEditSession(!exercise.addedToSession!!,exercise)
                val getPosition = holder.adapterPosition
                notifyItemRemoved(getPosition)
                var tag = "Delete Btn check??"
                Log.i(tag, "Übung wurde gelöscht: ${exercise.addedToSession} ${dataset.indexOf(exercise)} $position")
            }
        }

        //Um die Trainingsleistungen für jede Übung vom Nutzer abspeichern zu lassen, muss ich auf die EditText views der EditSessionItems zugreifen.


        /*      val sharedPreferences = context.getSharedPreferences("user_inputs", Context.MODE_PRIVATE)
              val editor = sharedPreferences.edit()

              var userInputReps = holder.binding.editReps
              var userInputSets = holder.binding.editSets
              var userInputWeight = holder.binding.editWeight*/

        /*  saveData(editor,userInputReps,userInputSets,userInputWeight)
          loadData(holder,position,sharedPreferences)*/
    }


    fun saveData(editor: SharedPreferences.Editor,userInputReps: EditText, userInputSets: EditText, userInputWeight: EditText){
        userInputReps.addTextChangedListener { userInput ->
            editor.putString("reps", userInput.toString())
            editor.apply()
        }
        userInputSets.addTextChangedListener { userInput ->
            editor.putString("sets", userInput.toString())
            editor.apply()
        }
        userInputWeight.addTextChangedListener { userInput ->
            editor.putString("weight", userInput.toString())
            editor.apply()
        }
    }


    fun loadData(holder: EditSessionItemViewHolder, position: Int, sharedPreferences: SharedPreferences){
        val savedReps = sharedPreferences.getString("reps",null)
        val savedSets = sharedPreferences.getString("sets",null)
        val savedWeight = sharedPreferences.getString("weight",null)

        holder.binding.editReps.setText(savedReps)
        holder.binding.editSets.setText(savedSets)
        holder.binding.editWeight.setText(savedWeight)

    }

}




