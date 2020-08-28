package app.yota.utils

import java.text.NumberFormat
import javax.inject.Inject

interface ICurrencyFormatter {

    fun format(value: Float): String
}

class CurrencyFormatter @Inject constructor() : ICurrencyFormatter {

    var numberFormat: NumberFormat = NumberFormat.getCurrencyInstance()

    override fun format(value: Float): String {
        return numberFormat.format(value)
    }
}