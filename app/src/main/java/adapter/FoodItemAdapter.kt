package adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.shapeminder_appidee.R
import com.example.shapeminder_appidee.databinding.ListItemFoodBinding
import model.data.remote.api_model.openFoodFacts.Product
import ui.viewModel.HomeViewModel
import ui.viewModel.NutrionViewModel

class FoodItemAdapter(
    private val dataset: List<Product>,
    private val nutrionViewModel: NutrionViewModel,
    private val context: Context
) : RecyclerView.Adapter<FoodItemAdapter.FoodItemViewHolder>() {

    inner class FoodItemViewHolder (val binding: ListItemFoodBinding): RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodItemViewHolder {
        val binding = ListItemFoodBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return FoodItemViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

    override fun onBindViewHolder(holder: FoodItemViewHolder, position: Int) {
        val food = dataset[position]
        var foodName = food.productNameDe?: "${context.getString(R.string.unknownFoodName)}"
        val words = foodName.split(" ","-")
        val truncatedFoodName = words.take(1).joinToString(" ")
        when(food.url){
            "" ->{
                holder.binding.foodImage.setImageResource(R.drawable.noimage)
            }
            else->{
                holder.binding.foodImage.load(food.url)
            }
        }


        if (food.productNameDe.isNullOrEmpty()){
            holder.binding.foodName.setText(context.getString(R.string.unknownFoodName))
            holder.binding.calories.setText(food!!.nutriments!!.calories.toString() + " kcal")
            holder.binding.fats.setText("${context.getString(R.string.fatsText)} " + food!!.nutriments!!.fat.toString()+ " g")
            holder.binding.proteins.setText("${context.getString(R.string.proteinText)} " + food!!.nutriments!!.proteins.toString()+ " g")
            holder.binding.carbs.setText("${context.getString(R.string.carbsText)} " + food!!.nutriments!!.carbohydrates.toString()+ " g")
            holder.binding.foodCategory.setText(food!!.categories.toString())
        } else{
            holder.binding.foodName.setText("${truncatedFoodName}...")
            holder.binding.calories.setText(food!!.nutriments!!.calories.toString() + " kcal")
            holder.binding.fats.setText("${context.getString(R.string.fatsText)} " + food!!.nutriments!!.fat.toString()+ " g")
            holder.binding.proteins.setText("${context.getString(R.string.proteinText)} " + food!!.nutriments!!.proteins.toString()+ " g")
            holder.binding.carbs.setText("${context.getString(R.string.carbsText)} " + food!!.nutriments!!.carbohydrates.toString()+ " g")
            holder.binding.foodCategory.setText(food!!.categories.toString())
        }

        holder.binding.materialCardView.setOnClickListener {
            nutrionViewModel.selectedFood(food)
            holder.binding.root.findNavController().navigate(R.id.foodDetailViewFragment)
        }
    }
}




