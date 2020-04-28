package com.mokresh.analyticsdashboard.models


import com.google.gson.annotations.SerializedName

data class PieChartItem(
    @SerializedName("key")
    val pieChartItemKey: String,
    @SerializedName("value")
    val pieChartItemValue: Double
)