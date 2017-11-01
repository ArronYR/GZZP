package com.helloarron.gzzp.activity.main;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.helloarron.dhroid.ioc.IocContainer;
import com.helloarron.dhroid.net.DhNet;
import com.helloarron.dhroid.net.JSONUtil;
import com.helloarron.dhroid.net.NetTask;
import com.helloarron.dhroid.net.Response;
import com.helloarron.gzzp.R;
import com.helloarron.gzzp.api.API;
import com.helloarron.gzzp.base.Const;
import com.helloarron.gzzp.base.GzzpBaseActivity;
import com.helloarron.gzzp.manage.BuilderManager;
import com.helloarron.gzzp.manage.WxShareManager;
import com.helloarron.gzzp.utils.GzzpPreference;
import com.nightonke.boommenu.BoomButtons.ButtonPlaceEnum;
import com.nightonke.boommenu.BoomButtons.OnBMClickListener;
import com.nightonke.boommenu.BoomButtons.TextInsideCircleButton;
import com.nightonke.boommenu.BoomMenuButton;
import com.nightonke.boommenu.ButtonEnum;
import com.nightonke.boommenu.Piece.PiecePlaceEnum;

import org.json.JSONObject;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by arron on 2017/5/1.
 */

public class MessageActivity extends GzzpBaseActivity {

    public GzzpPreference per;

    private Context context;
    public String url = Const.GZZP;
    public String title, description;

    public WebView wvContent;

    // 分享按钮
    public BoomMenuButton boomMenuButton;
    public WxShareManager shareManager;
    public WxShareManager.ShareContentWebpage shareContentWebpage;

    public String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        context = this;

        setTitle(getString(R.string.message_title));
        setTitleVisible();
        setSearchBarGone();
        setRightIconGone();
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

        shareManager = WxShareManager.getInstance(context);
        per = IocContainer.getShare().get(GzzpPreference.class);
        per.load();
    }

    @Override
    public void initView() {
        Intent intent = getIntent();
        if (intent == null) {
            showErrorDialog(getString(R.string.some_errors));
            return;
        }

        id = intent.getStringExtra("id");

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

        boomMenuButton = (BoomMenuButton) findViewById(R.id.bmb);
        boomMenuButton.setButtonEnum(ButtonEnum.TextInsideCircle);
        boomMenuButton.setPiecePlaceEnum(PiecePlaceEnum.DOT_5_3);
        boomMenuButton.setButtonPlaceEnum(ButtonPlaceEnum.SC_5_3);
        for (int i = 0; i < boomMenuButton.getPiecePlaceEnum().pieceNumber(); i++) {
            TextInsideCircleButton.Builder builder = new TextInsideCircleButton.Builder()
                    .rippleEffect(true)
                    .normalImageRes(BuilderManager.getImageResource())
                    .normalTextRes(BuilderManager.getTextResource())
                    .normalColorRes(BuilderManager.getColorResource())
                    .listener(new OnBMClickListener() {
                        @Override
                        public void onBoomButtonClick(int index) {
                            switch (index) {
                                case 0:
                                    shareManager.shareByWeixin(shareContentWebpage, WxShareManager.WEIXIN_SHARE_TYPE_TALK);
                                    break;
                                case 1:
                                    shareManager.shareByWeixin(shareContentWebpage, WxShareManager.WEIXIN_SHARE_TYPE_FRENDS);
                                    break;
                                case 2:
                                    shareManager.shareByWeixin(shareContentWebpage, WxShareManager.WEIXIN_SHARE_TYPE_FAVORITE);
                                    break;
                                case 3:
                                    collect(id);
                                    break;
                                case 4:
                                    openInBrowser(url);
                                    break;
                                default:
                                    break;
                            }
                        }
                    });
            boomMenuButton.addBuilder(builder);
        }
    }

    /**
     * 获取招聘详情
     *
     * @param id
     */
    private void getData(final String id) {
        DhNet gzzpNet = new DhNet(new API().MESSAGE + id);
        gzzpNet.doGetInDialog(new NetTask(self) {

            @Override
            public void doInUI(Response response, Integer transfer) {
                if (response.isSuccess() && !response.isErrorCode()) {
                    JSONObject jo = response.jSONFromResult();
                    String content = JSONUtil.getString(jo, "content");

                    wvContent.loadDataWithBaseURL(null, content, "text/html", "UTF-8", null);
                    url = Const.WEB_PAGE + id;
                    title = JSONUtil.getString(jo, "title");
                    description = "【" + JSONUtil.getString(jo, "type_text") + "】" + JSONUtil.getString(jo, "title");

                    shareContentWebpage = shareManager.new ShareContentWebpage(title, description, url, R.mipmap.ic_launcher);
                    boomMenuButton.setVisibility(View.VISIBLE);
                } else if (response.isSuccess()) {
                    showToast(response.getErrorMsg());
                } else {
                    showToast(getString(R.string.net_bad));
                }
            }
        });
    }

    /**
     * 收藏
     *
     * @param mid
     */
    private void collect(String mid) {
        String uid = per.getUid();
        if (TextUtils.isEmpty(uid)) {
            showToast(getString(R.string.need_login));
            return;
        }
        DhNet gzzpNet = new DhNet(new API().COLLECTION);
        gzzpNet.addParam("uid", uid);
        gzzpNet.addParam("mid", mid);
        gzzpNet.doPost(new NetTask(self) {
            @Override
            public void doInUI(Response response, Integer transfer) {
                if (response.isSuccess() && !response.isErrorCode()) {
                    showToast(getString(R.string.add_collect));
                } else if (response.success) {
                    showToast(response.getErrorMsg());
                } else {
                    showToast(getString(R.string.net_bad));
                }
            }
        });
    }

    /**
     * 浏览器打开
     *
     * @param url
     */
    public void openInBrowser(String url) {
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        intent.setData(Uri.parse(url));
        startActivity(intent);
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
