package com.buzzingsilently.dhirenparmarassignment.base

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import com.buzzingsilently.dhirenparmarassignment.R
import kotlinx.android.synthetic.main.activity_base.*
import kotlinx.android.synthetic.main.include_toolbar.*

@SuppressLint("Registered")
open class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)
    }

    protected fun setView(@LayoutRes layout: Int, @StringRes title: Int) {
        LayoutInflater.from(this).inflate(layout, llContainer, false)
        tvToolbar.setText(title)
    }
}