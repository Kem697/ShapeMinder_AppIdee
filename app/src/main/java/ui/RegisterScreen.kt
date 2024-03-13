package ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.shapeminder_appidee.databinding.FragmentRegisterScreenBinding


class RegisterScreen : Fragment() {
    private lateinit var binding: FragmentRegisterScreenBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegisterScreenBinding.inflate(layoutInflater)
        return binding.root
    }


}