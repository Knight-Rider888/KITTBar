package com.sample.bar;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sample.bar.adapter.NameAdapter;

import java.util.Arrays;
import java.util.List;

import knight.rider.kitt.adapter.attr.LoadState;
import knight.rider.kitt.bar.KittBar;
import knight.rider.kitt.bar.attr.RightBtn;
import knight.rider.kitt.bar.listener.OnBarEventListener;

public class SearchStyle1Activity extends AppCompatActivity {

    private RecyclerView rv;
    private KittBar bar;
    private NameAdapter nameAdapter;

    List<String> all = Arrays.asList("A1", "A2", "B", "V", "D");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_style1);

        rv = (RecyclerView) findViewById(R.id.rv);
        bar = (KittBar) findViewById(R.id.bar);

        rv.setLayoutManager(new LinearLayoutManager(SearchStyle1Activity.this));
        nameAdapter = new NameAdapter(SearchStyle1Activity.this);
        rv.setAdapter(nameAdapter);
        nameAdapter.addData(all);

        findViewById(R.id.fl).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bar.setSearchEditClearFocus();
                ((FrameLayout) findViewById(R.id.fl)).setVisibility(View.GONE);
            }
        });

        bar.setOnBarEventListener(new OnBarEventListener() {
            @Override
            public void onBack(ImageView backView) {

            }

            @Override
            public void onTitleClick(TextView titleView) {

            }

            @Override
            public void onSearchViewClick(EditText searchView) {

            }

            @Override
            public void onSearchViewFocusChange(EditText searchView, boolean b) {
                if (b) {
                    if (TextUtils.isEmpty(searchView.getText().toString())) {
                        ((FrameLayout) findViewById(R.id.fl)).setVisibility(View.VISIBLE);
                    }
                }
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
                nameAdapter.searchName(all, content);
                if (!TextUtils.isEmpty(content))
                    ((FrameLayout) findViewById(R.id.fl)).setVisibility(View.GONE);
                if (TextUtils.isEmpty(content) && bar.getBarSearchFocus()) {
                    ((FrameLayout) findViewById(R.id.fl)).setVisibility(View.VISIBLE);
                    nameAdapter.addData(all);
                    nameAdapter.setLoadState(LoadState.LOAD_COMPLETE);
                }
            }
        });
    }
}