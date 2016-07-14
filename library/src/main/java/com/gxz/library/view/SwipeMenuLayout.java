package com.gxz.library.view;

import android.annotation.SuppressLint;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v4.widget.ScrollerCompat;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.widget.FrameLayout;

/**
 * ================================================
 * <br/>作    者：顾修忠-guxiuzhong@youku.com/gfj19900401@163.com
 * <br/>版    本：
 * <br/>创建日期：16/7/8-下午5:19
 * <br/>描    述：包装RecyclerView的item的布局,实现
 * <br/>描    述：包装RecyclerView的item的布局,实现菜单操作的滑动打开与关闭
 * <br/>修订历史：
 * <br/>================================================
 */
@SuppressLint("ViewConstructor")
public class SwipeMenuLayout extends FrameLayout {

    private static final int DURATION = 350;
    private static final int STATE_CLOSE = 0;
    private static final int STATE_OPEN = 1;
    private int state = STATE_CLOSE;
    private boolean isFling;

    private View mContentView;
    private SwipeMenuView mMenuView;
    private GestureDetectorCompat mGestureDetector;
    private int mTouchSlop;
    private int mMinimumVelocity;
    private Interpolator mCloseInterpolator;
    private Interpolator mOpenInterpolator;
    private ScrollerCompat mOpenScroller;
    private ScrollerCompat mCloseScroller;
    private int mBaseX;
    private int mDownX;


    public SwipeMenuLayout(View contentView, SwipeMenuView menuView) {
        this(contentView, menuView, null, null);
    }

    public SwipeMenuLayout(View contentView, SwipeMenuView menuView, Interpolator closeInterpolator, Interpolator openInterpolator) {
        super(contentView.getContext());
        mContentView = contentView;
        mMenuView = menuView;
        mMenuView.setLayout(this);
        mCloseInterpolator = closeInterpolator;
        mOpenInterpolator = openInterpolator;
        init();
    }

