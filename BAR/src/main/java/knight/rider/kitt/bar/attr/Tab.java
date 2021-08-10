package knight.rider.kitt.bar.attr;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class Tab {


    // bundle，可空
    private Bundle mBundle;
    // fragment，可空
    private Class<? extends Fragment> mClss;


    // 是否是超大的图标（凸出）
    private boolean mIsLargeIcon;
    // 大图标的尺寸, 仅对超大图标属性为true有效
    private int mLargeIconSize;
    // 文字，可空
    private String mWord;
    // 未选中时的图片(默认图片)，可空 rawRes为lottie动画 drawableRes为静态图片
    // 代码根据此自动进行判断是否加载动画
    private int mNormalPicRes;
    // 选中时的图片，可空 rawRes为lottie动画 drawableRes为静态图片
    // 代码根据此自动进行判断是否加载动画
    private int mSelectedPicRes;


    // 这是一个实用工具类，支持绑定 (bound) 属性的 bean 可以使用该类。
    // 可以使用此类的实例作为 bean 的成员字段，并将各种工作委托给它。
    PropertyChangeSupport listeners = new PropertyChangeSupport(this);

    /**
     * 无参构造
     */
    public Tab() {
    }

    public boolean isLargeIcon() {
        return mIsLargeIcon;
    }

    public Tab setLargeIcon(boolean isLargeIcon, int largeIconSize) {


        // 获取旧值
        boolean oldValue = isLargeIcon();
        int oldValue2 = getLargeIconSize();

        // 仅设置大图标，不设置大小
        // 展示为默认图标
        if (isLargeIcon && largeIconSize <= 0)
            isLargeIcon = false;

        // 赋新值
        this.mIsLargeIcon = isLargeIcon;
        this.mLargeIconSize = largeIconSize;

        if (oldValue != isLargeIcon || (isLargeIcon && oldValue2 != largeIconSize))
            // 发布监听事件
            firePropertyChange("largeIcon", isLargeIcon, largeIconSize);

        return this;
    }

    public int getLargeIconSize() {
        return mLargeIconSize;
    }


    /****文字*****/

    public String getWord() {
        return mWord == null ? "" : mWord;
    }

    public Tab setWord(String word) {

        if (null == word)
            word = "";

        // 获取旧值
        String oldValue = getWord();
        // 赋新值
        this.mWord = word;

        if (!oldValue.equals(word)) {
            // 发布监听事件
            firePropertyChange("tabContent", oldValue, word);
        }

        return this;
    }


    /****图片*****/

    public int getNormalPicRes() {
        return mNormalPicRes;
    }

    public Tab setNormalPicRes(int normalPicRes) {

        // 获取旧值
        int oldValue = getNormalPicRes();
        // 赋新值
        this.mNormalPicRes = normalPicRes;

        if (oldValue != normalPicRes) {
            // 发布监听事件
            firePropertyChange("normalRes", oldValue, normalPicRes);
        }

        return this;
    }

    public int getSelectedPicRes() {
        return mSelectedPicRes;
    }

    public Tab setSelectedPicRes(int selectedPicRes) {

        // 获取旧值
        int oldValue = getSelectedPicRes();
        // 赋新值
        this.mSelectedPicRes = selectedPicRes;

        if (oldValue != selectedPicRes) {
            // 发布监听事件
            firePropertyChange("selectedRes", oldValue, selectedPicRes);
        }

        return this;
    }


    /**
     * 触发属性改变的事件
     */
    private void firePropertyChange(String prop, Object oldValue, Object newValue) {
        listeners.firePropertyChange(prop, oldValue, newValue);   //  系统方法
    }

    // 本地方法
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        listeners.addPropertyChangeListener(listener);  //  系统方法
    }

}
