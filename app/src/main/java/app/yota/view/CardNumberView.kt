package app.yota.view

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import android.widget.TextView
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
}