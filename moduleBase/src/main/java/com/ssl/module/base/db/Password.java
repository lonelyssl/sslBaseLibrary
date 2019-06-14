package com.ssl.module.base.db;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Keep;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Transient;

import java.io.Serializable;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by long
 * on 2018/1/30.
 */

@Entity
public class Password implements Serializable {

    @Transient
    static final long serialVersionUID = 42L;

    @Id(autoincrement = true)
    public Long id;

    @NotNull
    public String title;

    public String name;
    public String password;
    public String rePassword;
    public String remark;

    @Keep
    public Password(Long id, @NotNull String title, String name, String password,
                    String rePassword, String remark) {
        this.id = id;
        this.title = title;
        this.name = name;
        this.password = password;
        this.rePassword = rePassword;
        this.remark = remark;
    }
    @Keep
    public Password() {
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

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRePassword() {
        return this.rePassword;
    }

    public void setRePassword(String rePassword) {
        this.rePassword = rePassword;
    }

    public String getRemark() {
        return this.remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "PasswordDao{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", rePassword='" + rePassword + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}
