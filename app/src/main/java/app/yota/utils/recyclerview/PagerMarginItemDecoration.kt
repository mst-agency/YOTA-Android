package app.yota.utils.recyclerview

import android.graphics.Rect
import android.view.View
import androidx.core.view.updateLayoutParams
import androidx.recyclerview.widget.RecyclerView
import app.yota.utils.getScreenWidth

/**
 * https://github.com/yalive/RecyclerViewPager/blob/f82a22655be4878a445eec9cf1fa7297442f2246/app/src/main/java/com/yabahddou/pagersnaphelper/PagerMarginItemDecoration.kt
 */
class PagerMarginItemDecoration(
    private val horizontalMargin: Int = 0,
    private val adjacentVisibleSize: Int = 0
) :
    RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val position = parent.getChildAdapterPosition(view)
        val childCount = state.itemCount

        val firstItem = position == 0
        val lastItem = position == childCount - 1

        val screenWidth = parent.context.getScreenWidth()
        var itemWidth = screenWidth - 2 * adjacentVisibleSize - 4 * horizontalMargin

        if (firstItem || lastItem) {
            itemWidth = screenWidth - adjacentVisibleSize - 4 * horizontalMargin
        }

        if (firstItem && lastItem) {
            itemWidth = screenWidth - 4 * horizontalMargin
        }

        view.updateLayoutParams {
            width = itemWidth
            height = RecyclerView.LayoutParams.MATCH_PARENT
        }

        with(outRect) {
            left = if (firstItem) 2 * horizontalMargin else horizontalMargin
            right = if (lastItem) 2 * horizontalMargin else horizontalMargin
        }
    }
}