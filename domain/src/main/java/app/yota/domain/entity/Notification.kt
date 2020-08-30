package app.yota.domain.entity

data class Notification(
    val id: Long,
    val priority: Int,
    val closable: Boolean,
    val text: String,
    val button: Button?
) {

    data class Button(val text: String, val deeplink: String)
}