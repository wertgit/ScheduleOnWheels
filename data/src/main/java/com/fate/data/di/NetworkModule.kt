package com.fate.data.di


import android.content.Context
import android.net.ConnectivityManager
import android.net.wifi.WifiManager
import com.fate.data.BuildConfig
import com.fate.data.di.networking.ApiService
import com.fate.data.di.networking.RestApiManager
import com.fate.data.di.utils.AppConstants
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.qualifier.StringQualifier
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.net.ssl.HttpsURLConnection
import javax.net.ssl.SSLSessionContext


val networkModule = module {

    /**
     * default Retrofit for the project
     * [StringQualifier] to specificy name for the definition to distinguish it.
     * This is the main app [Retrofit] resolved  by name default
     */
    single<Retrofit>(StringQualifier(AppConstants.API_RETROFIT_NAME)) {
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(get())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    single<OkHttpClient> {

        val httpClient = OkHttpClient().newBuilder()
            .connectTimeout(AppConstants.REQUEST_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(AppConstants.REQUEST_TIMEOUT, TimeUnit.SECONDS)
            .hostnameVerifier { hostname, session ->
                val hv = HttpsURLConnection.getDefaultHostnameVerifier()
                hv.verify(hostname, session)
            }
            .writeTimeout(AppConstants.REQUEST_TIMEOUT, TimeUnit.SECONDS)
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        httpClient.addInterceptor(interceptor)
        httpClient.build()

    }


    single {
        provideRaisdApiService(get(StringQualifier(AppConstants.API_RETROFIT_NAME)))
    }

    single { RestApiManager(get()) }

}

fun provideRaisdApiService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)
