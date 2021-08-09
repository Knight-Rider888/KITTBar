package com.sample.bar;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import knight.rider.kitt.bar.KittCycleScrollBar;

public class CycleScrollBarActivity extends AppCompatActivity {

    private KittCycleScrollBar scrollBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cycle_scroll_bar);

        scrollBar = (KittCycleScrollBar) findViewById(R.id.bar);

        for (int i = 0; i < 5; i++) {
            TextView textView = new TextView(this);
            textView.setText("我是第" + (i + 1) + "条数据");
            scrollBar.addItemView(textView);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        scrollBar.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        scrollBar.onPause();
    }

    public void newData(View view) {

        scrollBar.clearAllViews();

        for (int i = 0; i < 5; i++) {
            TextView textView = new TextView(this);
            textView.setText("我是第" + (i + 1) + "条new数据");
            scrollBar.addItemView(textView);
        }
    }
}