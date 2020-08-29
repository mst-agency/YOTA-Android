package app.yota.view.notifications.carousel

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import app.yota.R

class CarouselAdapter : RecyclerView.Adapter<CarouselViewHolder>() {

    private var items = listOf<CarouselViewHolderModel>()

    fun setData(newData: List<CarouselViewHolderModel>) {
        items = newData
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarouselViewHolder {
        return CarouselViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.layout_notification_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: CarouselViewHolder, position: Int) {

    }

    override fun getItemCount() = if (items.size > 1) Integer.MAX_VALUE else items.size

    fun getRealItemCount() = items.size
}
