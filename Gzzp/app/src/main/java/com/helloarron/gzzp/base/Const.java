package com.helloarron.gzzp.base;

import android.os.Environment;

/**
 * 一些系统常量的定义
 * Created by arron on 2017/3/10.
 */

public class Const {
    public final static int LOADING = 0x0;
    public final static int LOADED = 0x1;

    public final static int CANCEL = 0x2;

    // 首先默认个文件保存路径
    // 保存到SD卡
    public static final String SAVE_PIC_PATH = Environment.getExternalStorageState().equalsIgnoreCase(Environment.MEDIA_MOUNTED) ? Environment.getExternalStorageDirectory().getAbsolutePath() : "/mnt/sdcard";
    // 保存的确切位置
    public static final String SAVE_REAL_PATH = SAVE_PIC_PATH + "/GZZP";

    public final static String HOST = "http://gzzp.helloarron.com/";

    public final static String GZZP = "http://www.163gz.com/gzzp8/zkxx/";
    public final static String WEB_PAGE = HOST + "/detail/";

    // 微信开发者平台
    public final static String WX_APP_ID = "wx53d7536afe86c1a0";
    public final static String WX_APP_SECRET = "e0bc770d549225f062a1cc013c3c7495";
}
