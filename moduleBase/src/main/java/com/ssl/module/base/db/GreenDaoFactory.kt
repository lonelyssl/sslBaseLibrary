package com.ssl.module.base.db

import android.content.Context

/**
 * Created by long
 * on 2018/1/30.
 */
class GreenDaoFactory {
    private var session: DaoSession? = null;
    private var daoMaster: DaoMaster;

    private constructor(context: Context) {
        daoMaster = DaoMaster(DaoMaster.DevOpenHelper(context, "ownTools").writableDb);
    }


    companion object {
        private var inst: GreenDaoFactory? = null;
        /**
         * 单例实现
         */
        fun getInstance(context: Context?): GreenDaoFactory? {
            if (inst == null && context != null) {
                synchronized(GreenDaoFactory::class.java) {
                    if (inst == null) {
                        inst = GreenDaoFactory(context);
                    }
                }
            }

            return inst;
        }

    }

    public fun getSession(): DaoSession? {
        if (session == null) {
            session = daoMaster.newSession();
        }
        return session;
    }

    fun insert(bean: Any) {
        getSession()?.insert(bean);
    }

    fun insertOrReplace(bean: Any) {
        getSession()?.insertOrReplace(bean)
    }


    fun update(bean: Any) {
        getSession()?.update(bean);
    }

    fun delete(bean: Any) {
        getSession()?.delete(bean);
    }

    fun <T> queryById(id: Long, tClass: Class<T>): T? {
        return getSession()?.queryRaw<T, Any>(tClass, "where _id=" + id, null)?.get(0);
    }

    fun <T> loadAll(clazz: Class<T>): MutableList<T>? {
        return getSession()?.loadAll<T, Any>(clazz)
    }


}