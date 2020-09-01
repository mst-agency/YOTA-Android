package app.yota.data.repository

import app.yota.domain.entity.Notification
import app.yota.domain.repository.INotificationsRepository
import io.reactivex.Single
import kotlin.random.Random

class FaceNotificationsRepository : INotificationsRepository {

    private val texts = arrayOf(
        "Подключить тариф можно в приложении или во время покупки SIM-карты в точке продаж. Нужно только выбрать количество минут, гигабайт, безлимитных приложений и SMS — столько, сколько нужно.",
        "Узнать свой тариф можно в приложении Yota на главном экране. В нем уже есть информация, сколько минут и Гб у вас осталось до конца тарифа.",
        "Выбрать новый тариф нужно заранее. Он начнет действовать, как только закончится срок действия текущего тарифа.",
        "Настройки → Сотовая связь → Сотовая сеть передачи данных → Режим модема. Вписать значение «internet.yota». После этого через несколько секунд, режим модема станет доступен в настройках устройства."
    )

    override fun getNotifications(): Single<List<Notification>> = Single.just(List(10) { index ->
        Notification(
            index.toLong(),
            Random.nextInt(1, 101),
            Random.nextInt(1, 4) == 1,
            texts[Random.nextInt(0, texts.size)],
            Random.nextInt(1, 4).takeIf { it != 1 }?.let {
                Notification.Button("Пополнить счет на 450 ₽", "yota//:payment")
            }
        )
    })
}