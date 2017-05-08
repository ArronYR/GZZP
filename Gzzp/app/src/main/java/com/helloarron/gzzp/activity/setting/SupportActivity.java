package com.helloarron.gzzp.activity.setting;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.helloarron.gzzp.R;
import com.helloarron.gzzp.base.GzzpBaseActivity;
import com.helloarron.gzzp.utils.ImageUtil;

import cn.jpush.android.api.JPushInterface;
import cn.pedant.SweetAlert.SweetAlertDialog;

public class SupportActivity extends GzzpBaseActivity {
    private Context context;

    private ImageView imWxPay;
    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_support);
        this.context = this;

        setTitle(getString(R.string.support));
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
        imWxPay = (ImageView) findViewById(R.id.im_wx_pay);
        bitmap = ((BitmapDrawable) imWxPay.getDrawable()).getBitmap();

        imWxPay.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                promptSave(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.dismissWithAnimation();
                        ImageUtil.saveImageToGallery(context, bitmap, false);
                    }
                });
                return false;
            }
        });
    }

    private void promptSave(SweetAlertDialog.OnSweetClickListener listener) {
        SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
                .setTitleText(getString(R.string.save))
                .setContentText(getString(R.string.pay_alert_content))
                .setConfirmText(getString(R.string.support_sure))
                .setCancelText(getString(R.string.support_cancel))
                .showCancelButton(true)
                .setConfirmClickListener(listener)
                .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.cancel();
                    }
                });
        sweetAlertDialog.setCanceledOnTouchOutside(true);
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
