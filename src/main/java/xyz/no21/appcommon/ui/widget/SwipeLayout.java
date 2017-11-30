package xyz.no21.appcommon.ui.widget;

import android.content.Context;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import xyz.no21.appcommon.R;


public class SwipeLayout extends LinearLayout {

    private static final String TAG = "SwipeLayout";
    View mContentView;
    View mActionView;
    private int mDragDistance;
    private ShowEdge mShowEdge = ShowEdge.Right;

    private ViewDragHelper mDragHelper;
    private GestureDetectorCompat mGestureDetector;

    private boolean mDragEnable = true;
    private int newLeft;

    public enum ShowEdge {
        Left, Right
    }

    public void setDragEnable(boolean dragEnable) {
        mDragEnable = dragEnable;
    }


    public SwipeLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SwipeLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setOrientation(HORIZONTAL);

        mDragHelper = ViewDragHelper.create(this, mCallback);
        GestureDetector.SimpleOnGestureListener mOnGestureListener = new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                return Math.abs(distanceX) >= Math.abs(distanceY);
            }

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                return velocityX >= velocityY;
            }
        };
        mGestureDetector = new GestureDetectorCompat(context, mOnGestureListener);
    }

    ViewDragHelper.Callback mCallback = new ViewDragHelper.Callback() {
        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            return true;
        }

        @Override
        public int getViewHorizontalDragRange(View child) {
            /*返回滑动范围，用来设置拖拽的范围,大于0即可，不会真正限制child的移动范围,内部用来计算是否此方向是否可以拖拽，以及释放时动画执行时间*/
            return mDragDistance;
        }

        @Override
        public int getViewVerticalDragRange(View child) {
            return Integer.MAX_VALUE;
        }

        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            /*修正最新的建议值left和所产生的偏移量dx 以得到横向滑动的最终左边位置*/
            newLeft = left;
            switch (mShowEdge) {
                case Left:
                    if (newLeft < 0)
                        newLeft = 0;
                    else if (newLeft > mDragDistance)
                        newLeft = mDragDistance;
                    break;
                case Right:
                    if (newLeft < -mDragDistance)
                        newLeft = -mDragDistance;
                    else if (newLeft > 0)
                        newLeft = 0;
                    break;
            }
            return newLeft;
        }

        @Override
        public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
            /*当view的位置改变时，在这里处理位置改变后要做的事情。*/
            if (changedView == mContentView) {
                /*如果用户用手指滑动的是前景View，那么也要将横向变化量dx交给背景View*/
                mActionView.offsetLeftAndRight(dx);
            } else if (changedView == mActionView) {
                /*如果用户用手指滑动的是背景View，那么也要将横向变化量dx交给前景View*/
                mContentView.offsetLeftAndRight(dx);
            }

            invalidate();
        }

        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            switch (mShowEdge) {
                case Left:
                    if (newLeft > 0.5f * mDragDistance) {
                        open();
                    } else {
                        close();
                    }
                    break;
                case Right:
                    if (newLeft < 0.5f * -mDragDistance) {
                        open();
                    } else {
                        close();
                    }
                    break;
            }
            invalidate();
        }
    };


    public void close() {
        if (mDragHelper.smoothSlideViewTo(mContentView, 0, 0)) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    public void open() {
        if (mDragHelper.smoothSlideViewTo(mContentView, -mDragDistance, 0)) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }


    @Override
    public void computeScroll() {
        /*在这里判断动画是否需要继续执行。会在View.draw(Canvas mCanvas)之前执行。*/
        if (mDragHelper.continueSettling(true)) {
            /*返回true，表示动画还没执行完，需要继续执行。*/
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        /*决定当前的SwipeLayout是否要把touch事件拦截下来，直接交由自己的onTouchEvent处理,返回true则为拦截*/
        if (mDragEnable) {
            return mDragHelper.shouldInterceptTouchEvent(ev) & mGestureDetector.onTouchEvent(ev);
        } else {
            return super.onInterceptTouchEvent(ev);
        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!mDragEnable) {
            return super.onTouchEvent(event);
        } else {
            mDragHelper.processTouchEvent(event);
            return true;
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mContentView = findViewById(R.id.swipe_content);
        mActionView = findViewById(R.id.swipe_action);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mDragDistance = mActionView.getMeasuredWidth();
    }

//    public void setShowEdge(ShowEdge showEdit) {
//        mShowEdge = showEdit;
//        requestLayout();
//    }
}