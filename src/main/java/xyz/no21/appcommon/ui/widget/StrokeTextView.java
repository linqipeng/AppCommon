package xyz.no21.appcommon.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.widget.AppCompatTextView;
import android.text.TextPaint;
import android.util.AttributeSet;

import xyz.no21.appcommon.R;

/**
 * Created by cookie on 2017/12/22.
 * Email:l437943145@gmail.com
 * Desc 带描边的textview
 */

public class StrokeTextView extends AppCompatTextView {

    public static final String TAG = "StrokeTextView";
    private TextPaint paint;

    public StrokeTextView(Context context) {
        super(context);
        init(context, null, 0);
    }

    public StrokeTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);

    }

    public StrokeTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.StrokeTextView);

        int color = typedArray.getColor(R.styleable.StrokeTextView_borderColor, Color.WHITE);
        float width = typedArray.getDimension(R.styleable.StrokeTextView_BorderWidth, 3);

        typedArray.recycle();

        paint = new TextPaint();
        paint.set(getPaint());
        paint.setStrokeWidth(width);
        paint.setColor(color);
        paint.setStyle(Paint.Style.STROKE);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        String text = getText().toString();
        canvas.drawText(text, 0, getBaseline(), paint);
        super.onDraw(canvas);
    }
}
