package com.buzzingsilently.dhirenparmarassignment.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.buzzingsilently.dhirenparmarassignment.R
import com.buzzingsilently.dhirenparmarassignment.base.BaseRecyclerAdapter
import com.buzzingsilently.dhirenparmarassignment.model.Repository
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_repo.view.*

class RepoAdapter(
    private var context: Context,
    listener: RvItemClickListener<Repository>
) :
    BaseRecyclerAdapter<Repository, RepoAdapter.RepoViewHolder>(Repository.DIFF_CALLBACK, listener) {

    //stores last clicked item index
    private var mLastExpandedIndex: Int = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_repo, parent, false)
        return RepoViewHolder(view)
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.bind(getItem(position)!!)
    }

    //Expand or collapse item and previously clicked item based on params
    //currentClickIndex : Int - latest clicked item index
    /*fun performClick(currentClickIndex: Int) {
        getItem(currentClickIndex)!!.isWatching =
            !getItem(currentClickIndex)!!.isWatching
        notifyItemChanged(currentClickIndex)

        if (mLastExpandedIndex != -1 && mLastExpandedIndex != currentClickIndex && getItem(
                mLastExpandedIndex
            )!!.isWatching
        ) {
            getItem(mLastExpandedIndex)!!.isWatching = false
            notifyItemChanged(mLastExpandedIndex)
        }

        mLastExpandedIndex = currentClickIndex
    }*/

    inner class RepoViewHolder(private var view: View) : BaseViewHolder(view) {

        init {
            clickableViews(view.btnWatch)
        }

        override fun bind(item: Repository) {
            Picasso.get().load(item.getAvtar()).into(itemView.ivProfile)
            view.tvAuthor.text = item.getOwnerName()
            view.tvRepoName.text = item.getRepoName()
            view.tvDesc.text = item.getDesc()
            view.tvLang.text = item.getLang()
            view.tvStar.text = item.getStarCount().toString()
            view.tvFork.text = item.getForkCount().toString()

            if (item.isWatching) {
                view.llItemContainer.setBackgroundColor(ContextCompat.getColor(context, R.color.colorDivider))
                view.btnWatch.text = context.getString(R.string.btn_unwatch)
                view.grpExpand.visibility = VISIBLE
            } else {
                view.llItemContainer.setBackgroundColor(ContextCompat.getColor(context, R.color.colorWhite))
                view.btnWatch.text = context.getString(R.string.btn_watch)
                view.grpExpand.visibility = GONE
            }
        }
    }
}