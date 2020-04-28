package com.mokresh.analyticsdashboard.models

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(
    tableName = "entity"
)
data class Entity(

    @PrimaryKey(autoGenerate = true)
    val id: Int?
)