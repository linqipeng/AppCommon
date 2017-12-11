package xyz.no21.appcommon.ui.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.View;

import xyz.no21.appcommon.base.BaseActivity;


/**
 * Created by lin on 14/2/17.
 * Email:L437943145@gmail.com
 * <p>
 * desc:
 */

public class CommonToolBar extends Toolbar {


    public CommonToolBar(Context context) {
        super(context);
    }

    public CommonToolBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CommonToolBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setup(BaseActivity context, String title) {
        setup(context, title, true);
    }

    public void setup(final BaseActivity context, String title, boolean canBack) {
        context.setSupportActionBar(this);
        //noinspection ConstantConditions
        context.getSupportActionBar().setTitle(title);
        if (canBack) {
            setNavigationOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    context.finish();
                }
            });
        } else {
            setNavigationIcon(null);
            setNavigationOnClickListener(null);
        }
    }
}
