package app.yota.domain.repository

import app.yota.domain.entity.AccountData
import io.reactivex.Single

interface IAccountRepository {
    fun getAccountData(): Single<AccountData>
}