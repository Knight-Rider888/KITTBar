package knight.rider.kitt.bar.other;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;

import knight.rider.kitt.bar.R;

public class BottomItem extends LinearLayout {


    // icon容器
    private FrameLayout mIconLayout;
    // badge
    private TextView mBadge;
    // 文字
    private TextView mWord;
    // 图片
    private LottieAnimationView mLottie;

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

    public TextView getWordView() {
        return mWord;
    }

    // TODO 红点待完成
    public void init(int unSelectedRes, int selectedRes, int textColorUnSelected, int textColorSelected, int textSize) {

        if (textColorUnSelected == 0)
            textColorUnSelected = Color.parseColor("#666666");

        if (selectedRes == 0)
            selectedRes = unSelectedRes;

        if (textColorSelected == 0)
            textColorSelected = textColorUnSelected;

        mSelectedPicRes = selectedRes;
        mUnSelectedPicRes = unSelectedRes;

        int picRes = isSelected() ? selectedRes : unSelectedRes;

        if (picRes == 0) {
            mLottie.setVisibility(INVISIBLE);
        } else {
            String typeName = getResources().getResourceTypeName(picRes);
            if ("raw".equalsIgnoreCase(typeName)) {
                // 动画资源
                mLottie.setAnimation(picRes);
            } else {
                // 静态资源
                mLottie.setImageResource(picRes);
            }
        }

        mWord.setTextColor(getStateListColor(textColorUnSelected, textColorSelected));
        mWord.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
    }

    public void changeIcon() {

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

    private static ColorStateList getStateListColor(int textColorUnSelected, int textColorSelected) {

        int[] colors = new int[]{textColorUnSelected, textColorSelected, textColorUnSelected};

        int[][] states = new int[3][];

        states[0] = new int[]{-android.R.attr.state_selected};

        states[1] = new int[]{android.R.attr.state_selected};

        states[2] = new int[]{};

        return new ColorStateList(states, colors);
    }

}
