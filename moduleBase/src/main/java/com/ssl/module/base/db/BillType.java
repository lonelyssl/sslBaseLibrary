package com.ssl.module.base.db;

import android.text.TextUtils;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Keep;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Transient;

import java.io.Serializable;

/**
 * Created by long
 * on 2018/1/30.
 */

@Entity
public class BillType implements Serializable {


    public static final int TYPE_IN = 1;
    public static final int TYPE_OUT = 2;

    @Transient
    static final long serialVersionUID = 42L;

    @Id(autoincrement = true)
    public Long typeId;

    @NotNull
    public String title;

    public int type = TYPE_OUT; //类型 1：收入  2：支出

    @Keep
    public BillType(Long typeId, String title, int type) {
        this.typeId = typeId;
        this.title = title;
        this.type = type;
    }

    @Generated(hash = 778408781)
    public BillType() {
    }

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }

    public String getTitle() {
        return this.title;
    }


    public void setTitle(String title) {
        this.title = title;
    }

    public int getType() {
        return this.type;
    }

    public void setType(int type) {
        this.type = type;
    }


    public String getShowTitle() {
        if (TextUtils.isEmpty(this.title)) {
            return this.type == 1 ? "收入" : "支出";
        } else {
            return (this.type == 1 ? "收入-" : "支出-") + this.title;
        }
    }
}
