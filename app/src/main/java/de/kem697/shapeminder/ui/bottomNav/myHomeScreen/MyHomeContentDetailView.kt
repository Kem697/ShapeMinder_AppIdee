package de.kem697.shapeminder.ui.bottomNav.myHomeScreen

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isInvisible
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import de.kem697.shapeminder.R
import de.kem697.shapeminder.databinding.FragmentHomeContentDetailViewBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import de.kem697.shapeminder.ui.viewModel.ContentViewModel


class MyHomeContentDetailView : Fragment() {

    private lateinit var binding: FragmentHomeContentDetailViewBinding
    private val contentViewModel: ContentViewModel by activityViewModels()



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeContentDetailViewBinding.inflate(layoutInflater)
        return binding.root
    }

    /*DE:
    *Die Datasource wurde gegen eine Repository getauscht, um auf
    * Live Daten zuzugreifen. Hierzu habe Live Daten zur Contentliste und
    * zum ausgewählten Content erstellt.*/

    /*EN:
    *The datasource was exchanged for a repository in order to access
    * access live data. For this purpose, live data for the content list and
    * created for the selected content. */


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navigateBack()
        contentViewModel.selectedExercise.observe(viewLifecycleOwner){
            binding.contentImage.setImageResource(it.imageRessource)
            binding.contentTitle.setText(it.stringRessourceTitle)
            binding.contentText.setText(it.stringRessourceText)
        }
    }


    fun navigateBack(){
        binding.backBtn.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    override fun onResume() {
        super.onResume()
        var navigationBar = requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigation)
        navigationBar.isInvisible = true

        var tag = "Pause"
        Log.e(tag,"Ist der Screen pausiert?")
    }

}