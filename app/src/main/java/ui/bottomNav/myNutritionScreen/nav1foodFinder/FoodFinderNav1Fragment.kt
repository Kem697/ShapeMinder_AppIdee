package ui.bottomNav.myNutritionScreen.nav1foodFinder

import adapter.GridAdapterMyNutrition
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.shapeminder_appidee.databinding.FragmentFoodFinderNav1Binding
import ui.viewModel.HomeViewModel


class FoodFinderNav1Fragment : Fragment() {


    private lateinit var binding: FragmentFoodFinderNav1Binding
    val viewModel: HomeViewModel by activityViewModels()



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
        viewModel.apiCall()


    }


    fun setupAdapter(){
        viewModel.requestAllFoodCats.observe(viewLifecycleOwner){
            var response = it.food_categories?.food_category?: listOf()
            binding.fooCategoryGrid.adapter = GridAdapterMyNutrition(response,viewModel,requireContext())
        }
    }


}