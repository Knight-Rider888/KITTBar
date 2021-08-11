package knight.rider.kitt.bar.other;

import android.content.Context;
import android.content.res.ColorStateList;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;

import knight.rider.kitt.bar.R;

public class BottomItem extends LinearLayout {


    // icon容器
    private final FrameLayout mIconLayout;
    // badge
    private final TextView mBadge;
    // 文字
    private final TextView mWord;
    // 图片
    private final LottieAnimationView mLottie;

    private int mUnSelectedPicRes;
    private int mSelectedPicRes;

    public BottomItem(Context context) {
        super(context);

        // 垂直布局，宽高由BottomBar进行控制了
        setOrientation(VERTICAL);

        // 直接添加到根视图
        LayoutInflater.from(getContext()).inflate(R.layout.kitt_tab_icon, this, true);
        LayoutInflater.from(getContext()).inflate(R.layout.kitt_tab_text, this, true);

        mIconLayout = (FrameLayout) findViewById(R.id.kitt_bar_icon_layout);
        mBadge = (TextView) findViewById(R.id.kitt_bar_item_badge);
        mWord = (TextView) findViewById(R.id.kitt_bar_item_word);

        mLottie = (LottieAnimationView) findViewById(R.id.kitt_bar_item_lottie);
    }


    public FrameLayout getIconGroup() {
        return mIconLayout;
    }

    public TextView getBadge() {
        return mBadge;
    }


    // TODO 红点待完成

    /**
     * 初始化
     *
     * @param unSelectedRes       可空，需判断
     * @param selectedRes         可空，需判断
     * @param textColorUnSelected 由BottomBar控制，默认 #666666
     * @param textColorSelected   由BottomBar控制，默认 #666666
     * @param textSize
     */
    public void init(int unSelectedRes, int selectedRes, int textColorUnSelected, int textColorSelected, String text, float textSize) {

        // 选中图片为空使用默认图片
        if (selectedRes == 0)
            selectedRes = unSelectedRes;

        // 赋值全局变量
        mSelectedPicRes = selectedRes;
        mUnSelectedPicRes = unSelectedRes;

        int picRes = isSelected() ? selectedRes : unSelectedRes;

        if (picRes == 0) {
            // 无图片资源，直接占位不显示
            mLottie.setVisibility(INVISIBLE);
        } else {

            // raw默认为动画类型
            String typeName = getResources().getResourceTypeName(picRes);
            if ("raw".equalsIgnoreCase(typeName)) {
                // 动画资源
                mLottie.setAnimation(picRes);

                // 初始化后要在最后一帧
                mLottie.setProgress(1);

            } else {
                // 静态资源
                mLottie.setImageResource(picRes);
            }
        }

        // 初始化tab字体大小
        updateTabTextSize(textSize);
        // 初始化tab字体颜色
        updateTabTextColor(textColorUnSelected, textColorSelected);
        // 初始化文字内容
        updateTabText(text);
    }

    /**
     * 改变icon的状态
     */
    public void changeIconState() {

        int picRes = isSelected() ? mSelectedPicRes : mUnSelectedPicRes;

        if (picRes == 0) {
            mLottie.setVisibility(INVISIBLE);
        } else {
            String typeName = getResources().getResourceTypeName(picRes);
            if ("raw".equalsIgnoreCase(typeName)) {
                // 动画资源
                mLottie.setAnimation(picRes);
                mLottie.playAnimation();
            } else {
                // 静态资源
                mLottie.setImageResource(picRes);
            }
        }
    }

    /**
     * 更新速度
     */
    public void updateSpeed(float speed) {
        mLottie.setSpeed(speed);
    }

    /**
     * 更新字体颜色
     *
     * @param normalTextColor   默认颜色
     * @param selectedTextColor 选中颜色
     */
    public void updateTabTextColor(int normalTextColor, int selectedTextColor) {
        mWord.setTextColor(getStateListColor(normalTextColor, selectedTextColor));
    }

    /**
     * 更新默认图标
     */
    public void updateTabNormalIcon(int icon) {

        mUnSelectedPicRes = icon;

        if (!isSelected()) {
            changeIconState();
            // 更为最后一帧
            mLottie.setProgress(1);
        }

    }

    /**
     * 更新选中图标
     */
    public void updateTabSelectedIcon(int icon) {

        mSelectedPicRes = icon;

        if (isSelected()) {
            changeIconState();
            // 更为最后一帧
            mLottie.setProgress(1);
        }

    }

    /**
     * 更新字体大小
     */
    public void updateTabTextSize(float textSize) {
        mWord.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);

    }

    /**
     * 更新tab内容
     */
    public void updateTabText(String text) {
        mWord.setText(text == null ? "" : text);
    }

    /**
     * 默认状态不满足，可自行逻辑处理
     */
    public void startAnim() {
        mLottie.setProgress(0);
        mLottie.playAnimation();
    }

    private static ColorStateList getStateListColor(int textColorUnSelected, int textColorSelected) {

        int[] colors = new int[]{textColorUnSelected, textColorSelected, textColorUnSelected};

        int[][] states = new int[3][];

        states[0] = new int[]{-android.R.attr.state_selected};

        states[1] = new int[]{android.R.attr.state_selected};

        states[2] = new int[]{};

        return new ColorStateList(states, colors);
    }

}
