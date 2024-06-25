package de.kem697.shapeminder.ui.bottomNav.myNutritionScreen.nav1foodFinder

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isInvisible
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import coil.load
import de.kem697.shapeminder.R
import de.kem697.shapeminder.databinding.FragmentFoodDetailViewBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import de.kem697.shapeminder.model.data.remote.api_model.openFoodFacts.Product
import de.kem697.shapeminder.ui.viewModel.NutrionViewModel


class FoodDetailViewFragment : Fragment() {
    private lateinit var binding: FragmentFoodDetailViewBinding
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

    override fun onResume() {
        super.onResume()
        var navigationBar =
            requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigation)
        navigationBar.isInvisible = true
    }


    override fun onStop() {
        super.onStop()
        var navigationBar =
            requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigation)
        navigationBar.isInvisible = false
    }


    fun navigateBack() {
        binding.backBtn.setOnClickListener {
            findNavController().navigateUp()
        }
    }


    fun setUpView() {
        nutrionViewModel.selectedFood.observe(viewLifecycleOwner) { food ->
            val roundedValueCal = "%.1f".format(food!!.nutriments!!.calories)
            val roundedValueFats = "%.1f".format(food.nutriments!!.fat)
            val roundedValueProteins = "%.1f".format(food.nutriments!!.proteins)
            val roundedValueCarbs = "%.1f".format(food.nutriments!!.carbohydrates)

            binding.foodDetailViewTitle.setText(nutrionViewModel.selectedContentTitle.value)
            when (food.url) {
                "" -> {
                    binding.foodImage.setImageResource(R.drawable.noimage)
                }

                else -> {
                    binding.foodImage.load(food.url)
                }
            }

            if (food.productNameDe.isNullOrEmpty()) {
                binding.foodName.setText(requireContext().getString(R.string.unknownFoodName))
                binding.categorie.setText(nutrionViewModel.selectedContentTitle.value)
//                binding.categorie.setText(food.categories.firstOrNull()?.substring(3)?.replace("-"," ").toString())
                binding.calories.setText(roundedValueCal + " kcal")
                binding.carbsAmount.setText(roundedValueCarbs + " g")
                binding.fatsAmount.setText(roundedValueFats + " g")
                binding.proteinAmount.setText(roundedValueProteins + " g")
            } else {
                binding.foodName.setText(food.productNameDe)
                binding.categorie.setText(nutrionViewModel.selectedContentTitle.value)
//                binding.categorie.setText(food.categories.firstOrNull()?.substring(3)?.replace("-"," ").toString())
                binding.calories.setText(roundedValueCal + " kcal")
                binding.carbsAmount.setText(roundedValueCarbs + " g")
                binding.fatsAmount.setText(roundedValueFats + " g")
                binding.proteinAmount.setText(roundedValueProteins + " g")

            }
            saveFood(food)
            storeFinder(food)
        }
    }


    fun saveFood(food: Product){
        var saveBtn = binding.saveFoodBtn
        saveBtn.setImageResource(if (food.isSaved) R.drawable.bookmark_fill1_wght400_grad0_opsz24 else R.drawable.bookmark_fill0_wght400_grad0_opsz24)
        saveBtn.setOnClickListener {
            if (food.isSaved) {
                nutrionViewModel.isSaved(!food.isSaved, food)
                binding.saveFoodBtn.setImageResource(R.drawable.bookmark_fill0_wght400_grad0_opsz24)
                food.isSaved = false
            } else {
                nutrionViewModel.isSaved(!food.isSaved, food)
                binding.saveFoodBtn.setImageResource(R.drawable.bookmark_fill1_wght400_grad0_opsz24)
                food.isSaved = true
            }
        }
    }




    /*EN:
    * This code enables to invoke a google maps where maps
    * retrieves his search parameter by one of the store tags
    * from the api call. Once the user tap the btn, the intent starts
    * and maps shows a grocery store where the user can find the product.
    *  */

    /*DE:
    * Dieser Code ermöglicht den Aufruf von Google Maps, wobei Google Maps
    * eines der Store-Tags aus dem Api-Aufruf als
    * Suchparameter bekommt. Sobald der Benutzer tippen
    * Sie auf die btn, beginnt die Absicht, ein Lebensmittelgeschäft zu zeigen,
    * wo der Benutzer das Produkt finden.*/

    fun storeFinder(product: Product) {
        binding.storeFinderBtn.setOnClickListener {
            try {
                val searchQuery = product.store?.random() // Zufällig ausgewählter Store als Suchbegriff
                if (searchQuery != null) {
                    val uri = Uri.parse("geo:0,0?q=$searchQuery")
                    val intent = Intent(Intent.ACTION_VIEW, uri)
                    intent.setPackage("com.google.android.apps.maps") // Um sicherzustellen, dass Google Maps verwendet wird
                    startActivity(intent)
                }
            } catch (e: Exception) {
                Toast.makeText(binding.root.context, requireContext().getString(R.string.failedStoreFinderText), Toast.LENGTH_SHORT).show()
                e.printStackTrace()
            }
        }

    }


}