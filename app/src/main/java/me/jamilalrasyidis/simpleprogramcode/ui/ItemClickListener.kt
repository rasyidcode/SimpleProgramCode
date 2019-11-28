package me.jamilalrasyidis.simpleprogramcode.ui

import android.view.View

interface ItemClickListener {
    fun onClick(view: View, position: Int, isLongClick: Boolean)
}