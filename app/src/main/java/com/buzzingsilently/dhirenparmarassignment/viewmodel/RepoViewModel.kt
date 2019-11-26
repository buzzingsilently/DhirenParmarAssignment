package com.buzzingsilently.dhirenparmarassignment.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.buzzingsilently.dhirenparmarassignment.R
import com.buzzingsilently.dhirenparmarassignment.database.RepoDatabase
import com.buzzingsilently.dhirenparmarassignment.model.RepoModel
import com.buzzingsilently.dhirenparmarassignment.paging.PagingExtras
import com.buzzingsilently.dhirenparmarassignment.paging.RepoListBoundaryCallback
import com.buzzingsilently.dhirenparmarassignment.utility.AppConstant.PAGE_SIZE

class RepoViewModel(application: Application) :
    AndroidViewModel(application) {

    private var repoBoundaryCallback: RepoListBoundaryCallback? = null
    private val horizontalPb: MutableLiveData<Boolean> = MutableLiveData()
    private var messageLiveData: MutableLiveData<String> = MutableLiveData()
    private var repoListLiveData: LiveData<PagedList<RepoModel>>? = null

    fun getRepoList(isForceUpdate: Boolean) {
        if (repoListLiveData != null) {
            clearLiveData()
        }

        repoBoundaryCallback = RepoListBoundaryCallback(getPagingExtra(isForceUpdate))
        val config = PagedList.Config.Builder()
            .setPageSize(PAGE_SIZE)
            .setInitialLoadSizeHint(PAGE_SIZE * 2)
            .setEnablePlaceholders(true)
            .build()
        repoListLiveData = LivePagedListBuilder(
            RepoDatabase.getInstance(getApplication()).repoDao().getRepoList(),
            config
        )
            .setBoundaryCallback(repoBoundaryCallback)
            .build()
    }

    private fun getPagingExtra(isForceUpdate: Boolean): PagingExtras {
        return PagingExtras(
            horizontalPb,
            messageLiveData,
            getApplication<Application>().getString(
                R.string.error_no_connection
            ),
            isForceUpdate,
            getApplication()
        )
    }

    //livedata of pagedlist of RepoModel, coming from database
    fun getRepoListsLiveData(): LiveData<PagedList<RepoModel>>? {
        return repoListLiveData
    }

    //livedata of any error or message coming from api or database
    fun getMessageLiveData(): MutableLiveData<String> {
        return messageLiveData
    }

    private fun clearLiveData() {
        RepoDatabase.getInstance(getApplication()).repoDao().clear()
        repoListLiveData = null
    }
}