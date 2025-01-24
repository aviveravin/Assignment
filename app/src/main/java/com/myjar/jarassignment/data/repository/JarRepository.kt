package com.myjar.jarassignment.data.repository

import android.util.Log
import com.myjar.jarassignment.data.api.ApiService
import com.myjar.jarassignment.data.local.CachedItemDao
import com.myjar.jarassignment.data.local.entities.CachedItem
import com.myjar.jarassignment.data.model.ComputerItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

interface JarRepository {
    suspend fun fetchResults(): Flow<List<ComputerItem>>
}

class JarRepositoryImpl(
    private val apiService: ApiService,
    private val cachedItemDao: CachedItemDao
) : JarRepository {

    override suspend fun fetchResults(): Flow<List<ComputerItem>> = flow {
        try {
            val response = apiService.fetchResults()
            val items = response.mapNotNull { item ->
                item.data?.cpuModel?.let {
                    CachedItem(id = item.id, name = item.name, data = it)
                }
            }
            cachedItemDao.clearCache()
            cachedItemDao.insertItems(items)
            emit(response)
        } catch (e: Exception) {
            Log.e("JarRepository", "Error fetching results: ${e.message}")
            emit(emptyList()) // Emit empty list in case of error
        }
    }
}
