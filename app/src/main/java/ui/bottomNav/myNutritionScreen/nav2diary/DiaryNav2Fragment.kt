package ui.bottomNav.myNutritionScreen.nav2diary

import adapter.FoodItemAdapter
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.shapeminder_appidee.R
import com.example.shapeminder_appidee.databinding.FragmentDiaryNav2Binding
import com.example.shapeminder_appidee.databinding.FragmentTrainingNav1Binding
import ui.viewModel.HomeViewModel
import ui.viewModel.NutrionViewModel


class DiaryNav2Fragment : Fragment() {
    private lateinit var binding: FragmentDiaryNav2Binding
    val nutrionViewModel: NutrionViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDiaryNav2Binding.inflate(layoutInflater)
        return binding.root
    }


}