package com.helloarron.gzzp.activity.main;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.helloarron.dhroid.ioc.IocContainer;
import com.helloarron.dhroid.net.DhNet;
import com.helloarron.dhroid.net.JSONUtil;
import com.helloarron.dhroid.net.NetTask;
import com.helloarron.dhroid.net.Response;
import com.helloarron.gzzp.R;
import com.helloarron.gzzp.api.API;
import com.helloarron.gzzp.base.GzzpBaseActivity;
import com.helloarron.gzzp.bean.User;
import com.helloarron.gzzp.manage.UserInfoManage;
import com.helloarron.gzzp.utils.GzzpPreference;

import org.json.JSONObject;

import cn.jpush.android.api.JPushInterface;

public class LoginActivity extends GzzpBaseActivity implements
        View.OnClickListener {

    private EditText etAccount, etPassword;
    private Button btnLogin;
    private TextView tvRegister;

    GzzpPreference per;

    public static UserInfoManage.LoginCallBack loginCall;
    boolean isLogin = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        setTitle(getString(R.string.login_title));
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

        etAccount = (EditText) findViewById(R.id.et_account);
        etPassword = (EditText) findViewById(R.id.et_password);
        btnLogin = (Button) findViewById(R.id.login);
        tvRegister = (TextView) findViewById(R.id.register);

        btnLogin.setOnClickListener(this);
        tvRegister.setOnClickListener(this);
    }

    @Override
    public void finish() {
        super.finish();
        if (loginCall != null) {
            if (isLogin) {
                loginCall.onisLogin();
            } else {
                loginCall.onLoginFail();
            }
        }
        loginCall = null;
    }

    @Override
    public void onClick(View v) {
        Intent it;
        switch (v.getId()) {
            // 登录
            case R.id.login:
                login();
                break;
            // 注册
            case R.id.register:
                it = new Intent(self, RegisterActivity.class);
                startActivityForResult(it, 0);
                break;
        }
    }

    private void login() {
        final String account = etAccount.getText().toString();
        final String password = etPassword.getText().toString();

        if (TextUtils.isEmpty(account) || !account.contains("@")) {
            showToast(getString(R.string.login_name_des));
            return;
        }
        if (TextUtils.isEmpty(password)) {
            showToast(getString(R.string.login_pwd_hint));
            return;
        }
        if (password.length() < 6 || password.length() > 15) {
            showToast(getString(R.string.editinfo_pwd_des));
            return;
        }

        DhNet gzzpNet = new DhNet(new API().login);
        gzzpNet.addParam("email", account);
        gzzpNet.addParam("password", password);
        gzzpNet.doPostInDialog(new NetTask(self) {
            @Override
            public void doInUI(Response response, Integer transfer) {
                if (response.isSuccess() && !response.isErrorCode()) {
                    JSONObject result = response.jSONFromResult();

                    per.setUid(JSONUtil.getString(result, "id"));
                    per.setName(JSONUtil.getString(result, "name"));
                    per.setEmail(account);
                    per.setPassword(password);
                    per.setToken(JSONUtil.getString(result, "token"));
                    per.commit();

                    User.getInstance().setLogin(true);
                    isLogin = true;

                    JPushInterface.setAliasAndTags(getApplicationContext(), "android" + per.getUid(), null, null);
                    finish();
                } else if (response.success) {
                    showToast(response.getErrorMsg());
                } else {
                    showToast(getString(R.string.net_bad));
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            String account = data.getStringExtra("account");
            etAccount.setText(account);
        }
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
