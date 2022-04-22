package com.example.challenge_chapther_five.data.local.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.challenge_chapther_five.data.local.response.User

@Dao
interface UserDao {
    @Insert
    fun insert(user: User)

    @Update
    fun update(user: User)

    @Query("SELECT * FROM user WHERE email = :email LIMIT 1")
    fun getUser(email: String): LiveData<User>

    @Query("SELECT * FROM user WHERE email = :email AND password = :password")
    fun login(email: String, password: String): LiveData<User>

    @Query("SELECT EXISTS(SELECT * FROM user WHERE email = :email AND password = :password LIMIT 1)")
    fun checkCredentials(email: String?, password: String?): Boolean
}