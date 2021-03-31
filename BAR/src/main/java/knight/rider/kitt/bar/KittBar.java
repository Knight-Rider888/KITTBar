package knight.rider.kitt.bar;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.TextKeyListener;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.annotation.FloatRange;
import androidx.annotation.IntDef;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.Size;
import androidx.annotation.StringRes;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import knight.rider.kitt.bar.attr.EditSupport;
import knight.rider.kitt.bar.attr.RightBtn;
import knight.rider.kitt.bar.listener.OnBarEventListener;

import static android.view.inputmethod.EditorInfo.IME_ACTION_SEARCH;


/**
 * 顶部导航栏
 */
public class KittBar extends FrameLayout {

    @IntDef({VISIBLE, INVISIBLE, GONE})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Visibility {
    }


    // 返回按钮
    private ImageView mBack;
    // 用户自定义布局的容器
    private FrameLayout mCustomLayout;
    // 用户填充的自定义布局
    private View mCustomView;

    // 标题
    private TextView mTitle;
    // 搜索容器
    private LinearLayout mSearchLayout;
    // 编辑框
    private EditText mSearchView;
    private ImageView mSearchRightTemp;
    private ImageView mSearchRightIcon;
    private ImageView mClearView;


    private GradientDrawable mSearchLayoutDrawable;

    // 是否支持清除和输入
    private int mSupportWriteAndClear;
    // 是否支持hint内容搜索
    private boolean mSupportHintSearch;


    private OnBarEventListener mListener;

    private TextView mRightBtn1;
    private TextView mRightBtn2;
    private TextView mRightBtn3;


    private Context mContext;

    public KittBar(@NonNull Context context) {
        this(context, null);
    }

