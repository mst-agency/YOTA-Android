package app.yota.view.notifications.carousel

interface ICarouselViewHolderModel {
    val id: Long
    val closable: Boolean
    val text: String
    val actionButtonText: String?
}