package com.mokresh.analyticsdashboard.models


import com.google.gson.annotations.SerializedName

data class JobItem(
    @SerializedName("description")
    val jobItemDescription: String,
    @SerializedName("growth")
    val jobItemGrowth: Int,
    @SerializedName("title")
    val jobItemTitle: String,
    @SerializedName("total")
    val jobItemTotal: Int
)