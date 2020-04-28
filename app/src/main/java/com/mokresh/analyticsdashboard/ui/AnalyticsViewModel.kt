package com.mokresh.analyticsdashboard.ui

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mokresh.analyticsdashboard.R
import com.mokresh.analyticsdashboard.app.ApiServices
import com.mokresh.analyticsdashboard.models.Analytics
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable

class AnalyticsViewModel(
    private val service: ApiServices
) : ViewModel() {

    val analyticsResponseBody: MutableLiveData<Analytics> = MutableLiveData()
    val loadingVisibility: MutableLiveData<Int> = MutableLiveData()
    val errorMessage: MutableLiveData<Int> = MutableLiveData()


    private lateinit var subscription: Disposable

    fun getAnalytics(scope: String) {
        subscription = service.analytics(scope)
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { onRetrieveAnalyticsStart() }
            .subscribe(
                { result -> onRetrieveAnalyticsSuccess(result) },
                { onRetrieveAnalyticsError() }
            )
    }


    private fun onRetrieveAnalyticsStart() {
        loadingVisibility.value = View.VISIBLE
        errorMessage.value = null
    }


    private fun onRetrieveAnalyticsSuccess(analytics: Analytics) {
        loadingVisibility.value = View.GONE

        analyticsResponseBody.value = analytics

    }

    private fun onRetrieveAnalyticsError() {
        errorMessage.value = R.string.load_error
    }

}