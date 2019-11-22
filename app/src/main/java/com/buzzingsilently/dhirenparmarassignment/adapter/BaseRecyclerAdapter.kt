package com.buzzingsilently.dhirenparmarassignment.adapter

import android.view.View
import android.view.View.OnLongClickListener
import androidx.recyclerview.widget.RecyclerView

abstract class BaseRecyclerAdapter<T : BaseRecyclerAdapter<Any, Any>.ViewHolder, M> :
    RecyclerView.Adapter<T>() {

    private var mData: MutableList<M>? = null
    private var mRvItemClickListener: RvItemClickListener<M>? = null

    fun setRvItemClickListener(
        mRvItemClickListener: RvItemClickListener<M>?
    ): BaseRecyclerAdapter<*, *> {
        this.mRvItemClickListener = mRvItemClickListener
        return this
    }

    fun getItem(position: Int): M {
        return mData!![position]
    }

    override fun getItemCount(): Int {
        return mData!!.size
    }

    fun getData(): List<M>? {
        return mData
    }

    fun setItems(items: MutableList<M>?) {
        mData = items
        notifyDataSetChanged()
    }

    fun addItems(m: List<M>?) {
        mData!!.addAll(m!!)
        notifyDataSetChanged()
    }

    fun addItem(m: M) {
        mData!!.add(m)
        notifyDataSetChanged()
    }

    interface RvItemClickListener<M> {
        fun onItemClick(view: View?, position: Int, model: M)
        fun onItemLongClick(view: View?, position: Int, model: M)
    }

    inner class ViewHolder(itemView: View?, model: M) :
        RecyclerView.ViewHolder(itemView!!) {
        private val mOnClickListener =
            View.OnClickListener { v ->
                if (mRvItemClickListener != null) {
                    mRvItemClickListener!!.onItemClick(v, layoutPosition, model)
                }
            }
        private val mOnLongClickListener = OnLongClickListener { view ->
            if (mRvItemClickListener != null) {
                mRvItemClickListener!!.onItemLongClick(view, layoutPosition, model)
            }
            true
        }

        //put here clickable views list
        fun clickableViews(vararg views: View) {
            for (view in views) {
                view.setOnClickListener(mOnClickListener)
            }
        }

        fun longClickableViews(vararg views: View) {
            for (view in views) {
                view.setOnLongClickListener(mOnLongClickListener)
            }
        }
    }
}