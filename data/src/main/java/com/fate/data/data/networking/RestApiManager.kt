package com.fate.data.data.networking

import com.fate.data.data.entities.EngineersResponce
import io.reactivex.Single


class RestApiManager(private val apiService: ApiService) : ApiService {

    override fun getEngineers(): Single<EngineersResponce> {
       return apiService.getEngineers()
    }

}