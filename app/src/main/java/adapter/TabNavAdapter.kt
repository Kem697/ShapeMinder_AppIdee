package adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter


/*Diese Adapterklasse wird zum Einrichten der Tabnavigation benötigt.*/

class TabNavAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    /*Ich habe hiere zwei Eigenschaften erstellt, die im Nachhinein, die
    * einzelne Fragmente und dessen Titel abspeichert. Standdarmäßig handelt
    * es sich um eine leere Array/Anordnung Liste von Fragment (var fragments)
    * und Fragmenttiteln (fragmentTitles).*/

    private var fragments: ArrayList<Fragment> = arrayListOf()
    private var fragmentTitles: ArrayList<String> = arrayListOf()


    override fun getCount(): Int {
        return fragments.size
    }

    override fun getItem(position: Int): Fragment {
        return fragments[position]
    }

    /*Diese Funktion fügt den Fragmenttitel und die Fragmente hinzu. */

    fun addFragment(fragment: Fragment, title: String) {
        fragments.add(fragment)
        fragmentTitles.add(title)
    }

    override fun getPageTitle(position: Int): CharSequence {
        return fragmentTitles[position]
    }
}