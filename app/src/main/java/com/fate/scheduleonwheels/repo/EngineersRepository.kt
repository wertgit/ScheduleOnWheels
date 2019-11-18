package com.fate.scheduleonwheels.repo

import com.fate.data.di.entities.EngineersResponce
import com.fate.data.di.managers.DataManager

import io.reactivex.Single

/**
 *  [EngineersRepository] handles fetching engineers.
 *  [dataManager] injected by constructor using koin
 *
 */
class EngineersRepository(private val dataManager: DataManager) {

    fun getEngineers(): Single<EngineersResponce> {
        return dataManager.getEngineers()
    }
}