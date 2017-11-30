package xyz.no21.appcommon.ui.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

import xyz.no21.appcommon.CommonUtils;
import xyz.no21.appcommon.R;

/**
 * Created by lin on 2017/9/13.
 * Email: L437943145@gmail.com
 * Desc:
 */

public class IndicationView extends View {
    private int indicationSize;
    private int currentPosition;

    private float radius;//圆点半径
    private float space;//两个圆点之间的间隔

    private Paint paint;
    private int selectedColor;
    private int defaultColor;

    public IndicationView(Context context) {
        this(context, null);
    }

    public IndicationView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public IndicationView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public IndicationView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context,attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        radius = CommonUtils.dp2px(getContext(), 3);
        space = CommonUtils.dp2px(getContext(), 20);


        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.IndicationView);

        selectedColor = typedArray.getColor(R.styleable.IndicationView_selectedColor, Color.RED);
        defaultColor = typedArray.getColor(R.styleable.IndicationView_defaultColor, Color.WHITE);

        typedArray.recycle();
    }

    public void setIndicationSize(int indicationSize) {
        this.indicationSize = indicationSize;
        requestLayout();
        invalidate();
    }

    public void setCurrentPosition(int currentPosition) {
        this.currentPosition = currentPosition;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        for (int i = 0; i < indicationSize; i++) {
            float cx = (i + 1) * radius + i * space;
            float cy = (getMeasuredHeight() - getPaddingTop() - getPaddingBottom()) / 2;

            if (i == currentPosition) {
                paint.setStyle(Paint.Style.FILL);
                paint.setColor( selectedColor);
                canvas.drawCircle(cx, cy, radius, paint);
            } else {
                paint.setStyle(Paint.Style.STROKE);
                paint.setColor(defaultColor);
                canvas.drawCircle(cx, cy, radius, paint);
            }
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);

        if (widthMode != MeasureSpec.EXACTLY) {
            setMeasuredDimension((int) (indicationSize * 2 * radius + (indicationSize - 1) * space), MeasureSpec.getSize(heightMeasureSpec));
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }
}
