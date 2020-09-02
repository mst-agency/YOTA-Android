package app.yota.view.appbar

import android.os.Parcel
import android.os.Parcelable
import android.util.Log
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.ViewCompat
import kotlinx.android.parcel.Parcelize

class MoneyAppbarBehavior(private val expandedHeight: Int) :
    CoordinatorLayout.Behavior<MoneyAppBarView>() {

    private var scrollOffset = 0f

    override fun onStartNestedScroll(
        coordinatorLayout: CoordinatorLayout,
        child: MoneyAppBarView,
        directTargetChild: View,
        target: View,
        nestedScrollAxes: Int,
        type: Int
    ): Boolean {
        return nestedScrollAxes == ViewCompat.SCROLL_AXIS_VERTICAL
    }

    override fun onNestedScroll(
        coordinatorLayout: CoordinatorLayout,
        child: MoneyAppBarView,
        target: View,
        dxConsumed: Int,
        dyConsumed: Int,
        dxUnconsumed: Int,
        dyUnconsumed: Int,
        type: Int,
        consumed: IntArray
    ) {
        scrollOffset += dyConsumed.toFloat()
        processOffset(child)
    }

    override fun onSaveInstanceState(
        parent: CoordinatorLayout,
        child: MoneyAppBarView
    ): Parcelable {
        return SavedState(super.onSaveInstanceState(parent, child), scrollOffset)
    }

    override fun onRestoreInstanceState(
        parent: CoordinatorLayout, child: MoneyAppBarView, state: Parcelable
    ) {
        val ss = state as SavedState
        super.onRestoreInstanceState(parent, child, ss.superState)
        scrollOffset = ss.scrollOffset
        processOffset(child)
    }

    private fun processOffset(child: MoneyAppBarView) {
        var offsetPercent = ((scrollOffset * .5f * 100 / expandedHeight).toInt()).toFloat()
        offsetPercent = offsetPercent.clamp()
        onExpand(child, offsetPercent)
    }

    private fun onExpand(child: MoneyAppBarView, percent: Float) {
        child.getChildAt(0).alpha = percent / 100f
        val params = child.layoutParams
        params.height = (percent * expandedHeight / 100f).toInt()
        child.requestLayout()
    }

    @Parcelize
    private class SavedState(
        val parcelable: Parcelable?,
        val scrollOffset: Float
    ) : View.BaseSavedState(parcelable)

    private fun Float.clamp(): Float {
        return when {
            this < 0 -> {
                0f
            }
            this > 100 -> {
                100f
            }
            else -> {
                this
            }
        }
    }
}
