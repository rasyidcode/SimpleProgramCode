package me.jamilalrasyidis.simpleprogramcode.ui.custom

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.viewpager.widget.ViewPager

class LockableViewPager(context: Context, attrs: AttributeSet?) : ViewPager(context, attrs) {

    var enabledSwipe: Boolean = true

    override fun onTouchEvent(ev: MotionEvent?): Boolean {
        if (enabledSwipe) {
            return super.onTouchEvent(ev)
        }

        return false
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        if (enabledSwipe) {
            return super.onInterceptTouchEvent(ev)
        }

        return false
    }

}