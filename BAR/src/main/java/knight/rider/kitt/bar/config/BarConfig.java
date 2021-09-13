package knight.rider.kitt.bar.config;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;

import androidx.annotation.DrawableRes;

import knight.rider.kitt.bar.R;
import knight.rider.kitt.bar.attr.BarTitleGravity;
import knight.rider.kitt.bar.attr.TextStyle;

/**
 * 全局样式，只要请单文件、代码无设置就会使用全局的样式，
 * 请尽量减少使用全局参数，全局95%都在用的在进行设置，如：返回键的资源id
 * 否则部分布局使用不到的全局样式也需要进行单独替换
 */
public class BarConfig {

    private final static BarConfig INSTANCE = new BarConfig();

    private static Builder builder;

    private BarConfig() {
        builder = new Builder();
    }

    public static BarConfig getInstance() {
        return INSTANCE;
    }


    public BarConfig setTitleGravity(BarTitleGravity titleGravity) {
        builder.setTitleGravity(titleGravity);
        return this;
    }

    public BarConfig setBackIcon(@DrawableRes int backIcon) {
        builder.setBackIcon(backIcon);
        return this;
    }

    public BarConfig setBackIconVerticalPadding(int backIconVerticalPadding) {
        builder.setBackIconVerticalPadding(backIconVerticalPadding);
        return this;
    }

    public BarConfig setBackIconPaddingLeft(int backIconPaddingLeft) {
        builder.setBackIconPaddingLeft(backIconPaddingLeft);
        return this;
    }

    public BarConfig setBackIconPaddingRight(int backIconPaddingRight) {
        builder.setBackIconPaddingRight(backIconPaddingRight);
        return this;
    }

    public BarConfig setTitleColor(int titleColor) {
        builder.setTitleColor(titleColor);
        return this;
    }

    public BarConfig setTitleTextSize(float titleTextSize) {
        builder.setTitleTextSize(titleTextSize);
        return this;
    }

    public BarConfig setTitleTextStyle(TextStyle titleTextStyle) {
        builder.setTitleTextStyle(titleTextStyle);
        return this;
    }

    /**
     * 如想无背景，请传透明ColorDrawable，不要传null
     */
    public BarConfig setSearchLayoutBackground(Drawable searchLayoutBackground) {
        builder.setSearchLayoutBackground(searchLayoutBackground);
        return this;
    }

    public BarConfig setSearchLayoutMarginLeft(int searchLayoutMarginLeft) {
        builder.setSearchLayoutMarginLeft(searchLayoutMarginLeft);
        return this;
    }


    public BarConfig setSearchLayoutMarginRight(int searchLayoutMarginRight) {
        builder.setSearchLayoutMarginRight(searchLayoutMarginRight);
        return this;
    }

    public BarConfig setSearchEditHintColor(int searchEditHintColor) {
        builder.setSearchEditHintColor(searchEditHintColor);
        return this;
    }

    public BarConfig setSearchEditTextColor(int searchEditTextColor) {
        builder.setSearchEditTextColor(searchEditTextColor);
        return this;
    }

    public BarConfig setSearchEditTextSize(int searchEditTextSize) {
        builder.setSearchEditTextSize(searchEditTextSize);
        return this;
    }

    public BarConfig setSearchEditPaddingLeft(int searchEditPaddingLeft) {
        builder.setSearchEditPaddingLeft(searchEditPaddingLeft);
        return this;
    }

    public BarConfig setSearchEditPaddingRight(int searchEditPaddingRight) {
        builder.setSearchEditPaddingRight(searchEditPaddingRight);
        return this;
    }

    public BarConfig setSearchEditClear(Drawable searchEditClear) {
        builder.setSearchEditClear(searchEditClear);
        return this;
    }

    public BarConfig setSearchEditClearPaddingLeft(int searchEditClearPaddingLeft) {
        builder.setSearchEditClearPaddingLeft(searchEditClearPaddingLeft);
        return this;
    }


    public BarConfig setSearchEditClearPaddingRight(int searchEditClearPaddingRight) {
        builder.setSearchEditClearPaddingRight(searchEditClearPaddingRight);
        return this;
    }

    public BarConfig setBarSearchLayoutHeight(int searchLayoutHeight) {
        builder.setBarSearchLayoutHeight(searchLayoutHeight);
        return this;
    }

    public BarConfig setRightButtonTextSize(int rightButtonTextSize) {
        builder.setRightButtonTextSize(rightButtonTextSize);
        return this;
    }

