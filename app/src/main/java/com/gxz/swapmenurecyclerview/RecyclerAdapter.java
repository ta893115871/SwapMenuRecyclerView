package com.gxz.swapmenurecyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.BounceInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.Toast;

import com.gxz.library.SwapWrapperUtils;
import com.gxz.library.SwipeMenuBuilder;
import com.gxz.library.view.SwipeMenuLayout;
import com.gxz.library.view.SwipeMenuView;

import java.util.List;


/**
 * ================================================
 * 作    者：顾修忠-guxiuzhong@youku.com/gfj19900401@163.com
 * 版    本：
 * 创建日期：16/7/8-下午6:11
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class RecyclerAdapter<T> extends RecyclerView.Adapter {
    private List<T> mData;
    private Context mContext;
    private SwipeMenuBuilder swipeMenuBuilder;

    public RecyclerAdapter(List<T> mData, Context context) {
        this.mData = mData;
        this.mContext = context;
        swipeMenuBuilder = (SwipeMenuBuilder) this.mContext;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //根据数据创建右边的操作view
        SwipeMenuView menuView = swipeMenuBuilder.create();
        //包装用户的item布局
        SwipeMenuLayout swipeMenuLayout = SwapWrapperUtils.wrap(parent, R.layout.item, menuView, new BounceInterpolator(), new LinearInterpolator());
        MyViewHolder holder = new MyViewHolder(swipeMenuLayout);
        setListener(parent, holder, viewType);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        MyViewHolder viewHolder = (MyViewHolder) holder;
        viewHolder.textView.setText(mData.get(position).toString());
        viewHolder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "textView-onClick->>>" + mData.get(position).toString(), Toast.LENGTH_LONG).show();

            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void remove(int pos) {
        this.notifyItemRemoved(pos);
    }

    protected void setListener(final ViewGroup parent, final MyViewHolder viewHolder, int viewType) {
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) {
                    int position = viewHolder.getAdapterPosition();
                    mOnItemClickListener.onItemClick(v, viewHolder, mData.get(position), position);
                }
            }
        });

        viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (mOnItemClickListener != null) {
                    int position = viewHolder.getAdapterPosition();
                    return mOnItemClickListener.onItemLongClick(v, viewHolder, mData.get(position), position);
                }
                return false;
            }
        });
    }


    public OnItemClickListener<T> mOnItemClickListener;

    public interface OnItemClickListener<T> {
        void onItemClick(View view, RecyclerView.ViewHolder holder, T o, int position);

        boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, T o, int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

}
