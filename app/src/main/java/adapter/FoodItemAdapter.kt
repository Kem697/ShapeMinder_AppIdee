package adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.shapeminder_appidee.R
import com.example.shapeminder_appidee.databinding.ListItemFoodBinding
import model.data.remote.api_model.openFoodFacts.Product
import ui.viewModel.HomeViewModel

class FoodItemAdapter(
    private val dataset: List<Product>,
    private val viewModel: HomeViewModel,
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
//        val foodImageUrl = "https://images.openfoodfacts.net/images/"
        holder.binding.foodName.setText(food!!.productName)
        holder.binding.calories.setText(food!!.nutriments!!.calories.toString() + " kcal")
        holder.binding.fats.setText(food!!.nutriments!!.fat.toString()+ " g")
        holder.binding.proteins.setText(food!!.nutriments!!.proteins.toString()+ " g")
        holder.binding.carbs.setText(food!!.nutriments!!.carbohydrates.toString()+ " g")
        if (food.url != null){
            holder.binding.foodImage.load(food.url)
        } else{
            holder.binding.foodImage.setImageResource(R.drawable.foodcat6_sweets)
        }
        var tag = "Image"
        Log.i(tag, "${food.url}")
//        holder.binding.foodCategory.setText(food.nutriments?.fat?:"0".toInt())


        holder.binding.materialCardView.setOnClickListener {
            val selectedCategory = ContextCompat.getString(context, R.string.gc_grain_and_corn)
            viewModel.getContentTitle(selectedCategory)
            holder.binding.root.findNavController().navigate(R.id.foodDetailViewFragment)
        }
    }
}




