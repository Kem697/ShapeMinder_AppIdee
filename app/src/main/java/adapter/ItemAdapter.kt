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
import androidx.core.view.isVisible
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.shapeminder_appidee.R
import com.example.shapeminder_appidee.databinding.ListItemBinding
import com.example.shapeminder_appidee.databinding.ListItemExerciseBinding
import com.example.shapeminder_appidee.databinding.ListItemMyTrainingBinding
import model.data.local.model.Content
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
        val savedExercise = dataset[position].isSaved
        return if (item.isExercise && !item.isInExerciseList) {
            smallContentCard
        } /*else if (savedExercise && item.isExercise &&!item.isInExerciseList) {
            smallContentCard
        }*/ else if (item.isExercise && item.isInExerciseList) {
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
        } else if (viewType == exerciseListCards) {
            val binding =
                ListItemExerciseBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            ExerciseListItemViewHolder(binding)
        } else {
            val binding = ListItemMyTrainingBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
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
        } else if (holder is ExerciseListItemViewHolder) {
            holder.binding.contentImage.setImageResource(content.imageRessource)
            holder.binding.contentTitle.setText(content.stringRessourceTitle)
            holder.binding.contentTextSnippet.setText(content.stringRessourceText)
            holder.binding.containtsVideo.isInvisible = content.video == null
            holder.binding.materialCardView.setOnClickListener {
                viewModel.navigateDetailView(content)
                holder.binding.root.findNavController().navigate(R.id.exercisePreviewFragment)
            }


            /*DE.
            *Der Code setzt das Bild der saveBtn je nach Zustand des Inhalts.
            *Wenn der Inhalt nicht gespeichert ist, erscheint die Schaltfläche als schwarz umrandetes Herz, andernfalls
            *erscheint sie rot ausgefüllt. Klickt der Benutzer dann auf die Schaltfläche, führt das Programm den else{}
            *Block aus und die Schaltfläche ändert ihr Aussehen in ein rot gefülltes Herz. Außerdem
            *wird die Übung in den savedExercise liveData hinzugefügt. Das Speichern der Übung wird
            *wird in der Methode viewModel.isSaved() behandelt. Wenn der Benutzer auf die Schaltfläche "Speichern" klickt, wobei die Schaltfläche als rot gefülltes Herz erscheint,
            *führt der Code den if (content.isSaved) Block aus. In diesem Block ändert die Schaltfläche
            *ändert die Schaltfläche ihr Aussehen in ihr Standardaussehen und entfernt die gespeicherte Übung.
            */

            /*EN:
            * The code sets the image of the saveBtn depending on the state of the content.
            * If the content is not saved, the button appears as a black bordered heart, else
            * it will appear red filled. Then user clicks the button, the program executes the else{}
            * block and the button changes his appearance to a red filled heart. Also
            * the exercise will be added in the savedExercise liveData. The saving of the exercise will
            * be handled in the viewModel.isSaved() method. If the user clicks the save button, where the button appears as a red filled heart ,
            * the code executes the if (content.isSaved) block. In this block the button
            * changes his appearance to its default appearance and removes the saved exercise.
            * */

            var saveBtn = holder.binding.saveExerciseBtn
            saveBtn.setImageResource(if (content.isSaved) R.drawable.favorite_fill1_wght400_grad0_opsz24 else R.drawable.favorite_fill0_wght400_grad0_opsz24)
            saveBtn.setOnClickListener {
                if (content.isSaved) {
                    viewModel.isSaved(!content.isSaved, content)
                    holder.binding.saveExerciseBtn.setImageResource(R.drawable.favorite_fill0_wght400_grad0_opsz24)
                    content.isSaved = false
                    var tag = "Fehler"
                    Log.e(
                        tag,
                        "Ungespeichertes Element:${position} ${content.isSaved} ${viewModel.savedExercises.value}"
                    )
                } else {
                    viewModel.isSaved(!content.isSaved, content)
                    holder.binding.saveExerciseBtn.setImageResource(R.drawable.favorite_fill1_wght400_grad0_opsz24)
                    content.isSaved = true
                    var tag = "Fehler"
                    Log.e(
                        tag,
                        "Gespeichertes Element:$position ${content.isSaved} ${viewModel.savedExercises.value}"
                    )
                }
            }
        } else if (holder is SmallContentItemViewHolder) {
            holder.binding.contentImage.setImageResource(content.imageRessource)
            holder.binding.contentTitle.setText(content.stringRessourceTitle)
            holder.binding.materialCardView.setOnClickListener {
                if (content.isSaved && !content.isInExerciseList) {
                    viewModel.navigateDetailView(content)
                    holder.binding.root.findNavController().navigate(R.id.exercisePreviewFragment)
                } else {
                    viewModel.navigateDetailView(content)
                    holder.binding.root.findNavController().navigate(R.id.homeContentDetailView)
                }
            }
        }


    }

    override fun getItemCount(): Int {
        return dataset.size
    }

}