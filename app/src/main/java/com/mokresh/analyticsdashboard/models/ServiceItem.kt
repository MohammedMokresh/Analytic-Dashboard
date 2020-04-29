package com.mokresh.analyticsdashboard.models


import com.google.gson.annotations.SerializedName

data class ServiceItem(
    @SerializedName("description")
    val serviceItemDescription: String,
    @SerializedName("growth")
    val serviceItemGrowth: Int,
    @SerializedName("title")
    val serviceItemTitle: String,
    @SerializedName("total")
    val serviceItemTotal: Int?
)