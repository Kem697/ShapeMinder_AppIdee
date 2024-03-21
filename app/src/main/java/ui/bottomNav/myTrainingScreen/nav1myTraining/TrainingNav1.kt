package ui.bottomNav.myTrainingScreen.nav1myTraining

import adapter.ItemAdapter
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.shapeminder_appidee.databinding.FragmentTrainingNav1Binding
import ui.viewModel.HomeViewModel


class TrainingNav1 : Fragment() {

    private lateinit var binding: FragmentTrainingNav1Binding
    val viewModel: HomeViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTrainingNav1Binding.inflate(layoutInflater)
        return binding.root
    }

    /*Ich habe hier den Recycler Views meiner Layouts den ItemAdapter zugewiesen.
    * Dazu habe ich vorher eine neue LiveDate erstellt, die eine Liste von
    * Content aus Kraftrainingsübungen enthält (isExercise = true)*/

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.exercises.observe(viewLifecycleOwner){
            binding.rvRecents.adapter = ItemAdapter(it,viewModel)
            binding.rvYourSessions.adapter = ItemAdapter(it,viewModel)
            binding.rvFavouriteExercises.adapter = ItemAdapter(it,viewModel)

        }
    }

}