    public BarConfig setRightButtonTextColor(int rightButtonTextColor) {
        builder.setRightButtonTextColor(rightButtonTextColor);
        return this;
    }

    // 返回builder
    public BarConfig.Builder getBuilder() {
        return builder;
    }

    public static class Builder {

        // 标题的位置
        private BarTitleGravity titleGravity;
        // 返回键按钮
        private String backIcon = "";
        // 返回键icon的垂直方向padding，同时设置paddingTop、paddingBottom
        private String backIcon_verticalPadding = "";
        // 返回键icon的paddingLeft
        private String backIcon_paddingLeft = "";
        // bar_backIcon_paddingRight
        private String backIcon_paddingRight = "";
        // title颜色
        private String title_color = "";
        // title字体大小
        private String title_textSize = "";
        // title字体样式
        private TextStyle title_textStyle;


        // 搜索框高度
        private String bar_searchLayout_height = "";
        // 搜索框背景
        private Drawable bar_searchLayoutBackground;
        // 搜索框margin
        private String bar_searchLayout_marginLeft = "";
        private String bar_searchLayout_marginRight = "";

        // 清除按钮
        private Drawable bar_searchEditClear_src;
        // 清除padding
        private String bar_searchEditClear_paddingLeft = "";
        private String bar_searchEditClear_paddingRight = "";

        // 搜索框输入控件的hint颜色
        private String bar_searchEdit_hintColor = "";
        // 搜索框输入控件的字体大小
        private String bar_searchEdit_textColor = "";
        // 搜索框输入控件的字体大小
        private String bar_searchEdit_textSize = "";
        // 搜索框输入控件的padding
        private String bar_searchEdit_paddingLeft = "";
        private String bar_searchEdit_paddingRight = "";

        private String bar_rightButton_textSize = "";
        private String bar_rightButton_textColor = "";

        public BarTitleGravity getTitleGravity() {
            return titleGravity == null ? BarTitleGravity.LEFT : titleGravity;
        }

        public void setTitleGravity(BarTitleGravity title_gravity) {
            this.titleGravity = title_gravity;
        }

        public int getBarBackIcon() {
            return backIcon.equals("") ? R.drawable.kitt_bar_back : Integer.parseInt(backIcon);
        }

        public void setBackIcon(int backIcon) {
            this.backIcon = String.valueOf(backIcon);
        }

        public int getBackIconVerticalPadding() {
            return backIcon_verticalPadding.equals("") ? 0 : Integer.parseInt(backIcon_verticalPadding);
        }

        public void setBackIconVerticalPadding(int backIcon_verticalPadding) {
            this.backIcon_verticalPadding = String.valueOf(backIcon_verticalPadding);
        }

        public int getBackIconPaddingRight() {
            return backIcon_paddingRight.equals("") ? 0 : Integer.parseInt(backIcon_paddingRight);
        }

        public void setBackIconPaddingRight(int backIcon_paddingRight) {
            this.backIcon_paddingRight = String.valueOf(backIcon_paddingRight);
        }

        public int getBackIconPaddingLeft() {
            return backIcon_paddingLeft.equals("") ? 0 : Integer.parseInt(backIcon_paddingLeft);
        }

        public void setBackIconPaddingLeft(int backIcon_paddingLeft) {
            this.backIcon_paddingLeft = String.valueOf(backIcon_paddingLeft);
        }

        public int getTitleColor() {
            return title_color.equals("") ? Color.parseColor("#000000") : Integer.parseInt(title_color);
        }

        public void setTitleColor(int title_color) {
            this.title_color = String.valueOf(title_color);
        }

        public float getTitleTextSize() {
            return title_textSize.equals("") ? 30 : Float.parseFloat(title_textSize);
        }

        public void setTitleTextSize(float title_textSize) {
            this.title_textSize = String.valueOf(title_textSize);
        }

        public TextStyle getTitleTextStyle() {
            return title_textStyle == null ? TextStyle.NORMAL : title_textStyle;
        }

        public void setTitleTextStyle(TextStyle title_textStyle) {
            this.title_textStyle = title_textStyle;
        }

        public Drawable getSearchLayoutBackground() {
            return bar_searchLayoutBackground == null ? getSearchLayoutDrawable() : bar_searchLayoutBackground;
        }

        public void setSearchLayoutBackground(Drawable bar_searchLayoutBackground) {
            this.bar_searchLayoutBackground = bar_searchLayoutBackground;
        }

        public int getSearchLayoutMarginLeft() {
            return bar_searchLayout_marginLeft.equals("") ? 0 : Integer.parseInt(bar_searchLayout_marginLeft);
        }

