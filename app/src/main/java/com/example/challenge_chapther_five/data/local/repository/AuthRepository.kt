package com.example.challenge_chapther_five.data.local.repository

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.example.challenge_chapther_five.data.local.database.UserDao
import com.example.challenge_chapther_five.data.local.database.UserDatabase
import com.example.challenge_chapther_five.data.local.response.User
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class AuthRepository(application: Application) {
    private val executor: ExecutorService = Executors.newSingleThreadExecutor()
    private val mUserDao: UserDao

    init {
        val db = UserDatabase.getDatabase(application)
        mUserDao = db.userDao()
    }

    fun insert(user: User) {
        executor.execute { mUserDao.insert(user) }
    }

    fun getUser(email: String): LiveData<User> = liveData {
        val result: LiveData<User> = mUserDao.getUser(email)
        emitSource(result)
    }

    fun login(email: String, password: String): LiveData<User> {
        return mUserDao.login(email, password)
    }


    fun checkCredentials(email: String?, password: String?): Boolean =
        mUserDao.checkCredentials(email, password)
}