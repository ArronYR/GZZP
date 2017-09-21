package com.helloarron.gzzp.manage;

import com.helloarron.gzzp.R;

/**
 * Created by arron on 2017/5/8.
 */

public class BuilderManager {

    private static BuilderManager ourInstance = new BuilderManager();

    public static BuilderManager getInstance() {
        return ourInstance;
    }

    private BuilderManager() {
    }

    private static int imageResourceIndex = 0;
    private static int textResourceIndex = 0;

    private static int[] imageResources = new int[]{
            R.drawable.icon_wechat,
            R.drawable.icon_moments,
            R.drawable.icon_wx_collection,
            R.drawable.icon_collect,
            R.drawable.icon_browser
    };

    private static int[] textResources = new int[]{
            R.string.wechat,
            R.string.moments,
            R.string.collection,
            R.string.collect_title,
            R.string.open_browser
    };

    public static int getImageResource() {
        if (imageResourceIndex >= imageResources.length) imageResourceIndex = 0;
        return imageResources[imageResourceIndex++];
    }

    public static int getTextResource() {
        if (textResourceIndex >= textResources.length) textResourceIndex = 0;
        return textResources[textResourceIndex++];
    }
}
