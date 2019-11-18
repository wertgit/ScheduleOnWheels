package com.fate.data.di.managers

import android.content.Context
import com.fate.data.di.entities.EngineersResponce
import com.fate.data.di.networking.ApiService
import io.reactivex.Single

internal class AppDataManager(
    private val restApiHelper: ApiService
) : DataManager {

    override fun getEngineers(): Single<EngineersResponce> {
        return restApiHelper.getEngineers()
    }


}