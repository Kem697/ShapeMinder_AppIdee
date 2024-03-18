package ui.nav3exercises

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.shapeminder_appidee.R
import com.example.shapeminder_appidee.databinding.FragmentExercisePreviewBinding


class ExercisePreviewFragment : Fragment() {
    private lateinit var binding: FragmentExercisePreviewBinding



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentExercisePreviewBinding.inflate(layoutInflater)
        return binding.root
    }

}