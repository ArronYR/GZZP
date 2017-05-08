package com.helloarron.gzzp.activity.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.helloarron.dhroid.activity.ActivityTack;
import com.helloarron.dhroid.dialog.IDialog;
import com.helloarron.dhroid.ioc.IocContainer;
import com.helloarron.gzzp.R;
import com.helloarron.gzzp.activity.collect.CollectPageFragment;
import com.helloarron.gzzp.activity.home.HomePageFragment;
import com.helloarron.gzzp.activity.recruit.RecruitPageFragment;
import com.helloarron.gzzp.bean.BackHomeEB;
import com.helloarron.gzzp.bean.User;
import com.helloarron.gzzp.manage.UserInfoManage;
import com.helloarron.gzzp.utils.GzzpPreference;
import com.pgyersdk.update.PgyUpdateManager;

import java.util.ArrayList;

import cn.jpush.android.api.JPushInterface;
import de.greenrobot.event.EventBus;

public class MainActivity extends FragmentActivity {

    private static boolean isExit = false;
    private FragmentManager fm;
    private Fragment currentFragment;
    private LinearLayout tabV;

    public HomePageFragment homeFragment;
    public Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EventBus.getDefault().register(this);
        ActivityTack.getInstanse().addActivity(this);
        homeFragment = new HomePageFragment();

        initView();
        initTab();
        setTab(0);

        // 默认对话框的版本更新检查
        PgyUpdateManager.register(this, "TljCxu2AOMavxJ7");
    }

    private void initView() {
        mHandler = new Handler();
        fm = getSupportFragmentManager();
        tabV = (LinearLayout) findViewById(R.id.tab);
    }

    private void initTab() {
        for (int i = 0; i < tabV.getChildCount(); i++) {
            final int index = i;
            LinearLayout childV = (LinearLayout) tabV.getChildAt(i);
            childV.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    setTab(index);
                }
            });
        }
    }

    private void setTab(final int index) {
        User user = User.getInstance();
        if (index == 2) {
            GzzpPreference per = IocContainer.getShare().get(GzzpPreference.class);
            per.load();

            if (!user.isLogin()) {
                UserInfoManage.getInstance().checkLogin(MainActivity.this, new UserInfoManage.LoginCallBack() {
                    @Override
                    public void onisLogin() {
                        setTab(index);
                    }

                    @Override
                    public void onLoginFail() {

                    }
                });
                return;
            }
        }

        for (int i = 0; i < tabV.getChildCount(); i++) {
            LinearLayout childV = (LinearLayout) tabV.getChildAt(i);
            RelativeLayout imgV = (RelativeLayout) childV.getChildAt(0);
            ImageView imgI = (ImageView) imgV.getChildAt(0);
            TextView textT = (TextView) childV.getChildAt(1);

            if (i == index) {
                switch (i) {
                    case 0:
                        switchContent(homeFragment);
                        imgI.setImageResource(R.drawable.icon_home_active);
                        textT.setTextColor(getResources().getColor(R.color.text_teal_400));
                        break;
                    case 1:
                        switchContent(RecruitPageFragment.getInstance());
                        imgI.setImageResource(R.drawable.icon_recruit_active);
                        textT.setTextColor(getResources().getColor(R.color.text_teal_400));
                        break;
                    case 2:
                        switchContent(CollectPageFragment.getInstance());
                        imgI.setImageResource(R.drawable.icon_collect_active);
                        textT.setTextColor(getResources().getColor(R.color.text_teal_400));
                        break;
                    default:
                        break;
                }
            } else {
                childV.setBackgroundColor(getResources().getColor(R.color.nothing));
                switch (i) {
                    case 0:
                        imgI.setImageResource(R.drawable.icon_home_disable);
                        textT.setTextColor(getResources().getColor(R.color.text_66_black));
                        break;
                    case 1:
                        imgI.setImageResource(R.drawable.icon_recruit_disable);
                        textT.setTextColor(getResources().getColor(R.color.text_66_black));
                        break;
                    case 2:
                        imgI.setImageResource(R.drawable.icon_collect_disable);
                        textT.setTextColor(getResources().getColor(R.color.text_66_black));
                        break;
                    default:
                        break;
                }
            }
        }
    }

    public void switchContent(Fragment fragment) {
        try {
            FragmentTransaction t = fm.beginTransaction();
            if (currentFragment != null) {
                t.hide(currentFragment);
            }
            if (!fragment.isAdded()) {
                t.add(R.id.main_content, fragment);

            }
            t.show(fragment);
            t.commitAllowingStateLoss();
            currentFragment = fragment;
        } catch (Exception e) {
        }
    }

    static public class ExitRunnable implements Runnable {
        @Override
        public void run() {
            isExit = false;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (!isExit) {
                isExit = true;
                IocContainer.getShare().get(IDialog.class).showToastShort(getApplicationContext(), getString(R.string.exit_des));
                mHandler.postDelayed(new ExitRunnable(), 2000);
            } else {
                ActivityTack.getInstanse().exit(MainActivity.this);
            }
            return false;
        }
        return false;
    }

    public void onEventMainThread(BackHomeEB event) {
        setTab(event.getIndex());
    }

    @Override
    public void finish() {
        ActivityTack.getInstanse().removeActivity(this);
        super.finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onResume() {
        super.onResume();
        JPushInterface.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        JPushInterface.onPause(this);
    }

    public interface MyTouchListener {
        public void onTouchEvent(MotionEvent event);
    }

    // 保存MyTouchListener接口的列表
    private ArrayList<MyTouchListener> myTouchListeners = new ArrayList<MainActivity.MyTouchListener>();

    /**
     * 提供给Fragment通过getActivity()方法来注册自己的触摸事件的方法
     *
     * @param listener
     */
    public void registerMyTouchListener(MyTouchListener listener) {
        myTouchListeners.add(listener);
    }

    /**
     * 提供给Fragment通过getActivity()方法来取消注册自己的触摸事件的方法
     *
     * @param listener
     */
    public void unRegisterMyTouchListener(MyTouchListener listener) {
        myTouchListeners.remove(listener);
    }

    /**
     * 分发触摸事件给所有注册了MyTouchListener的接口
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        for (MyTouchListener listener : myTouchListeners) {
            listener.onTouchEvent(ev);
        }
        return super.dispatchTouchEvent(ev);
    }
}
