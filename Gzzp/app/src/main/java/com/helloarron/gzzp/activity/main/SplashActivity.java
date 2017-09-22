package com.helloarron.gzzp.activity.main;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.helloarron.dhroid.ioc.IocContainer;
import com.helloarron.dhroid.net.DhNet;
import com.helloarron.dhroid.net.JSONUtil;
import com.helloarron.dhroid.net.NetTask;
import com.helloarron.dhroid.net.Response;
import com.helloarron.gzzp.R;
import com.helloarron.gzzp.api.API;
import com.helloarron.gzzp.base.Const;
import com.helloarron.gzzp.base.GzzpBaseActivity;
import com.helloarron.gzzp.bean.User;
import com.helloarron.gzzp.utils.GzzpPreference;
import com.pgyersdk.update.PgyUpdateManager;

import org.json.JSONObject;

/**
 * 欢迎页
 *
 * @author Administrator
 */
public class SplashActivity extends GzzpBaseActivity {

    private final Handler mHandler = new Handler();

    GzzpPreference per;
    private ImageView imgWelcome;
    private TextView tvLoading;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == Const.LOADED) {
                // 清空动画
                imgWelcome.clearAnimation();
                tvLoading.clearAnimation();
            } else {
                imgWelcome.setAnimation(AnimationUtils.loadAnimation(SplashActivity.this, R.anim.loading_img_scale));
                tvLoading.setAnimation(AnimationUtils.loadAnimation(SplashActivity.this, R.anim.loading_text_alpha));
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 全屏设置，隐藏窗口所有装饰
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        // 标题是属于View的，所以窗口所有的修饰部分被隐藏后标题依然有效,需要去掉标题
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_splash);

        handler.sendEmptyMessage(Const.LOADING);
    }

    @Override
    public void initView() {
        per = IocContainer.getShare().get(GzzpPreference.class);
        per.load();

        imgWelcome = (ImageView) findViewById(R.id.img_welcome);
        tvLoading = (TextView) findViewById(R.id.tv_loading);

        if (!TextUtils.isEmpty(per.name) && !TextUtils.isEmpty(per.password)) {
            login();
        } else {
            notFirst();
        }
    }

    // 登录
    private void login() {
        DhNet net = new DhNet(new API().login);
        net.addParam("password", per.password);
        net.addParam("email", per.email);

        net.doPost(new NetTask(self) {
            @Override
            public void doInUI(Response response, Integer transfer) {
                if (response.isSuccess() && !response.isErrorCode()) {
                    JSONObject result = response.jSONFromResult();

                    per.setUid(JSONUtil.getString(result, "id"));
                    per.setName(JSONUtil.getString(result, "name"));
                    per.setEmail(JSONUtil.getString(result, "email"));
                    per.setToken(JSONUtil.getString(result, "token"));
                    per.commit();

                    User.getInstance().setLogin(true);
                } else if (response.isSuccess()) {
                    showToast(response.getErrorMsg());

                    per.clear();
                    User.getInstance().setLogin(false);

                    handler.sendEmptyMessageDelayed(Const.LOADED, 0);

                    Intent it = new Intent(self, MainActivity.class);
                    startActivity(it);
                    finishWithoutAnim();
                }
                notFirst();
            }

            @Override
            public void onErray(Response response) {
                super.onErray(response);
                notFirst();
            }
        });
    }

    private void notFirst() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                handler.sendEmptyMessageDelayed(Const.LOADED, 0);

                Intent intent = new Intent(self, MainActivity.class);
                startActivity(intent);
                finishWithoutAnim();
            }
        }, 2000);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
