package knight.rider.kitt.bar.other;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

public class BottomPager extends ViewPager {

    private boolean mSliding;

    public BottomPager(@NonNull Context context) {
        super(context);
    }

    public BottomPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public void setSliding(boolean sliding) {
        this.mSliding = sliding;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return mSliding && super.onTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return mSliding && super.onInterceptTouchEvent(ev);

    }
}