package app.yota.utils

import app.yota.di.scope.PerApplication
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@PerApplication
class DateFormatter @Inject constructor() {
    fun nextPaymentDateFormat(rawDate: String): String? = runCatching {
        val locale = Locale("ru")
        val parsedDate = SimpleDateFormat("dd.MM.yyyy", locale).parse(rawDate)
        SimpleDateFormat("dd MMMM", locale).format(parsedDate)
    }.getOrNull()
}