package com.mokresh.analyticsdashboard.models


import com.google.gson.annotations.SerializedName

data class Job(
    @SerializedName("description")
    val jobDescription: String,
    @SerializedName("items")
    val jobItemsList: List<JobItem>,
    @SerializedName("title")
    val jobTitle: String
)