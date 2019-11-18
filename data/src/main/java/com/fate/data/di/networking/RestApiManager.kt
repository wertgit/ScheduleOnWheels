package com.fate.data.di.networking

import com.fate.data.di.entities.EngineersResponce
import io.reactivex.Single


class RestApiManager(private val apiService: ApiService) : ApiService {

    override fun getEngineers(): Single<EngineersResponce> {
       return apiService.getEngineers()
    }

}