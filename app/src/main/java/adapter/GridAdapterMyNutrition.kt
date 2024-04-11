package adapter


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
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
            "Getreide" -> {
                viewHolder.imageViewIcon.setOnClickListener {
                    val selectedCategory = "Getreide"

                    viewModel.getContentTitle(selectedCategory)
                    /*Hier muss das viewModel aufgerufen werden, welche die Daten von
                    * Lebensmittel aus der API anfrage nach Kategorien filtert.*/
//                    viewModel.filterExercisesByBodypart(selectedBodypart)
                    viewHolder.imageViewIcon.findNavController().navigate(R.id.foodListFragment)
                }
            }

            "Obst und Gemüse" -> {
                viewHolder.imageViewIcon.setOnClickListener {
                    val selectedCategory = "Obst und Gemüse"
                    viewModel.getContentTitle(selectedCategory)
                    viewHolder.imageViewIcon.findNavController().navigate(R.id.foodListFragment)
                }
            }

            "Käse, Molkerei und Eier" -> {
                viewHolder.imageViewIcon.setOnClickListener {
                    val selectedCategory = "Käse, Molkerei und Eier"
                    viewModel.getContentTitle(selectedCategory)
                    viewHolder.imageViewIcon.findNavController().navigate(R.id.foodListFragment)
                }
            }

            "Öle und Fette" -> {
                viewHolder.imageViewIcon.setOnClickListener {
                    val selectedCategory = "Öle und Fette"
                    viewModel.getContentTitle(selectedCategory)
                    viewHolder.imageViewIcon.findNavController().navigate(R.id.foodListFragment)
                }
            }

            "Fleisch und Fisch" -> {
                viewHolder.imageViewIcon.setOnClickListener {
                    val selectedCategory = "Fleisch und Fisch"
                    viewModel.getContentTitle(selectedCategory)
                    viewHolder.imageViewIcon.findNavController().navigate(R.id.foodListFragment)
                }
            }

            "Süssigkeiten" -> {
                viewHolder.imageViewIcon.setOnClickListener {
                    val selectedCategory = "Süssigkeiten"
                    viewModel.getContentTitle(selectedCategory)
                    viewHolder.imageViewIcon.findNavController().navigate(R.id.foodListFragment)
                }
            }
        }





        return itemView!!
    }


}
