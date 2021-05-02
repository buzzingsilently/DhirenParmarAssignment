package com.buzzingsilently.dhirenparmarassignment.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.buzzingsilently.dhirenparmarassignment.database.converter.OwnerConverter
import com.buzzingsilently.dhirenparmarassignment.database.dao.RepositoryDao
import com.buzzingsilently.dhirenparmarassignment.database.dao.UserDao
import com.buzzingsilently.dhirenparmarassignment.model.Repository
import com.buzzingsilently.dhirenparmarassignment.model.User

@Database(
    entities = [Repository::class, User::class],
    exportSchema= false,
    version = 1
)
@TypeConverters(value = [OwnerConverter::class])
abstract class AppDatabase : RoomDatabase() {

    companion object {
        private const val DB_NAME: String = "app_db"
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            if (instance == null) {
                synchronized(AppDatabase::class.java) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        DB_NAME
                    )
                        .allowMainThreadQueries()
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }
            return instance!!
        }
    }

    abstract fun userDao(): UserDao

    abstract fun repoDao(): RepositoryDao
}