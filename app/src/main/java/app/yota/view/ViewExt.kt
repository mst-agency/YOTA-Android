package app.yota.view

import android.view.View


inline fun <T : View> T.showIfOrHide(crossinline condition: T.() -> Boolean): T? =
    takeIf(condition)
        ?.apply { show() }
        ?: let {
            hide()
            null
        }

fun View.show() {
    visibility = View.VISIBLE
}

fun View.hide() {
    visibility = View.GONE
}