    public KittBar(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public KittBar(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        init(attrs, defStyleAttr);
        initListener();
    }

    private void initListener() {

        // 清除按钮的监听
        mClearView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                mSearchView.setText("");
            }
        });

        // 文字变化的监听
        mSearchView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String content = charSequence.toString();

                if (mSupportWriteAndClear == 0) {
                    // 都支持
                    if (TextUtils.isEmpty(content)) {
                        mClearView.setVisibility(GONE);
                        mSearchRightIcon.setVisibility(VISIBLE);
                    } else {
                        mClearView.setVisibility(VISIBLE);
                        mSearchRightIcon.setVisibility(GONE);
                    }
                } else {
                    // 仅支持写入或都不支持
                    mClearView.setVisibility(GONE);
                    mSearchRightIcon.setVisibility(VISIBLE);
                }

                if (mListener != null)
                    mListener.onTextChanged(content);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        // 返回键的监听事件
        mBack.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener != null && mBack.getDrawable() != null)
                    mListener.onBack(mBack);
            }
        });

        // 搜索框右侧按钮
        mSearchRightIcon.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener != null && mSearchRightIcon.getDrawable() != null)
                    mListener.onSearchRightIconClick(mSearchRightIcon);
            }
        });

        // 键盘搜索的监听
        mSearchView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {

                // 搜索
                if (i == IME_ACTION_SEARCH) {

                    boolean isHintSearch = false;

                    String searchContent = mSearchView.getText().toString();
                    String hint = mSearchView.getHint().toString().trim();

                    if (TextUtils.isEmpty(searchContent) && TextUtils.isEmpty(searchContent.trim()) && !TextUtils.isEmpty(hint) && mSupportHintSearch) {
                        mSearchView.setText(hint);
                        isHintSearch = true;
                        mSearchView.setSelection(mSearchView.getText().length());
                    }

                    if (mListener != null)
                        mListener.onKeyboardSearchClick(mSearchView.getText().toString(), isHintSearch);

                    InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(mSearchView.getWindowToken(), 0);
                }

                return true;
            }
        });


        mTitle.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener != null)
                    mListener.onTitle(mTitle);
            }
        });

        mRightBtn1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener != null)
                    mListener.onRightIconClick(RightBtn.RIGHT_FIRST, mRightBtn1);
            }
        });

        mRightBtn2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener != null)
                    mListener.onRightIconClick(RightBtn.RIGHT_SECOND, mRightBtn2);
            }
        });

        mRightBtn3.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener != null)
                    mListener.onRightIconClick(RightBtn.RIGHT_THIRD, mRightBtn3);
            }
        });
    }


    private void init(AttributeSet attrs, int defStyle) {

        TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.KittBar, defStyle, 0);
        LayoutInflater.from(getContext()).inflate(R.layout.kitt_bar, this, true);

        // 背景透明度，默认不透明
        float fraction = array.getFloat(R.styleable.KittBar_ui_background_alpha, 1);
        setBackgroundAlpha(fraction);

        // 返回键
        mBack = ((ImageView) findViewById(R.id.kitt_bar_back));

        // 设置返回键资源
        int backResourceId = array.getResourceId(R.styleable.KittBar_ui_backIcon_src, 0);
        setBackIconResource(backResourceId);
        // 设置返回键的padding
        int backIconPadding = (int) array.getDimension(R.styleable.KittBar_ui_backIcon_padding, 0);
        setBackIconPadding(backIconPadding);
        // 设置返回键是否可见
        int backVisible = array.getInt(R.styleable.KittBar_ui_backIcon_visibility, VISIBLE);
        setBackIconVisibility(backVisible);

        // 自定义布局
        mCustomLayout = findViewById(R.id.kitt_bar_custom_layout);

        // 标题
        mTitle = ((TextView) findViewById(R.id.kitt_bar_title));
        mTitle.setSelected(true);

        int titleColor = array.getColor(R.styleable.KittBar_ui_title_color, Color.parseColor("#000000"));
        setTitleColor(titleColor);

        String titleStr = array.getString(R.styleable.KittBar_ui_title_content);
        setTitleContent(titleStr);

        float titleSize = array.getDimension(R.styleable.KittBar_ui_title_textSize, dip2px(18));
        setTitleTextSize(titleSize);

        int titlePaddingLeft = (int) array.getDimension(R.styleable.KittBar_ui_title_paddingLeft, 0);
        int titlePaddingRight = (int) array.getDimension(R.styleable.KittBar_ui_title_paddingRight, 0);
        setTitlePadding(titlePaddingLeft, titlePaddingRight);


        int titleLeftDrawableId = array.getResourceId(R.styleable.KittBar_ui_title_drawableLeft, 0);
        int titleRightDrawableId = array.getResourceId(R.styleable.KittBar_ui_title_drawableRight, 0);
        int titleTopDrawableId = array.getResourceId(R.styleable.KittBar_ui_title_drawableTop, 0);
        int titleBottomDrawableId = array.getResourceId(R.styleable.KittBar_ui_title_drawableBottom, 0);

        setTitleCompoundDrawables(getDrawable(titleLeftDrawableId), getDrawable(titleTopDrawableId),
                getDrawable(titleRightDrawableId), getDrawable(titleBottomDrawableId));

        int titleDrawablePadding = (int) array.getDimension(R.styleable.KittBar_ui_title_drawablePadding, 0);
        setTitleCompoundDrawablePadding(titleDrawablePadding);

        // 搜索区域
        mSearchLayout = findViewById(R.id.kitt_bar_searchLayout);

        // 是否可见
        int searchLayoutVisible = array.getInt(R.styleable.KittBar_ui_searchLayout_visibility, INVISIBLE);
        setSearchLayoutVisibility(searchLayoutVisible);

        mSearchLayoutDrawable = getSearchLayoutDrawable();
        mSearchLayout.setBackground(mSearchLayoutDrawable);

        Drawable searchBg = array.getDrawable(R.styleable.KittBar_ui_searchLayout_color);
        setSearchLayoutBackground(searchBg);

        int searchBgStoke = (int) array.getDimension(R.styleable.KittBar_ui_searchLayout_stoke_width, 0);
        int searchBgStokeColor = array.getColor(R.styleable.KittBar_ui_searchLayout_stoke_color, Color.parseColor("#00000000"));
        setSearchLayoutStoke(searchBgStoke, searchBgStokeColor);

        float searchBgRadius = array.getDimension(R.styleable.KittBar_ui_searchLayout_radius, 0);
        setSearchLayoutCornerRadius(searchBgRadius);

        int searchLayoutMargin = (int) array.getDimension(R.styleable.KittBar_ui_searchLayout_margin, 0);
        int searchLayoutMarginTop = (int) array.getDimension(R.styleable.KittBar_ui_searchLayout_marginTop, 0);
        int searchLayoutMarginBottom = (int) array.getDimension(R.styleable.KittBar_ui_searchLayout_marginBottom, 0);
        int searchLayoutMarginLeft = (int) array.getDimension(R.styleable.KittBar_ui_searchLayout_marginLeft, 0);
        int searchLayoutMarginRight = (int) array.getDimension(R.styleable.KittBar_ui_searchLayout_marginRight, 0);

        if (searchLayoutMargin == 0)
            setSearchLayoutMargin(searchLayoutMarginLeft, searchLayoutMarginTop, searchLayoutMarginRight, searchLayoutMarginBottom);
        else
            setSearchLayoutMargin(searchLayoutMargin, searchLayoutMargin, searchLayoutMargin, searchLayoutMargin);

        // 搜索编辑框
        mSearchView = ((EditText) findViewById(R.id.kitt_bar_search_edit));

        String hint = array.getString(R.styleable.KittBar_ui_searchEdit_hint);
        setSearchEditHint(hint);
        int hintColor = array.getColor(R.styleable.KittBar_ui_searchEdit_hintColor, getResources().getColor(android.R.color.darker_gray));
        setSearchEditHintColor(hintColor);
        float editSize = array.getDimension(R.styleable.KittBar_ui_searchEdit_textSize, dip2px(13));
        setSearchEditTextSize(editSize);
        int editColor = array.getColor(R.styleable.KittBar_ui_searchEdit_textColor, getResources().getColor(android.R.color.black));
        setSearchEditTextColor(editColor);

        int searchEditViewPaddingLeft = (int) array.getDimension(R.styleable.KittBar_ui_searchEdit_paddingLeft, 0);
        int searchEditViewPaddingRight = (int) array.getDimension(R.styleable.KittBar_ui_searchEdit_paddingRight, 0);
        mSearchRightTemp = ((ImageView) findViewById(R.id.kitt_right_temp));
        setSearchEditViewPadding(searchEditViewPaddingLeft, searchEditViewPaddingRight);


        int drawableLeftResourceId = array.getResourceId(R.styleable.KittBar_ui_searchEdit_drawableLeft, 0);
        setSearchEditViewDrawableLeft(getDrawable(drawableLeftResourceId));

        mSearchRightIcon = ((ImageView) findViewById(R.id.kitt_edit_right_icon));
        int searchRightIconId = array.getResourceId(R.styleable.KittBar_ui_searchEdit_drawableRight, 0);
        setSearchEditViewDrawableRight(getDrawable(searchRightIconId));

        // 清除按钮
        mClearView = ((ImageView) findViewById(R.id.kitt_clear_View));
        // 要放在设置编辑框drawPadding前
        int closeResourceId = array.getResourceId(R.styleable.KittBar_ui_searchEditClear_src, R.drawable.lib_temp_close);
        setSearchEditClearViewResource(getDrawable(closeResourceId));

        int drawablePadding = (int) array.getDimension(R.styleable.KittBar_ui_searchEdit_drawablePadding, 0);
        setSearchEditCompoundDrawablePadding(drawablePadding);

        // 是否支持清除和输入
        mSupportWriteAndClear = array.getInt(R.styleable.KittBar_ui_searchEdit_support_write_and_clear, 0);
        EditSupport editSupport;
        switch (mSupportWriteAndClear) {
            case 1:
                editSupport = EditSupport.NONE_SUPPORT;
                break;
            case 2:
                editSupport = EditSupport.ONLY_WRITE;
                break;
            default:
                editSupport = EditSupport.WRITE_AND_CLEAR;
                break;
        }
        setSearchEditSupportWriteAndClear(editSupport);


        // 是否支持提示内容搜索
        mSupportHintSearch = array.getBoolean(R.styleable.KittBar_ui_searchEdit_hint_search_support, false);


        // 右侧按钮
        mRightBtn1 = findViewById(R.id.kitt_bar_right_btn1);
        mRightBtn2 = findViewById(R.id.kitt_bar_right_btn2);
        mRightBtn3 = findViewById(R.id.kitt_bar_right_btn3);

        int flagValue = array.getInt(R.styleable.KittBar_ui_rightButton_show, 0);
        setRightButtonsVisibility(flagValue);

        float rightBtnPadding = array.getDimension(R.styleable.KittBar_ui_rightButton_padding, 0);
        setRightButtonsPadding((int) rightBtnPadding);

        float rightBtnTextSize = array.getDimension(R.styleable.KittBar_ui_rightButton_textSize, dip2px(14));
        setRightButtonsTextSize(rightBtnTextSize);

        int color = array.getColor(R.styleable.KittBar_ui_rightButton_textColor, Color.parseColor("#000000"));
        setRightButtonsTextColor(color);

        String text1 = array.getString(R.styleable.KittBar_ui_rightButton_first_text);
        setRightButtonsText(RightBtn.RIGHT_FIRST, text1);
        String text2 = array.getString(R.styleable.KittBar_ui_rightButton_second_text);
        setRightButtonsText(RightBtn.RIGHT_SECOND, text2);
        String text3 = array.getString(R.styleable.KittBar_ui_rightButton_third_text);
        setRightButtonsText(RightBtn.RIGHT_THIRD, text3);

        int resourceId1 = array.getResourceId(R.styleable.KittBar_ui_rightButton_first_src, 0);
        setRightButtonsImage(RightBtn.RIGHT_FIRST, getDrawable(resourceId1));
        int resourceId2 = array.getResourceId(R.styleable.KittBar_ui_rightButton_second_src, 0);
        setRightButtonsImage(RightBtn.RIGHT_SECOND, getDrawable(resourceId2));
        int resourceId3 = array.getResourceId(R.styleable.KittBar_ui_rightButton_third_src, 0);
        setRightButtonsImage(RightBtn.RIGHT_THIRD, getDrawable(resourceId3));

        // 回收
        array.recycle();

    }


    /**
     * 设置导航条的透明度
     *
     * @param alphaPercent 0 means fully transparent, and 1 means fully opaque.
     */
    public void setBackgroundAlpha(@FloatRange(from = 0, to = 1) float alphaPercent) {

        Drawable background = this.getBackground();

        if (background == null)
            return;

        int alpha = 1;

        if (alphaPercent >= 1) {
            alpha = 255;
        } else {
            alpha = (int) (255 * alphaPercent);
        }

        background.mutate().setAlpha(alpha);
    }

    /**
     * 设置返回键按钮图片
     *
     * @param resId The desired resource identifier, as generated by the aapt
     *              tool. This integer encodes the package, type, and resource
     *              entry. The value 0 is an invalid identifier.
     */
    public void setBackIconResource(@DrawableRes int resId) {
        try {
            Drawable drawable_n = getResources().getDrawable(resId);
            mBack.setImageDrawable(drawable_n);
        } catch (Exception e) {
            Log.e("XTopNavigationBar", "setBackIconResource()", e);
        }
    }

    /**
     * 设置返回键的左、右padding
     *
     * @param backIconPadding the left and right padding in pixels
     */
    public void setBackIconPadding(int backIconPadding) {
        mBack.setPadding(backIconPadding, 0, backIconPadding, 0);
    }


    /**
     * 设置返回键是否显示
     *
     * @param visibility One of {@link #VISIBLE}, {@link #INVISIBLE}.
     */
    public void setBackIconVisibility(@Visibility int visibility) {
        mBack.setVisibility(visibility);
    }

    /**
     * 添加自定义布局
     *
     * @param resource ID for an XML layout resource to load (e.g.,
     *                 <code>R.layout.main_page</code>)
     */
    public void setCustomLayout(@LayoutRes int resource) {
        View inflate = LayoutInflater.from(getContext()).inflate(resource, this, false);
        mCustomLayout.removeAllViews();
        mCustomLayout.addView(inflate);
        mCustomView = inflate;
    }

    /**
     * 设置搜标题颜色
     *
     * @param color A color value in the form 0xAARRGGBB.
     */
    public void setTitleColor(@ColorInt int color) {
        mTitle.setTextColor(color);
    }

    /**
     * 设置标题颜色
     *
     * <ul>
     *   <li><code>#RRGGBB</code></li>
     *   <li><code>#AARRGGBB</code></li>
     * </ul>
     * <p>
     * The following names are also accepted: <code>red</code>, <code>blue</code>,
     * <code>green</code>, <code>black</code>, <code>white</code>, <code>gray</code>,
     * <code>cyan</code>, <code>magenta</code>, <code>yellow</code>, <code>lightgray</code>,
     * <code>darkgray</code>, <code>grey</code>, <code>lightgrey</code>, <code>darkgrey</code>,
     * <code>aqua</code>, <code>fuchsia</code>, <code>lime</code>, <code>maroon</code>,
     * <code>navy</code>, <code>olive</code>, <code>purple</code>, <code>silver</code>,
     * and <code>teal</code>.</p>
     */
    public void setTitleColor(@Size(min = 1) String colorString) {
        mTitle.setTextColor(Color.parseColor(colorString));
    }

    /**
     * 设置标题内容
     *
     * @param text text to be displayed
     */
    public void setTitleContent(CharSequence text) {
        mTitle.setText(text);
    }

    /**
     * 设置标题内容
     *
     * @param resId the resource identifier of the string resource to be displayed
     */
    public void setTitleContent(@StringRes int resId) {
        mTitle.setText(resId);
    }

    /**
     * 设置标题文字的大小，默认字体单位为px
     *
     * @param size The scaled pixel size.
     */
    public void setTitleTextSize(float size) {
        mTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
    }

    /**
     * 设置标题文字的大小
     *
     * @param unit The desired dimension unit.
     * @param size The desired size in the given units.
     */
    public void setTitleTextSize(int unit, float size) {
        mTitle.setTextSize(unit, size);
    }

    /**
     * 设置标题的左、右内边距
     *
     * @param left  the left padding in pixels.
     * @param right the right padding in pixels.
     */
    public void setTitlePadding(int left, int right) {
        mTitle.setPadding(left, 0, right, 0);
    }

    /**
     * 设置标题的左内边距
     *
     * @param left the left padding in pixels.
     */
    public void setTitlePaddingLeft(int left) {
        mTitle.setPadding(left, 0, 0, 0);
    }

    /**
     * 设置标题的右内边距
     *
     * @param right the right padding in pixels.
     */
    public void setTitlePaddingRight(int right) {
        mTitle.setPadding(0, 0, right, 0);
    }

    /**
     * 设置标题的左上右下的图片
     */
    public void setTitleCompoundDrawables(Drawable drawableLeft, Drawable drawableTop, Drawable drawableRight, Drawable drawableBottom) {

        try {
            if (drawableLeft != null)
                drawableLeft.setBounds(0, 0, drawableLeft.getMinimumWidth(), drawableLeft.getMinimumHeight());

            if (drawableTop != null)
                drawableTop.setBounds(0, 0, drawableTop.getMinimumWidth(), drawableTop.getMinimumHeight());

            if (drawableRight != null)
                drawableRight.setBounds(0, 0, drawableRight.getMinimumWidth(), drawableRight.getMinimumHeight());

            if (drawableBottom != null)
                drawableBottom.setBounds(0, 0, drawableBottom.getMinimumWidth(), drawableBottom.getMinimumHeight());

            mTitle.setCompoundDrawables(drawableLeft, drawableTop, drawableRight, drawableBottom);
        } catch (Exception e) {
            Log.e("TopBar", "setTitleDrawable()", e);
        }
    }

    /**
     * 设置标题文字距离图片的距离
     *
     * @param padding pixels padding.
     */
    public void setTitleCompoundDrawablePadding(int padding) {
        mTitle.setCompoundDrawablePadding(padding);
    }


    /**
     * 设置搜索布局背景
     *
     * @param background The Drawable to use as the background
     */
    private void setSearchLayoutBackground(Drawable background) {
        if (background instanceof ColorDrawable)
            mSearchLayoutDrawable.setColor(((ColorDrawable) background).getColor());
    }

    /**
     * 设置搜索布局背景色
     *
     * @param color A color value in the form 0xAARRGGBB.
     */
    public void setSearchLayoutColor(@ColorInt int color) {
        mSearchLayoutDrawable.setColor(color);
    }

    /**
     * 设置搜索布局背景色
     *
     * <ul>
     *   <li><code>#RRGGBB</code></li>
     *   <li><code>#AARRGGBB</code></li>
     * </ul>
     * <p>
     * The following names are also accepted: <code>red</code>, <code>blue</code>,
     * <code>green</code>, <code>black</code>, <code>white</code>, <code>gray</code>,
     * <code>cyan</code>, <code>magenta</code>, <code>yellow</code>, <code>lightgray</code>,
     * <code>darkgray</code>, <code>grey</code>, <code>lightgrey</code>, <code>darkgrey</code>,
     * <code>aqua</code>, <code>fuchsia</code>, <code>lime</code>, <code>maroon</code>,
     * <code>navy</code>, <code>olive</code>, <code>purple</code>, <code>silver</code>,
     * and <code>teal</code>.</p>
     */
    public void setSearchLayoutColor(@Size(min = 1) String colorString) {
        mSearchLayoutDrawable.setColor(Color.parseColor(colorString));
    }

    /**
     * 设置搜索布局背景描边
     *
     * @param width The width in pixels of the stroke
     * @param color The color of the stroke
     */
    public void setSearchLayoutStoke(int width, @ColorInt int color) {
        mSearchLayoutDrawable.setStroke(width, color);
    }

    /**
     * 设置搜索布局背景描边
     *
     * @param width       The width in pixels of the stroke
     * @param colorString like #AARRGGBB
     */
    public void setSearchLayoutStoke(int width, @Size(min = 1) String colorString) {
        mSearchLayoutDrawable.setStroke(width, Color.parseColor(colorString));
    }

    /**
     * 设置搜索布局背景圆角
     *
     * @param radius The radius in pixels of the corners of the background
     */
    public void setSearchLayoutCornerRadius(float radius) {
        mSearchLayoutDrawable.setCornerRadius(radius);
    }

    /**
     * 设置搜索布局外边距
     *
     * @param left   the left margin size
     * @param top    the top margin size
     * @param right  the right margin size
     * @param bottom the bottom margin size
     */
    public void setSearchLayoutMargin(int left, int top, int right, int bottom) {
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) mSearchLayout.getLayoutParams();
        layoutParams.setMargins(left, top, right, bottom);
        mSearchLayout.setLayoutParams(layoutParams);
    }


    /**
     * 设置搜索布局是否显示
     *
     * @param visibility One of {@link #VISIBLE}, {@link #INVISIBLE}.
     */
    public void setSearchLayoutVisibility(@Visibility int visibility) {
        mSearchLayout.setVisibility(visibility);
    }

    /**
     * 设置搜索提示内容
     *
     * @param hint Sets the text to be displayed when the text of the TextView is empty
     */
    public void setSearchEditHint(CharSequence hint) {

        if (hint == null)
            hint = "";

        mSearchView.setHint(hint);
    }

    /**
     * 设置搜索提示内容
     *
     * @param hint Sets the text to be displayed when the text of the TextView is empty
     */
    public void setSearchEditHint(@StringRes int hint) {
        mSearchView.setHint(hint);
    }


    /**
     * 设置搜索提示内容的颜色
     *
     * @param color Sets the text color to be displayed when the text of the TextView is empty
     */
    public void setSearchEditHintColor(@ColorInt int color) {
        mSearchView.setHintTextColor(color);
    }

    /**
     * 设置搜索提示内容的颜色
     *
     * @param colorString Sets the text color to be displayed when the text of the TextView is empty
     */
    public void setSearchEditHintColor(@Size(min = 1) String colorString) {
        mSearchView.setHintTextColor(Color.parseColor(colorString));
    }

    /**
     * 设置搜索编辑框文字的大小和提示内容文字大小
     *
     * @param size The desired size in the px units.
     */
    public void setSearchEditTextSize(float size) {
        mSearchView.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
    }

    /**
     * 设置搜索编辑框文字的大小和提示内容文字大小
     *
     * @param unit The desired dimension unit.
     * @param size The desired size in the given units.
     */
    public void setSearchEditTextSize(int unit, float size) {
        mSearchView.setTextSize(unit, size);
    }

    /**
     * 设置搜索编辑框文字颜色
     *
     * @param color A color value in the form 0xAARRGGBB.
     */
    public void setSearchEditTextColor(@ColorInt int color) {
        mSearchView.setTextColor(color);
    }

    /**
     * 设置搜索编辑框文字颜色
     *
     * @param colorString <ul>
     *                    <li><code>#RRGGBB</code></li>
     *                    <li><code>#AARRGGBB</code></li>
     *                    </ul>
     *
     *                    <p>The following names are also accepted: <code>red</code>, <code>blue</code>,
     *                    <code>green</code>, <code>black</code>, <code>white</code>, <code>gray</code>,
     *                    <code>cyan</code>, <code>magenta</code>, <code>yellow</code>, <code>lightgray</code>,
     *                    <code>darkgray</code>, <code>grey</code>, <code>lightgrey</code>, <code>darkgrey</code>,
     *                    <code>aqua</code>, <code>fuchsia</code>, <code>lime</code>, <code>maroon</code>,
     *                    <code>navy</code>, <code>olive</code>, <code>purple</code>, <code>silver</code>,
     *                    and <code>teal</code>.</p>
     */
    public void setSearchEditTextColor(@Size(min = 1) String colorString) {
        mSearchView.setTextColor(Color.parseColor(colorString));
    }


    /**
     * 设置搜索编辑框的内边距
     *
     * @param left  the left padding in pixels.
     * @param right the right padding in pixels.
     */
    public void setSearchEditViewPadding(int left, int right) {
        mSearchView.setPadding(left, 0, right, 0);
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) mSearchRightTemp.getLayoutParams();
        layoutParams.rightMargin = right;
        mSearchRightTemp.setLayoutParams(layoutParams);
    }

    /**
     * 设置搜索编辑框左侧的图片
     */
    public void setSearchEditViewDrawableLeft(@Nullable Drawable left) {

        if (left != null)
            left.setBounds(0, 0, left.getMinimumWidth(), left.getMinimumHeight());

        mSearchView.setCompoundDrawables(left, null, null, null);
    }

    /**
     * 设置搜索编辑框右侧的图片
     *
     * @param right Use {@code null} if you do not want a
     *              Drawable there
     */
    public void setSearchEditViewDrawableRight(@Nullable Drawable right) {
        mSearchRightIcon.setImageDrawable(right);
    }


    /**
     * 设置搜索输入文字距离与图片之间的距离
     *
     * @param padding padding pixels padding.
     */
    public void setSearchEditCompoundDrawablePadding(int padding) {
        mSearchView.setCompoundDrawablePadding(padding);
        Drawable drawable = mSearchRightIcon.getDrawable();
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) mSearchRightIcon.getLayoutParams();
        layoutParams.leftMargin = drawable == null ? 0 : padding;
        mSearchRightIcon.setLayoutParams(layoutParams);
        Drawable drawable1 = mClearView.getDrawable();
        LinearLayout.LayoutParams layoutParams1 = (LinearLayout.LayoutParams) mClearView.getLayoutParams();
        layoutParams1.leftMargin = drawable1 == null ? 0 : padding;
        mClearView.setLayoutParams(layoutParams1);
    }

    /**
     * 设置搜索清除的按钮
     *
     * @param clearIcon Use {@code null} if you do not want a
     *                  Drawable there
     */
    public void setSearchEditClearViewResource(@Nullable Drawable clearIcon) {
        mClearView.setImageDrawable(clearIcon);
    }


    /**
     * 编辑框支持
     */
    public void setSearchEditSupportWriteAndClear(EditSupport support) {
        // 不支持写入时 不弹出键盘
        mSearchView.setKeyListener(support.getType() == 1 ? null : new TextKeyListener(TextKeyListener.Capitalize.NONE, false));
        // 是否显示清除按钮
        mClearView.setVisibility(support.getType() == 0 ? (TextUtils.isEmpty(mSearchView.getText().toString().trim()) ? GONE : VISIBLE) : GONE);
        mSupportWriteAndClear = support.getType();
    }

    /**
     * 是否支持提示内容的搜索
     */
    public void setSearchEditSupportWriteAndClear(boolean supportHintSearch) {
        this.mSupportHintSearch = supportHintSearch;
    }


    /**
     * 设置右侧按钮是否可见
     */
    private void setRightButtonsVisibility(int flag) {

        mRightBtn1.setVisibility(GONE);
        mRightBtn2.setVisibility(GONE);
        mRightBtn3.setVisibility(GONE);

        int $hundred = flag / 100;
        int $ten = (flag - $hundred * 100) / 10;
        int $one = flag - $hundred * 100 - $ten * 10;

        if ($hundred == 0)
            mRightBtn3.setVisibility(GONE);
        else
            mRightBtn3.setVisibility(VISIBLE);

        if ($ten == 0)
            mRightBtn2.setVisibility(GONE);
        else
            mRightBtn2.setVisibility(VISIBLE);

        if ($one == 0)
            mRightBtn1.setVisibility(GONE);
        else
            mRightBtn1.setVisibility(VISIBLE);
    }

    /**
     * 设置右侧三个按钮是否可见
     */
    public void setRightButtonsVisibility(boolean leftVisible, boolean middleVisible, boolean rightVisible) {
        int flag = 0;
        flag += (leftVisible ? 1 : 0);
        flag += (middleVisible ? 10 : 0);
        flag += (rightVisible ? 100 : 0);
        setRightButtonsVisibility(flag);
    }


    /**
     * 设置右侧三个按钮内边距
     *
     * @param padding the left、right padding in pixels
     */
    public void setRightButtonsPadding(int padding) {
        mRightBtn1.setPadding(padding, 0, padding, 0);
        mRightBtn2.setPadding(padding, 0, padding, 0);
        mRightBtn3.setPadding(padding, 0, padding, 0);
    }

    /**
     * 设置右侧三个按钮的字体大小
     *
     * @param size he desired size in the px units.
     */
    public void setRightButtonsTextSize(float size) {
        mRightBtn1.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
        mRightBtn2.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
        mRightBtn3.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
    }


    /**
     * 设置右侧三个按钮的字体大小
     *
     * @param unit The desired dimension unit.
     * @param size The desired size in the given units.
     */
    public void setRightButtonsTextSize(int unit, float size) {
        mRightBtn1.setTextSize(unit, size);
        mRightBtn2.setTextSize(unit, size);
        mRightBtn3.setTextSize(unit, size);
    }


    /**
     * 设置右侧三个按钮的字体颜色
     *
     * @param color color value in the form 0xAARRGGBB.
     */
    public void setRightButtonsTextColor(@ColorInt int color) {
        mRightBtn1.setTextColor(color);
        mRightBtn2.setTextColor(color);
        mRightBtn3.setTextColor(color);
    }

    /**
     * TODO 设置右侧三个按钮的字体颜色
     *
     * @param colorString <ul>
     *                    <li><code>#RRGGBB</code></li>
     *                    <li><code>#AARRGGBB</code></li>
     *                    </ul>
     *
     *                    <p>The following names are also accepted: <code>red</code>, <code>blue</code>,
     *                    <code>green</code>, <code>black</code>, <code>white</code>, <code>gray</code>,
     *                    <code>cyan</code>, <code>magenta</code>, <code>yellow</code>, <code>lightgray</code>,
     *                    <code>darkgray</code>, <code>grey</code>, <code>lightgrey</code>, <code>darkgrey</code>,
     *                    <code>aqua</code>, <code>fuchsia</code>, <code>lime</code>, <code>maroon</code>,
     *                    <code>navy</code>, <code>olive</code>, <code>purple</code>, <code>silver</code>,
     *                    and <code>teal</code>.</p>
     */
    public void setRightButtonsTextColor(@Size(min = 1) String colorString) {
        mRightBtn1.setTextColor(Color.parseColor(colorString));
        mRightBtn2.setTextColor(Color.parseColor(colorString));
        mRightBtn3.setTextColor(Color.parseColor(colorString));
    }

    /**
     * 设置右侧三个按钮的文字内容
     *
     * @param rightBtn witch right button to be set
     * @param text     text to be displayed
     */
    public void setRightButtonsText(RightBtn rightBtn, CharSequence text) {

        if (text == null)
            text = "";

        switch (rightBtn) {
            case RIGHT_FIRST:
                mRightBtn1.setText(text);
                break;
            case RIGHT_SECOND:
                mRightBtn2.setText(text);
                break;
            case RIGHT_THIRD:
                mRightBtn3.setText(text);
                break;
        }
    }

    /**
     * 设置右侧三个按钮的文字内容
     *
     * @param rightBtn witch right button to be set
     * @StringRes int resId
     */
    public void setRightButtonsText(RightBtn rightBtn, @StringRes int resId) {

        switch (rightBtn) {
            case RIGHT_FIRST:
                mRightBtn1.setText(resId);
                break;
            case RIGHT_SECOND:
                mRightBtn2.setText(resId);
                break;
            case RIGHT_THIRD:
                mRightBtn3.setText(resId);
                break;
        }
    }


    /**
     * 设置右侧三个按钮的图片
     *
     * @param rightBtn witch right button to be set
     * @param drawable Use {@code null} if you do not want a
     *                 Drawable there
     */
    public void setRightButtonsImage(RightBtn rightBtn, @Nullable Drawable drawable) {

        if (drawable != null)
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());

        switch (rightBtn) {
            case RIGHT_FIRST:
                mRightBtn1.setCompoundDrawables(drawable, null, null, null);
                break;
            case RIGHT_SECOND:
                mRightBtn2.setCompoundDrawables(drawable, null, null, null);
                break;
            case RIGHT_THIRD:
                mRightBtn3.setCompoundDrawables(drawable, null, null, null);
                break;
        }
    }

    /**
     * 设置只能padding ,用来适应透明导航栏
     */
    public void setBarSmartPadding() {
        KittBarUtils.setPaddingSmart(getContext(), this);
    }

    /**
     * 设置监听
     */
    public void setOnBarEventListener(OnBarEventListener listener) {
        this.mListener = listener;
    }


    /**
     * 获取搜索的提示内容
     */
    public String getBarSearchWord() {
        return mSearchView.getText().toString();
    }


    /******************私有方法**********************/

    private float dip2px(float dpValue) {
        final float scale = getResources().getDisplayMetrics().density;
        return dpValue * scale + 0.5f;
    }


    @SuppressLint("UseCompatLoadingForDrawables")
    private Drawable getDrawable(@DrawableRes int resId) {
        try {
            return getResources().getDrawable(resId);
        } catch (Exception e) {
            return null;
        }
    }

    private GradientDrawable getSearchLayoutDrawable() {

        GradientDrawable drawable = new GradientDrawable();
        //设置圆角大小
        drawable.setCornerRadius(1000);
        //设置边缘线的宽以及颜色
        drawable.setStroke(0, Color.parseColor("#000000"));
        //设置shape背景色
        drawable.setColor(Color.parseColor("#F1F1F1"));
        return drawable;
    }
}
