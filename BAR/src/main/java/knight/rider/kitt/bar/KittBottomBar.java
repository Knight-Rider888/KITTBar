package knight.rider.kitt.bar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

import knight.rider.kitt.bar.adapter.BottomBarAdapter;
import knight.rider.kitt.bar.attr.Tab;
import knight.rider.kitt.bar.other.BottomItem;

public class KittBottomBar extends FrameLayout {

    // 最外层布局
    private final FrameLayout mLayout;
    // tab的布局
    private final LinearLayout mTabLayout;
    // pager
    private final ViewPager mPager;
    // tab的内间距
    private int mTabPaddingTop;
    private int mTabPaddingBottom;
    // tab的文字大小
    private float mTabTextSize;
    // tab的图标大小
    private float mTabIconSize;
    // 红点字体大小
    private float mBadgeSize;
    // 未选中时的字体颜色
    private int mWordColor;
    // 选中时的字体颜色
    private int mWordSelectedColor;
    // 动画播放速度
    private float mLottieSpeed;

    // tabs
    private final List<Tab> mTabs = new ArrayList<>();
    // fragments
    private final LinkedHashMap<Integer, Fragment> mFragmentTags = new LinkedHashMap<>();
    private final List<Fragment> mFragments = new ArrayList<>();
    // 当前位置
    private int mCurrentIndex;

    // 是否初始化完成？
    private boolean isInit;

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
        mPager = findViewById(R.id.kitt_bar_pager);

