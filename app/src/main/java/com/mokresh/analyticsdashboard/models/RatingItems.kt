package com.mokresh.analyticsdashboard.models


import com.google.gson.annotations.SerializedName

data class RatingItems(
    @SerializedName("1")
    val oneStarRating: Int,
    @SerializedName("2")
    val twoStarRating: Int,
    @SerializedName("3")
    val threeStarRating: Int,
    @SerializedName("4")
    val fourStarRating: Int,
    @SerializedName("5")
    val fiveStarRating: Int
)