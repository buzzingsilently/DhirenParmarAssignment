package com.buzzingsilently.dhirenparmarassignment.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.buzzingsilently.dhirenparmarassignment.model.RepoModel

@Database(
    entities = [RepoModel::class],
    exportSchema = false,
    version = 1
)
@TypeConverters(value = [(BuiltByTypeConverter::class)])
abstract class RepoDatabase : RoomDatabase() {

    companion object {
        private const val DB_NAME: String = "repo_db"
        private var instance: RepoDatabase? = null

        fun getInstance(context: Context): RepoDatabase {
            if (instance == null) {
                synchronized(RepoDatabase::class.java) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        RepoDatabase::class.java,
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

    abstract fun repoDao(): RepoDao
}