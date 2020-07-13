package com.xukui.library.domainadapter.store;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;

import com.xukui.library.domainadapter.DomainManager;
import com.xukui.library.domainadapter.bean.KeyValue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class DomainStore {

    private SharedPreferences mPreferences;
    private SharedPreferences.Editor mEditor;

    private DomainStore() {
        mPreferences = DomainManager.getApplication().getSharedPreferences("DomainAdapterSDK", Context.MODE_PRIVATE);
        mEditor = mPreferences.edit();
    }

    public static DomainStore getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private static class SingletonHolder {
        private static final DomainStore INSTANCE = new DomainStore();
    }

    @Nullable
    public String getString(String key, @Nullable String defValue) {
        return mPreferences.getString(key, defValue);
    }

    public static List<KeyValue> convertMapToList(Map<String, String> map) {
        List<KeyValue> list = new ArrayList<>();

        if (map != null) {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                list.add(new KeyValue(entry.getKey(), entry.getValue()));
            }
        }

        Collections.sort(list, new Comparator<KeyValue>() {

            @Override
            public int compare(KeyValue o1, KeyValue o2) {
                int i = o1.getKey().compareTo(o2.getKey());

                if (i > 0) {
                    return 1;

                } else {
                    return -1;
                }
            }

        });

        return list;
    }

    public List<KeyValue> getAll() {
        Map<String, String> map = (Map<String, String>) mPreferences.getAll();
        return convertMapToList(map);
    }

    public boolean putAll(Map<String, String> map) {
        mEditor.clear();

        if (map != null) {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                mEditor.putString(entry.getKey(), entry.getValue());
            }
        }

        return mEditor.commit();
    }

    public boolean putAll(List<KeyValue> list) {
        mEditor.clear();

        if (list != null) {
            for (KeyValue keyValue : list) {
                mEditor.putString(keyValue.getKey(), keyValue.getValue());
            }
        }

        return mEditor.commit();
    }

    public boolean isEmpty() {
        Map<String, ?> map = mPreferences.getAll();

        return map == null || map.isEmpty();
    }

}