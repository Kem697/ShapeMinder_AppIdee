package adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
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
        holder.binding.foodName.setText(food!!.productName)
//        holder.binding.foodCategory.setText(food.nutriments?.fat?:"0".toInt())



    /*    holder.binding.materialCardView.setOnClickListener {
            val selectedCategory = ContextCompat.getString(context, R.string.gc_grain_and_corn)
            viewModel.getContentTitle(selectedCategory)
            holder.binding.root.findNavController().navigate(R.id.foodDetailViewFragment)
        }*/
    }
}




