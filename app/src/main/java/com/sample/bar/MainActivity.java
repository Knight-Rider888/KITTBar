package com.sample.bar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
}