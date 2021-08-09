package knight.rider.kitt.bar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class KittBottomBar extends FrameLayout {

    // 最外层布局
    private final FrameLayout mLayout;
    // tab的布局
    private final LinearLayout mTabLayout;

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
        int tabPaddingTop = (int) array.getDimension(R.styleable.KittBottomBar_bar_tab_paddingTop, dip2px(3));
        int tabPaddingBottom = (int) array.getDimension(R.styleable.KittBottomBar_bar_tab_paddingBottom, dip2px(3));
        setTabLayoutPadding(tabPaddingTop, tabPaddingBottom);
        // 导航键容器背景
        Drawable tabLayoutDrawable = array.getDrawable(R.styleable.KittBottomBar_bar_tab_background);
        setTabLayoutBackground(tabLayoutDrawable);

        array.recycle();

    }


    /**************对外提供**************/
    /**
     * 设置Tab容器的上、下内间距
     *
     * @param top    the top padding in pixels.
     * @param bottom the bottom padding in pixels.
     */
    public final KittBottomBar setTabLayoutPadding(int top, int bottom) {
        mTabLayout.setPadding(0, top, 0, bottom);
        return this;
    }

    /**
     * 设置Tab容器的上内间距
     *
     * @param top the top padding in pixels.
     */
    public final KittBottomBar setTabLayoutPaddingTop(int top) {
        mTabLayout.setPadding(0, top, 0, 0);
        return this;
    }

    /**
     * 设置Tab容器的下内间距
     *
     * @param bottom the bottom padding in pixels.
     */
    public final KittBottomBar setTabLayoutPaddingBottom(int bottom) {
        mTabLayout.setPadding(0, 0, 0, bottom);
        return this;
    }


    /**
     * 设置Tab容器背景
     *
     * @param background The Drawable to use as the background
     */
    private final void setTabLayoutBackground(Drawable background) {
        if (background != null)
            mTabLayout.setBackground(background);
        else
            mTabLayout.setBackgroundDrawable(null);
    }


    /**
     * 强制超出父布局，由Tab的父布局来控制
     */
    @Override
    public final void setClipChildren(boolean clipChildren) {
        super.setClipChildren(clipChildren);
        this.post(new Runnable() {
            @Override
            public void run() {
                mLayout.setClipChildren(false);
                mLayout.setClipToPadding(false);
            }
        });
    }


    /******************私有方法**********************/

    private float dip2px(float dpValue) {
        final float scale = getResources().getDisplayMetrics().density;
        return dpValue * scale + 0.5f;
    }

}
