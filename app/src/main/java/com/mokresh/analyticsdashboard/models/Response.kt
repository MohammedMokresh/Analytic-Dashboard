package com.mokresh.analyticsdashboard.models


import com.google.gson.annotations.SerializedName

data class Response<T>(
    @SerializedName("data")
    val `data`: T,
    @SerializedName("message")
    val message: String
)