package com.sample.bar;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.sample.bar.fragment.OneFragment;
import com.sample.bar.fragment.ThreeFragment;

import knight.rider.kitt.bar.KittBottomBar;
import knight.rider.kitt.bar.attr.Tab;

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

    }

}