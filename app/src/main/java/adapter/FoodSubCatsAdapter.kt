package adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.shapeminder_appidee.R
import com.example.shapeminder_appidee.databinding.ListFoodSubCategorieBinding
import model.data.local.model.myNutrion.FoodFinderSubCategory
import ui.viewModel.NutrionViewModel

class FoodSubCatsAdapter (
    private val dataset: List<FoodFinderSubCategory>,
    private val nutritionViewModel: NutrionViewModel,
    private val context: Context
): RecyclerView.Adapter<FoodSubCatsAdapter.FoodSubCatViewHolder>() {

    inner class FoodSubCatViewHolder(val binding: ListFoodSubCategorieBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodSubCatViewHolder {
        val binding = ListFoodSubCategorieBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return FoodSubCatViewHolder(binding)
    }

    override fun getItemCount(): Int {
       return dataset.size
    }

    override fun onBindViewHolder(holder: FoodSubCatViewHolder, position: Int) {
        var foodSubCat = dataset[position]
        holder.binding.foodSubCatName.setText(foodSubCat.stringRessourceTitle)
        holder.binding.foodSubCatImage.setImageResource(foodSubCat.imageRessource)

        holder.binding.materialCardView.setOnClickListener {
            nutritionViewModel.getFoodTitle(context.getString(foodSubCat.stringRessourceTitle))
            nutritionViewModel.setCountyAndCategory(context.getString(foodSubCat.apiSearchTags),
                context.getString(R.string.apiCountrySearchTag))
            nutritionViewModel.searchFood()
            holder.binding.root.findNavController().navigate(R.id.action_foodSubCategoriesFragment_to_foodListFragment)
        }
    }

}













