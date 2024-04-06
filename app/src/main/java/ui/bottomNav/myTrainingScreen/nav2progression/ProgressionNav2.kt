package ui.bottomNav.myTrainingScreen.nav2progression

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.shapeminder_appidee.databinding.FragmentProgressionNav2Binding

class ProgressionNav2 : Fragment() {

    private lateinit var binding: FragmentProgressionNav2Binding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProgressionNav2Binding.inflate(layoutInflater)
        return binding.root
    }

}