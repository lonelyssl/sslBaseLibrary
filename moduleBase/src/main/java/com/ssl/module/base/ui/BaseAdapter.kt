package com.ssl.module.base.ui

import android.content.Context
import android.support.v7.widget.RecyclerView

open abstract class BaseAdapter<E> constructor(mContext: Context, mData: List<E>) : RecyclerView.Adapter<BaseVHolder>() {

    var mContext: Context;
    var mData: List<E>;

    init {
        this.mData = mData;
        this.mContext = mContext;
    }

    override fun getItemCount(): Int {
        return mData.size;
    }


}