package app.yota.view

import android.view.View


inline fun <T : View> T.showIfOrInvisible(crossinline condition: T.() -> Boolean): T? =
    takeIf(condition)
        ?.apply { show() }
        ?: let {
            invisible()
            null
        }

fun View.show() {
    visibility = View.VISIBLE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}