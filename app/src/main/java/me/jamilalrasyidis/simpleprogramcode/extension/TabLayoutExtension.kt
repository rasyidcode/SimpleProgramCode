package me.jamilalrasyidis.simpleprogramcode.extension

import com.google.android.material.tabs.TabLayout

fun TabLayout.setOnTabSelected(onTabSelected: (tab: TabLayout.Tab?) -> Unit) {
    addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
        override fun onTabReselected(tab: TabLayout.Tab?) {}

        override fun onTabUnselected(tab: TabLayout.Tab?) {}

        override fun onTabSelected(tab: TabLayout.Tab?) {
            onTabSelected(tab)
        }

    })
}