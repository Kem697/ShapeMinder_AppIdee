package ui.nav3exercises

import adapter.ItemAdapter
import android.content.ClipData.Item
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isInvisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.shapeminder_appidee.R
import com.example.shapeminder_appidee.databinding.FragmentExerciseListBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import ui.HomeViewModel

class ExerciseListFragment : Fragment() {
    private lateinit var binding: FragmentExerciseListBinding
    val viewModel: HomeViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentExerciseListBinding.inflate(layoutInflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.exercisesByBodyparts.observe(viewLifecycleOwner) {
            binding.listOfExercises.adapter = ItemAdapter(it, viewModel)
            sortByAlphabet()

        }
        searchInput()
        setDefaultHint()
        navigateBack()
    }


    fun navigateBack() {
        binding.backBtn.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    fun searchInput() {
        var searchBar = binding.myTSearchBarTextInput
        searchBar.addTextChangedListener {
            var userInput = binding.myTSearchBarTextInput.text
            if (userInput.isNotBlank()) {
                binding.myTSearchBar.setText(userInput)
            }
        }
    }

    fun setDefaultHint() {
        binding.myTSearchBar.hint = "Suche"
        if (binding.myTSearchBarTextInput.text.isNotBlank()) {
            binding.myTSearchBarTextInput.text.clear()
            binding.myTSearchBar.setText("")
        }

    }


/*    Die Methode `sortByAlphabet()` definiert einen Klick-Listener für den Sortieren-Button (`sortByNameBtn`).
 Wenn der Benutzer auf diesen Button klickt, wird die Methode `sortExercisesByAlphabet()` im ViewModel (`viewModel`) aufgerufen
  und der ausgewählte Körperteil (`selectedBodypart`) als Argument übergeben.

    Hier ist eine schrittweise Erläuterung, was die Methode tut:

    1. `binding.sortByNameBtn.setOnClickListener { ... }`:
    Dieser Teil setzt einen Klick-Listener für den Sortieren-Button.
    Wenn der Button geklickt wird, wird der darin enthaltene Code ausgeführt.

    2. `val selectedBodypart = viewModel.selectedBodypart.value ?: return@setOnClickListener`:
     Hier wird der ausgewählte Körperteil (`selectedBodypart`) aus der LiveData-Variable `selectedBodypart` im ViewModel (`viewModel`) abgerufen.
      Wenn diese Variable `null` ist, wird die Ausführung der Methode abgebrochen.
+
    3. `viewModel.sortExercisesByAlphabet(selectedBodypart)`:
    Hier wird die Methode `sortExercisesByAlphabet()` im ViewModel aufgerufen und der ausgewählte Körperteil (`selectedBodypart`)
     als Argument übergeben. Diese Methode wird verwendet, um die Liste der Übungen nach dem Alphabet zu sortieren, basierend auf dem ausgewählten Körperteil.
    Insgesamt ermöglicht diese Methode das Sortieren der Übungen nach dem Alphabet,
     wenn der Benutzer auf den Sortieren-Button klickt, und berücksichtigt dabei den ausgewählten Körperteil.

    */
    fun sortByAlphabet() {
        binding.sortByNameBtn.setOnClickListener {
            val selectedBodypart = viewModel.selectedContentTitle.value ?: return@setOnClickListener
            viewModel.sortExercisesByAlphabet(selectedBodypart)
        }
    }





}