package xyz.no21.appcommon.ui.widget;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.text.TextUtils;
import android.util.AttributeSet;

/**
 * Created by lin on 2017/6/30.
 * Email:L437943145@gmail.com
 * <p>
 * desc:
 */

public class CountDownTextView extends AppCompatTextView implements Runnable {
    private int count;
    private String prefix;
    private String suffix;


    private String originText;

    public CountDownTextView(Context context) {
        super(context);
    }

    public CountDownTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CountDownTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        removeCallbacks(this);
    }

    private void setTextString(String text) {
        setText((TextUtils.isEmpty(prefix) ? "" : prefix) + text + (TextUtils.isEmpty(suffix) ? "" : suffix));
    }

    public void start(final int count) {
        this.count = count;
        removeCallbacks(this);
        originText = getText().toString();
        setEnabled(false);
        postDelayed(this, 0);
    }

    public void reset() {
        removeCallbacks(this);
        setText(originText);
        setEnabled(true);
    }

    @Override
    public void run() {
        this.count--;
        setTextString(String.valueOf(count));
        if (count == 0) {
            setEnabled(true);
            setText(originText);
        } else {
            postDelayed(this, 1000);
        }

    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }
}
