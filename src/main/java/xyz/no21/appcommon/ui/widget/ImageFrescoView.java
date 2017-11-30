package xyz.no21.appcommon.ui.widget;

import android.content.Context;
import android.util.AttributeSet;

import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.view.SimpleDraweeView;

/**
 * 按照比例布局的ImageView,参考fresco
 */
public class ImageFrescoView extends SimpleDraweeView {


    public ImageFrescoView(Context context, GenericDraweeHierarchy hierarchy) {
        super(context, hierarchy);
    }

    public ImageFrescoView(Context context) {
        super(context);
    }

    public ImageFrescoView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ImageFrescoView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public ImageFrescoView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
}