package ui

import adapter.TabNavAdapter
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.fragment.app.FragmentManager
import androidx.navigation.fragment.findNavController
import androidx.viewpager.widget.ViewPager
import com.example.shapeminder_appidee.MainActivity
import com.example.shapeminder_appidee.R
import com.google.android.material.tabs.TabLayout


class MyTrainingScreen : Fragment() {


    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager: ViewPager

    /*    private lateinit var binding: FragmentMyTrainingScreenBinding*/


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        /* binding = FragmentMyTrainingScreenBinding.inflate(layoutInflater)
         return binding.root*/

        return inflater.inflate(R.layout.fragment_my_training_screen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        tabLayout = binding.myTrainingNav
//        viewPager = binding.myTrainingViewPager

        /*
        *Mithilfe eines Youtube Videos und ChatGpt habe ich
        * eine Tab Navigation in einem Fragment meiner App
        * eingerichtet. Problem war, dass ich über das Binding
        * nicht die View Elemente ansprechen konnte. Deswegen
        * musste ich die MainActivity aufrufen, um über findViewById
        * die erforderten Elemente für die Zuweisung meines Tablayouts und
        * ViewPagers einzurichten.
        *
        * */


        var main = activity as MainActivity

        tabLayout = main.findViewById(R.id.myTraining_nav)
        viewPager = main.findViewById(R.id.myTraining_viewPager)


        // Erstellen Sie zuerst den FragmentManager
        val fragmentManager: FragmentManager = childFragmentManager

        // Erstellen Sie dann den TabNavAdapter und übergeben Sie den FragmentManager
        val tabNavAdapter = TabNavAdapter(fragmentManager)

        // Fügen Sie Ihre Fragmente zum Adapter hinzu (Beispielhaft)
        tabNavAdapter.addFragment(TrainingNav1(), "Mein Training")
        tabNavAdapter.addFragment(ProgressionNav2(), "Progression")
        tabNavAdapter.addFragment(ExercisesNav3(), "Übungen")

        // Setzen Sie den Adapter zum ViewPager
        viewPager.adapter = tabNavAdapter

        // Verknüpfen Sie das TabLayout mit dem ViewPager
        tabLayout.setupWithViewPager(viewPager)


    }


}