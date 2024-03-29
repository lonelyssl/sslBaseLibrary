package com.ssl.module.base.share;


import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * ShareContentType
 * Support Share Content Types.
 */

@StringDef({ShareContentType.TEXT, ShareContentType.IMAGE,
        ShareContentType.AUDIO, ShareContentType.VIDEO, ShareContentType.File})
@Retention(RetentionPolicy.SOURCE)
public @interface ShareContentType {
    /**
     * Share Text
     */
    final String TEXT = "text/plain";

    /**
     * Share Image
     */
    final String IMAGE = "image/*";

    /**
     * Share Audio
     */
    final String AUDIO = "audio/*";

    /**
     * Share Video
     */
    final String VIDEO = "video/*";

    /**
     * Share File
     */
    final String File = "*/*";
}
