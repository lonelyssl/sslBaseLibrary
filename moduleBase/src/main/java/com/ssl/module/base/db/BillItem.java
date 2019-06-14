package com.ssl.module.base.db;

import org.greenrobot.greendao.DaoException;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Keep;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.ToOne;
import org.greenrobot.greendao.annotation.Transient;

import java.io.Serializable;

/**
 * Created by long
 * on 2018/1/30.
 */

@Entity
public class BillItem implements Serializable {

    @Transient
    static final long serialVersionUID = 42L;

    @Id(autoincrement = true)
    public Long id;

    @NotNull
    public String title;

    private long time;  //创建时间

    private long money;//金额

    private long typeId;// 类型id

    @ToOne(joinProperty = "typeId")
    private BillType type;

    /**
     * Used to resolve relations
     */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    /**
     * Used for active entity operations.
     */
    @Generated(hash = 1851095063)
    private transient BillItemDao myDao;

    @Generated(hash = 506996655)
    private transient Long type__resolvedKey;


    @Keep
    public BillItem(Long id, String title, long time, long money, long typeId) {
        this.id = id;
        this.title = title;
        this.time = time;
        this.money = money;
        this.typeId = typeId;
    }


    @Generated(hash = 1489286430)
    public BillItem() {
    }


    public Long getId() {
        return this.id;
    }


    public void setId(Long id) {
        this.id = id;
    }


    public String getTitle() {
        return this.title;
    }


    public void setTitle(String title) {
        this.title = title;
    }


    public long getTime() {
        return this.time;
    }


    public void setTime(long time) {
        this.time = time;
    }


    public long getMoney() {
        return this.money;
    }


    public void setMoney(long money) {
        this.money = money;
    }


    public long getTypeId() {
        return this.typeId;
    }


    public void setTypeId(long typeId) {
        this.typeId = typeId;
    }


    /**
     * To-one relationship, resolved on first access.
     */
    @Generated(hash = 609820452)
    public BillType getType() {
        long __key = this.typeId;
        if (type__resolvedKey == null || !type__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            BillTypeDao targetDao = daoSession.getBillTypeDao();
            BillType typeNew = targetDao.load(__key);
            synchronized (this) {
                type = typeNew;
                type__resolvedKey = __key;
            }
        }
        return type;
    }


    /**
     * called by internal mechanisms, do not call yourself.
     */
    @Generated(hash = 449172432)
    public void setType(@NotNull BillType type) {
        if (type == null) {
            throw new DaoException(
                    "To-one property 'typeId' has not-null constraint; cannot set to-one to null");
        }
        synchronized (this) {
            this.type = type;
            typeId = type.getTypeId();
            type__resolvedKey = typeId;
        }
    }


    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }


    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }


    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }


    /**
     * called by internal mechanisms, do not call yourself.
     */
    @Generated(hash = 1392616716)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getBillItemDao() : null;
    }
}
