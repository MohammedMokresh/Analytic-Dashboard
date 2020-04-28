package com.mokresh.analyticsdashboard.models


import com.google.gson.annotations.SerializedName

data class Rating(
    @SerializedName("avg")
    val ratingAvg: Int,
    @SerializedName("description")
    val ratingDescription: String,
    @SerializedName("items")
    val ratingItems: RatingItems,
    @SerializedName("title")
    val ratingTitle: String
)