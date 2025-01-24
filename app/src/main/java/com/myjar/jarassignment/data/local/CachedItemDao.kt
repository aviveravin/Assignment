package com.myjar.jarassignment.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.myjar.jarassignment.data.local.entities.CachedItem

@Dao
interface CachedItemDao {
    @Query("SELECT * FROM cached_items")
    suspend fun getCachedItems() : List<CachedItem>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItems(items: List<CachedItem?>)

    @Query("DELETE FROM cached_items")
    suspend fun clearCache()
}