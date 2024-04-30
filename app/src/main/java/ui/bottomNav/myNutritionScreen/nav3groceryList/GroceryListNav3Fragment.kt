package ui.bottomNav.myNutritionScreen.nav3groceryList

import adapter.FoodItemAdapter
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.shapeminder_appidee.R
import com.example.shapeminder_appidee.databinding.FragmentDiaryNav2Binding
import com.example.shapeminder_appidee.databinding.FragmentGroceryListNav3Binding
import ui.viewModel.NutrionViewModel

class GroceryListNav3Fragment : Fragment() {
    private lateinit var binding: FragmentGroceryListNav3Binding
    val nutrionViewModel: NutrionViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGroceryListNav3Binding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpAdapter()
    }

    fun setUpAdapter(){
        nutrionViewModel.savedFoods.observe(viewLifecycleOwner){savedFoods->
            binding.listOfSavedFoods.adapter = FoodItemAdapter(savedFoods,nutrionViewModel,requireContext())
        }
    }
}