package app.yota.view.notifications.carousel

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import app.yota.R

class CarouselAdapter(
    private val listener: CarouselViewHolder.Listener
) : RecyclerView.Adapter<CarouselViewHolder>() {

    private var items = listOf<ICarouselViewHolderModel>()

    fun setData(newData: List<ICarouselViewHolderModel>) {
        items = newData
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarouselViewHolder {
        return CarouselViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.layout_notification_item, parent, false),
            listener
        )
    }

    override fun onBindViewHolder(holder: CarouselViewHolder, position: Int) {
        holder.bind(items[getRealPosition(position)])
    }

    override fun getItemCount() = if (items.size > 1) Integer.MAX_VALUE else items.size

    fun getRealItemCount() = items.size

    private fun getRealPosition(position: Int) =
        if (itemCount < Integer.MAX_VALUE) position else position % items.size
}
