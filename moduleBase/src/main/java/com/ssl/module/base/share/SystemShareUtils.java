package com.ssl.module.base.share;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import java.io.File;
import java.util.List;


public class SystemShareUtils {

    private static final String TAG = "Share2";

    /**
     * Current activity
     */
    private Activity activity;

    /**
     * Share content type
     */
    private @ShareContentType
    String contentType;

    /**
     * Share title
     */
    private String title;

    /**
     * Share file Uri
     */
    private Uri shareFileUri;

    /**
     * Share content text
     */
    private String contentText;

    /**
     * Share to special component PackageName
     */
    private String componentPackageName;

    /**
     * Share to special component ClassName
     */
    private String componentClassName;


    private SystemShareUtils(@NonNull Builder builder) {
        this.activity = builder.activity;
        this.contentType = builder.contentType;
        this.title = builder.title;
        this.shareFileUri = builder.shareFileUri;
        this.contentText = builder.textContent;
        this.componentPackageName = builder.componentPackageName;
        this.componentClassName = builder.componentClassName;
    }

    /**
     * shareBySystem
     */
    public void shareBySystem() {
        if (checkShareParam()) {
            Intent shareIntent = createShareIntent();

            if (shareIntent == null) {
                Log.e(TAG, "shareBySystem cancel.");
                return;
            }

            if (title == null) {
                title = "";
            }

            if (shareIntent.resolveActivity(activity.getPackageManager()) != null) {
                try {
                    activity.startActivityForResult(Intent.createChooser(shareIntent, title), 911);
                } catch (Exception e) {
                    Log.e(TAG, Log.getStackTraceString(e));
                }
            }
        }
    }

    private Intent createShareIntent() {
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        shareIntent.addCategory("android.intent.category.DEFAULT");

        if (!TextUtils.isEmpty(this.componentPackageName) && !TextUtils.isEmpty(componentClassName)) {
            ComponentName comp = new ComponentName(componentPackageName, componentClassName);
            shareIntent.setComponent(comp);
        }

        switch (contentType) {
            case ShareContentType.TEXT:
                shareIntent.putExtra(Intent.EXTRA_TEXT, contentText);
                shareIntent.setType("text/plain");
                break;
            case ShareContentType.IMAGE:
            case ShareContentType.AUDIO:
            case ShareContentType.VIDEO:
            case ShareContentType.File:
                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.addCategory("android.intent.category.DEFAULT");
                shareIntent.setType(contentType);
                shareIntent.putExtra(Intent.EXTRA_STREAM, shareFileUri);
                shareIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

                Log.d(TAG, "Share uri: " + shareFileUri.toString());

                if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.KITKAT) {
                    List<ResolveInfo> resInfoList = activity.getPackageManager().queryIntentActivities(shareIntent, PackageManager.MATCH_DEFAULT_ONLY);
                    for (ResolveInfo resolveInfo : resInfoList) {
                        String packageName = resolveInfo.activityInfo.packageName;
                        activity.grantUriPermission(packageName, shareFileUri, Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    }
                }
                break;
            default:
                Log.e(TAG, contentType + " is not support share type.");
                shareIntent = null;
                break;
        }

        return shareIntent;
    }


    private boolean checkShareParam() {
        if (this.activity == null) {
            Log.e(TAG, "activity is null.");
            return false;
        }

        if (TextUtils.isEmpty(this.contentType)) {
            Log.e(TAG, "Share content type is empty.");
            return false;
        }

        if (ShareContentType.TEXT.equals(contentType)) {
            if (TextUtils.isEmpty(contentText)) {
                Log.e(TAG, "Share text context is empty.");
                return false;
            }
        } else {
            if (this.shareFileUri == null) {
                Log.e(TAG, "Share file path is null.");
                return false;
            }
        }

        return true;
    }

    public static class Builder {
        private Activity activity;
        private @ShareContentType
        String contentType = ShareContentType.File;
        private String title;
        private String componentPackageName;
        private String componentClassName;
        private Uri shareFileUri;
        private String textContent;

        public Builder(Activity activity) {
            this.activity = activity;
        }

        /**
         * Set Content Type
         *
         * @param contentType {@link ShareContentType}
         * @return Builder
         */
        public Builder setContentType(@ShareContentType String contentType) {
            this.contentType = contentType;
            return this;
        }

        /**
         * Set Title
         *
         * @param title title
         * @return Builder
         */
        public Builder setTitle(@NonNull String title) {
            this.title = title;
            return this;
        }

        /**
         * Set share file path
         *
         * @param shareFileUri shareFileUri
         * @return Builder
         */
        public Builder setShareFileUri(Uri shareFileUri) {
            this.shareFileUri = shareFileUri;
            return this;
        }

        public Builder setShareFile(File file) {
            return setShareFileUri(ShareFileUtils.getFileUri(activity, ShareContentType.File, file));
        }

        /**
         * Set text content
         *
         * @param textContent textContent
         * @return Builder
         */
        public Builder setTextContent(String textContent) {
            this.textContent = textContent;
            return this;
        }

        /**
         * Set Share To Component
         *
         * @param componentPackageName componentPackageName
         * @param componentClassName   componentPackageName
         * @return Builder
         */
        public Builder setShareToComponent(String componentPackageName, String componentClassName) {
            this.componentPackageName = componentPackageName;
            this.componentClassName = componentClassName;
            return this;
        }

        /**
         * build
         *
         * @return
         */
        public SystemShareUtils build() {
            return new SystemShareUtils(this);
        }

    }
}
