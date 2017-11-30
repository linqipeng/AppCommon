package xyz.no21.appcommon.ui.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

/**
 * Created by lin on 2017/9/20.
 * Email: L437943145@gmail.com
 * Desc:
 */

public class SlashTextView extends AppCompatTextView {

    private Paint paint;

    public SlashTextView(Context context) {
        super(context);
    }

    public SlashTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public SlashTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (getText().length() != 0) {
            paint.setColor(getCurrentTextColor());
            paint.setStrokeWidth(getHeight() / 8);
            canvas.drawLine(0, getMeasuredHeight() / 2, getWidth(), getHeight() / 2, paint);

        }
    }
}
