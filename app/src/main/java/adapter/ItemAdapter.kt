package adapter

/*DE:
In dieser Klasse habe ich meinen Beispieldatensetz in den Adapter implementiert.
* Meiner Adaptaer initialisiert die einzelnen Daten die dann in die View Elemente
* geladen werden. Zudem werden auch die Layouts der Listen Elemente erstellt und
* gebunden.
Nachtrag: Der Adapter wurde mit einem zweiten ViewHolder implementiert.
* Je nach dem, ob der anzuzeigende Content eine Kraftübung ist, wird ein
* anders aussehendes UI Item im RecyclerView anzeigt.
*
* Nachtrag: Der Adapter wurde mit einem dritten ViewHolder implementiert.
* Dadurch soll die Liste von Krafttrainingsübungen angezeigt werden. Ich komme
* leider nicht zur Listenansicht, da ich die Navigation nicht programmieren kann.*/

/* EN:
 In this class I have implemented my example data set in the adapter.
*My adapter initializes the individual data which is then loaded into the view elements.
*The layouts of the list elements are also created and bound.
*bound.
*Addendum:
*The adapter was implemented with a second ViewHolder.
*Depending on whether the content to be displayed a force exercise, a
*different looking UI item is displayed in the RecyclerView.
*Addendum:
*The adapter has been implemented with a third ViewHolder.
*This should display the list of strength training exercises.
*/

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isInvisible
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

            /*This explantation and improvement of this code is necessary.
            * I need to relocate the code parts, which assign the erasing of my exercises
            * from saved exercise list. These parts ar line 122 and 123.*/

            var saveBtn = holder.binding.saveExerciseBtn
            saveBtn.setImageResource(if (content.isSaved)R.drawable.favorite_fill1_wght400_grad0_opsz24 else R.drawable.favorite_fill0_wght400_grad0_opsz24)
            saveBtn.setOnClickListener {
                if (content.isSaved) {
//                    viewModel.isSaved(content.isSaved,content)
                    viewModel.isSaved(content.isSaved,content)
                    viewModel.savedExercises.value?.removeAt(position)
                    viewModel.savedExercises.value?.remove(content)
                    holder.binding.saveExerciseBtn.setImageResource(R.drawable.favorite_fill0_wght400_grad0_opsz24)
                    content.isSaved = false
                    var tag  = "Fehler"
                    Log.e(tag,"Element:${position} ${content.isSaved} ${viewModel.savedExercises.value}")
                } else {
//                    viewModel.isSaved(!content.isSaved)
//                    viewModel.savedExercises.value?.remove(content)
                    viewModel.isSaved(!content.isSaved,content)
                    holder.binding.saveExerciseBtn.setImageResource(R.drawable.favorite_fill1_wght400_grad0_opsz24)
                    content.isSaved = true
                    var tag  = "Fehler"
                    Log.e(tag,"Element:$position ${content.isSaved} ${viewModel.savedExercises.value}")
                }
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