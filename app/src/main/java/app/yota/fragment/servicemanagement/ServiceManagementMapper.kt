package app.yota.fragment.servicemanagement

import app.yota.domain.entity.AccountData
import app.yota.domain.entity.Notification
import app.yota.domain.entity.Profile
import app.yota.utils.DateFormatter
import javax.inject.Inject

class ServiceManagementMapper @Inject constructor(
    private val dateFormatter: DateFormatter
) {

    fun mapToAccountModel(accountData: AccountData) =
        ServiceManagementViewModel.AccountModel(
            accountData.cardNumber.takeLast(4).toInt(),
            accountData.money
        )

    fun mapToNotificationsModel(notifications: List<Notification>) =
        notifications.sortedBy { it.priority }.map {
            ServiceManagementViewModel.CarouselViewHolderModel(
                id = it.id,
                closable = it.closable,
                text = it.text,
                actionButtonText = it.button?.text,
                rawNotification = it
            )
        }.toMutableList()


    fun mapToProfileModel(profile: Profile) =
        ServiceManagementViewModel.ProfileModel(
            phoneNumber = profile.phoneNumber,
            totalAmount = profile.totalAmount,
            minutesAccumulator = ServiceManagementViewModel.ProfileModel.Accumulator(
                total = profile.minutesAccumulator.total,
                current = profile.minutesAccumulator.current
            ),
            gigabytesAccumulator = ServiceManagementViewModel.ProfileModel.Accumulator(
                total = profile.gigabytesAccumulator.total,
                current = profile.gigabytesAccumulator.current
            ),
            isCanChanged = profile.isCanChanged,
            nextPaymentDate = dateFormatter.nextPaymentDateFormat(profile.nextPaymentDate)
        )
}