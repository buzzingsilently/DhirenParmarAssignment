package com.buzzingsilently.dhirenparmarassignment.utility

import android.content.Context
import android.content.SharedPreferences
import com.buzzingsilently.dhirenparmarassignment.utility.AppConstant.SP_KEY_IS_SIGNED_IN
import com.buzzingsilently.dhirenparmarassignment.utility.AppConstant.SP_NAME

class Prefs(context: Context) {

    companion object {
         private var prefs: Prefs? = null

        fun getInstance(context: Context): Prefs? {
            prefs = if (prefs != null) prefs else Prefs(context)
            return prefs
        }
    }

    private var sharedPreferences: SharedPreferences? = null

    init {
        sharedPreferences = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)
    }

    fun clear() {
        sharedPreferences!!.edit().clear().apply()
    }

    var isSignedIn: Boolean
        set(value) = sharedPreferences!!.edit().putBoolean(SP_KEY_IS_SIGNED_IN, value).apply()
        get() = sharedPreferences!!.getBoolean(SP_KEY_IS_SIGNED_IN, false)
}