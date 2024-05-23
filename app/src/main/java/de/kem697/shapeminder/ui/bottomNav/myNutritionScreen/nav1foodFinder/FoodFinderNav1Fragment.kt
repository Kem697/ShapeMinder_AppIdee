package de.kem697.shapeminder.ui.bottomNav.myNutritionScreen.nav1foodFinder

import adapter.GridAdapterMyNutrition
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import de.kem697.shapeminder.databinding.FragmentFoodFinderNav1Binding
import de.kem697.shapeminder.ui.viewModel.NutrionViewModel


class FoodFinderNav1Fragment : Fragment() {


    private lateinit var binding: FragmentFoodFinderNav1Binding
    val nutritionViewModel : NutrionViewModel by activityViewModels()



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFoodFinderNav1Binding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAdapter()


    }


    fun setupAdapter(){
        nutritionViewModel.foodCategories.observe(viewLifecycleOwner){
            binding.fooCategoryGrid.adapter = GridAdapterMyNutrition(it,nutritionViewModel,requireContext())
        }
    }

}