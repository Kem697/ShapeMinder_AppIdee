package adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter




class MyNutritionTabNavAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {



    private var fragments: ArrayList<Fragment> = arrayListOf()
    private var fragmentTitles: ArrayList<String> = arrayListOf()


    override fun getCount(): Int {
        return fragments.size
    }

    override fun getItem(position: Int): Fragment {
        return fragments[position]
    }


    fun addFragment(fragment: Fragment, title: String) {
        fragments.add(fragment)
        fragmentTitles.add(title)
    }

    override fun getPageTitle(position: Int): CharSequence {
        return fragmentTitles[position]
    }
}