package com.xukui.domainadapter;

import com.xukui.library.domainadapter.bean.DomainTemplate;
import com.xukui.library.domainadapter.store.DomainStore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DomainUtil {

    //定义Key
    private static final String KEY_TZ_HOST = "TZ_HOST";
    private static final String KEY_TZ_APPID = "TZ_APPID";
    private static final String KEY_TZ_AES = "TZ_AES";
    private static final String KEY_TZ_RSA = "TZ_RSA";

    private static final String KEY_SH_HOST = "SH_HOST";
    private static final String KEY_SH_SID = "SH_SID";
    private static final String KEY_SH_SIGN = "SH_SIGN";

    //定义数据集合
    private static final Map<String, String> mReleaseMap = new HashMap<>();
    private static final Map<String, String> mDebugMap = new HashMap<>();

    static {
        //正式环境
        mReleaseMap.clear();
        mReleaseMap.put(KEY_TZ_HOST, "http://api.tz.com/");
        mReleaseMap.put(KEY_TZ_APPID, "tzatzatzatzatzatzatzatzatzatzatzatzatzatza");
        mReleaseMap.put(KEY_TZ_AES, "tzbtzbtzbtzbtzbtzbtzbtzbtzbtzbtzbtzb");
        mReleaseMap.put(KEY_TZ_RSA, "tzctzctzctzctzctzctzctzctzctzctzctzc");
        mReleaseMap.put(KEY_SH_HOST, "http://api.sh.com/");
        mReleaseMap.put(KEY_SH_SID, "shashashashashashashashashashashashasha");
        mReleaseMap.put(KEY_SH_SIGN, "shbshbshbshbshbshbshbshbshbshbshbshbshbshb");

        //测试环境
        mDebugMap.clear();
        mDebugMap.put(KEY_TZ_HOST, "http://api.test-tz.com/");
        mDebugMap.put(KEY_TZ_APPID, "test-tzatzatzatzatzatzatzatzatzatzatzatzatzatza");
        mDebugMap.put(KEY_TZ_AES, "test-tzbtzbtzbtzbtzbtzbtzbtzbtzbtzbtzbtzb");
        mDebugMap.put(KEY_TZ_RSA, "test-tzctzctzctzctzctzctzctzctzctzctzctzc");
        mDebugMap.put(KEY_SH_HOST, "http://api.test-sh.com/");
        mDebugMap.put(KEY_SH_SID, "test-shashashashashashashashashashashashasha");
        mDebugMap.put(KEY_SH_SIGN, "test-shbshbshbshbshbshbshbshbshbshbshbshbshbshb");
    }

    public static String getValue(String key) {
        String value = mReleaseMap.get(key);

        if (Config.DEV_ENABLE) {
            value = DomainStore.getInstance().getString(key, value);
        }

        return value;
    }

    /**
     * 创建模板列表
     */
    public static List<DomainTemplate> createTemplates() {
        List<DomainTemplate> templates = new ArrayList<>();
        templates.add(new DomainTemplate("正式环境", mReleaseMap));
        templates.add(new DomainTemplate("测试环境", mDebugMap));
        return templates;
    }

}