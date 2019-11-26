package com.buzzingsilently.dhirenparmarassignment.database

import androidx.paging.DataSource
import androidx.room.*
import com.buzzingsilently.dhirenparmarassignment.model.RepoModel

@Dao
interface RepoDao {

    @Query("Select * from repository")
    fun getRepoList() : DataSource.Factory<Int, RepoModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun setRepoList(list: List<RepoModel>)


    @Query("Delete from repository")
    fun clear()
}