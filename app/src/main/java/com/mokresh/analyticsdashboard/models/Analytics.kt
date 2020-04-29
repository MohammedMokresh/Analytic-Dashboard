package com.mokresh.analyticsdashboard.models


import com.google.gson.annotations.SerializedName



data class Analytics(
    @SerializedName("job")
    val job: Job?,
    @SerializedName("lineCharts")
    val lineCharts: List<List<LineChart>>?,
    @SerializedName("pieCharts")
    val pieCharts: List<PieChart>?,
    @SerializedName("rating")
    val rating: Rating?,
    @SerializedName("service")
    val service: Service?
)