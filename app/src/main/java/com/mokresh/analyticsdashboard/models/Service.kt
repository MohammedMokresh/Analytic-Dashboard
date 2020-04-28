package com.mokresh.analyticsdashboard.models


import com.google.gson.annotations.SerializedName

data class Service(
    @SerializedName("description")
    val serviceDescription: String,
    @SerializedName("items")
    val serviceItemsList: List<ServiceItem>,
    @SerializedName("title")
    val serviceTitle: String
)