package com.fate.data.data

import com.fate.data.data.managers.AppDataManager
import com.fate.data.data.managers.DataManager
import org.koin.dsl.module


val dataModule = module {

    single<DataManager> { AppDataManager(get()) }

}
