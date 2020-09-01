package app.yota.domain.repository

import app.yota.domain.entity.Profile
import io.reactivex.Single

interface IProfileRepository {
    fun getProfile(): Single<Profile>
}