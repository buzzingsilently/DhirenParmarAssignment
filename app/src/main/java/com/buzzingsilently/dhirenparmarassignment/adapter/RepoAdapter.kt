package com.buzzingsilently.dhirenparmarassignment.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.View.*
import android.view.ViewGroup
import androidx.core.graphics.drawable.DrawableCompat
import com.buzzingsilently.dhirenparmarassignment.R
import com.buzzingsilently.dhirenparmarassignment.base.BaseRecyclerAdapter
import com.buzzingsilently.dhirenparmarassignment.model.RepoModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_repo.view.*

class RepoAdapter(
    private var context: Context,
    listener: RvItemClickListener<RepoModel>
) :
    BaseRecyclerAdapter<RepoModel, RepoAdapter.RepoViewHolder>(RepoModel.DIFF_CALLBACK, listener) {

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
    fun performClick(currentClickIndex: Int) {
        getItem(currentClickIndex)!!.isExpanded =
            !getItem(currentClickIndex)!!.isExpanded
        notifyItemChanged(currentClickIndex)

        if (mLastExpandedIndex != -1 && mLastExpandedIndex != currentClickIndex && getItem(
                mLastExpandedIndex
            )!!.isExpanded
        ) {
            getItem(mLastExpandedIndex)!!.isExpanded = false
            notifyItemChanged(mLastExpandedIndex)
        }

        mLastExpandedIndex = currentClickIndex
    }

    inner class RepoViewHolder(private var view: View) : BaseViewHolder(view) {

        init {
            clickableViews(view.llItemContainer)
        }

        override fun bind(item: RepoModel) {
            Picasso.get().load(item.getAvtar()).into(itemView.ivProfile)
            view.tvAuthor.text = item.getAvtarName()
            view.tvRepoName.text = item.getRepoName()
            view.tvDesc.text = item.getDesc()

            if (item.getLang().isNotEmpty()) {
                view.tvLang.text = item.getLang()
                DrawableCompat.setTint(
                    view.tvLang.compoundDrawables[0],
                    Color.parseColor(item.getLangColor())
                )
            } else {
                view.tvLang.visibility = INVISIBLE
            }
            view.tvStar.text = item.getStarCount().toString()
            view.tvFork.text = item.getForkCount().toString()

            if (item.isExpanded)
                view.grpExpand.visibility = VISIBLE
            else
                view.grpExpand.visibility = GONE
        }
    }
}