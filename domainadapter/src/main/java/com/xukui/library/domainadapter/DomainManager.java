package com.xukui.library.domainadapter;

import android.app.Application;

import com.xukui.library.domainadapter.bean.DomainTemplate;
import com.xukui.library.domainadapter.setup.Logger;
import com.xukui.library.domainadapter.setup.Toaster;
import com.xukui.library.domainadapter.setup.impl.DefaultLogger;
import com.xukui.library.domainadapter.setup.impl.DefaultToaster;
import com.xukui.library.domainadapter.store.DomainStore;

import java.util.ArrayList;
import java.util.List;

public class DomainManager {

    private static Application mApplication;

    private static Logger mLogger;
    private static Toaster mToaster;

    private static boolean mIsLogEnabled;//是否打印日志
    private static List<DomainTemplate> mDomainTemplates;

    private DomainManager() {
    }

    public static void init(Application application, boolean isLogEnabled, List<DomainTemplate> domainTemplates, int defaultTemplateIndex) {
        mApplication = application;
        mIsLogEnabled = isLogEnabled;
        mDomainTemplates = (domainTemplates == null ? new ArrayList<DomainTemplate>() : domainTemplates);

        //如果数据未存储, 则存储指定的模板
        if (DomainStore.getInstance().isEmpty() && !mDomainTemplates.isEmpty()) {
            int index = 0;
            if (defaultTemplateIndex >= 0 && defaultTemplateIndex < mDomainTemplates.size()) {
                index = defaultTemplateIndex;
            }

            DomainTemplate domainTemplate = mDomainTemplates.get(index);
            DomainStore.getInstance().putAll(domainTemplate.getMap());
        }
    }

    public static Application getApplication() {
        return mApplication;
    }

    public static List<DomainTemplate> getDomainTemplates() {
        return mDomainTemplates;
    }

    public static DomainTemplate getDomainTemplate(int index) {
        if (mDomainTemplates != null && index >= 0 && index < mDomainTemplates.size()) {
            return mDomainTemplates.get(index);

        } else {
            return null;
        }
    }

    /**
     * 是否打印日志
     */
    public static boolean isLogEnabled() {
        return mIsLogEnabled;
    }

    public static void setLogger(Logger logger) {
        mLogger = logger;
    }

    public static Logger getLogger() {
        if (mLogger == null) {
            mLogger = new DefaultLogger();
        }

        return mLogger;
    }

    public static void setToaster(Toaster toaster) {
        mToaster = toaster;
    }

    public static Toaster getToaster() {
        if (mToaster == null) {
            mToaster = new DefaultToaster();
        }

        return mToaster;
    }

}