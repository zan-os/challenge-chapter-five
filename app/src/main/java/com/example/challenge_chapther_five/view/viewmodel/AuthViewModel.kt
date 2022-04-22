package com.example.challenge_chapther_five.view.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.challenge_chapther_five.data.local.repository.AuthRepository
import com.example.challenge_chapther_five.data.local.response.User
import kotlinx.coroutines.launch

class AuthViewModel(application: Application) : ViewModel() {
    private val mAuthRepository: AuthRepository = AuthRepository(application)
    private val _result = MutableLiveData<User>()
    val result: LiveData<User> = _result
    private var isValid = false

    fun insert(username: String, email: String, password: String) {
        val user = User(
            username = username,
            password = password,
            email = email
        )
        mAuthRepository.insert(user)
    }

    fun login(email: String, password: String): LiveData<User> {
        viewModelScope.launch {
            _result.value = mAuthRepository.login(email, password).value
        }
        return _result
    }

    fun checkCridential(email: String?, password: String?): Boolean {
        viewModelScope.launch {
            isValid = mAuthRepository.checkCredentials(email, password)
        }
        return isValid
    }

}