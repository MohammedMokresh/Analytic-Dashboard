package com.mokresh.analyticsdashboard.app

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.mokresh.analyticsdashboard.data.Dao
import com.mokresh.analyticsdashboard.models.Entity
import com.mokresh.analyticsdashboard.utils.Constants

@Database(
    entities = [Entity::class],
    version = 1,
    exportSchema = false
)

abstract class AppDatabase : RoomDatabase() {

    abstract fun dao(): Dao

    companion object {


        @Volatile
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, Constants.LOCAL_DB_NAME)
                .fallbackToDestructiveMigration()
                .build()
        }
    }
}