package ui.bottomNav.myTrainingScreen.nav3exercises

import android.net.Uri
import android.os.Bundle
import android.util.Log
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

    private var lastPosition: Int = 0
    private var isVideoPaused: Boolean = false



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

        playVideo()

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


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        // Speichere die letzte Position und den Pausenzustand des Videos
        outState.putInt("lastPosition", lastPosition)
        outState.putBoolean("isVideoPaused", isVideoPaused)
    }

    fun navigateBack() {
        binding.backBtn.setOnClickListener {
            findNavController().navigateUp()
        }
    }


    /*Kommentieren.....*/
    fun playVideo() {
        var playBtn = binding.playBtn
        val filename = R.raw.kh_bizespcurls
        val filePlace = "android.resource://" + "com.example.shapeminder_appidee" + "/raw/" + filename
        val videoView = binding.viedeoView


        playBtn.setOnClickListener {
            if (!videoView.isPlaying && !isVideoPaused) {
                // Wenn das Video nicht abgespielt wird und nicht pausiert ist, starte es von vorne
                binding.playBtn.setImageResource(R.drawable.pause_fill1_wght400_grad0_opsz24)
                videoView.setVideoURI(Uri.parse(filePlace))
                videoView.setMediaController(MediaController(requireContext()))
                videoView.start()
                var tag = "Video??"
                Log.i(tag, "Video startet von vorne!!")
            } else if (!videoView.isPlaying && isVideoPaused) {
                // Wenn das Video nicht abgespielt wird und pausiert ist, setze die letzte Position und starte das Video
                binding.playBtn.setImageResource(R.drawable.pause_fill1_wght400_grad0_opsz24)
                videoView.seekTo(lastPosition)
                videoView.start()
                var tag = "Video??"
                Log.i(tag, "Video läuft von letzter Position!!")
                isVideoPaused = false
            } else {
                // Andernfalls pausiere das Video und speichere die aktuelle Position
                binding.playBtn.setImageResource(R.drawable.play_arrow_fill1_wght400_grad0_opsz24)
                videoView.pause()
                lastPosition = videoView.currentPosition
                var tag = "Video??"
                Log.i(tag, "Video ist angehalten!")
                isVideoPaused = true
            }
        }
    }


    /*
    fun restartVideo() {
//        var restartBtn =
        val filename = R.raw.kh_bizespcurls
        val filePlace =
            "android.resource://" + "com.example.shapeminder_appidee" + "/raw/" + filename
        val videoView = binding.viedeoView
        videoView.stopPlayback()

    }
*/


}

