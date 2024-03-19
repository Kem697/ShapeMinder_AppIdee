package ui.nav3exercises

import adapter.ItemAdapter
import android.content.ClipData.Item
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.core.view.isInvisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.shapeminder_appidee.MainActivity
import com.example.shapeminder_appidee.R
import com.example.shapeminder_appidee.databinding.FragmentExerciseListBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomsheet.BottomSheetDialog
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
        sortByAlphabet()
        viewModel.exercisesByBodyparts.observe(viewLifecycleOwner) {
            binding.listOfExercises.adapter = ItemAdapter(it, viewModel)

            /*Mit diesen Befehlen initialisiere meine ViewElemente mit
            * den initialisierten Argumenten in den jeweiligen Eigenschaften
            * meines Content Objekts. Dies führt dazu, dass der Titel und
            * die Anzahl der Übungen pro Körperpartie entsprechen der
            * Körperpartie aktualisiert wird */
            binding.title.setText(it.first().bodyPart)
            binding.subTitle.setText("Anzahl von Übungen: ${it.size}")

            /*When Verzweigung dient dazu die korrekten Bilder zu setzen,
            * wenn das  initialisierte Argument mit dem des Körperparts
            * übereinstimmt. Die Verzweigung ist noch fehlerbehaftet.*/

            when (it.first().bodyPart) {
                "Arme" -> {
                    binding.bodyPartView.setImageResource(R.drawable.bp1arms)
                }
                "Bauch"->{
                    binding.bodyPartView.setImageResource(R.drawable.bp5abs)
                }

                "Schulter" -> {
                    binding.bodyPartView.setImageResource(R.drawable.bp3shoulders)
                }

                 "Rücken"->{
                     binding.bodyPartView.setImageResource(R.drawable.bp4back)
                 }
                 "Beine"->{
                     binding.bodyPartView.setImageResource(R.drawable.bp2legs)
                 }
                 "Brust"->{
                     binding.bodyPartView.setImageResource(R.drawable.bp6chest)
                 }

                else-> {
                    binding.bodyPartView.setImageResource(R.drawable.applogo)
                }
            }

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
        var isSortedDescending = false
        binding.sortByNameBtn.setOnClickListener {
            isSortedDescending = !isSortedDescending
            if (isSortedDescending) {
                binding.sortByNameBtn.text = "Sortiere A-Z"
            } else {
                binding.sortByNameBtn.text = "Sortiere Z-A"
            }

            val selectedBodypart = viewModel.selectedContentTitle.value ?: return@setOnClickListener
            viewModel.sortExercisesByAlphabet(selectedBodypart, isSortedDescending)
        }
    }






/*Diese Funktion ist eine Anbahnung zur Filteroption mittels einer RadioGroup.
* Die Funktion muss noch weiter ausgebaut werden.*/

//    fun sortRadioGroup() {
//        var dialog = BottomSheetDialog(activity as MainActivity, R.style.transparent)
//        dialog.setContentView(R.layout.dialog_sheet)
//        dialog.setCancelable(true)
//        dialog.setCanceledOnTouchOutside(true)
//        var isSortedDescending = false
//
//        binding.sortByNameBtn.setOnClickListener {
//            isSortedDescending = !isSortedDescending
//            dialog.show()
//            var group = requireActivity().findViewById<RadioGroup>(R.id.radioG_exerciseSort)
//            group.setOnCheckedChangeListener { group, checkedId -> }
//            val selectedBodypart = viewModel.selectedContentTitle.value ?: return@setOnClickListener
//            viewModel.sortExercisesByAlphabet(selectedBodypart, isSortedDescending)
//        }
//
//    }







}