package xyz.no21.appcommon.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.drawee.view.AspectRatioMeasure;

import xyz.no21.appcommon.CommonUtils;
import xyz.no21.appcommon.R;

/**
 * Created by lin on 2017/9/14.
 * Email: L437943145@gmail.com
 * Desc:bannet 自动滚动加小圆点
 */

public class BannerViewPager extends ViewPager implements Runnable {

    private final AspectRatioMeasure.Spec mMeasureSpec = new AspectRatioMeasure.Spec();
    BannerProvider bannerProvider;
    OnBannerClickListener onBannerClickListener;
    private long delay = 2000L;
    private int indicationSize;
    private int currentPosition;
    OnPageChangeListener onPageChangeListener = new OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            removeCallbacks(BannerViewPager.this);
            postDelayed(BannerViewPager.this, delay);
        }

        @Override
        public void onPageSelected(int position) {
            currentPosition = position % bannerProvider.size();
//            removeCallbacks(BannerViewPager.this);
//            postDelayed(BannerViewPager.this, delay);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
    private float radius;//圆点半径
    private float space;//两个圆点之间的间隔
    private Paint paint;
    private int selectedColor;
    private int defaultColor;
    private float mAspectRatio;
    private boolean autoScroll;

    public BannerViewPager(Context context) {
        this(context, null);
    }

    public BannerViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        radius = CommonUtils.dp2px(getContext(), 3);
        space = CommonUtils.dp2px(getContext(), 20);


        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.BannerViewPager);

        selectedColor = typedArray.getColor(R.styleable.BannerViewPager_selectedColor, Color.RED);
        defaultColor = typedArray.getColor(R.styleable.BannerViewPager_defaultColor, Color.WHITE);
        mAspectRatio = typedArray.getFloat(R.styleable.BannerViewPager_ratio, -1);
        autoScroll = typedArray.getBoolean(R.styleable.BannerViewPager_autoScroll, true);
        typedArray.recycle();

        removeOnPageChangeListener(onPageChangeListener);
        addOnPageChangeListener(onPageChangeListener);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        if (bannerProvider == null)
            return;
        //所有圆点的宽度

        float length = indicationSize * radius * 2 + (indicationSize - 1) * space;

        for (int i = 0; i < indicationSize; i++) {
            float cx = (i + 1) * radius + i * space + getScrollX() + getMeasuredWidth() / 2 - length / 2;
            float cy = getMeasuredHeight() - getPaddingBottom() - radius * 5;//5为随便写

            if (i == currentPosition) {
                paint.setStyle(Paint.Style.FILL);
                paint.setColor(selectedColor);
                canvas.drawCircle(cx, cy, radius, paint);
            } else {
                paint.setStyle(Paint.Style.STROKE);
                paint.setColor(defaultColor);
                canvas.drawCircle(cx, cy, radius, paint);
            }
        }
    }

    @Override
    public void run() {
        if (autoScroll)
            setCurrentItem(getCurrentItem() + 1);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        postDelayed(this, delay);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        removeCallbacks(this);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        mMeasureSpec.width = widthMeasureSpec;
        mMeasureSpec.height = heightMeasureSpec;
        AspectRatioMeasure.updateMeasureSpec(
                mMeasureSpec,
                mAspectRatio,
                getLayoutParams(),
                getPaddingLeft() + getPaddingRight(),
                getPaddingTop() + getPaddingBottom());

        super.onMeasure(mMeasureSpec.width, mMeasureSpec.height);
    }

    public void setBannerProvider(BannerProvider bannerProvider) {
        this.bannerProvider = bannerProvider;
        if (bannerProvider != null && bannerProvider.size() > 0) {
            indicationSize = bannerProvider.size();
            setAdapter(new BannerPagerAdapter());
        }
    }

    public void setOnBannerClickListener(OnBannerClickListener onBannerClickListener) {
        this.onBannerClickListener = onBannerClickListener;
    }

    public void setDelay(long delay) {
        this.delay = delay;
    }

    public interface BannerProvider {
        String provider(int position);

        int size();
    }

    public interface OnBannerClickListener {
        void onBannerClick(int position);
    }

    private class BannerPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        @Override
        public Object instantiateItem(final ViewGroup container, int posi) {
            final int position = posi % bannerProvider.size();
            final ImageFrescoView imageFrescoView = new ImageFrescoView(container.getContext());
            imageFrescoView.setScaleType(ImageFrescoView.ScaleType.CENTER_CROP);
            imageFrescoView.setImageURI(bannerProvider.provider(position));
            container.addView(imageFrescoView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            imageFrescoView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onBannerClickListener != null) {
                        onBannerClickListener.onBannerClick(position);
                    }
                }
            });
            return imageFrescoView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

    }

}
