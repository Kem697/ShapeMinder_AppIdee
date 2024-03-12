package ui.nav3exercises

import adapter.ItemAdapter
import android.content.ClipData.Item
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.shapeminder_appidee.R
import com.example.shapeminder_appidee.databinding.FragmentExerciseListBinding
import ui.HomeViewModel

class ExerciseListFragment : Fragment() {
    private lateinit var binding: FragmentExerciseListBinding
    val viewModel: HomeViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentExerciseListBinding.inflate(layoutInflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.exercisesByBodyparts.observe(viewLifecycleOwner){
            binding.listOfExercises.adapter = ItemAdapter(it,viewModel)
        }

        searchInput()
        setDefaultHint()
        navigateBack()
    }


    fun navigateBack(){
        binding.backBtn.setOnClickListener {
            findNavController().navigateUp()
        }
    }
    fun searchInput() {
        var searchBar = binding.myTSearchBarTextInput
        searchBar.addTextChangedListener {
            var userInput = binding.myTSearchBarTextInput.text
            if (userInput.isNotBlank()) {
                binding.myTSearchBar.setText(userInput)
            }
        }
    }
    fun setDefaultHint(){
        binding.myTSearchBar.hint = "Suche"
        if (binding.myTSearchBarTextInput.text.isNotBlank()){
            binding.myTSearchBarTextInput.text.clear()
            binding.myTSearchBar.setText("")
        }

    }




}