package com.fate.data.di.networking

import com.fate.data.di.entities.EngineersResponce
import io.reactivex.Single
import retrofit2.http.GET


interface ApiService {

    @GET("engineers")
    fun getEngineers(
    ): Single<EngineersResponce>

}