package com.gxz.library.bean;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.util.TypedValue;

/**
 * ================================================
 * 作    者：顾修忠-guxiuzhong@youku.com/gfj19900401@163.com
 * 版    本：
 * 创建日期：16/7/8-下午5:00
 * 描    述：menu的数据对象
 * 修订历史：
 * ================================================
 */
public class SwipeMenuItem {

    private static final int TITLE_SIZE = 20;//sp
    private static final int WIDTH = 80;//dp
    private int id;
    private Context mContext;
    private String title;
    private Drawable icon;
    private Drawable background;
    private int titleColor;
    private int titleSize;
    private int width;

    public SwipeMenuItem(Context context) {
        mContext = context;
        //设置默认值
        DisplayMetrics dm = mContext.getResources().getDisplayMetrics();
        titleColor = Color.WHITE;
        titleSize = TITLE_SIZE;
        width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, WIDTH, dm);
    }

    public int getId() {
        return id;
    }

    public SwipeMenuItem setId(int id) {
        this.id = id;
        return this;
    }

    public Context getmContext() {
        return mContext;
    }

    public SwipeMenuItem setmContext(Context mContext) {
        this.mContext = mContext;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public SwipeMenuItem setTitle(String title) {
        this.title = title;
        return this;
    }

    public Drawable getIcon() {
        return icon;
    }

    public SwipeMenuItem setIcon(Drawable icon) {
        this.icon = icon;
        return this;
    }

    public SwipeMenuItem setIcon(int resId) {
        this.icon = mContext.getResources().getDrawable(resId);
        return this;
    }

    public Drawable getBackground() {
        return background;
    }

    public SwipeMenuItem setBackground(Drawable background) {
        this.background = background;
        return this;
    }

    public int getTitleColor() {
        return titleColor;
    }

    public SwipeMenuItem setTitleColor(int titleColor) {
        this.titleColor = titleColor;
        return this;
    }

    public int getTitleSize() {
        return titleSize;
    }

    public SwipeMenuItem setTitleSize(int titleSize) {
        this.titleSize = titleSize;
        return this;
    }

    public int getWidth() {
        return width;
    }

    public SwipeMenuItem setWidth(int width) {
        this.width = width;
        return this;
    }
}
