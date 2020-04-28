package com.mokresh.analyticsdashboard.data

import com.mokresh.analyticsdashboard.app.ApiServices
import io.reactivex.disposables.CompositeDisposable

interface Repository {

    fun cleared()

    open class ListingRepositoryImpl(
        private val service: ApiServices,
        private val dao: Dao
    ) : Repository {

        private val compositeDisposable: CompositeDisposable = CompositeDisposable()

        // clear the  compositeDisposable after the view model cleared
        override fun cleared() {
            compositeDisposable.clear()
        }

    }


}