package xyz.no21.appcommon.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.DecimalFormat;

import xyz.no21.appcommon.R;

/**
 * Created by lin on 2017/9/18.
 * Email: L437943145@gmail.com
 * Desc:商城抢购倒计时
 */
public class TimeView extends LinearLayout implements Runnable {

    public static final int DELAY_MILLIS = 1000;
    TextView notity;
    TextView hour;
    TextView minute;
    TextView second;

    private long date;
    private String endText;
    private Drawable progressBackground;
    private Drawable textBackground;


    private OnTimeOverListener onOverListener;
    private int progreTextColor;
    private int textColor;
    private int notityTextColor;
    private int progreNotityTextColor;

    public TimeView(Context context) {
        super(context);
        throw new UnsupportedOperationException(" please call TimeView(Context context, @Nullable AttributeSet attrs)");
    }

    public TimeView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);

    }


    public TimeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public TimeView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);

    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        inflate(getContext(), R.layout.app_time_view, this);
        notity = findViewById(R.id.notity);
        hour = findViewById(R.id.hour);
        minute = findViewById(R.id.minute);
        second = findViewById(R.id.second);
    }

    private void init(Context context, AttributeSet attrs) {
        setOrientation(HORIZONTAL);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TimeView);

        progressBackground = typedArray.getDrawable(R.styleable.TimeView_progressTextBackground);
        textBackground = typedArray.getDrawable(R.styleable.TimeView_TextBackground);

        progreTextColor = typedArray.getColor(R.styleable.TimeView_progressTextColor, Color.WHITE);
        textColor = typedArray.getColor(R.styleable.TimeView_TextColor, Color.WHITE);

        progreNotityTextColor = typedArray.getColor(R.styleable.TimeView_progressNotityTextColor, Color.RED);
        notityTextColor = typedArray.getColor(R.styleable.TimeView_notityTextColor, Color.GRAY);

        typedArray.recycle();
    }

    public void setTime(long time) {
        removeCallbacks(this);
        date = (time - System.currentTimeMillis()) / 1000;
    }

    public void start(String progressText, String endText) {
        date -= DELAY_MILLIS;
        post(this);
        this.endText = endText;

        notity.setText(progressText);

        hour.setBackground(progressBackground);
        minute.setBackground(progressBackground);
        second.setBackground(progressBackground);

        hour.setTextColor(progreTextColor);
        minute.setTextColor(progreTextColor);
        second.setTextColor(progreTextColor);
        notity.setTextColor(progreNotityTextColor);
    }

    @Override
    public void run() {
        date--;

        long hou = 0;
        long minu = 0;
        long sec = 0;

        if (date > 0) {
            postDelayed(this, DELAY_MILLIS);
            hou = date / 3600;
            minu = date % 3600 / 60;
            sec = date % 3600 % 60;
        } else {
            notity.setText(endText);

            hour.setBackground(textBackground);
            minute.setBackground(textBackground);
            second.setBackground(textBackground);

            hour.setTextColor(textColor);
            minute.setTextColor(textColor);
            second.setTextColor(textColor);
            notity.setTextColor(notityTextColor);

            removeCallbacks(this);
            if (onOverListener != null) {
                onOverListener.timeOver();
            }

        }
        DecimalFormat format = new DecimalFormat("00");

        hour.setText(hou < 10 ? "0" + hou : String.valueOf(hou));
        minute.setText(format.format(minu));
        second.setText(format.format(sec));

    }


    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        removeCallbacks(this);
    }

    public void setOnOverListener(OnTimeOverListener onOverListener) {
        this.onOverListener = onOverListener;
    }

    public interface OnTimeOverListener {
        void timeOver();
    }
}
