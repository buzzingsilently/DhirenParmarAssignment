package com.buzzingsilently.dhirenparmarassignment.paging.datasource

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.buzzingsilently.dhirenparmarassignment.model.Repository
import com.buzzingsilently.dhirenparmarassignment.network.ApiService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.HttpException

open class RepositoryDataSource(
    private val searchKey: String,
    private val horizontalPb: MutableLiveData<Boolean>,
    private val messageLiveData: MutableLiveData<String>
) : PageKeyedDataSource<Int, Repository>() {

    private var totalCount = 0
    private var currentCount: Int = 0

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Repository>
    ) {
        if(searchKey.isEmpty()) return

        val subscriber = ApiService.getService()
            .searchRepoList(searchKey, 0, params.requestedLoadSize)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .doOnSubscribe { horizontalPb.postValue(true) }
            .doOnTerminate { horizontalPb.postValue(false) }
            .subscribe({
                if (it == null || it.totalCount <= 0) {
                    messageLiveData.postValue("Something went wrong, couldn't find repositories!")
                } else {
                    callback.onResult(it.repoList, 0, it.totalCount, null, 1)
                    totalCount = it.totalCount
                    currentCount += it.repoList.size
                }
            }, {
                if (it is HttpException) {
                    if (it.code() == 422)  {
                        messageLiveData.postValue("Please sanitize your search query!")
                        return@subscribe
                    }
                }
                messageLiveData.postValue("Something went wrong, couldn't find repositories!")
            })
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Repository>) {

    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Repository>) {
        if(searchKey.isEmpty()) return

        val subscriber = ApiService.getService()
            .searchRepoList(searchKey, params.key, params.requestedLoadSize)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .doOnSubscribe { horizontalPb.postValue(true) }
            .doOnTerminate { horizontalPb.postValue(false) }
            .subscribe({
                if (it == null || it.totalCount <= 0) {
                    messageLiveData.postValue("Something went wrong, couldn't find repositories!")
                } else {
                    callback.onResult(it.repoList, params.key + 1)
                    currentCount += it.repoList.size
                }
            }, {
                messageLiveData.postValue("Something went wrong, couldn't find repositories!")
            })
    }
}