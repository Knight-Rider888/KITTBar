package knight.rider.kitt.bar.config;

import androidx.annotation.DrawableRes;

import knight.rider.kitt.bar.R;
import knight.rider.kitt.bar.attr.BarTitleGravity;

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

    // 返回builder
    public BarConfig.Builder getBuilder() {
        return builder;
    }

    public static class Builder {

        private BarTitleGravity titleGravity;
        private int backIcon = R.drawable.kitt_bar_back;
        private int backIcon_verticalPadding;
        private int backIcon_paddingRight;
        private int backIcon_paddingLeft;


        public BarTitleGravity getTitleGravity() {
            return titleGravity == null ? BarTitleGravity.LEFT : titleGravity;
        }

        public void setTitleGravity(BarTitleGravity title_gravity) {
            this.titleGravity = title_gravity;
        }

        public int getBarBackIcon() {
            return backIcon;
        }

        public void setBackIcon(int backIcon) {
            this.backIcon = backIcon;
        }

        public int getBackIconVerticalPadding() {
            return backIcon_verticalPadding;
        }

        public void setBackIconVerticalPadding(int backIcon_verticalPadding) {
            this.backIcon_verticalPadding = backIcon_verticalPadding;
        }

        public int getBackIconPaddingRight() {
            return backIcon_paddingRight;
        }

        public void setBackIconPaddingRight(int backIcon_paddingRight) {
            this.backIcon_paddingRight = backIcon_paddingRight;
        }

        public int getBackIconPaddingLeft() {
            return backIcon_paddingLeft;
        }

        public void setBackIconPaddingLeft(int backIcon_paddingLeft) {
            this.backIcon_paddingLeft = backIcon_paddingLeft;
        }

    }

}
