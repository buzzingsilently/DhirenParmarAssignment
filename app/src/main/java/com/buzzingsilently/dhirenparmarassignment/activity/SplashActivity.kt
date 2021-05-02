package com.buzzingsilently.dhirenparmarassignment.activity

import android.os.Bundle
import android.os.Handler
import com.buzzingsilently.dhirenparmarassignment.R
import com.buzzingsilently.dhirenparmarassignment.base.BaseActivity
import com.buzzingsilently.dhirenparmarassignment.utility.AppConstant
import com.buzzingsilently.dhirenparmarassignment.utility.Prefs

class SplashActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        init()
    }

    private fun init() {
        Handler().postDelayed({
            if (Prefs.getInstance(this)!!.isSignedIn) {
                startActivityWithFinish(RepoListActivity.getIntent(this))
            } else {
                startActivityWithFinish(LoginActivity.getIntent(this))
            }
        }, AppConstant.SPLASH_TIME)
    }
}