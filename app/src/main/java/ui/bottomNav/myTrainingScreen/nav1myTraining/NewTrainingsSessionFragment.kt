package ui.bottomNav.myTrainingScreen.nav1myTraining

import adapter.NewSessionExercisesAdapter
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isInvisible
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.shapeminder_appidee.R
import com.example.shapeminder_appidee.databinding.FragmentNewTrainingsSessionBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import ui.viewModel.HomeViewModel


class NewTrainingsSessionFragment : Fragment() {

    private lateinit var binding: FragmentNewTrainingsSessionBinding
    val viewModel: HomeViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNewTrainingsSessionBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navigateBack()
        setUpAdapter()
        addMoreExercise()
    }


    override fun onResume() {
        super.onResume()
        var navigationBar =
            requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigation)
        navigationBar.isInvisible = true

    }





    fun navigateBack() {
        binding.backBtn.setOnClickListener {
            findNavController().navigateUp()
        }
    }


    fun setUpAdapter(){
        viewModel.addToSessionExercises.observe(viewLifecycleOwner){
            binding.rvNewSessionExercises.adapter = NewSessionExercisesAdapter(it,viewModel,requireContext())
        }
    }

    fun addMoreExercise(){
        binding.addExerciseBtn.setOnClickListener {
            findNavController().navigate(R.id.allExerciseListFragment)
        }
    }






}