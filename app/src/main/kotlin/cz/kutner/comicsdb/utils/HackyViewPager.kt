package cz.kutner.comicsdb.utils

import android.content.Context
import android.support.v4.view.ViewPager
import android.view.MotionEvent

class HackyViewPager(context: Context) : ViewPager(context) {
    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        try {
            return super.onInterceptTouchEvent(ev)
        } catch (e: IllegalArgumentException) {
            return false
        }
    }
}