package app.yota.view

import android.content.Context
import android.telephony.PhoneNumberUtils
import android.util.AttributeSet
import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
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
) : ConstraintLayout(context, attrs, defStyleAttr), ExpandableView.OnExpandListener {

    private val minutesValueProgressView: ValueProgressView
    private val gigabytesValueProgressView: ValueProgressView
    private val totalAmountValueTextView: TextView
    private val phoneNumberTextView: TextView
    private val nextPaymentTextView: TextView
    private val expandButton: PressableButton
    private val expandableView: ExpandableView
    private val submitButton: View

    init {
        inflate(context, R.layout.layout_services_card_view, this)
        minutesValueProgressView = findViewById(R.id.minutes_value_progress_view)
        gigabytesValueProgressView = findViewById(R.id.gigabytes_value_progress_view)
        totalAmountValueTextView = findViewById(R.id.total_amount_value_text_view)
        phoneNumberTextView = findViewById(R.id.phone_number_text_view)
        nextPaymentTextView = findViewById(R.id.next_payment_text_view)
        expandButton = findViewById(R.id.expand_button)
        expandableView = findViewById(R.id.expandable_view)
        submitButton = findViewById(R.id.submit_button)

        expandableView.setOnExpandListener(this)
        expandButton.setOnClickListener {
            expandableView.toggle()
        }
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        onExpand(expandableView.isExpanded)
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
            it.text = context.getString(R.string.next_payment, date)
        }
    }

    override fun onExpand(isExpanded: Boolean) {
        expandButton.setText(
            context.getString(
                if (isExpanded) {
                    R.string.collapse
                } else {
                    R.string.expand
                }
            )
        )
    }
}