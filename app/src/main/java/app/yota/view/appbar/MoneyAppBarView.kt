package app.yota.view.appbar

import android.content.Context
import android.util.AttributeSet
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import app.yota.R
import app.yota.view.CardNumberView

class MoneyAppBarView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr), CoordinatorLayout.AttachedBehavior {

    private val expandedHeight: Float

    private val cardNumberView: CardNumberView
    private val balanceValueTextView: TextView

    init {
        val a = context.obtainStyledAttributes(attrs, R.styleable.MoneyAppBarView)
        expandedHeight = a.getDimension(R.styleable.MoneyAppBarView_expanded_height, 0F)
        a.recycle()
        inflate(context, R.layout.layout_money_app_bar, this)

        cardNumberView = findViewById(R.id.card_number_view)
        balanceValueTextView = findViewById(R.id.balance_value_text_view)
    }

    fun setBalance(value: Float) {
        balanceValueTextView.text = context.getString(R.string.balance_formatted, value)
    }

    fun setCardNumber(value: Int) {
        cardNumberView.setCardNumber(value)
    }

    override fun getBehavior(): CoordinatorLayout.Behavior<MoneyAppBarView> {
        return MoneyAppbarBehavior(expandedHeight.toInt())
    }
}