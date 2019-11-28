package me.jamilalrasyidis.simpleprogramcode.ui.detail

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

class ViewPagerAdapter(fm: FragmentManager, private val tabCount: Int) :
    FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    private lateinit var fragment: Fragment

    override fun getItem(position: Int): Fragment {
        fragment = CodeViewerFragment()

        return fragment
    }

    override fun getCount(): Int {
        return tabCount
    }

}