package com.helloarron.gzzp.activity.main;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.helloarron.dhroid.net.DhNet;
import com.helloarron.dhroid.net.NetTask;
import com.helloarron.dhroid.net.Response;
import com.helloarron.gzzp.R;
import com.helloarron.gzzp.api.API;
import com.helloarron.gzzp.base.GzzpBaseActivity;

public class RegisterActivity extends GzzpBaseActivity implements
        View.OnClickListener {

    private EditText etAccount, etPassword, etNickname;
    private Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        setTitle(getString(R.string.register_title));
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
        etAccount = (EditText) findViewById(R.id.et_account);
        etPassword = (EditText) findViewById(R.id.et_password);
        etNickname = (EditText) findViewById(R.id.et_nickname);
        btnRegister = (Button) findViewById(R.id.btn_register);

        btnRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_register:
                register();
                break;
        }
    }

    private void register() {
        final String account = etAccount.getText().toString();
        String password = etPassword.getText().toString();
        String nickname = etNickname.getText().toString();

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
        if (TextUtils.isEmpty(nickname)) {
            showToast(getString(R.string.nickname_hint));
            return;
        }
        if (nickname.length() > 12) {
            showToast(getString(R.string.nickname_to_long));
            return;
        }
        DhNet gzzpNet = new DhNet(new API().REGISTER);
        gzzpNet.addParam("email", account);
        gzzpNet.addParam("password", password);
        gzzpNet.addParam("nickname", nickname);
        gzzpNet.doPostInDialog(new NetTask(self) {
            @Override
            public void doInUI(Response response, Integer transfer) {
                if (response.isSuccess() && !response.isErrorCode()) {
                    showToast(getString(R.string.register_success));

                    Intent it = new Intent();
                    it.putExtra("account", account);
                    setResult(RESULT_OK, it);
                    finish();
                } else if (response.success) {
                    showToast(response.getErrorMsg());
                } else {
                    showToast(getString(R.string.net_bad));
                }
            }
        });
    }
}
