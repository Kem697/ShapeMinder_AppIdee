package de.kem697.shapeminder.ui.bottomNav.myNutritionScreen.nav1foodFinder

import adapter.FoodItemAdapter
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
import de.kem697.shapeminder.MainActivity
import de.kem697.shapeminder.R
import de.kem697.shapeminder.databinding.FragmentFoodListBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomsheet.BottomSheetDialog
import de.kem697.shapeminder.model.data.remote.api_model.openFoodFacts.Product
import de.kem697.shapeminder.ui.viewModel.NutrionViewModel


class FoodListFragment : Fragment() {

    private lateinit var binding: FragmentFoodListBinding
    val nutritionViewModel: NutrionViewModel by activityViewModels()


    private var orginalFoodRequest : List<Product> = listOf()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFoodListBinding.inflate(layoutInflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpAdapter()
        sortRadioGroup()
        searchInput()
        navigateBack()

    }


    override fun onResume() {
        super.onResume()
        binding.progressBar.visibility = View.VISIBLE
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
        var isSortedDescending: Boolean

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
                            nutritionViewModel.sortFoodsByAlphabet(isSortedDescending)
                        }

                        R.id.z_a_descending -> {
                            isSortedDescending = true
                            nutritionViewModel.sortFoodsByAlphabet(isSortedDescending)
                        }
                    }
                    dialog.dismiss()
                }
        }
    }


    fun setUpAdapter(){
        nutritionViewModel.searchFood.observe(viewLifecycleOwner){ product->
            binding.screenTitle.text = nutritionViewModel.selectedContentTitle.value
            binding.listOfFood.adapter = FoodItemAdapter(product,nutritionViewModel,requireContext())
            orginalFoodRequest = product
            Log.i("Orginale Liste", "${orginalFoodRequest.size}")
            binding.progressBar.visibility = View.GONE
        }
    }


    fun searchInput() {
        var searchBar = binding.foodListSearchBarTextInput
        searchBar.addTextChangedListener { editable ->
            var userInput = editable.toString()
            if (userInput.isNotBlank()) {
                binding.foodListSearchBar.setText(userInput)
                var tag = "Filter???"
                Log.i(tag, "Werden die Inhalte hier gefiltert. :${userInput}")
                nutritionViewModel.filterFoodInCategorieByTitle(userInput,orginalFoodRequest)
            } else {
                searchBar.text.clear()
                binding.foodListSearchBar.clearText()
                binding.progressBar.visibility = View.VISIBLE
                nutritionViewModel.searchFood()
            }
        }
    }
    fun setDefaultHint() {
        binding.foodListSearchBar.hint = getString(R.string.searchBarHint)
        if (binding.foodListSearchBarTextInput.text.isNotBlank()) {
            binding.foodListSearchBarTextInput.text.clear()
            binding.foodListSearchBarTextInput.text.clearSpans()
            binding.foodListSearchBar.clearText()
        }
    }
}
