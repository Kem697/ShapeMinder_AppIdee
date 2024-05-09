package ui.bottomNav.myTrainingScreen.nav2exercises

import GridAdapterMyTraining
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.shapeminder_appidee.databinding.FragmentExercisesNav3Binding
import ui.viewModel.HomeViewModel


class Tab2Exercises : Fragment() {

    private lateinit var binding: FragmentExercisesNav3Binding
    val viewModel: HomeViewModel by activityViewModels()



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentExercisesNav3Binding.inflate(layoutInflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.listOfBodyparts.observe(viewLifecycleOwner){
            binding.bodypartsGrid.adapter = GridAdapterMyTraining(it,viewModel,requireContext())
        }
    }



}