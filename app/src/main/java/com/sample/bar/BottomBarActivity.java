package com.sample.bar;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.sample.bar.fragment.OneFragment;
import com.sample.bar.fragment.ThreeFragment;

import knight.rider.kitt.bar.KittBottomBar;
import knight.rider.kitt.bar.attr.Tab;
import knight.rider.kitt.bar.listener.OnBottomBarEventListener;

public class BottomBarActivity extends AppCompatActivity {

    Tab tab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_bar);


        KittBottomBar bottomBar = (KittBottomBar) findViewById(R.id.bar);

        tab = new Tab(OneFragment.class).setWord("主页").setNormalPicRes(R.raw.application_normal).setSelectedPicRes(R.raw.application_press);
        bottomBar.addTab(tab);


        Tab tab1 = new Tab().setWord("消息").setNormalPicRes(R.raw.msg_normal).setSelectedPicRes(R.raw.msg_press);
        bottomBar.addTab(tab1);
        bottomBar.addTab(new Tab(ThreeFragment.class).setWord("通讯录").setNormalPicRes(R.raw.phone_normal).setSelectedPicRes(R.raw.phone_press));
        bottomBar.addTab(new Tab().setWord("我的").setNormalPicRes(R.raw.me_normal).setSelectedPicRes(R.raw.me_press));
        bottomBar.init(getSupportFragmentManager());

        bottomBar.setLottieSpeed(1);

        bottomBar.setGestureSliding(true);
        bottomBar.setTabClickSmoothScroll(true);

        bottomBar.setOnBottomBarEventListener(new OnBottomBarEventListener() {
            @Override
            public void onEvent(int tabIndex, boolean isRepeat, boolean isBindFragment) {
                Log.e(BottomBarActivity.class.getName(), "当前Tab" + tabIndex + "-是否重复点击" + isRepeat + "-当前Tab是否绑定了fragment" + isBindFragment);
            }
        });

    }

}