package app.yota.view

import android.content.Context
import android.util.AttributeSet
import androidx.cardview.widget.CardView
import androidx.lifecycle.Lifecycle
import app.yota.R
import app.yota.view.valueprogress.ValueProgressView

class ServicesCardView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : CardView(context, attrs, defStyleAttr) {

    private val minutesValueProgressView: ValueProgressView
    private val gigabytesValueProgressView: ValueProgressView

    init {
        inflate(context, R.layout.layout_services_card_view, this)
        minutesValueProgressView = findViewById(R.id.minutes_value_progress_view)
        gigabytesValueProgressView = findViewById(R.id.gigabytes_value_progress_view)
    }

    fun attachToLifecycle(lifecycle: Lifecycle) {
        minutesValueProgressView.attachToLifecycle(lifecycle)
        gigabytesValueProgressView.attachToLifecycle(lifecycle)
        minutesValueProgressView.setProgress(1000, 500)
        gigabytesValueProgressView.setProgress(10, 7)
    }
}