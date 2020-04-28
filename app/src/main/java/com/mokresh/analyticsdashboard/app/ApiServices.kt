package com.mokresh.analyticsdashboard.app

import com.mokresh.analyticsdashboard.models.Analytics
import com.mokresh.analyticsdashboard.models.AnalyticsResponseBody
import com.mokresh.analyticsdashboard.models.Data
import com.mokresh.analyticsdashboard.models.Response
import com.mokresh.analyticsdashboard.utils.BaseSchedulers
import com.mokresh.analyticsdashboard.utils.Constants
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiServices {

    fun analytics(scope: String): Single<Analytics>

    class Network(
        private val retrofit: Retrofit,
        private val schedulers: BaseSchedulers
    ) : ApiServices {

        override fun analytics(scope: String): Single<Analytics> {
            return retrofit.create(NetworkCalls::class.java)
                .getAnalytics(scope)
                .subscribeOn(schedulers.io())
                .map { it.response.data.analytics }
        }

        // interface to define the API services
        interface NetworkCalls {

            @GET(Constants.ANALYTICS_API)
            fun getAnalytics(
                @Query("scope") scope: String
            ): Single<AnalyticsResponseBody<Response<Data<Analytics>>>>

        }

    }
}