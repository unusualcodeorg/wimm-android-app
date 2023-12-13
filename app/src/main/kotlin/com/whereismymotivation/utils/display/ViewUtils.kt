package com.whereismymotivation.utils.display

import android.content.res.Resources
import android.os.SystemClock
import android.view.MotionEvent
import android.view.View
import com.whereismymotivation.utils.log.Logger


object ViewUtils {

    fun dpToPx(dp: Float): Float = dp * Resources.getSystem().displayMetrics.density

    fun pxToDp(px: Float): Float = px / (Resources.getSystem().displayMetrics.densityDpi.toFloat() / 160f)

    fun programmaticallyClickAtCenter(view: View) {
        try {
            val downTime = SystemClock.uptimeMillis()
            val eventTime = SystemClock.uptimeMillis() + 100
            val x = view.width / 2
            val y = view.height / 2
            val metaState = 0
            val motionEventDown = MotionEvent.obtain(
                downTime,
                eventTime,
                MotionEvent.ACTION_DOWN,
                x.toFloat(),
                y.toFloat(),
                metaState
            )
            view.dispatchTouchEvent(motionEventDown)

            val motionEventUp = MotionEvent.obtain(
                downTime,
                eventTime,
                MotionEvent.ACTION_UP,
                x.toFloat(),
                y.toFloat(),
                metaState
            )
            view.dispatchTouchEvent(motionEventUp)
        } catch (e: Exception) {
            Logger.record(e)
        }
    }
}