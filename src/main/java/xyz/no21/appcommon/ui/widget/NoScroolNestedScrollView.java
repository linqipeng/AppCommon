package xyz.no21.appcommon.ui.widget;

import android.content.Context;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by cookie on 2017/11/19.
 * Email:l437943145@gmail.com
 * Desc
 */

public class NoScroolNestedScrollView extends NestedScrollView {
    public NoScroolNestedScrollView(Context context) {
        super(context);
    }

    public NoScroolNestedScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NoScroolNestedScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onStartNestedScroll(View child, View target, int nestedScrollAxes) {
        return true;
    }

    @Override
    public boolean isNestedScrollingEnabled() {
        return false;
    }

    @Override
    public boolean dispatchNestedPreScroll(int dx, int dy, int[] consumed, int[] offsetInWindow) {
        return super.dispatchNestedPreScroll(dx, dy, new int[]{0, 0}, offsetInWindow);
    }

    @Override
    public boolean dispatchNestedPreScroll(int dx, int dy, int[] consumed, int[] offsetInWindow,
                                           int type) {
        return super.dispatchNestedPreScroll(dx, dy, new int[]{0, 0}, offsetInWindow, type);
    }
}
