package ui.bottomNav.myNutritionScreen.nav1foodFinder

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isInvisible
import androidx.core.widget.addTextChangedListener
import androidx.navigation.fragment.findNavController
import com.example.shapeminder_appidee.R
import com.example.shapeminder_appidee.databinding.FragmentFoodDetailViewBinding

class FoodDetailViewFragment : Fragment() {
    private lateinit var binding : FragmentFoodDetailViewBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFoodDetailViewBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navigateBack()
    }


    fun navigateBack() {
        binding.backBtn.setOnClickListener {
            findNavController().navigateUp()
        }
    }



    /*
    fun sortRadioGroup() {
        var dialog = BottomSheetDialog(activity as MainActivity, R.style.transparent)
        dialog.setContentView(R.layout.dialog_sheet_sort)
        dialog.setCancelable(true)
        dialog.setCanceledOnTouchOutside(true)
        var isSortedDescending = false

        binding.sortByNameBtn.setOnClickListener {
            if (!dialog.isShowing) {
                dialog.findViewById<RadioButton>(R.id.a_z_ascending)!!.buttonTintList =
                    ColorStateList.valueOf(
                        ContextCompat.getColor(requireContext(), R.color.tertiary)
                    )
                dialog.findViewById<RadioButton>(R.id.z_a_descending)!!.buttonTintList =
                    ColorStateList.valueOf(
                        ContextCompat.getColor(requireContext(), R.color.tertiary)
                    )
                dialog.show()
            }

            dialog.findViewById<RadioGroup>(R.id.radioG_exerciseSort)
                ?.setOnCheckedChangeListener { group, checkedId ->
                    when (checkedId) {
                        R.id.a_z_ascending -> {
                            isSortedDescending = false
//   Hier Funktion zum Sortieren der Lebensmittel aufrufen (Beispiel:viewModel.sortAllExercisesByAlphabet(isSortedDescending))
                        }

                        R.id.z_a_descending -> {
                            isSortedDescending = true
//   Hier Funktion zum Sortieren der Lebensmittel aufrufen (Beispiel:viewModel.sortAllExercisesByAlphabet(isSortedDescending))
                        }
                    }
                    dialog.dismiss()
                }
        }
    }
*/
    /*    fun searchInput() {
        var searchBar = binding.myTSearchBarTextInput
        val context = requireContext()
        searchBar.addTextChangedListener { editable ->
            var userInput = editable.toString()
            if (userInput.isNotBlank()) {
                binding.myTSearchBar.setText(userInput)
                var tag = "Filter???"
                Log.i(tag, "Werden die Inhalte hier gefiltert. :${userInput}")
                viewModel.filterAllExercisesByTitle(userInput, context)
            } else {
                searchBar.text.clear()
                binding.myTSearchBar.clearText()
                viewModel.retrieveExercisesByBodyparts()
                binding.resetFilterBtn.isInvisible = true
            }
        }
    }

   */
    /*
    fun setDefaultHint() {
        binding.myTSearchBar.hint = getString(R.string.allExercisesSearchbarHint)
        if (binding.myTSearchBarTextInput.text.isNotBlank()) {
            binding.myTSearchBarTextInput.text.clear()
            binding.myTSearchBarTextInput.text.clearSpans()
            binding.myTSearchBar.clearText()
        }
    }
*/

}