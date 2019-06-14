package com.ssl.module.base.backups;

import android.content.Context;

import com.ssl.common.utils.DeviceUtil;
import com.ssl.common.utils.JsonParser;
import com.ssl.module.base.db.GreenDaoFactory;
import com.ssl.module.base.utils.AesEncryptionUtil;
import com.ssl.module.base.utils.LocalFileUtils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class BackupAndRestoreUtils {
    public static <T> File getBackupPath(Context mContext, Class<T> mClass) {
        String bPath = LocalFileUtils.Companion.getBackupPath(mContext);
        File pFile = new File(bPath);
        if (!pFile.exists()) {
            pFile.mkdirs();
        }
        File bFile = new File(pFile, mClass.getSimpleName() + ".own");
        return bFile;
    }

    public static File getBackupPath(Context mContext, String fileName) {
        String bPath = LocalFileUtils.Companion.getBackupPath(mContext);
        File pFile = new File(bPath);
        if (!pFile.exists()) {
            pFile.mkdirs();
        }
        File bFile = new File(pFile, fileName + ".own");
        return bFile;
    }

    /***
     * 导入
     * @param mContext
     * @param back
     */
    public static void restore(Context mContext, String path, CallBack back) {
        Observable.just(path)
                .observeOn(Schedulers.io())
                .map((temPath) -> {
                    File file = new File(temPath);
                    if (file != null && file.exists()) {
                        BufferedReader isr = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
                        String mClassName = isr.readLine();
                        StringBuilder sb = new StringBuilder();
                        String value;
                        while ((value = isr.readLine()) != null) {
                            sb.append(value);
                        }
                        isr.close();
                        String key = getKey(mContext);

                        String[] result = {mClassName, AesEncryptionUtil.decrypt(sb.toString(), key, key.substring(16))};
                        return result;
                    }
                    return null;
                })
                .map((String[] result) -> {
                    if (result != null && result.length == 2) {
                        List list = JsonParser.getListFromJson(result[1], Class.forName(result[0]));
                        for (Object t : list) {
                            GreenDaoFactory.Companion.getInstance(mContext).insertOrReplace(t);
                        }
                        return true;
                    }
                    return false;
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(flag -> {
                    if (back != null) {
                        if (flag) {
                            back.success("插入数据完成");
                        } else {
                            back.fail("未查询数据入口");
                        }
                    }

                });
    }


    /**
     * 备份数据
     *
     * @param mContext
     * @param <T>
     */
    public static <T> void backup(Context mContext, List<T> list, final File outPath, CallBack back) {
        if (list == null || list.size() == 0) {
            if (back != null) {
                back.fail("记录不存在");
            }
            return;
        }
        //类全路径
        String classPath = list.get(0).getClass().getName();

        Observable.just(list)
                .observeOn(Schedulers.io())
                .map((List<T> temList) -> {//转换加密
                    String json = JsonParser.getJsonFromList(temList);
                    String key = getKey(mContext);
                    return AesEncryptionUtil.encrypt(json, key, key.substring(16));
                })
                .map((String value) -> {//写入
                    File bFile = outPath;
                    BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(bFile)));
                    out.write(classPath);
                    out.newLine();
                    out.write(value);
                    out.flush();
                    out.close();
                    return bFile;
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(file -> {
                    if (file != null && file.exists()) {
                        if (back != null) {
                            back.success(file);
                        }
                    } else if (back != null) {
                        back.fail("文件备份失败");
                    }

                });

    }


    public static String getKey(Context mContex) {
        return DeviceUtil.getDeviceId(mContex);
    }

    public interface CallBack {
        public void success(Object obj);

        public void fail(String msg);

    }
}
