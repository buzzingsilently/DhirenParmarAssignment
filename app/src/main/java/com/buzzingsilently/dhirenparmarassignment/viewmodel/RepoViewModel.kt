package com.buzzingsilently.dhirenparmarassignment.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.buzzingsilently.dhirenparmarassignment.base.BaseViewModel
import com.buzzingsilently.dhirenparmarassignment.database.AppDatabase
import com.buzzingsilently.dhirenparmarassignment.model.Repository
import com.buzzingsilently.dhirenparmarassignment.paging.factory.RepositoryDataSourceFactory
import com.buzzingsilently.dhirenparmarassignment.utility.AppConstant.PAGE_SIZE

class RepoViewModel(application: Application) :
    BaseViewModel(application) {

    private val horizontalPb: MutableLiveData<Boolean> = MutableLiveData()
    private val networkDataSourceFactory =
        RepositoryDataSourceFactory(horizontalPb, messageLiveData)
    private var localDataSourceFactory =
        AppDatabase.getInstance(getApplication()).repoDao().getRepoList()
    private var networkListLiveData: LiveData<PagedList<Repository>>?
    private var localListLiveData: LiveData<PagedList<Repository>>?

    init {
        val config = PagedList.Config.Builder()
            .setPageSize(PAGE_SIZE)
            .setInitialLoadSizeHint(PAGE_SIZE * 2)
            .setEnablePlaceholders(false)
            .build()

        networkListLiveData = LivePagedListBuilder(networkDataSourceFactory, config).build()
        localListLiveData = LivePagedListBuilder(localDataSourceFactory, config).build()
    }

    fun searchRepoList(searchKey: String) {
        if (networkListLiveData != null && networkListLiveData!!.value != null && searchKey.isNotEmpty()) {
            networkDataSourceFactory.setSearchKey(searchKey)
            networkListLiveData!!.value!!.dataSource.invalidate()
        }
    }

    fun getLocalRepoList() {
        if (localListLiveData != null && localListLiveData!!.value != null) {
            localListLiveData!!.value!!.dataSource.invalidate()
        }
    }

    //livedata of pagedlist of Repository, coming from database
    fun searchRepoListLiveData(): LiveData<PagedList<Repository>>? {
        return networkListLiveData
    }

    //livedata of pagedlist of Repository, coming from database
    fun localRepoListLiveData(): LiveData<PagedList<Repository>>? {
        return localListLiveData
    }
}