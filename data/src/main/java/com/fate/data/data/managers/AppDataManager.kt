package com.fate.data.data.managers

import com.fate.data.data.entities.EngineersResponce
import com.fate.data.data.networking.ApiService
import io.reactivex.Single

internal class AppDataManager(
    private val restApiHelper: ApiService
) : DataManager {

    override fun getEngineers(): Single<EngineersResponce> {
        return restApiHelper.getEngineers()
    }


}