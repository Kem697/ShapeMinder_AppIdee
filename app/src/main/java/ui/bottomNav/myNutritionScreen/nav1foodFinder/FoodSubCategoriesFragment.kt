package ui.bottomNav.myNutritionScreen.nav1foodFinder

import adapter.FoodSubCatsAdapter
import android.content.res.ColorStateList
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isInvisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.shapeminder_appidee.MainActivity
import com.example.shapeminder_appidee.R
import com.example.shapeminder_appidee.databinding.FragmentFoodSubCategoriesBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomsheet.BottomSheetDialog
import ui.viewModel.NutrionViewModel

class FoodSubCategoriesFragment : Fragment() {

    private lateinit var binding: FragmentFoodSubCategoriesBinding

    val nutritionViewModel: NutrionViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFoodSubCategoriesBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navigateBack()
        setUpAdapter()
        searchInput()
    }


    override fun onResume() {
        super.onResume()
        nutritionViewModel.retrieveFilteredNaturalSubCats(requireContext(),nutritionViewModel.selectedFoodSubCategory.value.toString())

        var navigationBar =
            requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigation)
        navigationBar.isInvisible = true
    }


    override fun onStop() {
        super.onStop()
        setDefaultHint()
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
                            nutritionViewModel.sortFoodSubCategoriesByAlphabet(isSortedDescending,requireContext())
                        }

                        R.id.z_a_descending -> {
                            isSortedDescending = true
                            nutritionViewModel.sortFoodSubCategoriesByAlphabet(isSortedDescending,requireContext())
                        }
                    }
                    dialog.dismiss()
                }
        }
    }


    fun searchInput() {
        var searchBar = binding.foodSubCatsSearchBarTextInput
        searchBar.addTextChangedListener { editable ->
            var userInput = editable.toString()
            var tag = "Filter???"
            Log.i(tag, "Werden die Inhalte hier gefiltert. :${userInput}")

            if (userInput.isNotBlank()) {
                binding.foodSubCatsSearchBar.setText(userInput)
                nutritionViewModel.filterFoodSubCategoriesByInput(userInput,requireContext())
            } else {
                searchBar.text.clear()
                binding.foodSubCatsSearchBar.clearText()
                nutritionViewModel.retrieveFilteredNaturalSubCats(requireContext(),binding.screenTitle.text.toString())
            }
        }
    }
    fun setDefaultHint() {
        binding.foodSubCatsSearchBar.hint = getString(R.string.searchBarHint)
        if (binding.foodSubCatsSearchBarTextInput.text.isNotBlank()) {
            binding.foodSubCatsSearchBarTextInput.text.clear()
            binding.foodSubCatsSearchBarTextInput.text.clearSpans()
            binding.foodSubCatsSearchBar.clearText()
        }
    }



    fun setUpAdapter(){
        nutritionViewModel.foodSubCategories.observe(viewLifecycleOwner){ foodSubCategories->
            binding.screenTitle.setText(nutritionViewModel.selectedFoodSubCategory.value)
            binding.listOfFoodSubCategories.adapter = FoodSubCatsAdapter(foodSubCategories,nutritionViewModel,requireContext())
            var tag = "Filter???"

        }
    }


}