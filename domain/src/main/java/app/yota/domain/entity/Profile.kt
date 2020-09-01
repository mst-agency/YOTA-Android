package app.yota.domain.entity

data class Profile(
    val phoneNumber: String,
    val isCanChanged: Boolean,
    val totalAmount: Float,
    val minutesAccumulator: Accumulator,
    val nextPaymentDate: String,
    val gigabytesAccumulator: Accumulator
) {
    data class Accumulator(val total: Int, val current: Int)
}