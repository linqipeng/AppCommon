package xyz.no21.appcommon.ui.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Path;
import android.graphics.RectF;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.widget.ProgressBar;

/**
 * Created by lin on 2017/9/18.
 * Email: L437943145@gmail.com
 * Desc:圆角 中间带字的进度条
 * please use  style="?android:attr/progressBarStyleHorizontal"
 */

public class RoundedProgressBar extends ProgressBar {

    private Path path;
    private RectF rectF;
    private Paint paint;
    private PaintFlagsDrawFilter filter;

    public RoundedProgressBar(Context context) {
        super(context);
    }

    public RoundedProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }


    public RoundedProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public RoundedProgressBar(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);

    }

    private void init(Context context, AttributeSet attrs) {
        path = new Path();
        rectF = new RectF();
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);

        paint.setColor(Color.WHITE);
        paint.setTextSize(30);
        filter = new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        path.reset();
        rectF.set(0, 0, w, h);
        path.addRoundRect(rectF, h / 2, h / 2, Path.Direction.CCW);

    }

    @Override
    protected synchronized void onDraw(Canvas canvas) {
        canvas.setDrawFilter(filter);
        canvas.clipPath(path);
        super.onDraw(canvas);

        if (getMax() != 0) {
            String text = getProgress() * 100 / getMax() + "%";

            //文字的x轴坐标
            float stringWidth = paint.measureText(text);
            float x = (getWidth() - stringWidth) / 2;
            //文字的y轴坐标
            Paint.FontMetrics fontMetrics = paint.getFontMetrics();
            float y = getHeight() / 2 + (Math.abs(fontMetrics.ascent) - fontMetrics.descent) / 2;
            canvas.drawText(text, x, y, paint);
        }

    }
}
