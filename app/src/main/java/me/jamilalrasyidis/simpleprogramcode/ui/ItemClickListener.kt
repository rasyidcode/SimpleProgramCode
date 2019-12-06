package me.jamilalrasyidis.simpleprogramcode.ui

import android.view.View
import me.jamilalrasyidis.simpleprogramcode.data.model.entity.ProgramEntity

interface ItemClickListener {
    fun onClick(view: View, programs: List<ProgramEntity>, isLongClick: Boolean)
}