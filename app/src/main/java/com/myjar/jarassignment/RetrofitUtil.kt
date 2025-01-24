package com.myjar.jarassignment

import android.content.Context
import com.myjar.jarassignment.data.api.ApiService
import com.myjar.jarassignment.data.local.AppDatabase
import com.myjar.jarassignment.data.local.CachedItemDao
import com.myjar.jarassignment.data.repository.JarRepository
import com.myjar.jarassignment.data.repository.JarRepositoryImpl
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object AppModule {
    private lateinit var appDatabase: AppDatabase
    private lateinit var cachedItemDao: CachedItemDao
    private lateinit var networkUtils: NetWorkUtils

    private val apiService: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl("https://api.restful-api.dev")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

    fun initialize(context: Context) {
        appDatabase = AppDatabase.getInstance(context)
        cachedItemDao = appDatabase.cachedItemDao()
        networkUtils = NetWorkUtils
    }

    fun provideApiService(): ApiService = apiService

    fun provideCachedItemDao(): CachedItemDao {
        if (!this::cachedItemDao.isInitialized) {
            throw IllegalStateException("AppModule is not initialized. Call initialize() first.")
        }
        return cachedItemDao
    }

    fun provideNetworkUtils(): NetWorkUtils {
        if (!this::networkUtils.isInitialized) {
            throw IllegalStateException("AppModule is not initialized. Call initialize() first.")
        }
        return networkUtils
    }

    fun provideItemRepository(): JarRepository {
        return JarRepositoryImpl(provideApiService(), provideCachedItemDao())
    }
}





