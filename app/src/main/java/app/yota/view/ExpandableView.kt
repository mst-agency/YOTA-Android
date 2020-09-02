package app.yota.view

import android.animation.Animator
import android.animation.ValueAnimator
import android.content.Context
import android.os.Parcelable
import android.util.AttributeSet
import android.widget.FrameLayout
import androidx.core.animation.addListener
import androidx.core.view.updateLayoutParams
import app.yota.R
import kotlinx.android.parcel.Parcelize

class ExpandableView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    var isExpanded: Boolean = false
        private set

    var expandListener: OnExpandListener? = null

    private var expandableHeight: Int = 0

    private var activeAnimator: Animator? = null

    init {
        val a = context.obtainStyledAttributes(attrs, R.styleable.ExpandableView)
        isExpanded = a.getBoolean(R.styleable.ExpandableView_isExpanded, false)
        a.recycle()
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        if (state !is SavedState) {
            super.onRestoreInstanceState(state)
            return
        }

        super.onRestoreInstanceState(state.superState)
        isExpanded = state.isExpanded
    }

    override fun onSaveInstanceState(): Parcelable? {
        return SavedState(super.onSaveInstanceState(), isExpanded)
    }

    fun setOnExpandListener(listener: OnExpandListener) {
        expandListener = listener
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED)
        expandableHeight = measuredHeight
        if (!isExpanded) {
            updateLayoutParams {
                height = 0
            }
        }
    }

    fun toggle() {
        if (isExpanded) {
            collapse()
        } else {
            expand()
        }
    }

    private fun expand() {
        isExpanded = true
        activeAnimator?.cancel()

        ValueAnimator.ofFloat(0f, 1f).apply {
            duration = EXPAND_DURATION
            addUpdateListener {
                val value = it.animatedValue as Float
                updateLayoutParams {
                    height = (expandableHeight * value).toInt()
                }
            }
            addListener(onEnd = {
                expandListener?.onExpand(true)
            })
            start()
        }.also {
            activeAnimator = it
        }
    }

    private fun collapse() {
        isExpanded = false
        activeAnimator?.cancel()

        updateLayoutParams {
            height = measuredHeight
        }
        ValueAnimator.ofFloat(1f, 0f).apply {
            duration = EXPAND_DURATION
            addUpdateListener {
                val value = it.animatedValue as Float
                updateLayoutParams {
                    height = (height * value).toInt()
                }
            }
            addListener(onEnd = {
                expandListener?.onExpand(false)
            })
            start()
        }.also {
            activeAnimator = it
        }
    }

    interface OnExpandListener {
        fun onExpand(isExpanded: Boolean)
    }

    @Parcelize
    private class SavedState(
        val parcelable: Parcelable?,
        val isExpanded: Boolean
    ) : BaseSavedState(parcelable)

    companion object {
        private const val EXPAND_DURATION = 500L
    }
}