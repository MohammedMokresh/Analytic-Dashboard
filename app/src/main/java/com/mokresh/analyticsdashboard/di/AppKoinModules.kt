package com.mokresh.analyticsdashboard.di

import org.koin.core.module.Module

class AppKoinModules {

    companion object {
        fun getModules(): List<Module> {
            return mutableListOf(
                appModule,
                viewModelModule,
                serviceModule
            )
        }
    }
}