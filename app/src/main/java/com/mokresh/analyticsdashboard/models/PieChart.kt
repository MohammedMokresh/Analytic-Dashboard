package com.mokresh.analyticsdashboard.models


import com.google.gson.annotations.SerializedName

data class PieChart(
    @SerializedName("chartType")
    val pieChartChartType: String,
    @SerializedName("description")
    val pieChartDescription: String,
    @SerializedName("items")
    val pieChartItemsList: List<PieChartItem>,
    @SerializedName("title")
    val pieChartTitle: String
)