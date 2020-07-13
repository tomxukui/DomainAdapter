package com.xukui.library.domainadapter.setup.impl;

import android.view.Gravity;
import android.widget.Toast;

import com.xukui.library.domainadapter.DomainManager;
import com.xukui.library.domainadapter.setup.Toaster;

public class DefaultToaster implements Toaster {

    @Override
    public void showShort(String message) {
        Toast toast = Toast.makeText(DomainManager.getApplication(), message, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    @Override
    public void showLong(String message) {
        Toast toast = Toast.makeText(DomainManager.getApplication(), message, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

}