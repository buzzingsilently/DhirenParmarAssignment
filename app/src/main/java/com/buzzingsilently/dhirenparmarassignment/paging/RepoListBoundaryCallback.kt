package com.buzzingsilently.dhirenparmarassignment.paging

import androidx.paging.PagedList.BoundaryCallback
import com.buzzingsilently.dhirenparmarassignment.database.RepoDatabase
import com.buzzingsilently.dhirenparmarassignment.model.RepoModel
import com.buzzingsilently.dhirenparmarassignment.network.ApiService
import com.buzzingsilently.dhirenparmarassignment.utility.Prefs
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.net.UnknownHostException
import java.util.*

class RepoListBoundaryCallback(pagingExtra: PagingExtras) : BoundaryCallback<RepoModel>() {

    private val context = pagingExtra.context
    private var subscription: Disposable? = null
    private val horizontalPb = pagingExtra.horizontalPb
    private val apiErrorMessage = pagingExtra.apiErrorMessage
    private val internalError = pagingExtra.internetError

    override fun onZeroItemsLoaded() {
        super.onZeroItemsLoaded()
        callApi()
    }

    private fun callApi() {
        Prefs.getInstance(context!!)!!.lastLoadedDate = Calendar.getInstance().time

        subscription = ApiService.getService().
            getRepoList()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .doOnSubscribe { horizontalPb.postValue(true) }
            .doOnTerminate { horizontalPb.postValue(false) }
            .subscribe({ handleRequestListResponse(it) }, this::handleRequestListError)
    }

    private fun handleRequestListResponse(response: List<RepoModel>) {
        if (!response.isNullOrEmpty()) {
            RepoDatabase.getInstance(context!!).repoDao().setRepoList(response)
        }
    }

    private fun handleRequestListError(error: Throwable) {
        if (error is UnknownHostException) {
            apiErrorMessage.value = internalError
        } else {
            apiErrorMessage.value = error.localizedMessage
        }
    }
}