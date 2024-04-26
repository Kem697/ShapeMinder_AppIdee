package ui.bottomNav.myNutritionScreen.nav1foodFinder

import adapter.FoodItemAdapter
import android.content.res.ColorStateList
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isInvisible
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.shapeminder_appidee.MainActivity
import com.example.shapeminder_appidee.R
import com.example.shapeminder_appidee.databinding.FragmentFoodListBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomsheet.BottomSheetDialog
import ui.viewModel.HomeViewModel

class FoodListFragment : Fragment() {

    private lateinit var binding: FragmentFoodListBinding
    val viewModel: HomeViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFoodListBinding.inflate(layoutInflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        /*Diese Beobachteten Daten sind nur Platzhalter und müssen mit den Daten aus dem API Request ausgetauscht werden!!*/
        setUpAdapter()
        navigateBack()
        sortRadioGroup()
    }


    override fun onResume() {
        super.onResume()
        var navigationBar =
            requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigation)
        navigationBar.isInvisible = true
    }



    fun navigateBack() {
        binding.backBtn.setOnClickListener {
            findNavController().navigateUp()
        }
    }


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


    fun setUpAdapter(){
        viewModel.searchFood.observe(viewLifecycleOwner){ product->
            binding.screenTitle.text = viewModel.selectedContentTitle.value
            binding.listOfFood.adapter = FoodItemAdapter(product,viewModel,requireContext())
        }
    }
}
