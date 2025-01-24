package com.myjar.jarassignment.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cached_items")
data class CachedItem(
    @PrimaryKey val id: String,
    val name: String,
    val data: String
)
