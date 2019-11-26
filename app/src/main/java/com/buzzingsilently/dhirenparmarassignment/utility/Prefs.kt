package com.buzzingsilently.dhirenparmarassignment.utility

import android.content.Context
import android.content.SharedPreferences
import com.buzzingsilently.dhirenparmarassignment.utility.AppConstant.SP_KEY_LAST_LOADED_DATE
import com.buzzingsilently.dhirenparmarassignment.utility.AppConstant.SP_NAME
import java.util.*

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

    var lastLoadedDate: Date
        set(value) = sharedPreferences!!.edit().putLong(SP_KEY_LAST_LOADED_DATE, value.time).apply()
        get() = Date(sharedPreferences!!.getLong(SP_KEY_LAST_LOADED_DATE, 0))
}