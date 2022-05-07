package knight.rider.kitt.bar.view;

import android.content.Context;
import android.os.Parcelable;
import android.util.AttributeSet;

import com.airbnb.lottie.LottieAnimationView;

public class LottieAnimationView2 extends LottieAnimationView {

    public LottieAnimationView2(Context context) {
        super(context);
    }

    public LottieAnimationView2(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LottieAnimationView2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 重写此方法将LottieAnimationView的缓存去除
     * 解决因异常情况或旋转方向后页面重新加载
     * 导致lottie文件读取成最后一个tab文件的bug
     */
    @Override
    protected Parcelable onSaveInstanceState() {
        Parcelable parcelable = super.onSaveInstanceState();
        parcelable = null;
        return null;
    }
}
