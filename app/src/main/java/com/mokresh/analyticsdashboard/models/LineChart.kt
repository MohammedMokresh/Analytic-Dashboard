package com.mokresh.analyticsdashboard.models


import com.google.gson.annotations.SerializedName

data class LineChart(
    @SerializedName("chartType")
    val lineChartChartType: String,
    @SerializedName("description")
    val lineChartDescription: String,
    @SerializedName("items")
    val lineChartItemsList: List<LineChartItem>,
    @SerializedName("title")
    val lineChartTitle: String
)