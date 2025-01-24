package com.myjar.jarassignment.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.myjar.jarassignment.data.local.entities.CachedItem

@Database(entities = [CachedItem::class], version = 1)
abstract class AppDatabase: RoomDatabase() {

    abstract fun cachedItemDao(): CachedItemDao

    companion object {
        @Volatile
        private  var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this){
                instance ?: Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                ).build().also { instance = it }
            }
        }
    }
}