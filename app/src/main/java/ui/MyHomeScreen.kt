package ui

import adapter.ItemAdapter
import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isInvisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import com.example.shapeminder_appidee.R
import com.example.shapeminder_appidee.databinding.FragmentHomeScreenBinding
import com.google.android.material.bottomnavigation.BottomNavigationView


class MyHomeScreen : Fragment() {

    private lateinit var binding: FragmentHomeScreenBinding

    private val viewModel: HomeViewModel by activityViewModels()



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeScreenBinding.inflate(layoutInflater)
        return binding.root
    }

    /*Mein Adapter beobachtet nun die Live Daten aus dem Repository.
    * Zur Darstellung der Daten in der View ist dies nicht zwingend notwendig.
    * Für die Navigation zu den Detailansichten (siehe ItemAdapter) hilft das
    * aber bei der Datenübertragung.*/

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        searchInput()

        viewModel.contents.observe(viewLifecycleOwner){
            var navigationBar = requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigation)
            navigationBar.isInvisible = false
            binding.recyclerView.adapter = ItemAdapter(it,viewModel)
        }
    }


    @SuppressLint("ResourceAsColor")
    fun searchInput(){
        var searchBar = binding.searchBarTextInput
        searchBar.addTextChangedListener {
            var userInput = binding.searchBarTextInput.text.toString()
            if (userInput.isNotBlank()){
                searchBar.setTextColor(R.color.white)
                binding.searchBar.setText(userInput)
            }
        }


    }



}


/*

Kommentar zum home_fragment_xml.:
<!--Durch orientation kann ich deklarieren, in welche
Richtung sich mein Recyclerview scrollen lässt.
Hierbei ist jedoch das Problem, dass ich nicht
weiß, wie mein Screen sich vertikal und horizontal
scrollen lässt. Ich werde daran noch arbeiten-->
*/
