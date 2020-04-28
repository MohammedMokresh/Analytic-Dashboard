package com.mokresh.analyticsdashboard.models


import com.google.gson.annotations.SerializedName

data class LineChartItem(
    @SerializedName("key")
    val lineChartItemKey: String,
    @SerializedName("value")
    val lineChartItemValue: List<Value>
)