package com.buzzingsilently.dhirenparmarassignment.network.response

import com.buzzingsilently.dhirenparmarassignment.model.Repository
import com.google.gson.annotations.SerializedName

class SearchRepoListResponse(
    @SerializedName("total_count")
    val totalCount: Int = 0,
    @SerializedName("incomplete_results")
    val incompleteResults: Boolean = false,
    @SerializedName("items")
    val repoList: List<Repository>
)