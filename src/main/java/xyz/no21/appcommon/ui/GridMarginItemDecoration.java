package xyz.no21.appcommon.ui;

import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.DimenRes;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by lin on 2017/5/13.
 * Email:L437943145@gmail.com
 * <p>
 * desc:
 */

public class GridMarginItemDecoration extends RecyclerView.ItemDecoration {
    private final int spanCount;

    private int margin;


    public GridMarginItemDecoration(Context context, int spanCount, @DimenRes int margin) {
        this.margin = context.getResources().getDimensionPixelSize(margin);
        this.spanCount = spanCount;
    }


    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                               RecyclerView.State state) {
        int position = parent.getChildAdapterPosition(view) % spanCount;


        if (position == 0) {
            outRect.set(0, 0, margin * 3 / 4, 0);
        } else if (position == spanCount - 1) {
            outRect.set(margin * 3 / 4, 0, 0, 0);
        } else {
            outRect.set(margin / 4, 0, margin / 4, 0);
        }
    }


}
