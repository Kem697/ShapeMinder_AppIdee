package adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.shapeminder_appidee.R
import com.example.shapeminder_appidee.databinding.ListGymBinding
import com.example.shapeminder_appidee.databinding.ListItemMySessionBinding
import model.data.remote.api_model.GooglePlaces.Place
import model.data.remote.api_model.openFoodFacts.Product
import ui.viewModel.GymLocationsViewModel
import ui.viewModel.HomeViewModel

class GetGymLocationAdapter (
    private val dataset: List<Place>,
    private val viewModel: GymLocationsViewModel,
    private var context: Context
) : RecyclerView.Adapter<GetGymLocationAdapter.GymViewHolder>(){

    inner class GymViewHolder(val binding: ListGymBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GymViewHolder {
        val binding = ListGymBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return GymViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

    override fun onBindViewHolder(holder: GymViewHolder, position: Int) {
        var gym = dataset[position]
        holder.binding.gymName.setText(gym.name)
        holder.binding.gymAdress.setText(gym.adress)
        holder.binding.gymRating.setText(gym.averageRating.toString())

        if (!gym.openend.openNow){
            holder.binding.gymOpening.setText(context.getString(R.string.closeText))
        } else{
            holder.binding.gymOpening.setTextColor(ContextCompat.getColor(context, R.color.green))
            holder.binding.gymOpening.setText(context.getString(R.string.openText))
        }

//     Ich muss noch die Bilder rein bekommen. Stand jetzt hat es nicht geklapt.
/*
        var image = viewModel.getGymLocationPhotos(gym.photos.first().reference).toString()
*/
       /* image.toString()
        var tag = "Image"
        Log.i(tag,"$image")
        holder.binding.gymImage.load(image)*/


        holder.binding.materialCardView.setOnClickListener {
                    try {
                        val searchQuery = gym.name // Zufällig ausgewählter Store als Suchbegriff
                        if (searchQuery != null) {
                            val uri = Uri.parse("geo:0,0?q=$searchQuery")
                            val intent = Intent(Intent.ACTION_VIEW, uri)
                            intent.setPackage("com.google.android.apps.maps") // Um sicherzustellen, dass Google Maps verwendet wird
                            context.startActivity(intent)
                        }
                    } catch (e: Exception) {
                        Toast.makeText(holder.binding.root.context, context.getString(R.string.failedStoreFinderText), Toast.LENGTH_SHORT).show()
                        e.printStackTrace()
                    }

        }

    }


}