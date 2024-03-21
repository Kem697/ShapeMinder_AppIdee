package ui.bottomNav.myTrainingScreen.nav3exercises

import adapter.ItemAdapter
import android.content.res.ColorStateList
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.shapeminder_appidee.MainActivity
import com.example.shapeminder_appidee.R
import com.example.shapeminder_appidee.databinding.FragmentExerciseListBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import ui.viewModel.HomeViewModel

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
        sortRadioGroup()
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

//    fun sortByAlphabet() {
//        var isSortedDescending = false
//        binding.sortByNameBtn.setOnClickListener {
//            isSortedDescending = !isSortedDescending
//            if (isSortedDescending) {
//                binding.sortByNameBtn.text = "Sortiere A-Z"
//            } else {
//                binding.sortByNameBtn.text = "Sortiere Z-A"
//            }
//
//            val selectedBodypart = viewModel.selectedContentTitle.value ?: return@setOnClickListener
//            viewModel.sortExercisesByAlphabet(selectedBodypart, isSortedDescending)
//        }
//    }


/*Diese Funktion ist eine Anbahnung zur Filteroption mittels einer RadioGroup.
* Die Funktion wurde ausgebaut und ersetzt die sortByAlphabet Funktion.*/
    fun sortRadioGroup() {
        var dialog = BottomSheetDialog(activity as MainActivity, R.style.transparent)
        dialog.setContentView(R.layout.dialog_sheet)
        dialog.setCancelable(true)
        dialog.setCanceledOnTouchOutside(true)
        var isSortedDescending = false

        // Listener für den Button sortByNameBtn
        binding.sortByNameBtn.setOnClickListener {
            // Um den Dialog nur einmal zu zeigen, prüfen Sie zuerst, ob er nicht bereits gezeigt wird
            if (!dialog.isShowing) {
                /*Hier wird die Farbe des "Check"-Elements auf die Farbe tertiary gesetzt.
                * */
                dialog.findViewById<RadioButton>(R.id.a_z_ascending)!!.buttonTintList = ColorStateList.valueOf(
                    ContextCompat.getColor(requireContext(), R.color.tertiary))
                dialog.findViewById<RadioButton>(R.id.z_a_descending)!!.buttonTintList = ColorStateList.valueOf(
                    ContextCompat.getColor(requireContext(), R.color.tertiary))
                // Anschließend wird der Dialog geöffnet.
                dialog.show()
            }

            // Listener für die Auswahl der RadioButtons innerhalb des Dialogs
            dialog.findViewById<RadioGroup>(R.id.radioG_exerciseSort)?.setOnCheckedChangeListener { group, checkedId ->
                // Hier können Sie den Code für die Sortierung der Übungen implementieren
                val selectedBodypart = viewModel.selectedContentTitle.value ?: return@setOnCheckedChangeListener
                // Je nachdem, welcher RadioButton ausgewählt wurde, können Sie die Übungen sortieren
                when (checkedId) {
                    R.id.a_z_ascending -> {
                        isSortedDescending = false
                        viewModel.sortExercisesByAlphabet(selectedBodypart, isSortedDescending)
                    }
                    R.id.z_a_descending -> {
                        isSortedDescending = true
                        viewModel.sortExercisesByAlphabet(selectedBodypart, isSortedDescending)
                    }
                    // Weitere RadioButton-Optionen können hier hinzugefügt werden
                }
                // Schließen Sie den Dialog, nachdem eine Auswahl getroffen wurde
                dialog.dismiss()
            }
        }
    }







}