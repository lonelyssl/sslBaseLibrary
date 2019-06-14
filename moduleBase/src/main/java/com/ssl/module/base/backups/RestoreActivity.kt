package com.ssl.module.base.backups

import android.app.Activity
import android.content.Intent
import android.text.TextUtils
import android.widget.TextView
import android.widget.Toast
import com.ssl.module.base.R
import com.ssl.module.base.permission.EasyPermission
import com.ssl.module.base.permission.GrantResult
import com.ssl.module.base.permission.Permission
import com.ssl.module.base.permission.PermissionRequestListener
import com.ssl.module.base.ui.BaseEmptyActivity


public class RestoreActivity : BaseEmptyActivity() {

    companion object {
        fun start(mActivity: Activity, path: String?) {
            var intent = Intent(mActivity, RestoreActivity::class.java)
            if (!TextUtils.isEmpty(path)) {
                intent.putExtra("mPath", path);
            }
            mActivity.startActivity(intent)
        }

    }

    var filePath: String? = null;
    lateinit var tvFilePath: TextView;
    lateinit var tvBtn: TextView;
    override fun getLayoutId(): Int {
        return R.layout.bmodule_activity_open_file;
    }

    override fun initIntentData() {
        super.initIntentData()
        setTitle("数据恢复")
        var uri = intent.data
        if (uri != null) {
            filePath = uri.path
        }
    }

    override fun initView() {
        super.initView()
        tvFilePath = findViewById(R.id.tv_file_path);
        tvBtn = findViewById(R.id.bt_restore);
        if (!TextUtils.isEmpty(filePath)) {
            tvFilePath.setText(filePath)
            tvBtn.isEnabled = true;
        } else {
            tvBtn.isEnabled = false;
        }

    }


    fun restore(mActivity: Activity, path: String) {
        EasyPermission.with(mActivity)
                .addPermissions(Permission.Group.STORAGE)
                .request(object : PermissionRequestListener() {
                    override fun onGrant(result: MutableMap<String, GrantResult>?) {
                        var sResult = result?.get(Permission.READ_EXTERNAL_STORAGE)
                        if (sResult == GrantResult.GRANT) {
                            BackupAndRestoreUtils.restore(mActivity, path, object : BackupAndRestoreUtils.CallBack {
                                override fun fail(msg: String?) {
                                    toast("导入失败：" + msg);
                                }

                                override fun success(obj: Any?) {
                                    toast("导入成功");
                                }
                            })

                        } else {
                            Toast.makeText(mActivity, "请检查权限", Toast.LENGTH_LONG).show()
                        }
                    }

                    override fun onCancel(stopPermission: String?) {
                        Toast.makeText(mActivity, "请检查权限", Toast.LENGTH_LONG).show()
                    }
                })

    }

    override fun initEvent() {
        super.initEvent()
        tvBtn.setOnClickListener { v ->
            if (!TextUtils.isEmpty(filePath))
                restore(this, filePath!!)
        }
    }


}