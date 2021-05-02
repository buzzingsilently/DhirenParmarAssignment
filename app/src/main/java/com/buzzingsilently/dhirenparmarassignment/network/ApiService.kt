package com.buzzingsilently.dhirenparmarassignment.network

import com.buzzingsilently.dhirenparmarassignment.network.response.SearchRepoListResponse
import com.buzzingsilently.dhirenparmarassignment.utility.AppConstant.BASE_URL
import com.buzzingsilently.dhirenparmarassignment.utility.AppConstant.SEARCH_REPO_LIST
import io.reactivex.Observable
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET(SEARCH_REPO_LIST)
    fun searchRepoList(@Query("q") searchKey: String, @Query("page") page: Int, @Query("per_page") perPage: Int): Observable<SearchRepoListResponse>

    companion object {
        fun getService(): ApiService {
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            val client = OkHttpClient.Builder().addInterceptor(interceptor).build()

            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return retrofit.create(ApiService::class.java)
        }
    }
}