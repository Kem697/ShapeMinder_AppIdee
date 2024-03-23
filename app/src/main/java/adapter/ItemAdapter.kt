package adapter

/*In dieser Klasse habe ich meinen Beispieldatensetz in den Adapter implementiert.
* Meiner Adaptaer initialisiert die einzelnen Daten die dann in die View Elemente
* geladen werden. Zudem werden auch die Layouts der Listen Elemente erstellt und
* gebunden.*/

/*Nachtrag: Der Adapter wurde mit einem zweiten ViewHolder implementiert.
* Je nach dem, ob der anzuzeigende Content eine Kraftübung ist, wird ein
* anders aussehendes UI Item im RecyclerView anzeigt.
*
* Nachtrag: Der Adapter wurde mit einem dritten ViewHolder implementiert.
* Dadurch soll die Liste von Krafttrainingsübungen angezeigt werden. Ich komme
* leider nicht zur Listenansicht, da ich die Navigation nicht programmieren kann.*/

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.shapeminder_appidee.R
import com.example.shapeminder_appidee.databinding.ListItemBinding
import com.example.shapeminder_appidee.databinding.ListItemExerciseBinding
import com.example.shapeminder_appidee.databinding.ListItemMyTrainingBinding
import model.Content
import ui.viewModel.HomeViewModel

class ItemAdapter(
    private val dataset: List<Content>,
    private val viewModel: HomeViewModel
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val contentCard = 1
    val smallContentCard = 2
    val exerciseListCards = 3

    inner class ContentItemViewHolder(val binding: ListItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    inner class SmallContentItemViewHolder(val binding: ListItemMyTrainingBinding) :
        RecyclerView.ViewHolder(binding.root)

    inner class ExerciseListItemViewHolder(val binding: ListItemExerciseBinding) :
        RecyclerView.ViewHolder(binding.root)


    override fun getItemViewType(position: Int): Int {
        val item = dataset[position]
        return if (item.isExercise && !item.isInExerciseList) {
            smallContentCard
        } else if (item.isExercise && item.isInExerciseList){
            exerciseListCards
        } else {
            contentCard
        }
    }

    @SuppressLint("SuspiciousIndentation")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == contentCard) {
            val binding =
                ListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            ContentItemViewHolder(binding)
        }  else if (viewType ==exerciseListCards){
            val binding =
               ListItemExerciseBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            ExerciseListItemViewHolder(binding)
        } else {
            val binding = ListItemMyTrainingBinding.inflate(LayoutInflater.from(parent.context),parent,false)
                SmallContentItemViewHolder(binding)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        var content = dataset[position]
        if (holder is ContentItemViewHolder) {
            holder.binding.contentImage.setImageResource(content.imageRessource)
            holder.binding.contentTitle.setText(content.stringRessourceTitle)
            holder.binding.materialCardView.setOnClickListener {
                viewModel.navigateDetailView(content)
                holder.binding.root.findNavController().navigate(R.id.homeContentDetailView)
            }


        }

        else if (holder is ExerciseListItemViewHolder){
            holder.binding.contentImage.setImageResource(content.imageRessource)
            holder.binding.contentTitle.setText(content.stringRessourceTitle)
            holder.binding.contentTextSnippet.setText(content.stringRessourceText)
            holder.binding.materialCardView.setOnClickListener {
                viewModel.navigateDetailView(content)
                holder.binding.root.findNavController().navigate(R.id.exercisePreviewFragment)
            }
        }

        else if (holder is SmallContentItemViewHolder) {
            holder.binding.contentImage.setImageResource(content.imageRessource)
            holder.binding.contentTitle.setText(content.stringRessourceTitle)
            holder.binding.materialCardView.setOnClickListener {
                viewModel.navigateDetailView(content)
                holder.binding.root.findNavController().navigate(R.id.homeContentDetailView)
            }
        }
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

}