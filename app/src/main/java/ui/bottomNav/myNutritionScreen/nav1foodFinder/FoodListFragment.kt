package ui.bottomNav.myNutritionScreen.nav1foodFinder

import adapter.FoodItemAdapter
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isInvisible
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.shapeminder_appidee.R
import com.example.shapeminder_appidee.databinding.FragmentFoodFinderNav1Binding
import com.example.shapeminder_appidee.databinding.FragmentFoodListBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import ui.viewModel.HomeViewModel

class FoodListFragment : Fragment() {

    private lateinit var binding: FragmentFoodListBinding
    val viewModel: HomeViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFoodListBinding.inflate(layoutInflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        /*Diese Beobachteten Daten sind nur Platzhalter und müssen mit den Daten aus dem API Request ausgetauscht werden!!*/
        viewModel.foodCategories.observe(viewLifecycleOwner){
            binding.screenTitle.setText(it.first().grocery)
            binding.listOfFood.adapter = FoodItemAdapter(it,viewModel)
        }
        viewModel.apiCall()
        navigateBack()
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
}
