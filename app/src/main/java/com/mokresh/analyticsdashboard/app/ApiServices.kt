package com.mokresh.analyticsdashboard.app

import com.mokresh.analyticsdashboard.utils.BaseSchedulers
import retrofit2.Retrofit

interface ApiServices {


    class Network(
        private val retrofit: Retrofit,
        private val schedulers: BaseSchedulers
    ) : ApiServices {


        // interface to define the API services
        interface NetworkCalls {


        }

    }
}