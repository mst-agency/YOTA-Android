package app.yota.view.notifications.carousel

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import app.yota.R
import app.yota.utils.recyclerview.PagerMarginItemDecoration

class NotificationsCarouselView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private val recyclerView: RecyclerView
    private val recyclerLayoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
    private val snapHelper: SnapHelper
    private val recyclerAdapter: CarouselAdapter

    init {
        View.inflate(context, R.layout.layout_notifications_carousel_view, this)
        recyclerView = findViewById(R.id.recycler_view)

        recyclerAdapter = CarouselAdapter()

        recyclerView.apply {
            layoutManager = recyclerLayoutManager
            isNestedScrollingEnabled = false
            adapter = recyclerAdapter
            snapHelper = PagerSnapHelper().apply {
                attachToRecyclerView(recyclerView)
            }

            val horizontalPadding = getHorizontalPadding()
            addItemDecoration(PagerMarginItemDecoration(horizontalPadding, horizontalPadding))
        }
    }

    fun setData(newData: List<CarouselViewHolderModel>) {
        recyclerAdapter.setData(newData)
        val calculatePosition = recyclerAdapter.calculatePosition()

        recyclerLayoutManager.scrollToPositionWithOffset(
            calculatePosition,
            getHorizontalPadding().times(2)
        )
    }

    private fun getHorizontalPadding(): Int =
        context.resources.getDimension(R.dimen.notifications_carousel_horizontal_padding)
            .toInt()

    private fun CarouselAdapter.calculatePosition(): Int {
        var position = currentPosition
        if (position < 0) {
            val realItemCount = getRealItemCount()
            position = (itemCount / realItemCount / 2) * realItemCount
        }
        return position
    }

    private val currentPosition
        get() = recyclerLayoutManager.findFirstCompletelyVisibleItemPosition()
}