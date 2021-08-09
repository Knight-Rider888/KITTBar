package knight.rider.kitt.bar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import knight.rider.kitt.bar.attr.CircleStyle;
import knight.rider.kitt.bar.attr.Tab;
import knight.rider.kitt.bar.other.BottomItem;

public class KittBottomBar extends FrameLayout {

    // 最外层布局
    private final FrameLayout mLayout;
    // tab的布局
    private final LinearLayout mTabLayout;
    // tab的内间距
    private int mTabPaddingTop;
    private int mTabPaddingBottom;
    // tab的文字大小
    private float mTabTextSize;
    // tab的图标大小
    private float mTabIconSize;
    // 红点字体大小
    private float mBadgeSize;

    public KittBottomBar(@NonNull Context context) {
        this(context, null);
    }

    public KittBottomBar(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public KittBottomBar(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(getContext()).inflate(R.layout.kitt_bottom_bar, this, true);
        // 初始化组件
        mLayout = findViewById(R.id.kitt_bar_layout);
        mTabLayout = findViewById(R.id.kitt_bar_tab_layout);

        // 初始化自定义属性
        TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.KittBottomBar, defStyleAttr, 0);

        // 导航键上下间距
        mTabPaddingTop = (int) array.getDimension(R.styleable.KittBottomBar_bar_tab_paddingTop, dip2px(3));
        mTabPaddingBottom = (int) array.getDimension(R.styleable.KittBottomBar_bar_tab_paddingBottom, dip2px(3));
        // 此处不调用动态设置的方式，防止多次计算
        mTabLayout.setPadding(0, mTabPaddingTop, 0, mTabPaddingBottom);
        // 导航键容器背景
        Drawable tabLayoutDrawable = array.getDrawable(R.styleable.KittBottomBar_bar_tab_background);
        setTabLayoutBackground(tabLayoutDrawable);

        // tab的文字大小
        mTabTextSize = array.getDimension(R.styleable.KittBottomBar_bar_tab_text_size, dip2px(12));
        // 此处不设置，最后初始化完毕才进行设置

        // tab的图标大小
        mTabIconSize = array.getDimension(R.styleable.KittBottomBar_bar_tab_icon_normal_size, dip2px(28));
        // 此处不设置，最后初始化完毕才进行设置


        // 初始化完成，可设置TabLayout的高度
        setTabLayoutHeight();
        // Tab的初始化由代码去完成

        // 红点字体大小
        mBadgeSize = array.getDimension(R.styleable.KittBottomBar_bar_tab_badge_text_size, dip2px(8.5f));

        array.recycle();

    }


    /************对外提供***********/


    /**
     * 设置Tab容器背景
     *
     * @param background The Drawable to use as the background
     */
    public void setTabLayoutBackground(Drawable background) {
        if (background != null)
            mTabLayout.setBackground(background);
        else
            mTabLayout.setBackgroundDrawable(null);
    }


    // TODO 暂不对外提供,此处更改后涉及重新计算高度的问题,后期维护

    /**
     * 设置Tab容器的上、下内间距
     *
     * @param top    the top padding in pixels.
     * @param bottom the bottom padding in pixels.
     */
    private KittBottomBar setTabLayoutPadding(int top, int bottom) {
        mTabPaddingTop = top;
        mTabPaddingBottom = bottom;
        mTabLayout.setPadding(0, mTabPaddingTop, 0, mTabPaddingBottom);
        // TODO 重新计算Tab
        return this;
    }

    /**
     * 设置Tab容器的上内间距
     *
     * @param top the top padding in pixels.
     */
    private KittBottomBar setTabLayoutPaddingTop(int top) {
        mTabPaddingTop = top;
        mTabLayout.setPadding(0, mTabPaddingTop, 0, mTabPaddingBottom);
        // TODO 重新计算Tab
        return this;
    }

    /**
     * 设置Tab容器的下内间距
     *
     * @param bottom the bottom padding in pixels.
     */
    private KittBottomBar setTabLayoutPaddingBottom(int bottom) {
        mTabPaddingBottom = bottom;
        mTabLayout.setPadding(0, mTabPaddingTop, 0, mTabPaddingBottom);
        // TODO 重新计算Tab
        return this;
    }


    /******************私有方法**********************/

    private float dip2px(float dpValue) {
        final float scale = getResources().getDisplayMetrics().density;
        return dpValue * scale + 0.5f;
    }

    // 计算tab文字控件的高度
    private int calcTextViewHeight() {
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setTextSize(mTabTextSize);
        Rect bounds = new Rect();
        paint.getTextBounds("计算", 0, 2, bounds);
        Paint.FontMetricsInt fontMetrics = paint.getFontMetricsInt();
        return fontMetrics.bottom - fontMetrics.top;
    }

    // 计算导航栏的高度
    private float calcTabLayoutHeight() {
        return mTabPaddingTop + mTabPaddingBottom + mTabIconSize + calcTextViewHeight();
    }

    // 设置tab栏高度
    private void setTabLayoutHeight() {
        ViewGroup.LayoutParams params = mTabLayout.getLayoutParams();
        params.height = (int) calcTabLayoutHeight();
        mTabLayout.setLayoutParams(params);
    }

    // 摆放底部的item
    private void onLayoutItem(BottomItem item, Tab tab) {

        // Tab容器设置整体宽度
        LinearLayout tabGroup = item.getTabGroup();
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) tabGroup.getLayoutParams();
        params.weight = 1;
        params.width = 0;

        if (tab.isLargeIcon() && tab.getLargeIconSize() != 0)
            // 仅计算图片差即可
            params.height += (tab.getLargeIconSize() - mTabIconSize);

        // 容器设置完成
        tabGroup.setLayoutParams(params);

        // Icon的容器设置整体高度
        FrameLayout iconGroup = item.getIconGroup();
        ViewGroup.LayoutParams params1 = iconGroup.getLayoutParams();

        if (tab.isLargeIcon() && tab.getLargeIconSize() != 0) {
            // 计算超大图标
            params1.width = tab.getLargeIconSize();
            params1.height = tab.getLargeIconSize();
        } else {
            // 计算普通图标
            params1.width = (int) mTabIconSize;
            params1.height = (int) mTabIconSize;
        }

        // Icon容器完成设置
        iconGroup.setLayoutParams(params1);

        // badge设置
        TextView badge = item.getBadge();
        badge.setTextSize(TypedValue.COMPLEX_UNIT_PX, mBadgeSize);
        CircleStyle circleStyle = tab.getCircleStyle();
        // TODO badge完成设置

        // 文本设置
        TextView wordView = item.getWordView();
        wordView.setText(tab.getWord());
        wordView.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTabTextSize);
        // TODO 文本初始化完成
    }
}
