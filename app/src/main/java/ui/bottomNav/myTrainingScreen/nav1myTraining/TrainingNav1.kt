package ui.bottomNav.myTrainingScreen.nav1myTraining

import adapter.ItemAdapter
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isInvisible
import androidx.fragment.app.activityViewModels
import com.example.shapeminder_appidee.databinding.FragmentTrainingNav1Binding
import ui.viewModel.HomeViewModel


class TrainingNav1 : Fragment() {

    private lateinit var binding: FragmentTrainingNav1Binding
    val viewModel: HomeViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTrainingNav1Binding.inflate(layoutInflater)
        return binding.root
    }

    /*DE:
    *Ich habe hier den Recycler Views meiner Layouts den ItemAdapter zugewiesen.
    * Dazu habe ich vorher eine neue LiveDate erstellt, die eine Liste von
    * Content aus Kraftrainingsübungen enthält (isExercise = true)*/

    /*EN:
    *I have assigned the ItemAdapter to the recycler views of my layouts here.
    * I have previously created a new LiveDate that contains a list of
    * Content from strength training exercises (isExercise = true)*/

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var tag = "Lade Mein Training"
        Log.e(tag,"On ViewCreated || Screen wird geladen!!")
        viewModel.exercises.observe(viewLifecycleOwner){
            binding.rvRecents.adapter = ItemAdapter(it,viewModel)
            binding.rvYourSessions.adapter = ItemAdapter(it,viewModel)
        }
        viewModel.savedExercises.observe(viewLifecycleOwner){
           var rigedExercise= it.onEach { it.isInExerciseList = false }
            binding.rvFavouriteExercises.adapter = ItemAdapter(rigedExercise,viewModel)

        }

    }

    override fun onResume() {
        super.onResume()
        var tag = "Lade Mein Training"
        Log.e(tag,"On Resume || Screen wird ausgeführt!!")
    }

    override fun onStop() {
        super.onStop()
        rigLayout()
        var tag = "Lade Mein Training"
        Log.e(tag,"On Stop || Screen wird angehalten!!")
    }


    /*DE:
* Diese kleine Funktion ist notwendig, um das
* Layout meiner gespeicherten Übung zu manipulieren. Ich habe diese Funktion
* in den onStop Lebenszyklus implementiert. Wenn der Benutzer eine Übung speichert
* in der Übungsliste speichert, wird die gespeicherte Übung in
* der Registerkarte myTraining angezeigt. Das Problem ist, dass ohne diese Funktion
* die Übung im falschen Layout angezeigt wird. Deshalb habe ich beschlossen
* einen booleschen Wert in meinem Inhalt zu ändern, wenn dieses Fragment
* den onStop() Lebenszyklus erreicht. Mit diesem Schritt führe ich den Code
* in meiner ItemAdapter.kt. aus, um ein anderes Layout für die gespeicherten Übungen
*  aufzublasen. */

    /*EN:
 * This smallfunction is nessaccary to manipulate the
 * layout of my saved exercise. I ve implemented this function
 * in the onStop lifecycle. When the user  saves an exercise
 * in the exercise list, the saved exercise will be displayed in
 * the myTraining tab. The problem is that without these function
 * the exercise will be displayed in the wrong layout. So I decided
 * to change an Boolean value of my Content, when this fragment reaches
 * the onStop() lifecycle. With this step I conduct the code in my ItemAdapter.kt.
 * to proceed a different layout inflating for the saved exercises. */
    fun rigLayout(){
        viewModel.savedExercises.value?.onEach {
            it.isInExerciseList = true
        }
    }

}