package app.yota.utils

import android.content.Context
import android.util.DisplayMetrics
import android.view.WindowManager

fun Context.getScreenWidth(): Int {
    val windowManager = getSystemService(Context.WINDOW_SERVICE) as? WindowManager
    val displayMetrics = DisplayMetrics()
    windowManager?.defaultDisplay?.getMetrics(displayMetrics)
    return displayMetrics.widthPixels
}
