package com.fate.scheduleonwheels.di

import androidx.recyclerview.widget.LinearLayoutManager
import com.fate.scheduleonwheels.adapters.EngineersAdapter
import com.fate.scheduleonwheels.adapters.ScheduleAdapter
import com.fate.scheduleonwheels.viewmodels.ActivityViewModel
import com.fate.scheduleonwheels.viewmodels.EngineersViewModel
import com.fate.scheduleonwheels.viewmodels.ScheduleViewModel
import com.fate.scheduleonwheels.viewmodels.SharedViewModel
import io.reactivex.disposables.CompositeDisposable
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val appModule = module {

    single { CompositeDisposable() }
    single(named("CompositeDisposable")) { CompositeDisposable() }
    factory { LinearLayoutManager(get()) }

    /**
     * ViewModels
     */
    viewModel { ActivityViewModel(get()) }
    viewModel { EngineersViewModel(get()) }
    viewModel { ScheduleViewModel() }
    viewModel { SharedViewModel() }


    /**
     * Adapters
     */
    single { EngineersAdapter() }
    single { ScheduleAdapter() }

}


