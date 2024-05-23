package de.kem697.shapeminder.ui.bottomNav.myTrainingScreen.nav2exercises

import GridAdapterMyTraining
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import de.kem697.shapeminder.databinding.FragmentExercisesNav2Binding
import de.kem697.shapeminder.ui.viewModel.ExercisesViewModel


class Tab2Exercises : Fragment() {

    private lateinit var binding: FragmentExercisesNav2Binding
    val viewModel: ExercisesViewModel by activityViewModels()



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentExercisesNav2Binding.inflate(layoutInflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.listOfBodyparts.observe(viewLifecycleOwner){
            binding.bodypartsGrid.adapter = GridAdapterMyTraining(it,viewModel,requireContext())
        }
    }



}