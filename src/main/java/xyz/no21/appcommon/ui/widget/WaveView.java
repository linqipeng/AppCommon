package xyz.no21.appcommon.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.Iterator;

import xyz.no21.appcommon.R;

public class WaveView extends View implements Runnable {

    /**
     * 波浪圆圈颜色
     */
    private int mColor = Color.RED;
    /**
     * 波浪圆之间间距
     */
    private int mWidth = 3;
    /**
     * 是否正在扩散中
     */
    private boolean mIsWave = false;
    // 扩散圆半径集合
    ArrayList<Item> mRadius = new ArrayList<>(20);


    private Paint mPaint;

    public WaveView(Context context) {
        this(context, null);
    }

    public WaveView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WaveView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);

        mRadius.add(new Item(0));

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.WaveView, defStyleAttr, 0);
        mColor = a.getColor(R.styleable.WaveView_waveColor, mColor);
        mWidth = a.getDimensionPixelSize(R.styleable.WaveView_waveWidth, mWidth);
        a.recycle();

    }


    @Override
    public void onDraw(Canvas canvas) {
        /*
      最大宽度
     */
        int mMaxRadius = getWidth() > getHeight() ? getHeight() / 2 : getWidth() / 2;

        // 绘制扩散圆
        mPaint.setColor(mColor);

        Iterator<Item> iterator = mRadius.iterator();

        while (iterator.hasNext()) {
            Item radius = iterator.next();

            if (radius.radius < mMaxRadius) {
                radius.radius += 1;
                int alpha = (int) (255.0F * (1.0F - (radius.radius) * 1.0f / mMaxRadius));

                mPaint.setAlpha(alpha);
                canvas.drawCircle(getWidth() / 2, getHeight() / 2, radius.radius, mPaint);
            } else {
                iterator.remove();
            }
            if (radius.radius + 1 == mWidth) {
                mRadius.add(new Item(0));
            }

        }

        if (mIsWave) {
            postDelayed(this, 22);
        }
    }

    @Override
    public void run() {
        invalidate();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        removeCallbacks(this);
    }

    /**
     * 开始扩散
     */
    public void start() {
        mIsWave = true;
        invalidate();
    }

    /**
     * 停止扩散
     */
    public void stop() {
        mIsWave = false;
    }

    /**
     * 是否扩散中
     */
    public boolean isWave() {
        return mIsWave;
    }

    /**
     * 设置波浪圆颜色
     */
    public void setColor(int colorId) {
        mColor = colorId;
    }

    /**
     * 设置波浪圆之间间距
     */
    public void setWidth(int width) {
        mWidth = width;
    }

    private static class Item {
        public int radius;

        public Item(int radius) {
            this.radius = radius;
        }
    }
}