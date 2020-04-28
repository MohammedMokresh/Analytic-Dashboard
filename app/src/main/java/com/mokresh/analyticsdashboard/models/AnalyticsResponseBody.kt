package com.mokresh.analyticsdashboard.models


import com.google.gson.annotations.SerializedName

data class AnalyticsResponseBody<T>(
    @SerializedName("httpStatus")
    val httpStatus: Int,
    @SerializedName("response")
    val response: T
)