package com.mokresh.analyticsdashboard.models


import com.google.gson.annotations.SerializedName

data class Data<T>(
    @SerializedName("analytics")
    val analytics: T
)