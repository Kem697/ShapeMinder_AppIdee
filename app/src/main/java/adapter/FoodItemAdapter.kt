package adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.shapeminder_appidee.databinding.ListItemFoodBinding
import model.data.local.model.FoodFinderCategory
import model.data.remote.api_model.Food
import ui.viewModel.HomeViewModel

class FoodItemAdapter(
    private val dataset: List<Food>,
    private val viewModel: HomeViewModel
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
        holder.binding.foodName.setText(food.food_name)
        holder.binding.foodCategory.setText(food.food_type)
    }
}




