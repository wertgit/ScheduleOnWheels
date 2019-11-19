package com.fate.data.data.networking

import com.fate.data.data.entities.EngineersResponce
import io.reactivex.Single
import retrofit2.http.GET


interface ApiService {

    @GET("engineers")
    fun getEngineers(
    ): Single<EngineersResponce>

}