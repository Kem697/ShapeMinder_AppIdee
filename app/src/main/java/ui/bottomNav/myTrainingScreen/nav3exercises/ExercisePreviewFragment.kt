package ui.bottomNav.myTrainingScreen.nav3exercises

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.shapeminder_appidee.databinding.FragmentExercisePreviewBinding
import ui.viewModel.HomeViewModel


class ExercisePreviewFragment : Fragment() {
    private lateinit var binding: FragmentExercisePreviewBinding
    val viewModel: HomeViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentExercisePreviewBinding.inflate(layoutInflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.selectedContent.observe(viewLifecycleOwner) {
            binding.title.setText(it.stringRessourceTitle)
            when (it.bodyPart) {
                "Arme" -> {
                    binding.subTitle.text = "Arm Übung"
                }

                "Bauch" -> {
                    binding.subTitle.text = "Bauch Übung"
                }

                "Schulter" -> {
                    binding.subTitle.text = "Schulter Übung"


                }

                "Rücken" -> {
                    binding.subTitle.text = "Rücken übung"

                }

                "Beine" -> {
                    binding.subTitle.text = "Bein übung"

                }

                "Brust" -> {
                    binding.subTitle.text = "Brust Übung"
                }
            }
        }

        binding.backBtn.setOnClickListener {
            findNavController().navigateUp()
        }
    }

}