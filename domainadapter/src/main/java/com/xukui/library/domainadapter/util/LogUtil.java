package com.xukui.library.domainadapter.util;

import com.xukui.library.domainadapter.DomainManager;
import com.xukui.library.domainadapter.setup.Logger;

public class LogUtil {

    private static final String TAG = "域名适配器";

    private static Logger getLogger() {
        if (DomainManager.isLogEnabled()) {
            return DomainManager.getLogger();
        }

        return null;
    }

    public static void v(String msg) {
        Logger logger = getLogger();
        if (logger != null) {
            logger.v(TAG, msg);
        }
    }

    public static void d(String msg) {
        Logger logger = getLogger();
        if (logger != null) {
            logger.d(TAG, msg);
        }
    }

    public static void i(String msg) {
        Logger logger = getLogger();
        if (logger != null) {
            logger.i(TAG, msg);
        }
    }

    public static void w(String msg) {
        Logger logger = getLogger();
        if (logger != null) {
            logger.w(TAG, msg);
        }
    }

    public static void e(String msg) {
        Logger logger = getLogger();
        if (logger != null) {
            logger.e(TAG, msg);
        }
    }

    public static void e(String msg, Throwable tr) {
        Logger logger = getLogger();
        if (logger != null) {
            logger.e(TAG, msg, tr);
        }
    }

}