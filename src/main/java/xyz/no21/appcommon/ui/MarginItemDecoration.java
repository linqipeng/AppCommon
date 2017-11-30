package xyz.no21.appcommon.ui;

import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.DimenRes;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by lin on 2017/5/13.
 * Email:L437943145@gmail.com
 * <p>
 * desc:
 */

public class MarginItemDecoration extends RecyclerView.ItemDecoration {
    public static final int HORIZONTAL = LinearLayout.HORIZONTAL;
    public static final int VERTICAL = LinearLayout.VERTICAL;

    private int mOrientation;
    private int margin;


    public MarginItemDecoration(Context context, int mOrientation, @DimenRes int margin) {
        this.margin = context.getResources().getDimensionPixelSize(margin);
        this.mOrientation = mOrientation;
    }


    public void setOrientation(int orientation) {
        if (orientation != HORIZONTAL && orientation != VERTICAL) {
            throw new IllegalArgumentException(
                    "Invalid orientation. It should be either HORIZONTAL or VERTICAL");
        }
        mOrientation = orientation;
    }


    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                               RecyclerView.State state) {
        if (mOrientation == VERTICAL) {
            outRect.set(0, 0, 0, margin);
        } else {
            outRect.set(0, 0, margin, 0);
        }
    }


}
