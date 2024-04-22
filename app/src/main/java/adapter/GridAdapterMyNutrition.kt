package adapter


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import com.example.shapeminder_appidee.R
import model.data.local.model.FoodFinderCategory
import ui.viewModel.HomeViewModel


class GridAdapterMyNutrition(
    private val dataset: List<FoodFinderCategory>,
    private val viewModel: HomeViewModel,
    private val context: Context,
) : BaseAdapter() {

    private inner class ViewHolder(view: View) {
        val textViewTitle: TextView = view.findViewById(R.id.foodCatContentTitle)
        val imageViewIcon: ImageView = view.findViewById(R.id.foodCatContentImage)
    }

    override fun getCount(): Int {
        return dataset.size
    }

    override fun getItem(position: Int): Any {
        return dataset[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var itemView = convertView
        val viewHolder: ViewHolder

        if (itemView == null) {
            val inflater =
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            itemView = inflater.inflate(R.layout.grid_item_food_categories, parent, false)
            viewHolder = ViewHolder(itemView)
            itemView.tag = viewHolder
        } else {
            viewHolder = itemView.tag as ViewHolder
        }

        val item = dataset[position]
        viewHolder.textViewTitle.setText(item.stringRessourceTitle)
        viewHolder.imageViewIcon.setImageResource(item.imageRessource)



        when (viewHolder.textViewTitle.text) {
            ContextCompat.getString(context, R.string.gc_grain_and_corn) -> {
                viewHolder.imageViewIcon.setOnClickListener {
                    val selectedCategory = ContextCompat.getString(context, R.string.gc_grain_and_corn)

                    viewModel.getContentTitle(selectedCategory)
                    /*Hier muss das viewModel aufgerufen werden, welche die Daten von
                    * Lebensmittel aus der API anfrage nach Kategorien filtert.*/
//                    viewModel.filterExercisesByBodypart(selectedBodypart)
                    viewHolder.imageViewIcon.findNavController().navigate(R.id.foodListFragment)
                }
            }

            ContextCompat.getString(context, R.string.gc_fruits_and_vegetable) -> {
                viewHolder.imageViewIcon.setOnClickListener {
                    val selectedCategory = ContextCompat.getString(context, R.string.gc_fruits_and_vegetable)
                    viewModel.getContentTitle(selectedCategory)
                    viewHolder.imageViewIcon.findNavController().navigate(R.id.foodListFragment)
                }
            }

            ContextCompat.getString(context, R.string.gc_milk_and_eg) -> {
                viewHolder.imageViewIcon.setOnClickListener {
                    val selectedCategory = ContextCompat.getString(context, R.string.gc_milk_and_eg)
                    viewModel.getContentTitle(selectedCategory)
                    viewHolder.imageViewIcon.findNavController().navigate(R.id.foodListFragment)
                }
            }

            ContextCompat.getString(context, R.string.gc_oil_and_fats) -> {
                viewHolder.imageViewIcon.setOnClickListener {
                    val selectedCategory = ContextCompat.getString(context, R.string.gc_oil_and_fats)
                    viewModel.getContentTitle(selectedCategory)
                    viewHolder.imageViewIcon.findNavController().navigate(R.id.foodListFragment)
                }
            }

            ContextCompat.getString(context, R.string.gc_meat_and_fish) -> {
                viewHolder.imageViewIcon.setOnClickListener {
                    val selectedCategory = ContextCompat.getString(context, R.string.gc_meat_and_fish)
                    viewModel.getContentTitle(selectedCategory)
                    viewHolder.imageViewIcon.findNavController().navigate(R.id.foodListFragment)
                }
            }

            ContextCompat.getString(context, R.string.gc_sweets) -> {
                viewHolder.imageViewIcon.setOnClickListener {
                    val selectedCategory = ContextCompat.getString(context, R.string.gc_sweets)
                    viewModel.getContentTitle(selectedCategory)
                    viewHolder.imageViewIcon.findNavController().navigate(R.id.foodListFragment)
                }
            }
        }





        return itemView!!
    }


}
