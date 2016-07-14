package com.gxz.library.bean;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

/**
 * ================================================
 * <br/>作    者：顾修忠-guxiuzhong@youku.com/gfj19900401@163.com
 * <br/>版    本：
 * <br/>创建日期：16/7/8-下午5:10
 * <br/>描    述：包含SwipeMenuItem对象的辅助类
 * <br/>修订历史：
 * <br/>================================================
 */
public class SwipeMenu {

    private Context mContext;
    private List<SwipeMenuItem> mItems;

    public SwipeMenu(Context context) {
        mContext = context;
        mItems = new ArrayList<>();
    }

    public Context getContext() {
        return mContext;
    }

    public void addMenuItem(SwipeMenuItem item) {
        mItems.add(item);
    }

    public void removeMenuItem(SwipeMenuItem item) {
        mItems.remove(item);
    }

    public List<SwipeMenuItem> getMenuItems() {
        return mItems;
    }

    public SwipeMenuItem getMenuItem(int index) {
        return mItems.get(index);
    }

}
