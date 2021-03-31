package com.sample.bar;

import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import knight.rider.kitt.bar.KittBar;
import knight.rider.kitt.bar.attr.RightBtn;
import knight.rider.kitt.bar.listener.OnBarEventListener;

public class MainActivity extends AppCompatActivity {

    KittBar kittBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        kittBar = (KittBar) findViewById(R.id.kitt_bar);

        kittBar.setBarSmartPadding();
        kittBar.setOnBarEventListener(new OnBarEventListener() {
            @Override
            public void onBack(ImageView backView) {

            }

            @Override
            public void onTitle(TextView titleView) {

            }

            @Override
            public void onRightIconClick(RightBtn rightBtn, TextView right) {

            }

            @Override
            public void onSearchRightIconClick(ImageView rightIcon) {

            }

            @Override
            public void onKeyboardSearchClick(String content, boolean isHintSearch) {

            }

            @Override
            public void onTextChanged(String content) {

            }
        });

        SeekBar seekBar = (SeekBar) findViewById(R.id.seek);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                kittBar.setBackgroundAlpha((float) (progress * 1.0 / 100));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    public void backVisible(View view) {
        kittBar.setBackIconVisibility(System.currentTimeMillis() % 2 == 0 ? View.VISIBLE : View.GONE);
    }

    public void backIcon(View view) {
        kittBar.setBackIconResource(R.mipmap.ic_launcher);
    }

    public void backIconPadding(View view) {
        kittBar.setBackIconPadding(100);
    }

    public void custom(View view) {
        kittBar.setCustomLayout(R.layout.custom);
    }

    public void title(View view) {
        kittBar.setTitleContent("我是标题");
        kittBar.setTitleColor("blue");
        kittBar.setTitleTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);
        kittBar.setTitlePaddingLeft(0);
        kittBar.setTitlePaddingRight(10);
    }

    public void titleDrawable(View view) {
        kittBar.setTitleCompoundDrawables(null, null, getDrawable(R.mipmap.zhankai2), null);
        kittBar.setTitleCompoundDrawablePadding(20);
    }

    public void searchLayoutBg(View view) {
        kittBar.setSearchLayoutCornerRadius(1000);
        kittBar.setSearchLayoutColor("red");
        kittBar.setSearchLayoutMargin(1, 40, 5, 20);
        kittBar.setSearchLayoutStoke(5, "blue");
    }

    public void searchVisible(View view) {
        kittBar.setSearchLayoutVisibility(View.VISIBLE);
    }

    public void searchEditBase(View view) {
        kittBar.setSearchEditHint("hint");
        kittBar.setSearchEditHintColor("red");
        kittBar.setSearchEditTextColor("green");
        kittBar.setSearchEditTextSize(40);
    }

    public void right(View view) {
        kittBar.setRightButtonsVisibility(false, false, true);
        kittBar.setRightButtonsText(RightBtn.RIGHT_THIRD, "第三个");
    }
}