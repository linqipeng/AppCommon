package xyz.no21.appcommon.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

import java.util.List;

import xyz.no21.appcommon.CommonItemBean;
import xyz.no21.appcommon.R;

/**
 * Created by cookie on 2017/10/7.
 * Email:l437943145@gmail.com
 * Desc 类似商品物流
 */

public class FlowView extends AppCompatTextView {

    private int position;
    private int defaultColor;
    private int selectedColor;
    private int radius = 12;
    private Paint paint;
    private List<CommonItemBean> items;

    public FlowView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }


    public FlowView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (items == null || items.size() == 0)
            return;

        int itemCount = items.size();
        int length = (getMeasuredWidth() - getPaddingStart() - getPaddingEnd()) / (itemCount * 2);

        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(radius);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setColor(defaultColor);

        canvas.drawLine(getPaddingStart() + length, radius + getPaddingTop(),
                getMeasuredWidth() - getPaddingEnd() - length, radius + getPaddingTop(), paint);

        paint.setColor(selectedColor);

        int stopX = length * (2 * position + 1) + getPaddingStart();

        canvas.drawLine(getPaddingStart() + length, radius + getPaddingTop(),
                stopX, radius + getPaddingTop(), paint);

        for (int i = 1; i < itemCount * 2; i += 2) {
            float cx;

            cx = length * i + getPaddingStart();
            if (i <= 2 * position + 1) {
                paint.setColor(selectedColor);
            } else {
                paint.setColor(defaultColor);
            }
            paint.setStyle(Paint.Style.FILL);
            canvas.drawCircle(cx, radius + getPaddingTop(), radius, paint);

            paint.setTextSize(getTextSize());
            //文字的x轴坐标
            String text = items.get(i / 2).getName();
            float stringWidth = paint.measureText(text);
            float x = cx - stringWidth / 2;
            //文字的y轴坐标
            Paint.FontMetrics fontMetrics = paint.getFontMetrics();
            float y = getHeight() / 2 + (Math.abs(fontMetrics.ascent) - fontMetrics.descent) / 2;
            canvas.drawText(text, x, y + 15, paint);

        }
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.FlowView);

        defaultColor = array.getColor(R.styleable.FlowView_defaultColor, Color.GRAY);
        selectedColor = array.getColor(R.styleable.FlowView_selectedColor, Color.GRAY);

        array.recycle();
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    }

    public void setData(List<CommonItemBean> items, int position) {

        this.position = position;
        this.items = items;
        invalidate();
        if (position < 0 || position > items.size()) {
            throw new IllegalArgumentException();
        }

    }

}