        // 初始化自定义属性
        TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.KittBottomBar, defStyleAttr, 0);

        // 导航键上下间距
        mTabPaddingTop = (int) array.getDimension(R.styleable.KittBottomBar_bar_tab_paddingTop, dip2px(3));
        mTabPaddingBottom = (int) array.getDimension(R.styleable.KittBottomBar_bar_tab_paddingBottom, dip2px(3));

        // 导航键容器背景
        Drawable tabLayoutDrawable = array.getDrawable(R.styleable.KittBottomBar_bar_tab_background);
        setTabLayoutBackground(tabLayoutDrawable);

        // tab的文字大小
        mTabTextSize = array.getDimension(R.styleable.KittBottomBar_bar_tab_text_size, dip2px(12));
        // 此处不设置，最后初始化完毕才进行设置

        // tab的图标大小
        mTabIconSize = array.getDimension(R.styleable.KittBottomBar_bar_tab_icon_normal_size, dip2px(28));
        // 此处不设置，最后初始化完毕才进行设置


        // 红点字体大小
        mBadgeSize = array.getDimension(R.styleable.KittBottomBar_bar_tab_badge_text_size, dip2px(8.5f));
        // 此处不设置，最后初始化完毕才进行设置


        // 字体颜色
        mWordColor = array.getColor(R.styleable.KittBottomBar_bar_tab_text_color, Color.parseColor("#666666"));
        mWordSelectedColor = array.getColor(R.styleable.KittBottomBar_bar_tab_text_selected_color, Color.parseColor("#666666"));
        // 此处不设置，最后初始化完毕才进行设置

        // 播放速度
        mLottieSpeed = array.getFloat(R.styleable.KittBottomBar_bar_lottie_speed, 1);
        // 此处不设置，最后初始化完毕才进行设置

        array.recycle();

    }


    /************对外提供***********/


    /**
     * √添加Tab信息
     */
    public final KittBottomBar addTab(Tab tab) {

        if (isInit)
            throw new RuntimeException("调用init()后不允许继续添加Tab");

        // 保存tab信息
        mTabs.add(tab);

        return this;
    }


    /**
     * √初始化
     */
    public final KittBottomBar init(FragmentManager manager) {

        if (isInit)
            throw new RuntimeException("请不要多次调用init()");

        isInit = true;

        // 设置padding
        mTabLayout.setPadding(0, mTabPaddingTop, 0, mTabPaddingBottom);

        // 初始化完成，可设置TabLayout的高度
        setTabLayoutHeight();

        // Tab的初始化由代码去完成

        for (int i = 0; i < mTabs.size(); i++) {

            Tab tab = mTabs.get(i);

            if (tab.getClss() != null) {
                try {
                    Class<?> aClass = Class.forName(tab.getClss().getName());
                    Fragment obj = (Fragment) aClass.newInstance();
                    // tab的index,tab对应的fragment
                    mFragments.add(obj);
                    mFragmentTags.put(i, obj);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }


            // 加入tab
            BottomItem item = new BottomItem(getContext());
            item.updateSpeed(mLottieSpeed);

            if (i == 0)
                item.setSelected(true);

            // 初始化Tab相关样式
            item.init(tab.getNormalPicRes(), tab.getSelectedPicRes(), mWordColor, mWordSelectedColor, tab.getWord(), mTabTextSize);

            int finalI = i;
            item.setOnClickListener(new OnClickListener() {

                final int clickIndex = finalI;

                @Override
                public void onClick(View view) {

                    // 点击位置发生变化，才更新
                    if (mCurrentIndex != clickIndex) {
                        mTabLayout.getChildAt(mCurrentIndex).setSelected(false);
                        ((BottomItem) mTabLayout.getChildAt(mCurrentIndex)).changeIconState();
                        mCurrentIndex = clickIndex;
                        item.setSelected(true);
                        item.changeIconState();

                        // 是否绑定fragment
                        Fragment fragment = mFragmentTags.get(clickIndex);
                        if (fragment != null)
                            mPager.setCurrentItem(mFragments.indexOf(fragment));

                    }
                }
            });
            mTabLayout.addView(item);
            onLayoutTabItem(item, tab);

            // 初始化完成后监听实体类
            tab.addPropertyChangeListener(new PropertyChangeListener() {
                @Override
                public void propertyChange(PropertyChangeEvent propertyChangeEvent) {
                    // 属性改变的tab下标
                    int index = mTabs.indexOf(tab);
                    BottomItem item = (BottomItem) mTabLayout.getChildAt(index);

                    switch (propertyChangeEvent.getPropertyName()) {
                        case "tabContent":
                            item.updateTabText((String) propertyChangeEvent.getNewValue());
                            break;
                        case "largeIcon":
                            onLayoutTabItem(item, tab);
                            break;
                        case "normalRes":
                            item.updateTabNormalIcon(tab.getNormalPicRes());
                            break;
                        case "selectedRes":
                            item.updateTabSelectedIcon(tab.getSelectedPicRes());
                            break;
                    }
                }
            });
        }

        // 绑定适配器
        mPager.setAdapter(new BottomBarAdapter(manager, mFragments));
        mPager.setOffscreenPageLimit(mFragments.size());
        mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                // key值遍历器
                Iterator<Integer> iterator = mFragmentTags.keySet().iterator();
                while (iterator.hasNext()) {

                    // 当前的key值
                    Integer integer = iterator.next();

                    if (position == 0) {
                        // 真正的tab的位置
                        position = integer;
                        break;
                    }
                    position--;
                }


                // 当前position所对应的item
                BottomItem item = (BottomItem) mTabLayout.getChildAt(position);

                // 上一个tab设置为未选中
                mTabLayout.getChildAt(mCurrentIndex).setSelected(false);
                ((BottomItem) mTabLayout.getChildAt(mCurrentIndex)).changeIconState();
                mCurrentIndex = position;
                item.setSelected(true);
                item.changeIconState();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        return this;
    }

    /**
     * √设置Tab容器背景
     *
     * @param background The Drawable to use as the background
     */
    public void setTabLayoutBackground(Drawable background) {
        if (background != null)
            mTabLayout.setBackground(background);
        else
            mTabLayout.setBackgroundDrawable(null);
    }

    /**
     * √设置Tab播放速度
     */
    public void setLottieSpeed(float speed) {

        mLottieSpeed = speed;

        // 未初始化前可随意更改，无需设置
        if (!isInit)
            return;

        // 已经初始化循环更改
        for (int i = 0; i < mTabLayout.getChildCount(); i++) {
            BottomItem item = (BottomItem) mTabLayout.getChildAt(i);
            item.updateSpeed(speed);
        }
    }


    /**
     * √设置tab文字的颜色
     *
     * @param tabTextColor         the normal text color.
     * @param tabTextSelectedColor the selected text color
     */
    public void setTabTextColor(@ColorInt int tabTextColor, @ColorInt int tabTextSelectedColor) {

        // 未初始化前可随意更改，无需循环更改
        if (!isInit) {
            mWordColor = tabTextColor;
            mWordSelectedColor = tabTextSelectedColor;
            return;
        }

        // 已经初始化循环更改
        for (int i = 0; i < mTabLayout.getChildCount(); i++) {
            BottomItem item = (BottomItem) mTabLayout.getChildAt(i);
            item.updateTabTextColor(tabTextColor, tabTextSelectedColor);
        }

    }

    /**
     * √设置tab文字的大小
     */
    public void setTabTextSize(int textSize) {

        mTabTextSize = textSize;

        // 未初始化前可随意更改，无需重绘
        if (!isInit)
            return;


        // 已经初始化重新计算bar的整体高度
        setTabLayoutHeight();

        // 已经初始化循环更改
        for (int i = 0; i < mTabLayout.getChildCount(); i++) {
            BottomItem item = (BottomItem) mTabLayout.getChildAt(i);
            onLayoutTabItem(item, mTabs.get(i));
            item.updateTabTextSize(textSize);
        }

    }

    /**
     * √设置tab图片默认值,传0，即不使用图片
     */
    public void setTabIconNormalSize(int iconSIze) {

        mTabIconSize = iconSIze;

        // 未初始化前可随意更改，无需重绘
        if (!isInit)
            return;


        // 已经初始化重新计算bar的整体高度
        setTabLayoutHeight();

        // 已经初始化循环更改
        for (int i = 0; i < mTabLayout.getChildCount(); i++) {
            BottomItem item = (BottomItem) mTabLayout.getChildAt(i);
            onLayoutTabItem(item, mTabs.get(i));
        }

    }


    /**
     * √设置Tab容器的上、下内间距
     *
     * @param top    the top padding in pixels.
     * @param bottom the bottom padding in pixels.
     */
    public final KittBottomBar setTabLayoutPadding(int top, int bottom) {

        mTabPaddingTop = top;
        mTabPaddingBottom = bottom;

        // 初始化后需重新计算高度
        if (isInit) {
            mTabLayout.setPadding(0, mTabPaddingTop, 0, mTabPaddingBottom);

            // 重新TabLayout的高度，并不影响tab的高度
            setTabLayoutHeight();
        }

        return this;
    }

    /**
     * √设置Tab容器的上内间距
     *
     * @param top the top padding in pixels.
     */
    public final KittBottomBar setTabLayoutPaddingTop(int top) {

        mTabPaddingTop = top;

        // 初始化后需重新计算高度
        if (isInit) {
            mTabLayout.setPadding(0, mTabPaddingTop, 0, mTabPaddingBottom);

            // 重新TabLayout的高度，并不影响tab的高度
            setTabLayoutHeight();
        }

        return this;
    }

    /**
     * √设置Tab容器的下内间距
     *
     * @param bottom the bottom padding in pixels.
     */
    public final KittBottomBar setTabLayoutPaddingBottom(int bottom) {

        mTabPaddingBottom = bottom;

        // 初始化后需重新计算高度
        if (isInit) {
            mTabLayout.setPadding(0, mTabPaddingTop, 0, mTabPaddingBottom);

            // 重新TabLayout的高度，并不影响tab的高度
            setTabLayoutHeight();
        }

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

    // 底部的Item重新计算高度
    private void onLayoutTabItem(BottomItem item, Tab tab) {

        // Tab容器设置整体宽、高
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) item.getLayoutParams();
        params.weight = 1;
        params.width = 0;
        // 先设置为默认高度
        params.height = mTabLayout.getLayoutParams().height - mTabPaddingTop - mTabPaddingBottom;

        if (tab.isLargeIcon())
            // 仅计算图片差即可,必须设置，否则无凸出效果
            params.height += (tab.getLargeIconSize() - mTabIconSize);

        // 容器设置完成
        item.setLayoutParams(params);

        // Icon的容器设置整体高度
        FrameLayout iconGroup = item.getIconGroup();
        ViewGroup.LayoutParams params1 = iconGroup.getLayoutParams();

        if (tab.isLargeIcon()) {
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
    }
}
