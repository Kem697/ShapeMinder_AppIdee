package adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.shapeminder_appidee.databinding.ListItemEditSessionBinding
import com.example.shapeminder_appidee.databinding.ListItemMySessionBinding
import model.data.local.model.Content
import ui.viewModel.HomeViewModel


class EditTrainingAdapter (
    private val dataset: List<Content>,
    private val viewModel: HomeViewModel,
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
        val item = dataset[position]
        holder.binding.exerciseName.setText(item.stringRessourceTitle)
        holder.binding.exerciseBodyPart.setText(item.bodyPart)
        holder.binding.exerciseImage.setImageResource(item.imageRessource)


        holder.binding.deleteExercise.setOnClickListener{
            if (item.addedToSession == true){
                viewModel.deleteWorkoutInEditSession(!item.addedToSession!!,item)
                item.addedToSession = false
                notifyItemRemoved(position)
                var tag = "Delete Btn check??"
                Log.i(tag, "Übung wurde gelöscht: ${item.addedToSession}")
            }
        }
    }

}




