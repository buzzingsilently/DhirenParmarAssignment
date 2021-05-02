package com.buzzingsilently.dhirenparmarassignment.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.buzzingsilently.dhirenparmarassignment.R
import com.buzzingsilently.dhirenparmarassignment.base.BaseViewModel
import com.buzzingsilently.dhirenparmarassignment.database.AppDatabase
import com.buzzingsilently.dhirenparmarassignment.model.User
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class UserViewModel(application: Application) :
    BaseViewModel(application) {

    private val database = AppDatabase.getInstance(getApplication())

    private val signUpLiveData = MutableLiveData<Boolean>()
    private val signInLiveData = MutableLiveData<Boolean>()

    fun observeSignUp(): LiveData<Boolean> = signUpLiveData
    fun observeSignIn(): LiveData<Boolean> = signInLiveData

    fun signUp(fullName: String, email: String, password: String) {
        messageLiveData.postValue(getApplication<Application>().getString(R.string.load_sign_up))

        val disposable = database.userDao().insert(User(fullName, email, password))
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                signUpLiveData.postValue(it != null)
                emptyMessageLiveData()
            }, {
                signUpLiveData.postValue(false)
                emptyMessageLiveData()
            })
        compositeDisposable.add(disposable)
    }

    fun signIn(email: String, password: String) {
        messageLiveData.postValue(getApplication<Application>().getString(R.string.load_sign_in))

        val disposable = database.userDao().get(email, password)
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                signInLiveData.postValue(it != null && it.isNotEmpty())
                emptyMessageLiveData()
            }, {
                signInLiveData.postValue(false)
                emptyMessageLiveData()
            })
        compositeDisposable.add(disposable)
    }
}