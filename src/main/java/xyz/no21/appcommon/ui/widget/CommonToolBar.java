package xyz.no21.appcommon.ui.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import xyz.no21.appcommon.R;
import xyz.no21.appcommon.base.BaseActivity;


/**
 * Created by lin on 14/2/17.
 * Email:L437943145@gmail.com
 * <p>
 * desc:
 */

public class CommonToolBar extends Toolbar {


    private BaseActivity context;
    private OnOptionMenuClickListener optionMenuClick;

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

    public void setup(BaseActivity context, int title) {
        setup(context, title, true);
    }

    public void setup(final BaseActivity context, int title, boolean canBack) {
        setup(context, getResources().getString(title), canBack);
    }

    public void setup(final BaseActivity context, String title, boolean canBack) {
        this.context = context;
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

    public void setRightText(Menu menu, String rightText, final OnOptionMenuClickListener menuClickListener) {
        context.getMenuInflater().inflate(R.menu.app_toolbar_provider, menu);
        MenuItem menuItem = menu.findItem(R.id.actionRightText);
        View view = LayoutInflater.from(getContext()).inflate(R.layout.app_toolbar_provider, this, false);
        TextView rightTextView = view.findViewById(R.id.rightTextView);
        rightTextView.setText(rightText);
        menuItem.setActionView(view);
        if (menuClickListener != null) {
            rightTextView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    menuClickListener.onRightTextClick();
                }
            });
        }

    }


    public interface OnOptionMenuClickListener {
        void onRightTextClick();
    }
}
