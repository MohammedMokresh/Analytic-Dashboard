package com.mokresh.analyticsdashboard.models


import com.google.gson.annotations.SerializedName

data class Value(
    @SerializedName("key")
    val key: String,
    @SerializedName("value")
    val value: Int
)