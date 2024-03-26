package ui.bottomNav.myTrainingScreen.nav3exercises

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.shapeminder_appidee.R
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
        /*Mit der Grußen When und dem binding.title.setText steuere ich
        * Anzeige der Übungsnamen und der beanspruchen Muskelgruppe */
        super.onViewCreated(view, savedInstanceState)
        navigateBack()
        viewModel.selectedContent.observe(viewLifecycleOwner) {
            binding.title.setText(it.stringRessourceTitle)
            when (it.bodyPart) {
                "Arme" -> {
                    binding.subTitle.text = "Arm Übung"
                    binding.bodyPartView.setImageResource(R.drawable.bp1arms)
                }

                "Bauch" -> {
                    binding.subTitle.text = "Bauch Übung"
                    binding.bodyPartView.setImageResource(R.drawable.bp5abs)

                }

                "Schulter" -> {
                    binding.subTitle.text = "Schulter Übung"
                    binding.bodyPartView.setImageResource(R.drawable.bp3shoulders)


                }

                "Rücken" -> {
                    binding.subTitle.text = "Rücken übung"
                    binding.bodyPartView.setImageResource(R.drawable.bp4back)


                }

                "Beine" -> {
                    binding.subTitle.text = "Bein übung"
                    binding.bodyPartView.setImageResource(R.drawable.bp2legs)


                }

                "Brust" -> {
                    binding.subTitle.text = "Brust Übung"
                    binding.bodyPartView.setImageResource(R.drawable.bp6chest)
                }
            }
        }


    }

    fun navigateBack(){
        binding.backBtn.setOnClickListener {
            findNavController().navigateUp()
        }
    }

}