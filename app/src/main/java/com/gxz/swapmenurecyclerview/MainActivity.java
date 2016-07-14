package com.gxz.swapmenurecyclerview;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.View;
import android.widget.Toast;

import com.gxz.library.SwapRecyclerView;
import com.gxz.library.SwipeMenuBuilder;
import com.gxz.library.bean.SwipeMenu;
import com.gxz.library.bean.SwipeMenuItem;
import com.gxz.library.view.SwipeMenuView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SwipeMenuBuilder {


    private FloatingActionButton floatingActionButton;
    private RecyclerAdapter adapter;
    private List<String> list = new ArrayList<>();
    private SwapRecyclerView recyclerView;
    private int pos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        floatingActionButton = (FloatingActionButton) findViewById(R.id.fab);


        recyclerView = (SwapRecyclerView) findViewById(R.id.id_rv);
        initData();
        adapter = new RecyclerAdapter<String>(list, this);
//        recyclerView.setLayoutManager(new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false));
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));

        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        recyclerView.setOnSwipeListener(new SwapRecyclerView.OnSwipeListener() {
            @Override
            public void onSwipeStart(int position) {
//                Toast.makeText(MainActivity.this,"onSwipeStart-"+position,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSwipeEnd(int position) {
//                Toast.makeText(MainActivity.this, "onSwipeEnd-" + position, Toast.LENGTH_SHORT).show();
                pos = position;
            }
        });

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerView.smoothCloseMenu(pos);
            }
        });

        adapter.setOnItemClickListener(new RecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, Object o, int position) {
                Toast.makeText(MainActivity.this, "onItemClick-->>>"+list.get(position), Toast.LENGTH_LONG).show();
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, Object o, int position) {
                Toast.makeText(MainActivity.this, "onItemLongClick-->>>"+list.get(position), Toast.LENGTH_LONG).show();
                return true;//!!!!!!!!!!!!
            }

        });

    }

    private SwipeMenuView.OnMenuItemClickListener mOnSwipeItemClickListener = new SwipeMenuView.OnMenuItemClickListener() {

        @Override
        public void onMenuItemClick(int pos, SwipeMenu menu, int index) {
            Toast.makeText(MainActivity.this, menu.getMenuItem(index).getTitle(), Toast.LENGTH_LONG).show();
            if (index == 1) {
                recyclerView.smoothCloseMenu(pos);
                list.remove(pos);
                adapter.remove(pos);
            }
        }
    };

    private void initData() {
        for (int i = 1; i < 50; i++) {
            list.add("第 " + i + " 项数据");
        }
    }


    @Override
    public SwipeMenuView create() {

        SwipeMenu menu = new SwipeMenu(this);

        SwipeMenuItem item = new SwipeMenuItem(MainActivity.this);
        item.setTitle("分享")
                .setTitleColor(Color.WHITE)
                .setBackground(new ColorDrawable(Color.GRAY))
                .setWidth(dp2px(80))
                .setTitleSize(20)
                .setIcon(android.R.drawable.ic_menu_share);

        menu.addMenuItem(item);

        item = new SwipeMenuItem(MainActivity.this);
        item.setTitle("删除")
                .setTitleColor(Color.WHITE)
                .setBackground(new ColorDrawable(Color.RED));
        menu.addMenuItem(item);

        SwipeMenuView menuView = new SwipeMenuView(menu);

        menuView.setOnMenuItemClickListener(mOnSwipeItemClickListener);

        return menuView;
    }

    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                getResources().getDisplayMetrics());
    }
}
