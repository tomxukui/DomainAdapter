package com.xukui.domainadapter;

import android.app.Application;

import com.xukui.library.domainadapter.DomainManager;
import com.xukui.library.domainadapter.bean.DomainTemplate;

import java.util.List;

public class Mapp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        List<DomainTemplate> templates = DomainUtil.createTemplates();
        DomainManager.init(this, true, templates, 0);
    }

}
