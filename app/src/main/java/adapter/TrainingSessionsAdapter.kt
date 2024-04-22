package adapter
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.shapeminder_appidee.R
import com.example.shapeminder_appidee.databinding.ListItemMySessionBinding
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
        val item = dataset[position]
        if (position == 0) {
            // Behandlung des ersten Elements
            holder.binding.sessionDate.visibility = View.GONE // Verstecke das Datum
            holder.binding.sessionTitle.text = context.getString(R.string.addNewTrainingsessionText)
            holder.binding.contentImage.setBackgroundColor(context.getColor(R.color.black))
            holder.binding.contentImage.setImageResource(R.drawable.add_fill0_wght400_grad0_opsz24)
            holder.binding.materialCardView.setOnClickListener {
                holder.binding.root.findNavController().navigate(R.id.newTrainingsSessionFragment)
            }

        } else {
            holder.binding.sessionDate.visibility = View.VISIBLE // Zeige das Datum
            holder.binding.sessionDate.text = item.sessionDate
            holder.binding.sessionTitle.text = item.sessionName
            holder.binding.contentImage.setImageResource(R.drawable.content2_img)
            holder.binding.materialCardView.setOnClickListener {
                holder.binding.root.findNavController().navigate(R.id.editTrainingSessionFragment)
            }
        }
    }
}




