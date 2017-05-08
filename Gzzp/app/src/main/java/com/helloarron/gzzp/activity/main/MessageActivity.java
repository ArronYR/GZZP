package com.helloarron.gzzp.activity.main;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.helloarron.dhroid.adapter.NetJSONAdapter;
import com.helloarron.dhroid.net.DhNet;
import com.helloarron.dhroid.net.JSONUtil;
import com.helloarron.dhroid.net.NetTask;
import com.helloarron.dhroid.net.Response;
import com.helloarron.gzzp.R;
import com.helloarron.gzzp.api.API;
import com.helloarron.gzzp.base.Const;
import com.helloarron.gzzp.base.GzzpBaseActivity;
import com.helloarron.gzzp.manage.BuilderManager;
import com.helloarron.gzzp.views.RefreshListViewAndMore;
import com.nightonke.boommenu.Animation.BoomEnum;
import com.nightonke.boommenu.BoomButtons.ButtonPlaceEnum;
import com.nightonke.boommenu.BoomButtons.OnBMClickListener;
import com.nightonke.boommenu.BoomButtons.TextInsideCircleButton;
import com.nightonke.boommenu.BoomButtons.TextOutsideCircleButton;
import com.nightonke.boommenu.BoomMenuButton;
import com.nightonke.boommenu.ButtonEnum;
import com.nightonke.boommenu.Piece.PiecePlaceEnum;
import com.nightonke.boommenu.Util;

import org.json.JSONObject;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by arron on 2017/5/1.
 */

public class MessageActivity extends GzzpBaseActivity {

    String url = Const.GZZP;

    WebView wvContent;

    BoomMenuButton bmb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        setTitle(getString(R.string.message_title));
        setTitleVisible();
        setSearchBarGone();
        setRightIconVisible();
        setLeftAction(R.drawable.icon_back, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 对Activity进行finish的时候，webview持有的页面并不会立即释放，
                // 如果页面中有在执行js等其他操作，仅仅进行finish是完全不够的。
                wvContent.loadUrl("about:blank");
                Intent intent = getIntent();
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });

        setRightAction(R.drawable.icon_earth, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction("android.intent.action.VIEW");
                intent.setData(Uri.parse(url));
                startActivity(intent);
            }
        });
    }

    @Override
    public void initView() {
        Intent intent = getIntent();
        if (intent == null) {
            showErrorDialog(getString(R.string.some_errors));
            return;
        }

        String id = intent.getStringExtra("id");

        wvContent = (WebView) findViewById(R.id.wb_content);
        wvContent.getSettings().setDefaultTextEncodingName("UTF -8");
        WebSettings webSettings = wvContent.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setSupportZoom(true);        //支持缩放
        webSettings.setUseWideViewPort(false);   //将图片调整到适合webview的大小
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN); //支持内容重新布局
        webSettings.setAllowFileAccess(true);   //设置可以访问文件
        webSettings.setLoadsImagesAutomatically(true);  //支持自动加载图片

        getData(id);

        bmb = (BoomMenuButton) findViewById(R.id.bmb);
        bmb.setButtonEnum(ButtonEnum.TextOutsideCircle);
        bmb.setPiecePlaceEnum(PiecePlaceEnum.DOT_4_1);
        bmb.setButtonPlaceEnum(ButtonPlaceEnum.SC_4_1);
        for (int i = 0; i < bmb.getPiecePlaceEnum().pieceNumber(); i++) {
            TextOutsideCircleButton.Builder builder = new TextOutsideCircleButton.Builder()
                    .normalImageRes(BuilderManager.getImageResource())
                    .normalText(getString(BuilderManager.getTextResource()))
                    .listener(new OnBMClickListener() {
                        @Override
                        public void onBoomButtonClick(int index) {
                            // When the boom-button corresponding this builder is clicked.
                            Toast.makeText(self, "Clicked " + index, Toast.LENGTH_SHORT).show();
                        }
                    });
            bmb.addBuilder(builder);
        }
    }

    private void getData(String id) {
        DhNet gzzpNet = new DhNet(new API().message + id);
        gzzpNet.doGetInDialog(new NetTask(self) {

            @Override
            public void doInUI(Response response, Integer transfer) {
                if (response.isSuccess() && !response.isErrorCode()) {
                    JSONObject jo = response.jSONFromResult();
                    String content = JSONUtil.getString(jo, "content");

                    wvContent.loadDataWithBaseURL(null, content, "text/html", "UTF-8", null);
                    url = JSONUtil.getString(jo, "url");
                } else if (response.isSuccess()) {
                    showToast(response.getErrorMsg());
                } else {
                    showToast(getString(R.string.net_bad));
                }
            }
        });
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
