package com.gxz.swapmenurecyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

/**
 * ================================================
 * 作    者：顾修忠-guxiuzhong@youku.com/gfj19900401@163.com
 * 版    本：
 * 创建日期：16/7/8-下午6:10
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class MyViewHolder extends RecyclerView.ViewHolder {

    public TextView textView;

    public MyViewHolder(View itemView) {
        super(itemView);
        textView = (TextView) itemView.findViewById(R.id.id_tv);

    }
}
