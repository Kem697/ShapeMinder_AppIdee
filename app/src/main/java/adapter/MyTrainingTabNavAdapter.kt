package adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter


/*DE:
*Diese Adapterklasse wird zum Einrichten der Tabnavigation benötigt.
*Ich habe hiere zwei Eigenschaften erstellt, die im Nachhinein, die
* einzelne Fragmente und dessen Titel abspeichert. Standdarmäßig handelt
* es sich um eine leere Array/Anordnung Liste von Fragment (var fragments)
* und Fragmenttiteln (fragmentTitles). In der addFragment Funktion werden die
* Fragmenttitel und die Fragmente hinzugefügt.*/

/*EN:
*This adapter class is required to set up the tab navigation.
*I have created two properties here that subsequently save the
*saves individual fragments and their titles. By default
* an empty array/array list of fragments (var fragments)
* and fragment titles (fragmentTitles). The addFragment functions
* adds the fragments and their titles to the adapter. */

class MyTrainingTabNavAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {


    private var fragments: ArrayList<Fragment> = arrayListOf()
    private var fragmentTitles: ArrayList<String> = arrayListOf()


    override fun getCount(): Int {
        return fragments.size
    }

    override fun getItem(position: Int): Fragment {
        return fragments[position]
    }

    /* */

    fun addFragment(fragment: Fragment, title: String) {
        fragments.add(fragment)
        fragmentTitles.add(title)
    }

    override fun getPageTitle(position: Int): CharSequence {
        return fragmentTitles[position]
    }
}