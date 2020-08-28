package app.yota.view

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.RippleDrawable
import android.util.AttributeSet
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.core.content.ContextCompat
import app.yota.R


class CardNumberView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private val cardNumberTextView: TextView

    init {
        inflate(context, R.layout.layout_card_number_view, this)
        cardNumberTextView = findViewById(R.id.card_number_text_view)
    }

    fun setCardNumber(value: Int) {
        cardNumberTextView.text = context.getString(R.string.card_number_formatted, value)
    }

    override fun setBackgroundColor(@ColorInt color: Int) {
        background = RippleDrawable(
            ColorStateList.valueOf(
                ContextCompat.getColor(
                    context,
                    R.color.colorAccent
                )
            ), GradientDrawable().apply {
                setColor(color)
                cornerRadius = context.resources.getDimension(R.dimen.card_nimber_corner_radius)
            }, null
        )


    }
}