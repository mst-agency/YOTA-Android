package app.yota.view.appbar

import android.os.Parcel
import android.os.Parcelable
import android.util.Log
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.ViewCompat

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
        return SavedState(super.onSaveInstanceState(parent, child), this)
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

    private class SavedState : View.BaseSavedState {

        val scrollOffset: Float

        constructor(parcelable: Parcelable?, behavior: MoneyAppbarBehavior) : super(parcelable) {
            scrollOffset = behavior.scrollOffset
        }

        constructor(parcel: Parcel) : super(parcel) {
            scrollOffset = parcel.readFloat()
        }

        override fun writeToParcel(parcel: Parcel, flags: Int) {
            super.writeToParcel(parcel, flags)
            parcel.writeFloat(scrollOffset)
        }

        companion object CREATOR : Parcelable.Creator<SavedState> {

            override fun createFromParcel(parcel: Parcel): SavedState {
                return SavedState(parcel)
            }

            override fun newArray(size: Int): Array<SavedState?> {
                return arrayOfNulls(size)
            }
        }
    }

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
