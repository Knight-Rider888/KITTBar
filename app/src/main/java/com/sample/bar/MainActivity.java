package com.sample.bar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import knight.rider.kitt.bar.KittBar;
import knight.rider.kitt.bar.attr.RightBtn;
import knight.rider.kitt.bar.listener.OnBarEventListener;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        KittBar kittBar = (KittBar) findViewById(R.id.bar);
        kittBar.setOnBarEventListener(new OnBarEventListener() {
            @Override
            public void onBack(ImageView backView) {

            }

            @Override
            public void onTitleClick(TextView titleView) {

            }

            @Override
            public void onSearchViewClick(EditText searchView) {
                startActivity(new Intent(MainActivity.this, SearchStyle2Activity.class));
            }

            @Override
            public void onSearchViewFocusChange(EditText searchView, boolean b) {

            }

            @Override
            public void onRightButtonClick(RightBtn rightBtn, TextView right) {

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
    }


    public void bar(View view) {
        startActivity(new Intent(this, TopBarActivity.class));
    }

    public void slideBar(View view) {
        startActivity(new Intent(this, SlideBarActivity.class));
    }

    public void KittCycleScrollBar(View view) {
        startActivity(new Intent(this, CycleScrollBarActivity.class));
    }

    public void KittBottomBar(View view) {
        startActivity(new Intent(this, BottomBarActivity.class));
    }

    public void bar_search_Style1(View view) {
        startActivity(new Intent(this, SearchStyle1Activity.class));
    }
}