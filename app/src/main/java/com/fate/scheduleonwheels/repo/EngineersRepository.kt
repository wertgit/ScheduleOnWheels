package com.fate.scheduleonwheels.repo

import com.fate.data.data.entities.EngineersResponce
import com.fate.data.data.managers.DataManager

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