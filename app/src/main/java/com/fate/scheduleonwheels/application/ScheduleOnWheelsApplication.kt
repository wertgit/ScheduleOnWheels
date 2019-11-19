package com.fate.scheduleonwheels.application

import android.app.Application
import com.fate.data.di.dataModule
import com.fate.data.di.networkModule
import com.fate.scheduleonwheels.BuildConfig
import com.fate.scheduleonwheels.di.appModule
import com.fate.scheduleonwheels.di.reposModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import timber.log.Timber


class ScheduleOnWheelsApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin()
        startTimber()
    }


    private fun startTimber() {

        // Only runs during development and testing
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    /**
     * fun to initialize and start Koin for dependency Injection.
     */
    private fun startKoin() {

        startKoin {

            // use AndroidLogger as Koin Logger - default Level.INFO
            if (BuildConfig.DEBUG) {
                androidLogger()
            }

            // Android context
            androidContext(this@ScheduleOnWheelsApplication)

            // used modules declared
            modules(
                appModule,
                reposModule,
                dataModule,
                networkModule
            )
        }
    }


}