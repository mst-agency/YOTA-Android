package app.yota.view.notifications.carousel

import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import app.yota.R
import app.yota.view.showIfOrHide

class CarouselViewHolder(
    val view: View,
    private val listener: Listener
) :
    RecyclerView.ViewHolder(view) {

    private var model: ICarouselViewHolderModel? = null

    private val textView = view.findViewById<TextView>(R.id.text_view)
    private val actionButton = view.findViewById<Button>(R.id.action_button)
    private val closeButton = view.findViewById<View>(R.id.close_button)

    init {
        actionButton.setOnClickListener {
            model?.id?.let {
                listener.onNotificationActionButtonClick(it)
            }
        }

        closeButton.setOnClickListener {
            model?.id?.let {
                listener.onNotificationCloseButtonClick(it)
            }
        }
    }

    fun bind(model: ICarouselViewHolderModel) {
        this.model = model;

        textView.text = model.text
        actionButton.showIfOrHide { model.actionButtonText != null }?.text = model.actionButtonText
        closeButton.showIfOrHide { model.closable }
    }

    interface Listener {
        fun onNotificationActionButtonClick(id: Long)
        fun onNotificationCloseButtonClick(id: Long)
    }
}