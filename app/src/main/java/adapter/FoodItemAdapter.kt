package adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import coil.load
import de.kem697.shapeminder.R
import de.kem697.shapeminder.databinding.ListItemFoodBinding
import de.kem697.shapeminder.model.data.remote.api_model.openFoodFacts.Product
import de.kem697.shapeminder.ui.viewModel.NutrionViewModel


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

//        val category = food!!.categories.firstOrNull()?.substring(3)?.replace("-", " ")
//        val categoryFirstWord = category?.split("\\s+".toRegex())?.firstOrNull()



        val roundedValueCal = "%.1f".format(food!!.nutriments!!.calories)
        val roundedValueFats = "%.1f".format(food!!.nutriments!!.fat)
        val roundedValueProteins = "%.1f".format(food!!.nutriments!!.proteins)
        val roundedValueCarbs = "%.1f".format(food!!.nutriments!!.carbohydrates)


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
            holder.binding.calories.setText(roundedValueCal + " kcal")
            holder.binding.fats.setText("${context.getString(R.string.fatsText)} " + roundedValueFats + " g")
            holder.binding.proteins.setText("${context.getString(R.string.proteinText)} " + roundedValueProteins + " g")
            holder.binding.carbs.setText("${context.getString(R.string.carbsText)} " + roundedValueCarbs + " g")
//            holder.binding.foodCategory.setText(categoryFirstWord + "...")
        } else{
            holder.binding.foodName.setText("${truncatedFoodName}...")
            holder.binding.calories.setText(roundedValueCal + " kcal")
            holder.binding.fats.setText("${context.getString(R.string.fatsText)} " + roundedValueFats + " g")
            holder.binding.proteins.setText("${context.getString(R.string.proteinText)} " + roundedValueProteins + " g")
            holder.binding.carbs.setText("${context.getString(R.string.carbsText)} " + roundedValueCarbs + " g")

//            holder.binding.foodCategory.setText(categoryFirstWord + "...")
        }

        holder.binding.materialCardView.setOnClickListener {
            nutrionViewModel.selectedFood(food)
            holder.binding.root.findNavController().navigate(R.id.foodDetailViewFragment)
        }


        var saveBtn = holder.binding.saveFoodBtn
        saveBtn.setImageResource(if (food.isSaved) R.drawable.bookmark_fill1_wght400_grad0_opsz24 else R.drawable.bookmark_fill0_wght400_grad0_opsz24)
        saveBtn.setOnClickListener {
            if (food.isSaved) {
                nutrionViewModel.isSaved(!food.isSaved, food)
                saveBtn.setImageResource(R.drawable.bookmark_fill0_wght400_grad0_opsz24)
                food.isSaved = false
                var tag = "Fehler"
                Log.e(
                    tag,
                    "Ungespeichertes Element:${position} ${food.isSaved} ${nutrionViewModel.savedFoods.value}"
                )
            } else {
                nutrionViewModel.isSaved(!food.isSaved, food)
                saveBtn.setImageResource(R.drawable.bookmark_fill1_wght400_grad0_opsz24)
                food.isSaved = true
                var tag = "Fehler"
                Log.e(
                    tag,
                    "Gespeichertes Element:${position} ${food.isSaved} ${nutrionViewModel.savedFoods.value}"
                )
            }
        }
    }
}




