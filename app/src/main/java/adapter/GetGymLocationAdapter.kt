package adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.shapeminder_appidee.R
import com.example.shapeminder_appidee.databinding.ListGymBinding
import com.example.shapeminder_appidee.databinding.ListItemMySessionBinding
import model.data.remote.api_model.GooglePlaces.Place
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
//        holder.binding.gymImage.load(gym.photos.first().reference)


        if (!gym.openend.openNow){
            holder.binding.gymOpening.setText(context.getString(R.string.closeText))
        } else{
            holder.binding.gymOpening.setTextColor(ContextCompat.getColor(context, R.color.green))
            holder.binding.gymOpening.setText(context.getString(R.string.openText))
        }
        var image = viewModel.getGymLocationPhotos(gym.photos.first().reference,gym.photos.first().attributions).toString()
        image.toString()
        var tag = "Image"
        Log.i(tag,"$image")
        holder.binding.gymImage.load(image)
    }


}