        public void setSearchLayoutMarginLeft(int bar_searchLayout_marginLeft) {
            this.bar_searchLayout_marginLeft = String.valueOf(bar_searchLayout_marginLeft);
        }

        public int getSearchLayoutMarginRight() {
            return bar_searchLayout_marginRight.equals("") ? 0 : Integer.parseInt(bar_searchLayout_marginRight);
        }

        public void setSearchLayoutMarginRight(int bar_searchLayout_marginRight) {
            this.bar_searchLayout_marginRight = String.valueOf(bar_searchLayout_marginRight);
        }

        public int getSearchEditClearPaddingLeft() {
            return bar_searchEditClear_paddingLeft.equals("") ? 0 : Integer.parseInt(bar_searchEditClear_paddingLeft);
        }

        public void setSearchEditClearPaddingLeft(int bar_searchEditClear_paddingLeft) {
            this.bar_searchEditClear_paddingLeft = String.valueOf(bar_searchEditClear_paddingLeft);
        }

        public int getSearchEditClearPaddingRight() {
            return bar_searchEditClear_paddingRight.equals("") ? 0 : Integer.parseInt(bar_searchEditClear_paddingRight);
        }

        public void setSearchEditClearPaddingRight(int bar_searchEditClear_paddingRight) {
            this.bar_searchEditClear_paddingRight = String.valueOf(bar_searchEditClear_paddingRight);
        }

        public int getSearchEditHintColor() {
            return bar_searchEdit_hintColor.equals("") ? Color.parseColor("#999999") : Integer.parseInt(bar_searchEdit_hintColor);
        }

        public void setSearchEditHintColor(int bar_searchEdit_hintColor) {
            this.bar_searchEdit_hintColor = String.valueOf(bar_searchEdit_hintColor);
        }

        public int getSearchEditTextColor() {
            return bar_searchEdit_textColor.equals("") ? Color.parseColor("#666666") : Integer.parseInt(bar_searchEdit_textColor);
        }

        public void setSearchEditTextColor(int bar_searchEdit_textColor) {
            this.bar_searchEdit_textColor = String.valueOf(bar_searchEdit_textColor);
        }

        public int getSearchEditTextSize() {
            return bar_searchEdit_textSize.equals("") ? 27 : Integer.parseInt(bar_searchEdit_textSize);
        }

        public void setSearchEditTextSize(int bar_searchEdit_textSize) {
            this.bar_searchEdit_textSize = String.valueOf(bar_searchEdit_textSize);
        }

        public int getSearchEditPaddingLeft() {
            return bar_searchEdit_paddingLeft.equals("") ? 0 : Integer.parseInt(bar_searchEdit_paddingLeft);
        }

        public void setSearchEditPaddingLeft(int bar_searchEdit_paddingLeft) {
            this.bar_searchEdit_paddingLeft = String.valueOf(bar_searchEdit_paddingLeft);
        }

        public int getSearchEditPaddingRight() {
            return bar_searchEdit_paddingRight.equals("") ? 0 : Integer.parseInt(bar_searchEdit_paddingRight);
        }

        public void setSearchEditPaddingRight(int bar_searchEdit_paddingRight) {
            this.bar_searchEdit_paddingRight = String.valueOf(bar_searchEdit_paddingRight);
        }

        public Drawable getSearchEditClear() {
            return bar_searchEditClear_src;
        }

        public void setSearchEditClear(Drawable bar_searchEditClear_src) {
            this.bar_searchEditClear_src = bar_searchEditClear_src;
        }

        public int getSearchLayoutHeight() {
            return bar_searchLayout_height.equals("") ? 90 : Integer.parseInt(bar_searchLayout_height);
        }

        public void setBarSearchLayoutHeight(int bar_searchLayout_height) {
            this.bar_searchLayout_height = String.valueOf(bar_searchLayout_height);
        }

        public int getRightButtonTextSize() {
            return bar_rightButton_textSize.equals("") ? 28 : Integer.parseInt(bar_rightButton_textSize);
        }

        public void setRightButtonTextSize(int bar_rightButton_textSize) {
            this.bar_rightButton_textSize = String.valueOf(bar_rightButton_textSize);
        }

        public int getRightButtonTextColor() {
            return bar_rightButton_textColor.equals("") ? Color.parseColor("#666666") : Integer.parseInt(bar_rightButton_textColor);
        }

        public void setRightButtonTextColor(int bar_rightButton_textColor) {
            this.bar_rightButton_textColor = String.valueOf(bar_rightButton_textColor);
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

}
