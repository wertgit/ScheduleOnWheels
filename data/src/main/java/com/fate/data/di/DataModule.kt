package com.fate.data.di

import android.app.Application
import android.content.SharedPreferences
import androidx.room.Room
import com.fate.data.di.managers.AppDataManager
import com.fate.data.di.managers.DataManager
import com.google.gson.Gson
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module
import java.util.concurrent.Executors


val dataModule = module {

    single<DataManager> { AppDataManager(get()) }

}
