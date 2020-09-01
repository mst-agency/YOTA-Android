package app.yota.data.repository

import app.yota.domain.entity.Profile
import app.yota.domain.repository.IProfileRepository
import io.reactivex.Single

class FakeProfileRepository : IProfileRepository {
    override fun getProfile(): Single<Profile> {
        return Single.just(
            Profile(
                phoneNumber = "79453423133",
                isCanChanged = true,
                gigabytesAccumulator = Profile.Accumulator(
                    total = 10,
                    current = 7
                ),
                minutesAccumulator = Profile.Accumulator(
                    total = 1000,
                    current = 750
                ),
                totalAmount = 450f,
                nextPaymentDate = "23.01.2021"
            )
        )
    }

}