package app.yota.view

import android.content.Context
import android.util.AttributeSet
import androidx.cardview.widget.CardView
import app.yota.R

class ServicesCardView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : CardView(context, attrs, defStyleAttr) {

    init {
        inflate(context, R.layout.layout_services_card_view, this)
    }
}