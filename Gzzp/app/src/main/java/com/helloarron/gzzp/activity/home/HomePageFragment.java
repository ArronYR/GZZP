package com.helloarron.gzzp.activity.home;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.helloarron.gzzp.activity.main.MainActivity;
import com.helloarron.gzzp.activity.main.MessageActivity;
import com.helloarron.gzzp.activity.setting.SettingActivity;
import com.helloarron.gzzp.api.API;
import com.helloarron.gzzp.base.GzzpBaseFagment;
import com.helloarron.gzzp.utils.GzzpPreference;
import com.helloarron.gzzp.views.LoadMoreEmptyView;
import com.helloarron.gzzp.views.RefreshListViewAndMore;

import org.json.JSONObject;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by arron on 2017/5/1.
 */

public class HomePageFragment extends GzzpBaseFagment implements View.OnClickListener {

    public Activity self;
    GzzpPreference per;

    static HomePageFragment instance;
    View mainV, emptyV;
    LayoutInflater mLayoutInflater;

    RefreshListViewAndMore listV;
    ListView contentListV;
    NetJSONAdapter adapter;

    private EditText etSearch;
    private ImageView imSearch, imClear;
    private String searchText = "";

    public static HomePageFragment getInstance() {
        if (instance == null) {
            instance = new HomePageFragment();
        }

        return instance;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mainV = inflater.inflate(R.layout.fragment_home_page, null);
        mLayoutInflater = inflater;

        per = IocContainer.getShare().get(GzzpPreference.class);
        per.load();

        self = getActivity();
        setTitle(mainV, getString(R.string.home_title));
        setSearchBarVisible(mainV);
        setLeftAction(mainV, R.drawable.icon_system, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(self, SettingActivity.class);
                startActivity(intent);
            }
        });
        initView();

        /**
         * Fragment中，注册接收MainActivity的Touch回调的对象
         * 重写其中的onTouchEvent函数，并进行该Fragment的逻辑处理
         */
        MainActivity.MyTouchListener myTouchListener = new MainActivity.MyTouchListener() {
            @Override
            public void onTouchEvent(MotionEvent event) {
                // 处理手势事件
                InputMethodManager manager = (InputMethodManager) self.getSystemService(Context.INPUT_METHOD_SERVICE);
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    if (self.getCurrentFocus() != null && self.getCurrentFocus().getWindowToken() != null) {
                        manager.hideSoftInputFromWindow(self.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    }
                }
            }
        };
        // 将myTouchListener注册到分发列表
        ((MainActivity) this.getActivity()).registerMyTouchListener(myTouchListener);

        return mainV;
    }

    private void initView() {
        listV = (RefreshListViewAndMore) mainV.findViewById(R.id.my_listview);
        emptyV = new LoadMoreEmptyView(self);
        listV.setEmptyView(emptyV);
        contentListV = listV.getListView();

        etSearch = (EditText) mainV.findViewById(R.id.et_search);
        imSearch = (ImageView) mainV.findViewById(R.id.im_search_icon);
        imClear = (ImageView) mainV.findViewById(R.id.im_clear);

        imSearch.setOnClickListener(this);
        imClear.setOnClickListener(this);
        etSearch.addTextChangedListener(watcher);
        etSearch.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    closeInputMethod();
                }
            }
        });

        getData(searchText);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.im_search_icon:
                etSearch.clearFocus();
                searchText = etSearch.getText().toString();
                getData(searchText);
                break;
            case R.id.im_clear:
                etSearch.setText("");
                searchText = "";
                getData(searchText);
                break;
        }
    }

    private void getData(String keyword) {
        adapter = new NetJSONAdapter(API.messages, self, R.layout.item_message_list);
        adapter.addparam("keyword", keyword);
        adapter.addparam("type", 1);
        adapter.fromWhat("result.data");
        adapter.addField("title", R.id.tv_message_title);
        adapter.addField(new FieldMap("published_at", R.id.tv_message_time) {
            @Override
            public Object fix(View itemV, Integer position, Object o, Object jo) {
                return getString(R.string.published_at) + getString(R.string.colon_symbol) + o;
            }
        });
        adapter.addField(new FieldMap("type_text", R.id.tv_message_type) {
            @Override
            public Object fix(View itemV, Integer position, Object o, Object jo) {
                JSONObject data = (JSONObject) jo;
                TextView tvTitle = (TextView) itemV.findViewById(R.id.tv_message_title);
                String color = JSONUtil.getString(data, "color");
                if (!"".equals(color)) {
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
                intent.putExtra("id", JSONUtil.getString(jo, "id"));
                startActivity(intent);
            }
        });

        contentListV.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                JSONObject jo = adapter.getTItem(position);
                final String mid = JSONUtil.getString(jo, "id");
                promptCollect(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismissWithAnimation();
                        collect(mid);
                    }
                });
                return true;
            }
        });

        adapter.refresh();
        listV.setAdapter(adapter);
    }

    private void collect(String mid) {
        String uid = per.getUid();
        if (TextUtils.isEmpty(uid)) {
            showToast(self, getString(R.string.need_login));
            return;
        }
        DhNet gzzpNet = new DhNet(new API().collection);
        gzzpNet.addParam("uid", uid);
        gzzpNet.addParam("mid", mid);
        gzzpNet.doPost(new NetTask(self) {
            @Override
            public void doInUI(Response response, Integer transfer) {
                if (response.isSuccess() && !response.isErrorCode()) {
                    showToast(self, getString(R.string.add_collect));
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
                .setTitleText(getString(R.string.collect_title))
                .setContentText(getString(R.string.collect_alert_content))
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

    private void closeInputMethod() {
        InputMethodManager imm = (InputMethodManager) self.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive()) {
            // imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);//没有显示则显示
            imm.hideSoftInputFromWindow(etSearch.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    private TextWatcher watcher = new TextWatcher() {

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (s.length() == 0) {
                imClear.setVisibility(View.GONE);
            } else {
                imClear.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            // TODO Auto-generated method stub
        }

        @Override
        public void afterTextChanged(Editable s) {
            // TODO Auto-generated method stub
        }
    };
}
