package com.buzzingsilently.dhirenparmarassignment.activity

import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.buzzingsilently.dhirenparmarassignment.R
import com.buzzingsilently.dhirenparmarassignment.base.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*

class RepoListActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setView(R.layout.activity_main, R.string.title_trending)
        init()
    }

    private fun init() {
        initView()
    }

    private fun initView() {
        rvRepoList.apply {
            layoutManager = LinearLayoutManager(this@RepoListActivity).apply { orientation = RecyclerView.VERTICAL }
            itemAnimator = DefaultItemAnimator()
        }
    }


}
