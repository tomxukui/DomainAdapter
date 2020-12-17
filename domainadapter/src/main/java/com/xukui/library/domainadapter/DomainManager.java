package com.xukui.library.domainadapter;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;

import com.xukui.library.domainadapter.bean.DomainTemplate;
import com.xukui.library.domainadapter.setup.Logger;
import com.xukui.library.domainadapter.setup.Toaster;
import com.xukui.library.domainadapter.setup.impl.DefaultLogger;
import com.xukui.library.domainadapter.setup.impl.DefaultToaster;
import com.xukui.library.domainadapter.store.DomainStore;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class DomainManager {

    private static Application mApplication;

    private static Logger mLogger;
    private static Toaster mToaster;

    private static boolean mIsLogEnabled;//是否打印日志
    private static List<DomainTemplate> mDomainTemplates;

    private static List<WeakReference<Activity>> mActivities;

    private DomainManager() {
    }

    public static void init(Application application, boolean isLogEnabled, List<DomainTemplate> domainTemplates, int defaultTemplateIndex) {
        mApplication = application;
        mIsLogEnabled = isLogEnabled;
        mDomainTemplates = (domainTemplates == null ? new ArrayList<DomainTemplate>() : domainTemplates);
        mActivities = new ArrayList<>();

        //如果数据未存储, 则存储指定的模板
        if (DomainStore.getInstance().isEmpty() && !mDomainTemplates.isEmpty()) {
            int index = 0;
            if (defaultTemplateIndex >= 0 && defaultTemplateIndex < mDomainTemplates.size()) {
                index = defaultTemplateIndex;
            }

            DomainTemplate domainTemplate = mDomainTemplates.get(index);
            DomainStore.getInstance().putAll(domainTemplate.getMap());
        }

        //注册Activity生命周期
        registerActivityLifecycle(application);
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

    private static void registerActivityLifecycle(Application application) {
        application.registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks() {

            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                mActivities.add(new WeakReference<>(activity));
            }

            @Override
            public void onActivityStarted(Activity activity) {
            }

            @Override
            public void onActivityResumed(Activity activity) {
            }

            @Override
            public void onActivityPaused(Activity activity) {
            }

            @Override
            public void onActivityStopped(Activity activity) {
            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
            }

            @Override
            public void onActivityDestroyed(Activity activity) {
                for (int i = mActivities.size() - 1; i >= 0; i--) {
                    WeakReference<Activity> item = mActivities.get(i);

                    if (activity == item.get()) {
                        mActivities.remove(item);
                        break;
                    }
                }
            }

        });
    }

    /**
     * 关闭app
     */
    public static void closeApp() {
        for (int i = mActivities.size() - 1; i >= 0; i--) {
            Activity activity = mActivities.get(i).get();
            if (activity != null) {
                activity.finish();
            }
        }

        //不能先杀掉主进程, 否则逻辑代码无法继续执行, 需先杀掉相关进程最后杀掉主进程
        ActivityManager activityManager = (ActivityManager) mApplication.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> processes = activityManager.getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo process : processes) {
            if (process.pid != android.os.Process.myPid()) {
                android.os.Process.killProcess(process.pid);
            }
        }
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }

}