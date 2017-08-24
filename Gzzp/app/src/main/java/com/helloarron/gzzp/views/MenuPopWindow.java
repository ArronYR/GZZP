package com.helloarron.gzzp.views;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.helloarron.gzzp.R;
import com.helloarron.gzzp.adapter.MenuPopAdapter;

/**
 * Created by arron on 2017/3/19.
 */

public class MenuPopWindow extends PopupWindow {

    private View contentView;
    private ListView lvContent;

    public MenuPopWindow(Activity context, String[] list) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        contentView = inflater.inflate(R.layout.menu_pop, null);
        lvContent = (ListView) contentView.findViewById(R.id.lv_menu);
        lvContent.setAdapter(new MenuPopAdapter(context, list));
        int h = context.getWindowManager().getDefaultDisplay().getHeight();
        int w = context.getWindowManager().getDefaultDisplay().getWidth();
        // 设置SelectPicPopupWindow的View
        this.setContentView(contentView);
        // 设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(w / 2);
        // 设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        // 设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        // 刷新状态
        this.update();
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0000000000); // 0000000000
        // 点back键和其他地方使其消失,设置了这个才能触发OnDismissListener ，设置其他控件变化等操作
        this.setBackgroundDrawable(dw);
        // 设置SelectPicPopupWindow弹出窗体动画效果
        // this.setAnimationStyle(android.R.style.Animation_Dialog);
        // this.setAnimationStyle(R.style.PopupAnimation);
    }

    public void setOnItemClick(AdapterView.OnItemClickListener myOnItemClickListener) {
        lvContent.setOnItemClickListener(myOnItemClickListener);
    }

    /**
     * 显示popupWindow
     *
     * @param parent
     */
    public void showPopupWindow(View parent) {
        if (!this.isShowing()) {
            // 以下拉方式显示popupwindow
            int parent_width = parent.getWidth();//获取对应的控件view宽度px值
            int width = parent_width / 4;//获取x轴偏移量px
            this.showAsDropDown(parent, width, 0);
        } else {
            this.dismiss();
        }
    }

}
