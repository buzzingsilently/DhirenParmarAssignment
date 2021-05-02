package com.buzzingsilently.dhirenparmarassignment.base

import android.content.Intent
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.buzzingsilently.dhirenparmarassignment.utility.Utility
import com.buzzingsilently.dhirenparmarassignment.utility.hideKeyboard

abstract class BaseActivity: AppCompatActivity() {

    fun hasNetwork() = Utility.hasNetwork(getRootView(), this)

    fun showSnackBar(message: String) = Utility.showSnackBar(getRootView(), message)

    private fun getRootView(): View {
        val contentViewGroup = findViewById<View>(android.R.id.content) as ViewGroup
        var rootView = contentViewGroup.getChildAt(0)
        if (rootView == null) rootView = window.decorView.rootView
        return rootView!!
    }

    fun startActivityWithFinish(intent: Intent) {
        startActivity(intent)
        finish()
    }

    open fun onClick(view: View) {
        view.hideKeyboard()
    }
}