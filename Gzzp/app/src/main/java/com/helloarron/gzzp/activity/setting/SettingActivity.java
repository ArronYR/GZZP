package com.helloarron.gzzp.activity.setting;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.helloarron.dhroid.ioc.IocContainer;
import com.helloarron.gzzp.R;
import com.helloarron.gzzp.activity.main.LoginActivity;
import com.helloarron.gzzp.base.GzzpBaseActivity;
import com.helloarron.gzzp.bean.BackHomeEB;
import com.helloarron.gzzp.bean.User;
import com.helloarron.gzzp.manage.UserInfoManage;
import com.helloarron.gzzp.utils.GzzpPreference;
import com.helloarron.gzzp.utils.GzzpUtils;
import com.helloarron.gzzp.views.VersionDialog;
import com.pgyersdk.feedback.PgyFeedback;
import com.pgyersdk.views.PgyerDialog;

import cn.jpush.android.api.JPushInterface;
import cn.pedant.SweetAlert.SweetAlertDialog;
import de.greenrobot.event.EventBus;

public class SettingActivity extends GzzpBaseActivity implements View.OnClickListener {

    private GzzpPreference per;
    private LinearLayout llUser, llVersion, llSupport, llWipeCache, llLogout, llFeedback;
    private Button btnLogout;
    private TextView tvUserTip, tvUserName, tvVersionName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        setTitle(getString(R.string.system_title));
        setTitleVisible();
        setSearchBarGone();
        setLeftAction(R.drawable.icon_back, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void initView() {
        per = IocContainer.getShare().get(GzzpPreference.class);
        per.load();

        llUser = (LinearLayout) findViewById(R.id.ll_user);
        llVersion = (LinearLayout) findViewById(R.id.ll_version);
        llSupport = (LinearLayout) findViewById(R.id.ll_support);
        llWipeCache = (LinearLayout) findViewById(R.id.ll_wipe_cache);
        llLogout = (LinearLayout) findViewById(R.id.ll_logout);
        llFeedback = (LinearLayout) findViewById(R.id.ll_feedback);

        btnLogout = (Button) findViewById(R.id.logout);
        tvUserTip = (TextView) findViewById(R.id.tv_user_tip);
        tvUserName = (TextView) findViewById(R.id.tv_username);
        tvVersionName = (TextView) findViewById(R.id.tv_version_name);

        llVersion.setOnClickListener(this);
        llSupport.setOnClickListener(this);
        llWipeCache.setOnClickListener(this);
        llFeedback.setOnClickListener(this);
        btnLogout.setOnClickListener(this);

        User user = User.getInstance();
        if (user.isLogin) {
            tvUserName.setText("" + per.getName());
            tvUserTip.setText(getString(R.string.current_user));
            llLogout.setVisibility(View.VISIBLE);
        } else {
            tvUserTip.setText(getString(R.string.login_title));
            llUser.setOnClickListener(this);
            llLogout.setVisibility(View.GONE);
        }

        tvVersionName.setText(GzzpUtils.getVersionName(this));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_user:
                login();
                break;
            case R.id.ll_version:
                VersionDialog versionDialog = new VersionDialog(this);
                versionDialog.show();
                break;
            case R.id.ll_support:
                Intent intent = new Intent(SettingActivity.this, SupportActivity.class);
                startActivity(intent);
                break;
            case R.id.ll_wipe_cache:
                wipeCache();
                break;
            case R.id.logout:
                logout();
                break;
            case R.id.ll_feedback:
                feedback();
                break;
        }
    }

    private void feedback() {
        // 以对话框的形式弹出
        PgyerDialog.setDialogTitleBackgroundColor("#26a69a");
        PgyerDialog.setDialogTitleTextColor("#ffffff");

        if (TextUtils.isEmpty(per.getUid())) {
            PgyFeedback.getInstance().setMoreParam("uid", per.getUid());
        }
        PgyFeedback.getInstance().showDialog(this);
    }

    private void login() {
        UserInfoManage.getInstance().checkLogin(self, new UserInfoManage.LoginCallBack() {
            @Override
            public void onisLogin() {
                // 更新页面状态
                initView();
            }

            @Override
            public void onLoginFail() {

            }
        });
    }

    private void wipeCache() {
        SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                .setTitleText(getString(R.string.wipe_cache))
                .setContentText(getString(R.string.wipe_cache_sure))
                .setConfirmText(getString(R.string.sure))
                .setCancelText(getString(R.string.cancel))
                .showCancelButton(true)
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        per.clear();
                        per.commit();
                        showToast(getString(R.string.wipe_success));
                        sDialog.dismissWithAnimation();
                    }
                });
        sweetAlertDialog.setCanceledOnTouchOutside(true);
        sweetAlertDialog.setCancelable(true);
        sweetAlertDialog.show();
    }

    private void logout() {
        SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                .setTitleText(getString(R.string.exit))
                .setContentText(getString(R.string.exit_desc))
                .setConfirmText(getString(R.string.sure))
                .setCancelText(getString(R.string.cancel))
                .showCancelButton(true)
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.dismissWithAnimation();
                        per.clear();
                        User.getInstance().setLogin(false);
                        EventBus.getDefault().post(new BackHomeEB());
                        Intent it = new Intent(self, LoginActivity.class);
                        startActivity(it);
                        finish();
                    }
                });
        sweetAlertDialog.setCanceledOnTouchOutside(true);
        sweetAlertDialog.setCancelable(true);
        sweetAlertDialog.show();
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
}
