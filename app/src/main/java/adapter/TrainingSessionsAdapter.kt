package adapter
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.shapeminder_appidee.R
import com.example.shapeminder_appidee.databinding.ListItemBinding
import com.example.shapeminder_appidee.databinding.ListItemMySessionBinding
import com.example.shapeminder_appidee.databinding.ListItemNewSessionExerciseBinding
import model.data.local.model.TrainingsSession
import ui.viewModel.HomeViewModel
class TrainingSessionsAdapter (
    private val dataset: List<TrainingsSession>,
    private val viewModel: HomeViewModel,
    private var context: Context
): RecyclerView.Adapter<TrainingSessionsAdapter.TrainingsSessionItemViewHolder>() {
    inner class TrainingsSessionItemViewHolder(val binding: ListItemMySessionBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TrainingsSessionItemViewHolder {
        val binding = ListItemMySessionBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return TrainingsSessionItemViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

    override fun onBindViewHolder(holder: TrainingsSessionItemViewHolder, position: Int) {
        var item = dataset[position]
        holder.binding.sessionDate.setText(item.sessionDate)
        holder.binding.sessionTitle.setText(item.sessionName)
        holder.binding.contentImage.setImageResource(R.drawable.content2_img)
    }
}



