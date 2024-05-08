package adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.shapeminder_appidee.BuildConfig
import com.example.shapeminder_appidee.R
import com.example.shapeminder_appidee.databinding.ListGymBinding
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.fido.fido2.api.common.RequestOptions
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.PhotoMetadata
import com.google.android.libraries.places.api.net.FetchPlaceRequest
import com.google.android.libraries.places.api.net.FetchResolvedPhotoUriRequest
import model.data.remote.api_model.GooglePlaces.Place
import ui.viewModel.GymLocationsViewModel
import com.google.android.libraries.places.api.model.Place.Field


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

        val key = BuildConfig.apiKey

        var gym = dataset[position]
        holder.binding.gymName.setText(gym.name)
        holder.binding.gymAdress.setText(gym.address)
        holder.binding.gymRating.setText(gym.averageRating.toString())
        if (gym.photos.isNotEmpty()){
            var placeReference = gym.photos[0].reference
//            var string = "https://maps.googleapis.com/maps/api/place/photo?maxheight=500&photo_reference=AUGGfZkN9F5nN0ft3p_brOTWBEep-MVE2faDmZ5bOoGa1yEz-N2nrU9UYLBDm2wa4lCBrAPg6YnrLgMUaJ9Q6vBaQjmgC41keWzf9Vma7b8nJ1YZCS1DDkwDe9ywdoO5pMs6lHSCNyo1P4U1C2X6tMh96d5T56IZ1bl7HYvnb_C5vMDXFU26&key=AIzaSyCkZf1Yv5d4k1rI_0eH61GSZFY_LxgmowE"
            holder.binding.gymImage.load("https://maps.googleapis.com/maps/api/place/photo?maxheight=500&photo_reference=${placeReference}&key=$key")
        } else{
            holder.binding.gymImage.setImageResource(R.drawable.content2_img)
        }

        if (!gym.currentlyOpen.openNow){
            holder.binding.gymOpening.setText(context.getString(R.string.closeText))
        } else{
            holder.binding.gymOpening.setTextColor(ContextCompat.getColor(context, R.color.green))
            holder.binding.gymOpening.setText(context.getString(R.string.openText))
        }


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


//    private fun loadGymImage(place: Place, holder: GymViewHolder){
//            val placesClient = Places.createClient(context)
//
//
//        // Erhalte Metadaten für das Foto.
//            val metadata = place.photos
//            if (metadata == null || metadata.isEmpty()) {
//                Log.w("GymLocAdapter", "Keine Fotometadaten.")
//            }
//            val photoMetadata = PhotoMetadata
//                .builder(metadata[0].reference)
//                .setHeight(300)
//                .setWidth(500)
//                .setAttributions(metadata[0].attributions.toString())
//                .build()
//
//            // Erhalte den Attributions-Text.
//            // Erstelle eine FetchResolvedPhotoUriRequest.
//            val photoRequest = FetchResolvedPhotoUriRequest.builder(photoMetadata)
//                .setMaxWidth(500) // Optional.
//                .setMaxHeight(300) // Optional.
//                .build()
//
//            // Ich fordere die Foto-URI an.
//            placesClient.fetchResolvedPhotoUri(photoRequest).addOnSuccessListener { photoResponse ->
//                val uri = photoResponse.uri
//                // Hier setze ich die Uri in mein Imageview
//                var string = "https://lh3.googleusercontent.com/places/ANXAkqGgdsBYLV1LvQqCqtW-aEbRKhjSLpzcuojMfewQjAADtZYG3iP71dIFrZs8sb4RQSgRoOw2sdElrh8eDdTZnTY9YjZ2rORlnDU=s1600-h500"
//                holder.binding.gymImage.load(string)
//
//            }.addOnFailureListener { exception ->
//                if (exception is ApiException) {
//                    val apiException = exception as ApiException
//                    Log.e("GymLocAdapter", "Ort nicht gefunden: " + exception.message)
//                    val statusCode = apiException.statusCode
//                    // TODO: Fehler mit dem angegebenen Statuscode behandeln.
//                }
//        }
//    }

}