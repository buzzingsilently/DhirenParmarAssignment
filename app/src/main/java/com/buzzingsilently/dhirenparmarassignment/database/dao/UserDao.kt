package com.buzzingsilently.dhirenparmarassignment.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.buzzingsilently.dhirenparmarassignment.model.User
import io.reactivex.Maybe

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user: User) : Maybe<Long>

    @Query("Select * from user where email=:email AND password=:password")
    fun get(email: String, password: String) : Maybe<List<User>>
}