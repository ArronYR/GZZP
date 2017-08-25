package com.helloarron.gzzp;

import android.app.Application;
import android.content.Context;

import com.helloarron.dhroid.adapter.ValueFix;
import com.helloarron.dhroid.dialog.IDialog;
import com.helloarron.dhroid.ioc.Instance;
import com.helloarron.dhroid.ioc.IocContainer;
import com.helloarron.dhroid.net.CodeHandler;
import com.helloarron.dhroid.net.GlobalCodeHandler;
import com.helloarron.dhroid.net.GlobalParams;
import com.helloarron.dhroid.net.cache.DaoHelper;
import com.helloarron.dhroid.util.UserLocation;
import com.helloarron.gzzp.base.GzzpCodeHandler;
import com.helloarron.gzzp.base.GzzpGlobalCodeHandler;
import com.helloarron.gzzp.base.GzzpValueFix;
import com.helloarron.gzzp.utils.GzzpPreference;
import com.helloarron.gzzp.views.NormalDialog;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.pgyersdk.crash.PgyCrashManager;
import com.pgyersdk.update.PgyUpdateManager;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by arron on 2017/4/30.
 */

public class GzzpApplication extends Application implements Thread.UncaughtExceptionHandler {

    private static GzzpApplication instance;

    public static GzzpApplication getInstance() {
        return instance;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;
        IocContainer.getShare().initApplication(this);
        IocContainer.getShare().bind(GzzpValueFix.class).to(ValueFix.class).scope(Instance.InstanceScope.SCOPE_SINGLETON);
        IocContainer.getShare().bind(NormalDialog.class).to(IDialog.class).scope(Instance.InstanceScope.SCOPE_SINGLETON);
        IocContainer.getShare().bind(DaoHelper.class).to(OrmLiteSqliteOpenHelper.class).scope(Instance.InstanceScope.SCOPE_SINGLETON);
        IocContainer.getShare().bind(GzzpGlobalCodeHandler.class).to(GlobalCodeHandler.class).scope(Instance.InstanceScope.SCOPE_SINGLETON);
        IocContainer.getShare().bind(GzzpCodeHandler.class).to(CodeHandler.class).scope(Instance.InstanceScope.SCOPE_SINGLETON);
        // 全局请求参数
        GlobalParams globalParams = IocContainer.getShare().get(GlobalParams.class);
        // 语言
        globalParams.setGlobalParam("lang", "zh-cn");
        globalParams.setGlobalParam("rows", "1");

        GzzpPreference per = IocContainer.getShare().get(GzzpPreference.class);
        per.load();

        if (per.isFirst != 0) {
            UserLocation location = UserLocation.getInstance();
            location.init(this);
        }
        PgyCrashManager.register(this);
        JPushInterface.setDebugMode(false);
        JPushInterface.init(this);
        JPushInterface.setLatestNotificationNumber(this, 1);
        JPushInterface.setAliasAndTags(this, null, null, null);
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        android.os.Process.killProcess(android.os.Process.myPid());
    }

}
