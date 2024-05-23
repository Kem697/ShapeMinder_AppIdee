package de.kem697.shapeminder.ui.bottomNav.myTrainingScreen

import adapter.MyTrainingTabNavAdapter
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isInvisible
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.activityViewModels
import androidx.viewpager.widget.ViewPager
import de.kem697.shapeminder.MainActivity
import de.kem697.shapeminder.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.tabs.TabLayout
import de.kem697.shapeminder.ui.bottomNav.myTrainingScreen.nav1myTraining.Tab1MyTraining
import de.kem697.shapeminder.ui.bottomNav.myTrainingScreen.nav2exercises.Tab2Exercises
import de.kem697.shapeminder.ui.viewModel.ExercisesViewModel



class MyTrainingScreen : Fragment() {


    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager: ViewPager
    val viewModel: ExercisesViewModel by activityViewModels()







    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_my_training_screen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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
        val tabNavAdapter = MyTrainingTabNavAdapter(fragmentManager)

        // Fügen Sie Ihre Fragmente zum Adapter hinzu (Beispielhaft)
        tabNavAdapter.addFragment(Tab1MyTraining(), getString(R.string.myTtab1Title))
        tabNavAdapter.addFragment(Tab2Exercises(), getString(R.string.myTtab2Title))

        // Setzen Sie den Adapter zum ViewPager
        viewPager.adapter = tabNavAdapter

        // Verknüpfen Sie das TabLayout mit dem ViewPager
        tabLayout.setupWithViewPager(viewPager)


    }



    override fun onResume() {
        super.onResume()
        var navigationBar = requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigation)
        navigationBar.isInvisible = false
        var tag = "Pause"
        Log.e(tag,"Ist der Screen pausiert?")
    }

}


