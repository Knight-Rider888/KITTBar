package knight.rider.kitt.bar.other;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import knight.rider.kitt.bar.R;

public class BottomItem extends FrameLayout {

    // tab容器
    private LinearLayout mTab;
    // icon容器
    private FrameLayout mIconLayout;
    // badge
    private TextView mBadge;
    // 文字
    private TextView mWord;

    public BottomItem(@NonNull Context context) {
        this(context, null);
    }

    public BottomItem(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BottomItem(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        LayoutInflater.from(getContext()).inflate(R.layout.kitt_bottom_item, this, true);

        mTab = (LinearLayout) findViewById(R.id.kitt_bar_item);
        mIconLayout = (FrameLayout) findViewById(R.id.kitt_bar_icon_layout);
        mBadge = (TextView) findViewById(R.id.kitt_bar_item_badge);
        mWord = (TextView) findViewById(R.id.kitt_bar_item_word);
    }


    public LinearLayout getTabGroup() {
        return mTab;
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
}
