package com.buzzingsilently.dhirenparmarassignment.utility

object AppConstant {

    //Api Constant
    const val BASE_URL : String = "https://api.github.com"
    const val SEARCH_REPO_LIST : String = "/search/repositories"

    //utility constant
    const val SPLASH_TIME : Long = 2000
    const val TIME_INTERVAL = 2*60*60 //two hour interval time
    const val PAGE_SIZE : Int = 5

    //shared pref constant
    const val SP_NAME : String = "DhirenPrefs"
    const val SP_KEY_IS_SIGNED_IN = "isSignedIn"
}