package com.buzzingsilently.dhirenparmarassignment.activity

import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.buzzingsilently.dhirenparmarassignment.R
import com.buzzingsilently.dhirenparmarassignment.adapter.RepoAdapter
import com.buzzingsilently.dhirenparmarassignment.base.BaseRecyclerAdapter
import com.buzzingsilently.dhirenparmarassignment.model.RepoModel
import com.buzzingsilently.dhirenparmarassignment.utility.Prefs
import com.buzzingsilently.dhirenparmarassignment.utility.Utility
import com.buzzingsilently.dhirenparmarassignment.utility.Utility.hasNetwork
import com.buzzingsilently.dhirenparmarassignment.utility.Utility.showSnackBar
import com.buzzingsilently.dhirenparmarassignment.viewmodel.RepoViewModel
import kotlinx.android.synthetic.main.activity_repo_list.*
import kotlinx.android.synthetic.main.include_toolbar.*

class RepoListActivity : AppCompatActivity(), BaseRecyclerAdapter.RvItemClickListener<RepoModel> {

    private lateinit var mAdapter: RepoAdapter
    private lateinit var mViewModel: RepoViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_repo_list)
        tvToolbar.setText(R.string.title_trending)
        init()
    }

    private fun init() {
        initView()
        setObserver()
    }

    private fun initView() {
        mAdapter = RepoAdapter(this, this)
        rvRepoList.layoutManager =
            LinearLayoutManager(this@RepoListActivity, RecyclerView.VERTICAL, false)
        rvRepoList.setHasFixedSize(true)
        rvRepoList.adapter = mAdapter
        swipeRefresh.setOnRefreshListener { getRepoList(true) }
    }

    private fun setObserver() {
        mViewModel = ViewModelProviders.of(this).get(RepoViewModel::class.java)

        getRepoList(Utility.isTimeDue(Prefs.getInstance(application)!!.lastLoadedDate))

        mViewModel.getRepoListsLiveData()?.observe({ this.lifecycle }, {
            mAdapter.submitList(it)
            setSuccessState()
        })

        mViewModel.getMessageLiveData().observe(this, Observer { setErrorState(it) })
    }

    private fun getRootView(): View {
        val contentViewGroup = findViewById<View>(android.R.id.content) as ViewGroup
        var rootView = contentViewGroup.getChildAt(0)
        if (rootView == null) rootView = window.decorView.rootView
        return rootView!!
    }

    private fun getRepoList(isForceUpdate: Boolean) {
        if (hasNetwork(this)) {
            swipeRefresh.visibility = GONE
            llErrorContainer.visibility = GONE
            pbLoader.visibility = VISIBLE

            mViewModel.getRepoList(isForceUpdate)
        } else {
            setErrorState(getString(R.string.error_no_connection))
        }
    }

    private fun setSuccessState() {
        swipeRefresh.isRefreshing = false
        swipeRefresh.visibility = VISIBLE
        llErrorContainer.visibility = GONE
        pbLoader.visibility = GONE
    }

    private fun setErrorState(message: String) {
        showSnackBar(getRootView(), message)
        swipeRefresh.visibility = GONE
        llErrorContainer.visibility = VISIBLE
        pbLoader.visibility = GONE
    }

    override fun onItemClick(view: View?, position: Int) {
        mAdapter.performClick(position)
    }

    override fun onItemLongClick(view: View?, position: Int) {
    }

    fun onClick(view: View) {
        when (view.id) {
            R.id.btnRetry -> getRepoList(true)
            R.id.ibToolbar  -> showSnackBar(getRootView(), getString(R.string.msg_coming_soon))
        }
    }
}
