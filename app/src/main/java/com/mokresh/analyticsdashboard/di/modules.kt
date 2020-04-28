package com.mokresh.analyticsdashboard.di

import com.mokresh.analyticsdashboard.app.ApiServices
import com.mokresh.analyticsdashboard.ui.AnalyticsViewModel
import com.mokresh.analyticsdashboard.utils.BaseSchedulers
import com.mokresh.analyticsdashboard.utils.Constants
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


val appModule by lazy {
    module {
        single<BaseSchedulers> { BaseSchedulers.BaseSchedulersImpl() }
    }
}


val viewModelModule by lazy {
    module {
        viewModel {
            AnalyticsViewModel(get())
        }
    }
}


val serviceModule by lazy {

    module {
        single<ApiServices> { ApiServices.Network(get(), get()) }
        single<Retrofit> {
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            val client = OkHttpClient.Builder().addInterceptor(interceptor)
                .build()

            Retrofit.Builder()
                .baseUrl(Constants.API_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
        }
    }
}