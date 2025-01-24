package com.myjar.jarassignment.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.myjar.jarassignment.data.local.entities.CachedItem
import com.myjar.jarassignment.data.model.ComputerItem

@Dao
interface CachedItemDao {
    @Query("SELECT * FROM cached_items")
    suspend fun getCachedItems() : List<ComputerItem>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItems(items: List<ComputerItem?>)

    @Query("DELETE FROM cached_items")
    suspend fun clearCache()
}