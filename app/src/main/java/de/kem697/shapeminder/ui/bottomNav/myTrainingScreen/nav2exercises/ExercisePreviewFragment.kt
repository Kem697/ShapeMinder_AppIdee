package de.kem697.shapeminder.ui.bottomNav.myTrainingScreen.nav2exercises

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isInvisible
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import de.kem697.shapeminder.R
import de.kem697.shapeminder.databinding.FragmentExercisePreviewBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import de.kem697.shapeminder.model.data.local.model.myTraining.Exercise
import de.kem697.shapeminder.ui.viewModel.ExercisesViewModel



class ExercisePreviewFragment : Fragment() {
    private lateinit var binding: FragmentExercisePreviewBinding

    val viewModel: ExercisesViewModel by activityViewModels()

    private var lastPosition: Int = 0
    private var isVideoPaused: Boolean = false



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
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
        viewModel.selectedExercise.observe(viewLifecycleOwner) {
            binding.title.setText(it.stringRessourceTitle)
            when (getString(it.bodyPart)) {
                getString(R.string.bpBiceps) -> {
                    binding.subTitle.text = getString(R.string.bpArmeSubTitle)
                    binding.bodyPartView.setImageResource(R.drawable.small_bp1_biceps_silhoette_png)
                    binding.descriptionText.setText(it.stringRessourceText)
                }

                getString(R.string.bpBauch) -> {
                    binding.subTitle.text = getString(R.string.bpBauchSubTitle)
                    binding.bodyPartView.setImageResource(R.drawable.small_bp5_abs_silhoette_png)
                    binding.descriptionText.setText(it.stringRessourceText)
                }

                getString(R.string.bpSchulter) -> {
                    binding.subTitle.text = getString(R.string.bpSchulterSubTitle)
                    binding.bodyPartView.setImageResource(R.drawable.small_bp3_shoulder_silhoette_png)
                    binding.descriptionText.setText(it.stringRessourceText)
                }

                getString(R.string.bpRücken) -> {
                    binding.subTitle.text = getString(R.string.bpRückenSubTitle)
                    binding.bodyPartView.setImageResource(R.drawable.small_bp4_back_silhoette_png)
                    binding.descriptionText.setText(it.stringRessourceText)
                }

                getString(R.string.bpBeine) -> {
                    binding.subTitle.text = getString(R.string.bpBeineSubTitle)
                    binding.bodyPartView.setImageResource(R.drawable.small_bp2_legs_silhoette_png)
                    binding.descriptionText.setText(it.stringRessourceText)
                }

                getString(R.string.bpBrust) -> {
                    binding.subTitle.text = getString(R.string.bpBrustSubTitle)
                    binding.bodyPartView.setImageResource(R.drawable.small_bp6_chest_silhoette_png)
                    binding.descriptionText.setText(it.stringRessourceText)
                }
            }
            saveExercise(it)
            playVideoOnYoutube(it)
            shareExercise(it)
            addExerciseToTraining(it)
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


    fun saveExercise(exercise: Exercise){
        var saveBtn = binding.saveExerciseBtn
        saveBtn.setImageResource(if (exercise.isSaved) R.drawable.favorite_fill1_wght400_grad0_opsz24 else R.drawable.favorite_fill0_wght400_grad0_opsz24)
        saveBtn.setOnClickListener {
            if (exercise.isSaved) {
                viewModel.isExerciseSaved(!exercise.isSaved, exercise)
                binding.saveExerciseBtn.setImageResource(R.drawable.favorite_fill0_wght400_grad0_opsz24)
                exercise.isSaved = false
            } else {
                viewModel.isExerciseSaved(!exercise.isSaved, exercise)
                binding.saveExerciseBtn.setImageResource(R.drawable.favorite_fill1_wght400_grad0_opsz24)
                exercise.isSaved = true
            }
        }
    }



    /*DE:
    * Muss überarbeitet werden..*/

    fun shareExercise(videoExercise: Exercise) {
        var shareBtn = binding.shareBtn
        val url = videoExercise.video?.let { "https://www.youtube.com/watch?v=${getString(it)}" }
        shareBtn.setOnClickListener {
            if (videoExercise.video!=null){
                val intent = Intent(Intent.ACTION_SEND)
                intent.type = "text/plain"
                intent.putExtra(Intent.EXTRA_TITLE,"${getString(R.string.intentRecommendText)} || ${getString(videoExercise.stringRessourceTitle)} ||")
                intent.putExtra(Intent.EXTRA_TEXT, "$url")
                val chooser = Intent.createChooser(intent, context?.getString(R.string.intentShareText))
                startActivity(chooser)
            } else
                Toast.makeText(requireContext(), context?.getString(R.string.toastShareVideoFailure), Toast.LENGTH_SHORT).show()
        }
    }





    /*DE:
    * Ich habe eine Funktion programmiert, um eine Krafttrainingsübung
    * abzuspielen, die auf Youtube hochgeladen ist. Sie bekommt als Parameter
    * die Kräftigungsübung übergeben, um im Nachhinein auf die video Eigenschaft,
    * welche einer Stringwert ist, zurückzugreifen. Die playVideoOnYoutube-Funktion ruft das
    * YouTubePlayerView-Objekt über das binding auf und fügt ihm einen YouTubePlayerListener hinzu,
    * um auf Ereignisse des YouTube-Players zu reagieren. In der OnReady-Methode des YoutubePlayerListener
    * wird überprüft, ob das videoExercise ein gültiges enthält. Falls ja, kann ein Video
    * von Youtube abgespielt werden. Falls nein, wird in der View automatisch eine Fehlermeldung
    * angezeigt. Zudem wird ein Tag im Logcat aufgerufen.
    * */

    /*EN:
    *  I have programmed a function to play a strength training exercise
    *  that has been uploaded to YouTube. It gets as parameter
    *  the strength training exercise in order to access the video property afterwards,
    *  which is a string value. The playVideoOnYoutube function calls the
    *  YouTubePlayerView object via the binding and adds a YouTubePlayerListener to it,
    *  to respond to YouTube player events. In the OnReady method of the YouTubePlayerListener
    *  checks whether the videoExercise contains a valid one. If it does, a video
    *  from YouTube can be played. If not, an error message is automatically displayed in the view
    *  is automatically displayed in the view. In addition, a tag is called in the logcat.
    * */

    fun playVideoOnYoutube(videoExercise: Exercise){
        var ytPlayer = binding.videoViewYtPlayer
        lifecycle.addObserver(ytPlayer)
        ytPlayer.addYouTubePlayerListener(object : AbstractYouTubePlayerListener(){
            override fun onReady(youTubePlayer: YouTubePlayer) {
                super.onReady(youTubePlayer)
                var videoId = videoExercise.video?.let { getString(it) }
                if (videoId!=null){
                    youTubePlayer.cueVideo(videoId,0F)
                }
                else{
                    var tag = "Kein Video"
                    Log.e(tag,"Video ist nicht vorhanden!: $videoId")

                }
            }

            override fun onError(youTubePlayer: YouTubePlayer, error: PlayerConstants.PlayerError) {
                super.onError(youTubePlayer, error)
                Toast.makeText(
                    binding.root.context,
                    context?.getString(R.string.toastLoadVideoFailure),
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }


    fun addExerciseToTraining(exercise: Exercise){
        binding.addTrainingBtn.setOnClickListener {
            viewModel.addToNewWorkout(exercise)
            findNavController().navigate(R.id.action_exercisePreviewFragment_to_newTrainingsSessionFragment)
        }
    }
}






