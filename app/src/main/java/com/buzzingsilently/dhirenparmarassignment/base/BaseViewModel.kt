package com.buzzingsilently.dhirenparmarassignment.base

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.disposables.CompositeDisposable




abstract class BaseViewModel(application: Application) :
    AndroidViewModel(application) {

    var compositeDisposable = CompositeDisposable()
    protected var messageLiveData: MutableLiveData<String> = MutableLiveData()

    //livedata of any error or message coming from api or database
    open fun observeMessage(): LiveData<String> {
        return messageLiveData
    }

    fun emptyMessageLiveData() {
        messageLiveData.postValue("")
    }

    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }
}