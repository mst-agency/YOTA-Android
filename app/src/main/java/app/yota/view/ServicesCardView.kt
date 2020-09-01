package app.yota.view

import android.content.Context
import android.telephony.PhoneNumberUtils
import android.util.AttributeSet
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.lifecycle.Lifecycle
import app.yota.R
import app.yota.utils.balanceColor
import app.yota.utils.formatBalance
import app.yota.view.valueprogress.ValueProgressView
import java.util.*

class ServicesCardView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : CardView(context, attrs, defStyleAttr) {

    private val minutesValueProgressView: ValueProgressView
    private val gigabytesValueProgressView: ValueProgressView
    private val totalAmountValueTextView: TextView
    private val phoneNumberTextView: TextView
    private val nextPaymentTextView: TextView

    init {
        inflate(context, R.layout.layout_services_card_view, this)
        minutesValueProgressView = findViewById(R.id.minutes_value_progress_view)
        gigabytesValueProgressView = findViewById(R.id.gigabytes_value_progress_view)
        totalAmountValueTextView = findViewById(R.id.total_amount_value_text_view)
        phoneNumberTextView = findViewById(R.id.phone_number_text_view)
        nextPaymentTextView = findViewById(R.id.next_payment_text_view)
    }

    fun attachToLifecycle(lifecycle: Lifecycle) {
        minutesValueProgressView.attachToLifecycle(lifecycle)
        gigabytesValueProgressView.attachToLifecycle(lifecycle)
    }

    fun setMinutesAccumulatorDate(totalValue: Int, currentValue: Int) {
        minutesValueProgressView.setProgress(totalValue, currentValue)
    }

    fun setGigabytesAccumulatorDate(totalValue: Int, currentValue: Int) {
        gigabytesValueProgressView.setProgress(totalValue, currentValue)
    }

    fun setTotalAmount(value: Float) {
        totalAmountValueTextView.text = value.formatBalance()
        totalAmountValueTextView.setTextColor(value.balanceColor(context))
    }

    fun setPhoneNumber(phoneNumber: String) {
        phoneNumberTextView.text =
            "+${PhoneNumberUtils.formatNumber(phoneNumber, Locale.getDefault().country)}"
    }

    fun setNextPaymentDate(date: String?) {
        nextPaymentTextView.showIfOrHide { date != null }?.let {
            it.text =context.getString(R.string.next_payment, date)
        }
    }
}