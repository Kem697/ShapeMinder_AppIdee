package ui.bottomNav.myNutritionScreen.nav1foodFinder

import adapter.GridAdapterMyNutrition
import android.content.res.ColorStateList
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import com.example.shapeminder_appidee.MainActivity
import com.example.shapeminder_appidee.R
import com.example.shapeminder_appidee.databinding.FragmentFoodFinderNav1Binding
import com.google.android.material.bottomsheet.BottomSheetDialog
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


    }


    fun setupAdapter(){
        viewModel.foodCategories.observe(viewLifecycleOwner){
            binding.fooCategoryGrid.adapter = GridAdapterMyNutrition(it,viewModel,requireContext())
        }
    }


}