package com.buzzingsilently.dhirenparmarassignment.activity

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.buzzingsilently.dhirenparmarassignment.R
import com.buzzingsilently.dhirenparmarassignment.adapter.RepoAdapter
import com.buzzingsilently.dhirenparmarassignment.base.BaseActivity
import com.buzzingsilently.dhirenparmarassignment.base.BaseRecyclerAdapter
import com.buzzingsilently.dhirenparmarassignment.database.AppDatabase
import com.buzzingsilently.dhirenparmarassignment.model.Repository
import com.buzzingsilently.dhirenparmarassignment.utility.Prefs
import com.buzzingsilently.dhirenparmarassignment.utility.getTextValue
import com.buzzingsilently.dhirenparmarassignment.utility.hideKeyboard
import com.buzzingsilently.dhirenparmarassignment.viewmodel.RepoViewModel
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_repo_list.*
import kotlinx.android.synthetic.main.activity_repo_list.pbLoader
import kotlinx.android.synthetic.main.include_toolbar.*


class RepoListActivity : BaseActivity(), BaseRecyclerAdapter.RvItemClickListener<Repository>,
    TextView.OnEditorActionListener {

    companion object {
        fun getIntent(context: Context): Intent {
            return Intent(context, RepoListActivity::class.java)
        }
    }

    private lateinit var adapter: RepoAdapter
    private lateinit var viewModel: RepoViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_repo_list)
        tvToolbar.setText(R.string.title_repo_list)
        init()
    }

    override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            getRepoList(tietSearch.getTextValue())
            return true
        }
        return false;
    }

    override fun onItemClick(view: View?, position: Int) {
        if (adapter.currentList != null && adapter.currentList!![position] != null) {
            val dao = AppDatabase.getInstance(this).repoDao()
            adapter.currentList!![position]!!.isWatching =
                !adapter.currentList!![position]!!.isWatching
            adapter.notifyItemChanged(position, adapter.currentList!![position]!!)
            dao.insert(adapter.currentList!![position]!!)
        }

    }

    override fun onItemLongClick(view: View?, position: Int) {}

    override fun onClick(view: View) {
        super.onClick(view)

        when (view.id) {
            R.id.btnRetry -> clearSearch()
            R.id.ibToolbar -> {
                showSignOutDialog()
            }
        }
    }

    private fun init() {
        initView()
        setObserver()
    }

    private fun initView() {
        adapter = RepoAdapter(this, this)
        rvRepoList.layoutManager =
            LinearLayoutManager(this@RepoListActivity, RecyclerView.VERTICAL, false)
        rvRepoList.adapter = adapter

        swipeRefresh.setOnRefreshListener { clearSearch() }
        tilSearch.setEndIconOnClickListener { clearSearch() }
        tietSearch.setOnEditorActionListener(this)
    }

    private fun setObserver() {
        viewModel = ViewModelProviders.of(this).get(RepoViewModel::class.java)
        clearSearch()
    }

    private fun showSignOutDialog() {
        val dialogClickListener = DialogInterface.OnClickListener { dialog, which ->
            when (which) {
                DialogInterface.BUTTON_POSITIVE -> {
                    AppDatabase.getInstance(this).repoDao().clear()
                    Prefs.getInstance(application)!!.clear()
                    dialog.dismiss()
                    startActivityWithFinish(LoginActivity.getIntent(this))
                }
                DialogInterface.BUTTON_NEGATIVE -> {
                    dialog.dismiss()
                }
            }
        }

        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder
            .setTitle(R.string.title_signout)
            .setMessage(R.string.message_signout)
            .setPositiveButton(R.string.btn_signout_positive, dialogClickListener)
            .setNegativeButton(R.string.btn_signout_negative, dialogClickListener)
            .setCancelable(true)
            .show()
    }

    private fun attemptDataInsert(it: PagedList<Repository>?) {
        adapter.submitList(it)
        setSuccessState()
    }

    private fun clearSearch() {
        tietSearch.setText("")
        getRepoList("")
    }

    private fun getRepoList(searchKey: String) {
        tietSearch.hideKeyboard()

        if (hasNetwork()) {
            swipeRefresh.visibility = GONE
            llErrorContainer.visibility = GONE
            pbLoader.visibility = VISIBLE

            if (searchKey.isNotEmpty()) {
                if (viewModel.searchRepoListLiveData() != null) viewModel.searchRepoListLiveData()!!
                    .removeObserver(liveDataObserver)
                viewModel.searchRepoList(searchKey)
                viewModel.searchRepoListLiveData()!!.observeForever(liveDataObserver)
            } else {
                if (viewModel.localRepoListLiveData() != null) viewModel.localRepoListLiveData()!!
                    .removeObserver(liveDataObserver)
                viewModel.getLocalRepoList()
                viewModel.localRepoListLiveData()!!.observeForever(liveDataObserver)
            }
            viewModel.observeMessage().observe({ this.lifecycle }, {
                setErrorState(it)
            })
        }
    }

    private fun setSuccessState() {
        swipeRefresh.isRefreshing = false
        swipeRefresh.visibility = VISIBLE
        llErrorContainer.visibility = GONE
        pbLoader.visibility = GONE
    }

    private fun setErrorState(error: String) {
        tvErrorMsg.text = error
        swipeRefresh.visibility = GONE
        pbLoader.visibility = GONE
        llErrorContainer.visibility = VISIBLE
    }

    private val liveDataObserver = Observer<PagedList<Repository>> { attemptDataInsert(it) }
}
