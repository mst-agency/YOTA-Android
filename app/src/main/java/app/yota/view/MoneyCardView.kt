package app.yota.view

import android.content.Context
import android.util.AttributeSet
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import app.yota.R
import app.yota.utils.balanceColor
import app.yota.utils.formatBalance

class MoneyCardView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : CardView(context, attrs, defStyleAttr) {

    private val cardNumberView: CardNumberView
    private val balanceValueTextView: TextView

    init {
        inflate(context, R.layout.layout_money_card_view, this)

        cardNumberView = findViewById(R.id.card_number_view)
        balanceValueTextView = findViewById(R.id.balance_value_text_view)
        cardNumberView.setBackgroundColor(ContextCompat.getColor(context, R.color.card_number_background_color_expanded))
    }

    fun setBalance(value: Float) {
        balanceValueTextView.text = value.formatBalance()
        balanceValueTextView.setTextColor(value.balanceColor(context))
    }

    fun setCardNumber(value: Int) {
        cardNumberView.setCardNumber(value)
    }

    fun setOnCardNumberCLickListener(listener: OnClickListener) {
        cardNumberView.setOnClickListener(listener)
    }
}