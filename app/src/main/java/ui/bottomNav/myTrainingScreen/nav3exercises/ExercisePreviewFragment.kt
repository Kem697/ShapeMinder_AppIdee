package ui.bottomNav.myTrainingScreen.nav3exercises

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.MediaController
import android.widget.VideoView
import androidx.core.view.isInvisible
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.shapeminder_appidee.R
import com.example.shapeminder_appidee.databinding.FragmentExercisePreviewBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
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
        /*DE:
        *Mit der großen When Verzweigung und dem binding.title.setText deklariere ich,
        * welcher Übungsname und die beanspruchte Muskelgruppe in der UI angezeigt*/

        /*EN:
        * With binding.title.setText in the big when branch, I declare which
        * exercise name and approached muscle group will display in the ui.*/

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


    override fun onResume() {
        super.onResume()
        var navigationBar =
            requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigation)
        navigationBar.isInvisible = true
    }

    override fun onStop() {
        super.onStop()
        var navigationBar =
        requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigation)
        navigationBar.isInvisible = false
    }

    fun navigateBack(){
        binding.backBtn.setOnClickListener {
            findNavController().navigateUp()
        }
    }


//    fun playVideo(){
//        val filename = "video"
//        val filePlace = "android.resource://" + packageName + "/raw/" + R.raw.video
//        val videoView = binding.viedeoView.setVideoURI()
//        videoView.setVideoURI(Uri.parse(filePlace))
//        videoView.setMediaController(MediaController( requireContext()))
//        videoView.start()
//    }


}