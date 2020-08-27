package app.yota.view

import android.content.Context
import android.util.AttributeSet
import android.widget.TextView
import androidx.cardview.widget.CardView
import app.yota.R

class MoneyCardView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : CardView(context, attrs, defStyleAttr) {

    private val cardNumberView: CardNumberView
    private val balanceValueTextView: TextView

    init {
        inflate(context, R.layout.layout_money_card_view, this)

        cardNumberView = findViewById(R.id.card_number_view)
        balanceValueTextView = findViewById(R.id.balance_value_text_view)
    }

    fun setBalance(value: Float) {
        balanceValueTextView.text = context.getString(R.string.balance_formatted, value)
    }

    fun setCardNumber(value: Int) {
        cardNumberView.setCardNumber(value)
    }
}