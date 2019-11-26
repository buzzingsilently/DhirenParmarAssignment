package com.buzzingsilently.dhirenparmarassignment.network

import com.buzzingsilently.dhirenparmarassignment.model.RepoModel
import com.buzzingsilently.dhirenparmarassignment.utility.AppConstant.BASE_URL
import com.buzzingsilently.dhirenparmarassignment.utility.AppConstant.REPO_LIST
import io.reactivex.Observable
import io.reactivex.Single
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface ApiService {

    @GET(REPO_LIST)
    fun getRepoList(): Observable<List<RepoModel>>

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