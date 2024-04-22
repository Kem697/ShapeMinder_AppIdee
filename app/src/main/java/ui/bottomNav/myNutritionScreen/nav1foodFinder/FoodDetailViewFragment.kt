package ui.bottomNav.myNutritionScreen.nav1foodFinder

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

}