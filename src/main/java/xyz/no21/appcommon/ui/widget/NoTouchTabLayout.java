package xyz.no21.appcommon.ui.widget;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by cookie on 2017/11/13.
 * Email:l437943145@gmail.com
 * Desc
 */

public class NoTouchTabLayout extends TabLayout {
    public NoTouchTabLayout(Context context) {
        super(context);
    }

    public NoTouchTabLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NoTouchTabLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return true;
    }
}
