package com.buzzingsilently.dhirenparmarassignment.paging

import android.content.Context
import androidx.lifecycle.MutableLiveData

// Extra params that can be used by boundary callback or datasource implementations
data class PagingExtras(
    val horizontalPb: MutableLiveData<Boolean>,
    val apiErrorMessage: MutableLiveData<String>,
    val internetError: String,
    val isForceUpdate: Boolean,
    val context: Context? = null
)