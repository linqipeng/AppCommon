package xyz.no21.appcommon.ui.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewStub;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import xyz.no21.appcommon.R;

/**
 * Created by lin on 2017/7/7.
 * Email:L437943145@gmail.com
 * <p>
 * desc:用于页面各种状态显示 加载中 空数据 网络错误等
 */

public class RootView extends FrameLayout {

    public static final String TAG = "RootView";
    private ViewStub mEnptyViewStub;
    private ViewStub mLoadingViewStub;
    private ViewStub mErrorViewStub;


    private View mEmptyView;
    private View mErrorView;
    private View mContentView;
    private View mLoadingView;


    private View showingView;
    private int emptyImageId;
    private String emptyString;

    private OnReloadListener onReloadListener;
    private int errorImageId;

    public RootView(@NonNull Context context) {
        super(context);
        init(context, null);
    }

    public RootView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public RootView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public RootView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr, @StyleRes int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        LayoutInflater.from(getContext()).inflate(R.layout.app_root_view_content, this);

        mEnptyViewStub = (ViewStub) findViewById(R.id.enptyViewStub);
        mLoadingViewStub = (ViewStub) findViewById(R.id.loadingViewStub);
        mErrorViewStub = (ViewStub) findViewById(R.id.errorViewStub);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RootView);

        int emptyLayoutId = typedArray.getResourceId(R.styleable.RootView_enptyLayoutId, R.layout.app_root_view_empty);
        mEnptyViewStub.setLayoutResource(emptyLayoutId);

        int errorLayoutId = typedArray.getResourceId(R.styleable.RootView_errorLayoutId, R.layout.app_root_view_error);
        mErrorViewStub.setLayoutResource(errorLayoutId);

        int loadingLayoutId = typedArray.getResourceId(R.styleable.RootView_loadingLayoutId, R.layout.app_root_view_loading);
        mLoadingViewStub.setLayoutResource(loadingLayoutId);

        emptyImageId = typedArray.getResourceId(R.styleable.RootView_emptyImage, -1);
        errorImageId = typedArray.getResourceId(R.styleable.RootView_errorImage, -1);
        emptyString = typedArray.getString(R.styleable.RootView_emptyText);
        typedArray.recycle();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        int count = getChildCount();
        if (count > 4) {
            throw new IllegalArgumentException("child view can not > 1");
        }
        mContentView = getChildAt(count - 1);
        showingView = mContentView;
    }

    public void showEmpty() {
        if (mEmptyView == null) {
            mEmptyView = mEnptyViewStub.inflate();
        }
        if (showingView != null) {
            showingView.setVisibility(INVISIBLE);
        }

        mEmptyView.setVisibility(VISIBLE);
        if (emptyImageId != -1) {
            ImageView emptyImageView = mEmptyView.findViewById(R.id.emptyImage);
            emptyImageView.setImageResource(emptyImageId);
        }
        if (!TextUtils.isEmpty(emptyString)) {
            TextView emptyTextView = mEmptyView.findViewById(R.id.emptyText);
            emptyTextView.setText(emptyString);
        }
        showingView = mEmptyView;
    }


    public void showError() {
        if (mErrorView == null) {
            mErrorView = mErrorViewStub.inflate();
        }
        if (showingView != null) {
            showingView.setVisibility(INVISIBLE);
        }
        mErrorView.setVisibility(VISIBLE);

        if (errorImageId != -1) {
            TextView errorTextView = mErrorView.findViewById(R.id.errorTextView);
            errorTextView.setCompoundDrawablesRelativeWithIntrinsicBounds(0, errorImageId, 0, 0);
        }

        mErrorView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onReloadListener != null) {
                    onReloadListener.OnRootViewReload();
                }
            }
        });
        showingView = mErrorView;
    }

    public void showLoading() {
        if (mLoadingView == null) {
            mLoadingView = mLoadingViewStub.inflate();
        } else {
            mLoadingView.setVisibility(VISIBLE);
        }
        if (showingView != null) {
            showingView.setVisibility(INVISIBLE);
        }
        showingView = mLoadingView;
    }

    public void showContent() {
        if (mContentView == null) {
            return;
        }
        if (showingView != null) {
            showingView.setVisibility(GONE);
        }
        mContentView.setVisibility(VISIBLE);
        showingView = mContentView;
    }

    public void setEmptyViewLayout(int layoutId) {
        mEnptyViewStub.setLayoutResource(layoutId);
    }

    public void setErrorViewLayout(int layoutId, int listenerId) {
        mErrorViewStub.setLayoutResource(layoutId);
    }

    public void setLoadingLayout(int layoutId) {
        mLoadingViewStub.setLayoutResource(layoutId);
    }

    private void setContentViewsVisibilty(boolean visible) {
    }

    public void setOnReloadListener(OnReloadListener onReloadListener) {
        this.onReloadListener = onReloadListener;
    }

    public interface OnReloadListener {
        void OnRootViewReload();
    }
}
