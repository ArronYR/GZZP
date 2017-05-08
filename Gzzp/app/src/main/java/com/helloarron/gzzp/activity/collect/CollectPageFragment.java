package com.helloarron.gzzp.activity.collect;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.helloarron.dhroid.adapter.FieldMap;
import com.helloarron.dhroid.adapter.INetAdapter;
import com.helloarron.dhroid.adapter.NetJSONAdapter;
import com.helloarron.dhroid.ioc.IocContainer;
import com.helloarron.dhroid.net.DhNet;
import com.helloarron.dhroid.net.JSONUtil;
import com.helloarron.dhroid.net.NetTask;
import com.helloarron.dhroid.net.Response;
import com.helloarron.gzzp.R;
import com.helloarron.gzzp.activity.main.LoginActivity;
import com.helloarron.gzzp.activity.main.MessageActivity;
import com.helloarron.gzzp.api.API;
import com.helloarron.gzzp.base.GzzpBaseFagment;
import com.helloarron.gzzp.utils.GzzpPreference;
import com.helloarron.gzzp.views.LoadMoreEmptyView;
import com.helloarron.gzzp.views.RefreshListViewAndMore;

import org.json.JSONObject;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by arron on 2017/3/13.
 */

public class CollectPageFragment extends GzzpBaseFagment {

    static CollectPageFragment instance;

    public Activity self;
    GzzpPreference per;

    View mainV, emptyV;
    LayoutInflater mLayoutInflater;

    RefreshListViewAndMore listV;
    ListView contentListV;
    NetJSONAdapter adapter;

    String uid = "";

    public static CollectPageFragment getInstance() {
        if (instance == null) {
            instance = new CollectPageFragment();
        }

        return instance;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mainV = inflater.inflate(R.layout.fragment_collect_page, null);
        mLayoutInflater = inflater;

        per = IocContainer.getShare().get(GzzpPreference.class);
        per.load();
        uid = per.getUid();

        self = getActivity();
        setTitle(mainV, getString(R.string.collect_title));
        setTitleVisible(mainV);
        setLeftIconGone(mainV);
        setSearchBarGone(mainV);
        initView();

        return mainV;
    }

    private void initView() {
        listV = (RefreshListViewAndMore) mainV.findViewById(R.id.my_listview);
        emptyV = new LoadMoreEmptyView(self).setContentText(getString(R.string.collection_tip));
        listV.setEmptyView(emptyV);
        contentListV = listV.getListView();

        adapter = new NetJSONAdapter(API.collections + uid, self, R.layout.item_message_list);
        adapter.fromWhat("result.data");
        adapter.addField("message.title", R.id.tv_message_title);
        adapter.addField(new FieldMap("message.published_at", R.id.tv_message_time) {
            @Override
            public Object fix(View itemV, Integer position, Object o, Object jo) {
                return getString(R.string.published_at) + getString(R.string.colon_symbol) + o;
            }
        });
        adapter.addField(new FieldMap("message.type_text", R.id.tv_message_type) {
            @Override
            public Object fix(View itemV, Integer position, Object o, Object jo) {
                JSONObject data = (JSONObject) jo;
                JSONObject message = JSONUtil.getJSONObject(data, "message");
                Log.d("main", message.toString());
                TextView tvTitle = (TextView) itemV.findViewById(R.id.tv_message_title);
                String color = JSONUtil.getString(message, "color");
                if (!"".equals(color) && !TextUtils.isEmpty(color)) {
                    tvTitle.setTextColor(Color.parseColor(color));
                }
                return getString(R.string.type) + getString(R.string.colon_symbol) + o;
            }
        });
        adapter.setOnLoadSuccess(new INetAdapter.LoadSuccessCallBack() {
            @Override
            public void callBack(Response response) {
                JSONObject result = response.jSONFromResult();
            }
        });

        contentListV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                JSONObject jo = adapter.getTItem(position);
                Intent intent = new Intent(getActivity(), MessageActivity.class);
                intent.putExtra("id", JSONUtil.getString(jo, "message_id"));
                startActivity(intent);
            }
        });

        contentListV.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                JSONObject jo = adapter.getTItem(position);
                final String collection_id = JSONUtil.getString(jo, "id");
                promptCollect(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismissWithAnimation();
                        unfavorite(collection_id);
                    }
                });
                return true;
            }
        });

        listV.setAdapter(adapter);
    }

    private void unfavorite(String id) {
        String uid = per.getUid();

        DhNet gzzpNet = new DhNet(new API().unfavorite);
        gzzpNet.addParam("id", id);
        gzzpNet.doPost(new NetTask(self) {
            @Override
            public void doInUI(Response response, Integer transfer) {
                if (response.isSuccess() && !response.isErrorCode()) {
                    showToast(self, getString(R.string.cancel_collect));
                    adapter.refresh();
                } else if (response.success) {
                    showToast(self, response.getErrorMsg());
                } else {
                    showToast(self, getString(R.string.net_bad));
                }
            }
        });
    }

    private void promptCollect(SweetAlertDialog.OnSweetClickListener listener) {
        SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(self, SweetAlertDialog.WARNING_TYPE)
                .setTitleText(getString(R.string.cancel_collect_title))
                .setContentText(getString(R.string.cancel_collect_tip))
                .setConfirmText(getString(R.string.sure))
                .setCancelText(getString(R.string.cancel))
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        adapter.refresh();
    }
}
