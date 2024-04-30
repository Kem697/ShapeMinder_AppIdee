package ui.bottomNav.myNutritionScreen.nav1foodFinder

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isInvisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import coil.load
import com.example.shapeminder_appidee.MainActivity
import com.example.shapeminder_appidee.R
import com.example.shapeminder_appidee.databinding.FragmentFoodDetailViewBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import ui.viewModel.HomeViewModel
import ui.viewModel.NutrionViewModel

class FoodDetailViewFragment : Fragment() {
    private lateinit var binding : FragmentFoodDetailViewBinding
    val nutrionViewModel: NutrionViewModel by activityViewModels()




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFoodDetailViewBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpView()
        navigateBack()
    }


    fun navigateBack() {
        binding.backBtn.setOnClickListener {
            findNavController().navigateUp()
        }
    }


    fun setUpView(){
        nutrionViewModel.selectedFood.observe(viewLifecycleOwner){
            when(it.url){
                "" ->{
                    binding.foodImage.setImageResource(R.drawable.noimage)
                }
                else->{
                    binding.foodImage.load(it.url)
                }
            }

            if (it.productNameDe.isNullOrEmpty()){
                binding.foodName.setText(requireContext().getString(R.string.unknownFoodName))
                binding.categorie.setText(it.categories.first())
                binding.carbsAmount.setText("${it.nutriments?.carbohydrates.toString()}" + " g")
                binding.fatsAmount.setText("${it.nutriments?.fat.toString()}" + " g")
                binding.proteinAmount.setText("${it.nutriments?.proteins.toString()}" + " g")
//                binding.foodImage.load(it.url)
            } else {
                binding.foodName.setText(it.productNameDe)
                binding.categorie.setText(it.categories.first())
                binding.carbsAmount.setText("${it.nutriments?.carbohydrates.toString()}" + " g")
                binding.fatsAmount.setText("${it.nutriments?.fat.toString()}" + " g")
                binding.proteinAmount.setText("${it.nutriments?.proteins.toString()}" + " g")
//                binding.foodImage.load(it.url)

            }
        }
    }

}