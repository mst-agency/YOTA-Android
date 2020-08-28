package app.yota.data

import app.yota.domain.entity.AccountData
import app.yota.domain.repository.IAccountRepository
import io.reactivex.Single
import java.util.concurrent.TimeUnit

class FakeAccountRepository : IAccountRepository {
    override fun getAccountData(): Single<AccountData> {
        return Single.just(AccountData("4485141078446448", 1234.567f))
            .delay(900, TimeUnit.MILLISECONDS)
    }
}