    private void init() {
        mTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
        mMinimumVelocity = (int) (ViewConfiguration.get(getContext())
                .getScaledMinimumFlingVelocity() * 2.2f);
        //屏幕的宽
        setLayoutParams(new ViewGroup.LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT));
        mMenuView.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT));
        addView(mContentView);
        addView(mMenuView);
        GestureDetector.SimpleOnGestureListener mGestureListener = new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onDown(MotionEvent e) {
                isFling = false;
                return super.onDown(e);
            }

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                if ((e1.getX() - e2.getX()) > mTouchSlop
                        && Math.abs(velocityX) > mMinimumVelocity) {
                    isFling = true;
                }
                return super.onFling(e1, e2, velocityX, velocityY);
            }
        };
        mGestureDetector = new GestureDetectorCompat(getContext(),
                mGestureListener);
        if (mCloseInterpolator != null) {
            mCloseScroller = ScrollerCompat.create(getContext(),
                    mCloseInterpolator);
        } else {
            mCloseScroller = ScrollerCompat.create(getContext());
        }
        if (mOpenInterpolator != null) {
            mOpenScroller = ScrollerCompat.create(getContext(),
                    mOpenInterpolator);
        } else {
            mOpenScroller = ScrollerCompat.create(getContext());
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //测量mMenuView的宽,高为mContentView的高
        mMenuView.measure(MeasureSpec.makeMeasureSpec(0,
                MeasureSpec.UNSPECIFIED), MeasureSpec.makeMeasureSpec(
                getMeasuredHeight(), MeasureSpec.EXACTLY));
    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        mContentView.layout(0, 0, getMeasuredWidth(),
                mContentView.getMeasuredHeight());
        //在mContentView的右侧
        mMenuView.layout(getMeasuredWidth(), 0,
                getMeasuredWidth() + mMenuView.getMeasuredWidth(),
                mContentView.getMeasuredHeight());
    }


    public void onSwipe(MotionEvent event) {
        mGestureDetector.onTouchEvent(event);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mDownX = (int) event.getX();
                isFling = false;
                break;
            case MotionEvent.ACTION_MOVE:
                //按下去-当前的位置
                int dis = (int) (mDownX - event.getX());
                //menuView打开状态dis+mMenuView宽
                if (state == STATE_OPEN) {
                    dis += mMenuView.getWidth();
                }
                swipe(dis);
                break;
            case MotionEvent.ACTION_UP:
                //快速滑动,或者超过了mMenuView宽的一半则打开,否则关闭
                if (isFling || (mDownX - event.getX()) > (mMenuView.getWidth() / 2)) {
                    smoothOpenMenu();
                } else {
                    smoothCloseMenu();
                }
                break;
        }
    }

    /**
     * 更改位置
     *
     * @param dis dis mContentView的left
     */
    private void swipe(int dis) {
        //mContentView的最大为mMenuView的宽
        if (dis > mMenuView.getWidth()) {
            dis = mMenuView.getWidth();
        }
        //mContentView-left的最小值为0即正常值
        if (dis < 0) {
            dis = 0;
        }
        //设置完mContentView的left就可以得出right以及mMenuView的left和right了
        //主要是left,right
        //left 最大值为-mMenuView.getWidth()
        mContentView.layout(-dis, mContentView.getTop(),
                mContentView.getWidth() - dis, getMeasuredHeight());
        mMenuView.layout(mContentView.getWidth() - dis, mMenuView.getTop(),
                mContentView.getWidth() + mMenuView.getWidth() - dis,
                mMenuView.getBottom());
    }

    @Override
    public void computeScroll() {
        //让mMenuView打开
        if (state == STATE_OPEN) {
            //是否停止了滑动
            if (mOpenScroller.computeScrollOffset()) {
                swipe(mOpenScroller.getCurrX());
                //重绘UI
                postInvalidate();
            }
        } else {//让mMenuView关闭
               //mContentView的
            if (mCloseScroller.computeScrollOffset()) {
                //mBaseX为当前的mContentView的left,可以结合
                swipe(mBaseX - mCloseScroller.getCurrX());
                postInvalidate();
            }
        }
    }

    /**
     * 平滑的关闭mMenuView
     */
    public void smoothCloseMenu() {
        state = STATE_CLOSE;
        mBaseX = -mContentView.getLeft();
        //关闭是我们要让mContentView的慢慢的减小,
        //mCloseScroller.getCurrX()的范围是(0,mBaseX)
        mCloseScroller.startScroll(0, 0, mBaseX, 0, DURATION);
        postInvalidate();
    }
    /**
     * 平滑的打开mMenuView
     */
    public void smoothOpenMenu() {
        state = STATE_OPEN;
        //其实我们这里是用到了Scroller类产生的值(当然借助Interpolator来实现不同的值渐变,从而实现不同的效果)
        //打开的时候mContentView的left从当前的-mContentView.getLeft()到mMenuView.getWidth()
        //在computeScroll方法中 swipe(mOpenScroller.getCurrX());即可
        //mOpenScroller.getCurrX()的范围是(-mContentView.getLeft(),mMenuView.getWidth())
        //-mContentView.getLeft()为正值
        mOpenScroller.startScroll(-mContentView.getLeft(), 0,
                mMenuView.getWidth(), 0, DURATION);
        postInvalidate();
    }
    /**
     *无平滑的关闭mMenuView
     */
    public void closeMenu() {
        if (mCloseScroller.computeScrollOffset()) {
            mCloseScroller.abortAnimation();
        }
        if (state == STATE_OPEN) {
            state = STATE_CLOSE;
            swipe(0);
        }
    }
    /**
     * 无平滑的打开mMenuView
     */
    public void openMenu() {
        if (state == STATE_CLOSE) {
            state = STATE_OPEN;
            swipe(mMenuView.getWidth());
        }
    }

    public boolean isOpen() {
        return state == STATE_OPEN;
    }

    public SwipeMenuView getmMenuView() {
        return mMenuView;
    }

    public void setCloseInterpolator(Interpolator interpolator) {
        mCloseInterpolator = interpolator;
    }

    public void setOpenInterpolator(Interpolator interpolator) {
        mOpenInterpolator = interpolator;
    }

    public Interpolator getOpenInterpolator() {
        return mOpenInterpolator;
    }

    public Interpolator getCloseInterpolator() {
        return mCloseInterpolator;
    }
}
