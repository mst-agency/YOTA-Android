package app.yota.domain.repository

import app.yota.domain.entity.Notification
import io.reactivex.Single

interface INotificationsRepository {
    fun getNotifications(): Single<List<Notification>>
}