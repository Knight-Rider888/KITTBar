package knight.rider.kitt.bar.attr;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

public class Tab {

    // 未选中时的图片，可空
    private String mUnSelectedPicRes;
    // 选中时的图片，可空
    private String mSelectedPicRes;
    // 文字，可控
    private String mWord;
    // bundle，可空
    private Bundle mBundle;
    // fragment，可空
    private Class<? extends Fragment> mClss;
    // 数字提醒的样式，可空
    private CircleStyle mCircleStyle;
    // 文字是否变色，可空
    private boolean mChangeWordColor;

    /**
     * 无参构造
     */
    public Tab() {
    }

    public String getUnSelectedPicRes() {
        return mUnSelectedPicRes;
    }

    public void setUnSelectedPicRes(String unSelectedPicRes) {
        this.mUnSelectedPicRes = unSelectedPicRes;
    }

    public String getSelectedPicRes() {
        return mSelectedPicRes;
    }

    public void setSelectedPicRes(String selectedPicRes) {
        this.mSelectedPicRes = selectedPicRes;
    }

    public String getWord() {
        return mWord;
    }

    public void setWord(String word) {
        this.mWord = word;
    }

    public Bundle getBundle() {
        return mBundle;
    }

    public void setBundle(Bundle bundle) {
        this.mBundle = bundle;
    }

    public Class<? extends Fragment> getClss() {
        return mClss;
    }

    public void setClss(Class<? extends Fragment> clss) {
        this.mClss = clss;
    }

    public CircleStyle getCircleStyle() {
        return mCircleStyle;
    }

    public void setCircleStyle(CircleStyle circleStyle) {
        this.mCircleStyle = circleStyle;
    }

    public boolean isChangeWordColor() {
        return mChangeWordColor;
    }

    public void setChangeWordColor(boolean changeWordColor) {
        this.mChangeWordColor = changeWordColor;
    }
}
