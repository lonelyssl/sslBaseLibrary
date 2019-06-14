package com.ssl.module.base.ui

import android.support.annotation.IdRes
import android.support.v7.widget.RecyclerView
import android.util.SparseArray
import android.view.View

open class BaseVHolder : RecyclerView.ViewHolder {

    private val mViews = SparseArray<View>()

    constructor(view: View) : super(view) {
    }


    fun <T : View> fId(@IdRes viewId: Int): T {
        var view: View? = mViews.get(viewId)
        if (view == null) {
            view = itemView.findViewById(viewId)
            mViews.put(viewId, view)
        }
        return view as T
    }


    fun <T : View> fId(tClass: Class<T>,@IdRes viewId: Int): T {
        var view: View? = mViews.get(viewId)
        if (view == null) {
            view = itemView.findViewById(viewId)
            mViews.put(viewId, view)
        }
        return view as T
    }


}