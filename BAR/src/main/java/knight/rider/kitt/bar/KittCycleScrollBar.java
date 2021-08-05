package knight.rider.kitt.bar;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.ViewFlipper;


/**
 * 作者： mr.Wang
 * 自动上下滚动栏
 */
public class KittCycleScrollBar extends RelativeLayout {

    // 默认滚动间隔
    private int mAutoScrollInterval = 2000;
    private final ViewFlipper mFlipper1;
    private final ViewFlipper mFlipper2;

    private boolean mIsVisibleFlipper1 = true;


    public KittCycleScrollBar(Context context) {
        this(context, null);
    }

    public KittCycleScrollBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public KittCycleScrollBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        getAttrs(context, attrs);

        LayoutInflater.from(context).inflate(R.layout.kitt_cycle_scroll_bar, this, true);
        mFlipper1 = (ViewFlipper) findViewById(R.id.kitt_bar_one);
        mFlipper2 = (ViewFlipper) findViewById(R.id.kitt_bar_two);

        mFlipper1.setAutoStart(true);
        mFlipper1.setFlipInterval(mAutoScrollInterval);
        mFlipper2.setAutoStart(true);
        mFlipper2.setFlipInterval(mAutoScrollInterval);
    }

    /**
     * 得到属性值
     */
    private void getAttrs(Context context, AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.KittCycleScrollBar);
        mAutoScrollInterval = ta.getInteger(R.styleable.KittCycleScrollBar_bar_auto_scroll_interval, mAutoScrollInterval);
        ta.recycle();
    }


    /**
     * 添加待滚动的view
     */
    public final KittCycleScrollBar addItemView(View view) {
        if (mIsVisibleFlipper1)
            mFlipper1.addView(view);
        else
            mFlipper2.addView(view);
        return this;
    }

    /**
     * 清空所有view
     */
    public final KittCycleScrollBar clearAllViews() {

        mFlipper1.removeAllViews();
        mFlipper2.removeAllViews();

        if (mIsVisibleFlipper1) {
            mFlipper1.setVisibility(GONE);
            mFlipper2.setVisibility(VISIBLE);
            mIsVisibleFlipper1 = false;
        } else {
            mFlipper1.setVisibility(VISIBLE);
            mFlipper2.setVisibility(GONE);
            mIsVisibleFlipper1 = true;
        }

        return this;
    }


    /**
     * 生命周期进行调用
     */
    public final void onResume() {
        mFlipper1.startFlipping();
        mFlipper2.startFlipping();
    }

    /**
     * 生命周期进行调用
     */
    public final void onPause() {
        mFlipper1.stopFlipping();
        mFlipper2.stopFlipping();
    }

}
