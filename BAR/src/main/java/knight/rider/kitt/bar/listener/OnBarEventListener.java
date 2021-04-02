package knight.rider.kitt.bar.listener;

import android.widget.ImageView;
import android.widget.TextView;

import knight.rider.kitt.bar.attr.RightBtn;


public interface OnBarEventListener {

    void onBack(ImageView backView);

    void onTitleClick(TextView titleView);

    void onRightButtonClick(RightBtn rightBtn, TextView right);

    void onSearchRightIconClick(ImageView rightIcon);

    // 键盘搜索点击事件，如果用户搜索的是hint内容会同步调用onTextChange()
    void onKeyboardSearchClick(String content, boolean isHintSearch);

    void onTextChanged(String content);
}
