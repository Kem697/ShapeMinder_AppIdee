package ui.bottomNav.myTrainingScreen

import adapter.MyNutritionTabNavAdapter
import adapter.MyTrainingTabNavAdapter
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.core.view.isInvisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.FragmentManager
import androidx.viewpager.widget.ViewPager
import com.example.shapeminder_appidee.MainActivity
import com.example.shapeminder_appidee.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.search.SearchBar
import com.google.android.material.tabs.TabLayout
import ui.bottomNav.myNutritionScreen.nav1foodFinder.FoodFinderNav1Fragment
import ui.bottomNav.myNutritionScreen.nav2diary.DiaryNav2Fragment
import ui.bottomNav.myNutritionScreen.nav3groceryList.GroceryListNav3Fragment


class MyNutrionScreen : Fragment() {


    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager: ViewPager


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_my_nutrion_screen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        searchInput()

        /*DE:
        *Mithilfe eines Youtube Videos und ChatGpt habe ich
        * eine Tab Navigation in einem Fragment meiner App
        * eingerichtet. Problem war, dass ich über das Binding
        * nicht die View Elemente ansprechen konnte. Deswegen
        * musste ich die MainActivity aufrufen, um über findViewById
        * die erforderten Elemente für die Zuweisung meines Tablayouts und
        * ViewPagers einzurichten.
        *
        * */

        /*EN:
        *With the help of a Youtube video and ChatGpt I have
        * a tab navigation in a fragment of my app
        * set it up. The problem was that I could not address the view elements via the binding
        * could not address the view elements. Therefore
        * I had to call the MainActivity to find the required elements via findViewById
        * to set up the required elements for the assignment of my tab layout and
        * ViewPager via findViewById.
        *
        * */


        var main = activity as MainActivity

        tabLayout = main.findViewById(R.id.myNutrition_nav)
        viewPager = main.findViewById(R.id.myNutrition_viewPager)


        // Erstellen Sie zuerst den FragmentManager
        val fragmentManager: FragmentManager = childFragmentManager

        // Erstellen Sie dann den TabNavAdapter und übergeben Sie den FragmentManager
        val tabNavAdapter = MyNutritionTabNavAdapter(fragmentManager)

        // Fügen Sie Ihre Fragmente zum Adapter hinzu (Beispielhaft)
        tabNavAdapter.addFragment(FoodFinderNav1Fragment(), getString(R.string.myNutrionTab1Title))
        tabNavAdapter.addFragment(DiaryNav2Fragment(), getString(R.string.myNutrionTab2Title))
        tabNavAdapter.addFragment(GroceryListNav3Fragment(), getString(R.string.myNutrionTab3Title))

        // Setzen Sie den Adapter zum ViewPager
        viewPager.adapter = tabNavAdapter

        // Verknüpfen Sie das TabLayout mit dem ViewPager
        tabLayout.setupWithViewPager(viewPager)



    }


    override fun onResume() {
        super.onResume()
        var navigationBar = requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigation)
        navigationBar.isInvisible = false
//        setDefaultHint()
    }


/*    fun searchInput() {
        var mainActivity = activity as MainActivity
        var searchBarTextInput = mainActivity.findViewById<EditText>(R.id.myN_searchBar_textInput)
        searchBarTextInput.addTextChangedListener {
            var userInput = searchBarTextInput.text
            if (userInput.isNotBlank()) {
                var searchBar = mainActivity.findViewById<SearchBar>(R.id.myN_searchBar)
                searchBar.setText(userInput)
            }
        }*/

 /*   }
    fun setDefaultHint(){
        var mainActivity = activity as MainActivity
        var searchBar = mainActivity.findViewById<SearchBar>(R.id.myN_searchBar)
        var searchBarTextInput = mainActivity.findViewById<EditText>(R.id.myN_searchBar_textInput)
        searchBar.hint = "Suche"
        if (searchBarTextInput.text.isNotBlank()){
            searchBarTextInput.text.clear()
            searchBar.setText("")
        }

    }*/






}


