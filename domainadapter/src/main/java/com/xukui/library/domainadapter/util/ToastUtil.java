package com.xukui.library.domainadapter.util;

import com.xukui.library.domainadapter.DomainManager;
import com.xukui.library.domainadapter.setup.Toaster;

public class ToastUtil {

    public static void showShort(String msg) {
        Toaster toaster = DomainManager.getToaster();
        if (toaster != null) {
            toaster.showShort(msg);
        }
    }

    public static void showLong(String msg) {
        Toaster toaster = DomainManager.getToaster();
        if (toaster != null) {
            toaster.showLong(msg);
        }
    }

}
