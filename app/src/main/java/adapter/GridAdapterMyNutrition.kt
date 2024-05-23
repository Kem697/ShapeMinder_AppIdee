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
import de.kem697.shapeminder.R
import de.kem697.shapeminder.model.data.local.model.myNutrion.FoodFinderCategory
import de.kem697.shapeminder.ui.viewModel.NutrionViewModel



class GridAdapterMyNutrition(
    private val dataset: List<FoodFinderCategory>,
    private val nutrionViewModel: NutrionViewModel,
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


        /*1.
             The value of selectedCategory must correspond with parentCategory
             value of the aimed sub Categories. This necessitate that I change
             the name of the stringRessourceTitle according to the name of the parent
             category
             2.
             After this step I have to initialize the navController to retrieve
             the list of foodSubCategories which are befitting with the parent
             Categories*/



        when (viewHolder.textViewTitle.text) {
            ContextCompat.getString(context, R.string.parentCat_Carbs) -> {
                viewHolder.imageViewIcon.setOnClickListener {
                    var getSubCategory = ContextCompat.getString(context,R.string.parentCat_Carbs)
                    nutrionViewModel.getFoodSubCategory(getSubCategory)
                    nutrionViewModel.getFoodSubCats(getSubCategory,context, listOf())
                    viewHolder.imageViewIcon.findNavController().navigate(R.id.foodSubCategoriesFragment)
                }
            }

            ContextCompat.getString(context, R.string.parentCat_Fibers) -> {
                viewHolder.imageViewIcon.setOnClickListener {
                    var getSubCategory = ContextCompat.getString(context,R.string.parentCat_Fibers)
                    nutrionViewModel.getFoodSubCategory(getSubCategory)
                    nutrionViewModel.getFoodSubCats(getSubCategory,context, listOf())
                    viewHolder.imageViewIcon.findNavController().navigate(R.id.foodSubCategoriesFragment)
                }
            }

            ContextCompat.getString(context, R.string.parentCat_Protein) -> {
                viewHolder.imageViewIcon.setOnClickListener {
                    var getSubCategory = ContextCompat.getString(context,R.string.parentCat_Protein)
                    nutrionViewModel.getFoodSubCategory(getSubCategory)
                    nutrionViewModel.getFoodSubCats(getSubCategory,context, listOf())
                    viewHolder.imageViewIcon.findNavController().navigate(R.id.foodSubCategoriesFragment)
                }
            }

            ContextCompat.getString(context, R.string.parentCat_Fats) -> {
                viewHolder.imageViewIcon.setOnClickListener {
                        var getSubCategory = ContextCompat.getString(context, R.string.parentCat_Fats)
                        nutrionViewModel.getFoodSubCategory(getSubCategory)
                        nutrionViewModel.getFoodSubCats(getSubCategory, context, listOf())
                        viewHolder.imageViewIcon.findNavController().navigate(R.id.foodSubCategoriesFragment)
                }
            }

        }



        return itemView!!
    }


}
