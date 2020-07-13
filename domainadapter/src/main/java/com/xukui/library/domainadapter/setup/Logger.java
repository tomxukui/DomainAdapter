package com.xukui.library.domainadapter.setup;

public interface Logger {

    void v(String tag, String msg);

    void d(String tag, String msg);

    void i(String tag, String msg);

    void w(String tag, String msg);

    void e(String tag, String msg);

    void e(String tag, String msg, Throwable tr);

}