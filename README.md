# 支持左滑显示菜单的RecyclerView
可以定制滑出来menu的样式</p>
#效果图
<img src="/screenshots/swap2.gif"/></p>
<img src="/screenshots/swap3.gif"/></p>
#Maven

```java
<dependency>
  <groupId>com.gxz</groupId>
  <artifactId>swapmenurecyclerview</artifactId>
  <version>1.0</version>
  <type>pom</type>
</dependency>
```
# Gradle

```java

    compile 'com.gxz:swapmenurecyclerview:1.0'
    
```

#Ivy
```java

   <dependency org='com.gxz' name='swapmenurecyclerview' rev='1.0'>
     <artifact name='$AID' ext='pom'></artifact>
   </dependency>
    
```

#使用方法
1.在 RecyclerView.Adapter中

```java
in activity 
public class MainActivity extends AppCompatActivity implements SwipeMenuBuilder 
....

    //构造SwipeMenuView
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

```

in adapter 

```java
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
```

#操作菜单的回调和状态的回调
```java
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
    
    recyclerView.setOnSwipeListener(new SwapRecyclerView.OnSwipeListener() {
        @Override
        public void onSwipeStart(int position) {
            Toast.makeText(MainActivity.this,"onSwipeStart-"+position,Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onSwipeEnd(int position) {
           Toast.makeText(MainActivity.this, "onSwipeEnd-" + position, Toast.LENGTH_SHORT).show();
        }
    });
            
```
##其它设置
关闭某一条打开的menu</p>
1.recyclerView.smoothCloseMenu(pos);</p>
2.设置SwipeMenuLayout的差值器-动画效果</p>
```java
    public void setCloseInterpolator(Interpolator interpolator) {
        mCloseInterpolator = interpolator;
    }

    public void setOpenInterpolator(Interpolator interpolator) {
        mOpenInterpolator = interpolator;
    }
            
```


