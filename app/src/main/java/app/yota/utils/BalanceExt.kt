package app.yota.utils

import android.content.Context
import androidx.core.content.ContextCompat
import app.yota.R
import java.text.NumberFormat

fun Float.balanceColor(context: Context): Int {
    return if (this >= 0) {
        ContextCompat.getColor(context, R.color.balance_color)
    } else {
        ContextCompat.getColor(context, android.R.color.holo_red_light)
    }
}

fun Float.formatBalance(): String {
    return NumberFormat.getCurrencyInstance().format(this)
}