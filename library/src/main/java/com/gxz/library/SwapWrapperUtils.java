package com.gxz.library;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;

import com.gxz.library.view.SwipeMenuLayout;
import com.gxz.library.view.SwipeMenuView;

/**
 * ================================================
 * 作    者：顾修忠-guxiuzhong@youku.com/gfj19900401@163.com
 * 版    本：
 * 创建日期：16/7/10-上午12:17
 * 描    述：包装RecyclerView的item的辅助类
 * 修订历史：
 * ================================================
 */
public class SwapWrapperUtils {

    /***
     * 包装RecyclerView的item
     *
     * @param parent            parent
     * @param layoutId          layoutId
     * @param menuView          menuView 菜单的view
     * @param closeInterpolator closeInterpolator 关闭的差值器
     * @param openInterpolator  openInterpolator  打开的差值器
     * @return SwipeMenuLayout 包装完后的SwipeMenuLayout
     */
    public static SwipeMenuLayout wrap(ViewGroup parent, int layoutId, SwipeMenuView menuView, Interpolator closeInterpolator, Interpolator openInterpolator) {
        View contentView = LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false);
        SwipeMenuLayout swipeMenuLayout = new SwipeMenuLayout(contentView, menuView, closeInterpolator, openInterpolator);
        return swipeMenuLayout;
    }
}
