package app.yota.view.valueprogress

import android.content.Context
import android.util.AttributeSet
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.Lifecycle
import app.yota.R

class ValueProgressView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val totalTextView: TextView
    private val currentView: TextView

    init {
        inflate(context, R.layout.layout_value_progress_view, this)
        totalTextView = findViewById(R.id.total_text_view)
        currentView = findViewById(R.id.current_text_view)
    }

    fun attachToLifecycle(lifecycle: Lifecycle) {
        if (background is ValueProgressDrawable) {
            return
        }

        ValueProgressDrawable(context).let {
            lifecycle.addObserver(it)
            background = it
        }
    }

    fun setProgress(total: Int, current: Int) {
        totalTextView.text = total.toString()
        currentView.text = context.getString(R.string.value_progress_current_formatted, current)

        val valueProgressDrawable = background
        if (valueProgressDrawable is ValueProgressDrawable) {
            valueProgressDrawable.progress = current *  100f / total
        }
    }
}