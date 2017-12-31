package xyz.no21.appcommon.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.ColorRes;
import android.support.annotation.DimenRes;
import android.support.v4.content.ContextCompat;
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

    private final Rect mBounds = new Rect();
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

    private int mOrientation = VERTICAL;
    private int margin;
    private int color;
    private int size;


    public MarginItemDecoration(Context context, int mOrientation, @DimenRes int margin) {
        this.margin = context.getResources().getDimensionPixelSize(margin);
        this.mOrientation = mOrientation;
    }

    public MarginItemDecoration(Context context, @ColorRes int color, @DimenRes int size, @DimenRes int margin) {
        this.color = ContextCompat.getColor(context, color);
        this.size = context.getResources().getDimensionPixelSize(size);
        this.margin = context.getResources().getDimensionPixelSize(margin);
    }

    public void setOrientation(int orientation) {
        if (orientation != HORIZONTAL && orientation != VERTICAL) {
            throw new IllegalArgumentException(
                    "Invalid orientation. It should be either HORIZONTAL or VERTICAL");
        }
        mOrientation = orientation;
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        if (parent.getLayoutManager() == null || color == 0 || size == 0) {
            return;
        }
        if (mOrientation == VERTICAL) {
            drawVertical(c, parent);
        } else {
//            drawHorizontal(c, parent);
        }
    }

    @SuppressLint("NewApi")
    private void drawVertical(Canvas canvas, RecyclerView parent) {
        canvas.save();
        final int left;
        final int right;
        if (parent.getClipToPadding()) {
            left = parent.getPaddingLeft();
            right = parent.getWidth() - parent.getPaddingRight();
            canvas.clipRect(left, parent.getPaddingTop(), right,
                    parent.getHeight() - parent.getPaddingBottom());
        } else {
            left = 0;
            right = parent.getWidth();
        }

        int childCount = parent.getChildCount();

        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            if (child.getVisibility() == View.VISIBLE) {

                parent.getDecoratedBoundsWithMargins(child, mBounds);
                final int bottom = mBounds.bottom + Math.round(child.getTranslationY());
                final int top = bottom - size;

                paint.setColor(color);
                paint.setStyle(Paint.Style.FILL);

                canvas.drawRect(left + margin, top, right - margin, bottom, paint);
            }
        }
        canvas.restore();
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                               RecyclerView.State state) {
        if (view.getVisibility() != View.VISIBLE)
            return;
        if (mOrientation == VERTICAL) {
            outRect.set(0, 0, 0, size);
        } else {
            outRect.set(0, 0, 0, 0);
        }
    }


}
