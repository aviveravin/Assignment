package com.myjar.jarassignment.data.repository

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import com.myjar.jarassignment.NetWorkUtils
import com.myjar.jarassignment.data.api.ApiService
import com.myjar.jarassignment.data.local.CachedItemDao
import com.myjar.jarassignment.data.local.entities.CachedItem
import com.myjar.jarassignment.data.model.ComputerItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

interface JarRepository {
    suspend fun fetchResults(isOnline: Boolean): Flow<List<ComputerItem>>
}

class JarRepositoryImpl(
    private val apiService: ApiService,
    private val cachedItemDao: CachedItemDao
) : JarRepository {
    override suspend fun fetchResults(isOnline: Boolean): Flow<List<ComputerItem>> = flow {
        if (isOnline) {
            // Fetch from API and cache it
            val items = apiService.fetchResults()
            cachedItemDao.insertItems(items)
            emit(items)
        } else {
            // Fetch from local cache
            val cachedItems = cachedItemDao.getCachedItems()
            emit(cachedItems)
        }
    }
}



