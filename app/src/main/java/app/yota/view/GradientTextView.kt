package app.yota.view

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Shader
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import app.yota.R

class GradientTextView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : AppCompatTextView(context, attrs, defStyleAttr) {

    private val startColor: Int
    private val endColor: Int

    init {
        val a = context.obtainStyledAttributes(attrs, R.styleable.GradientTextView)
        startColor = a.getColor(R.styleable.GradientTextView_start_color, 0)
        endColor = a.getColor(R.styleable.GradientTextView_end_color, 0)
        a.recycle()
    }

    @SuppressLint("DrawAllocation")
    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)

        if (changed) {
            paint.shader = LinearGradient(
                0f, 0f, width.toFloat(), height.toFloat(),
                startColor,
                endColor,
                Shader.TileMode.CLAMP
            )
        }
    }
}