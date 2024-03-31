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
import model.Content
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
            saveExercise(it)
        }

//        playVideo()

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


    /*Kommentieren
    DE: Die Playvideo Funktion funktioniert.
    Ich habe sie jedoch auskommentiert, da ich Probleme beim
    Pushen auf GitHub habe, wenn ich das Video in meinem Ressourcenmanager
    hinterlegt habe. Dementsprechend habe ich mich entschieden das Video
    aus meinem Ressourcenmanager vorerst zu entfernen. Dadurch
    funktioniert die Funktion aber nicht, weil es ein Video zum
    Laden erwartet.......*/


/*
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
*/

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

    /*DE:
    * Ich habe in die Detailansicht eine Funktion implementiert, welche die Speicher-
    * funktion in der Detailansicht der Fitnessübung ermöglicht. Die Funktion erwartet
    * als Parameter ein Objekt der Klasse Content. Hierbei handelt es sich, um die
    * Kraftitrainingsübung, dessen Speicherzustand genändert werden soll. Im Endeffekt
    * ist der Code fast identisch mit dem Code aus der ItemAdapter.kt. */

    /*En:
     * Ive implemented a function to save the selected content. The code is similar
     * to the code in the ItemAdapter class, but the main distinction of this function
     * is that it expects an object as a parameter. This object is the selected training
     * exercises  due will observed as liveData. Hence the code will invoked in the observer,
     * their i can set the related liveData in this function.
      */


    fun saveExercise(content: Content){
        var saveBtn = binding.saveExerciseBtn
        saveBtn.setImageResource(if (content.isSaved) R.drawable.favorite_fill1_wght400_grad0_opsz24 else R.drawable.favorite_fill0_wght400_grad0_opsz24)
        saveBtn.setOnClickListener {
            if (content.isSaved) {
                viewModel.isSaved(!content.isSaved, content)
                binding.saveExerciseBtn.setImageResource(R.drawable.favorite_fill0_wght400_grad0_opsz24)
                content.isSaved = false
            } else {
                viewModel.isSaved(!content.isSaved, content)
                binding.saveExerciseBtn.setImageResource(R.drawable.favorite_fill1_wght400_grad0_opsz24)
                content.isSaved = true
            }
        }
    }


}

