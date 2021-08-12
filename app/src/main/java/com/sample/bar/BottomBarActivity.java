package com.sample.bar;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.sample.bar.fragment.OneFragment;
import com.sample.bar.fragment.ThreeFragment;

import knight.rider.kitt.bar.KittBottomBar;
import knight.rider.kitt.bar.attr.Tab;
import knight.rider.kitt.bar.listener.OnBottomBarEventListener;

public class BottomBarActivity extends AppCompatActivity {

    Tab tab;
    KittBottomBar bottomBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_bar);


        bottomBar = (KittBottomBar) findViewById(R.id.bar);

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
            public void onEvent(int tabIndex, boolean isRepeat, boolean isBindFragment, Tab tab) {
                Log.e(BottomBarActivity.class.getName(), "当前Tab" + tabIndex + "-是否重复点击" + isRepeat + "-当前Tab是否绑定了fragment" + isBindFragment);

                if (tabIndex == 0 && isRepeat) {
                    // 变化图片
                    tab.setSelectedPicRes(R.raw.download);
                    // 并开启动画
                    bottomBar.startAnim(0);
                }

                if (tabIndex == 0 && !isRepeat)
                    tab.setSelectedPicRes(R.raw.application_press);
            }
        });

        bottomBar.setDividerHeight(2);
        bottomBar.setCurrentTab(2);
        bottomBar.setDividerColor(getResources().getDrawable(R.mipmap.ic_launcher));


    }

    public void click1(View view) {
        // 切换主页图标,无动画
        tab.setSelectedPicRes(R.raw.download);
    }
}