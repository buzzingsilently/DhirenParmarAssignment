package com.buzzingsilently.dhirenparmarassignment.paging.factory

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.buzzingsilently.dhirenparmarassignment.model.Repository
import com.buzzingsilently.dhirenparmarassignment.paging.datasource.RepositoryDataSource

class RepositoryDataSourceFactory(
    private val horizontalPb: MutableLiveData<Boolean>,
    private val messageLiveData: MutableLiveData<String>
) : DataSource.Factory<Int, Repository>() {

    private var dataSource: RepositoryDataSource? = null
    private var searchKey: String = ""

    fun setSearchKey(searchKey: String) {
        this.searchKey = searchKey
    }

    override fun create(): DataSource<Int, Repository> {
        dataSource = RepositoryDataSource(searchKey, horizontalPb, messageLiveData)
        return dataSource!!
    }
}