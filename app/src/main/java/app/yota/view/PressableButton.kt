package app.yota.view

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.animation.Animation
import android.view.animation.ScaleAnimation
import android.widget.TextView
import androidx.cardview.widget.CardView
import app.yota.R

class PressableButton @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : CardView(context, attrs, defStyleAttr) {

    private val labelTextView: TextView

    init {
        inflate(context, R.layout.layout_pressable_button, this)
        labelTextView = findViewById(R.id.label_text_view)

        setOnTouchListener { v, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    v.startAnimation(createScaleAnimation(true))
                    return@setOnTouchListener true
                }
                MotionEvent.ACTION_UP -> {
                    performClick()
                    v.startAnimation(createScaleAnimation(false))
                }
                MotionEvent.ACTION_CANCEL -> {
                    v.startAnimation(createScaleAnimation(false))
                }
            }

            true
        }
    }

    fun setText(value: String) {
        labelTextView.text = value;
    }

    private fun createScaleAnimation(scaleDown: Boolean): Animation =
        if (scaleDown) {
            createScaleAnimation(1F, DEFAULT_BUTTON_TAP_SCALE_FACTOR)
        } else {
            createScaleAnimation(DEFAULT_BUTTON_TAP_SCALE_FACTOR, 1F)
        }

    private fun createScaleAnimation(
        startScale: Float,
        endScale: Float,
        duration: Long = TAP_ANIMATION_DURATION
    ): Animation {
        return ScaleAnimation(
            startScale, endScale,
            startScale, endScale,
            Animation.RELATIVE_TO_SELF, 0.5F,
            Animation.RELATIVE_TO_SELF, 0.5F
        ).apply {
            this.fillAfter = true
            this.duration = duration
        }
    }

    companion object {
        private const val DEFAULT_BUTTON_TAP_SCALE_FACTOR = 0.9F
        private const val TAP_ANIMATION_DURATION = 100L
    }
}