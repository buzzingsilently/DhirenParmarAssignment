package com.buzzingsilently.dhirenparmarassignment.database.dao

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.buzzingsilently.dhirenparmarassignment.model.Repository

@Dao
interface RepositoryDao {

    @Query("Select * from repository")
    fun getRepoList() : DataSource.Factory<Int, Repository>

    @Query("select * from Repository where id=:id")
    fun getRepo(id: Int) : List<Repository>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(repository: Repository) : Long

    @Query("Delete from repository")
    fun clear()
}