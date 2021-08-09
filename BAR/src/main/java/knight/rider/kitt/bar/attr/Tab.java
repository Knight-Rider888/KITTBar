package knight.rider.kitt.bar.attr;

import android.os.Bundle;
import android.text.TextUtils;

import androidx.fragment.app.Fragment;

public class Tab {

    // 未选中时的图片，可空
    private String mUnSelectedPicRes;
    // 选中时的图片，可空
    private String mSelectedPicRes;

    // bundle，可空
    private Bundle mBundle;
    // fragment，可空
    private Class<? extends Fragment> mClss;

    // 文字是否变色，可空
    private boolean mChangeWordColor;


    // 是否是超大的图标（凸出）
    private boolean mIsLargeIcon;
    // 大图标的尺寸, 仅对超大图标属性为true有效
    private int mLargeIconSize;
    // 图标类型
    private IconRule mIconRule;
    // 数字提醒的样式，可空
    private CircleStyle mCircleStyle;
    // 文字，可控
    private String mWord;


    /**
     * 无参构造
     */
    public Tab() {
    }

    public boolean isLargeIcon() {
        return mIsLargeIcon;
    }

    public void setLargeIcon(boolean isLargeIcon, int largeIconSize) {
        this.mIsLargeIcon = isLargeIcon;
        this.mLargeIconSize = largeIconSize;
    }

    public int getLargeIconSize() {
        return mLargeIconSize;
    }

    // 默认静态图样式
    public IconRule getIconRule() {
        return mIconRule == null ? IconRule.NONE_LOTTIE : mIconRule;
    }

    public void setIconRule(IconRule iconRule) {
        this.mIconRule = iconRule;
    }

    // 默认 白边红心样式
    public CircleStyle getCircleStyle() {
        return mCircleStyle == null ? CircleStyle.CIRCLE_RED_SOLID_WRITE_STOKE : mCircleStyle;
    }

    public void setCircleStyle(CircleStyle circleStyle) {
        this.mCircleStyle = circleStyle;
    }

    // 默认空字符
    public String getWord() {
        return TextUtils.isEmpty(mWord) ? "" : mWord;
    }

    public void setWord(String mWord) {
        this.mWord = mWord;
    }
}
