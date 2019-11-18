package com.fate.scheduleonwheels.di

import com.fate.scheduleonwheels.repo.EngineersRepository
import org.koin.dsl.module

val reposModule = module {
    single { EngineersRepository(get()) }
}