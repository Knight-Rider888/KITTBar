package com.sample.bar;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
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

public class SearchStyle2Activity extends AppCompatActivity {

    private RecyclerView rv;
    private KittBar bar;
    private NameAdapter nameAdapter;

    List<String> all = Arrays.asList("A1", "A2", "B", "V", "D");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_style2);

        rv = (RecyclerView) findViewById(R.id.rv);
        bar = (KittBar) findViewById(R.id.bar);

        rv.setLayoutManager(new LinearLayoutManager(this));
        nameAdapter = new NameAdapter(this);
        rv.setAdapter(nameAdapter);

        bar.setOnBarEventListener(new OnBarEventListener() {
            @Override
            public void onBack(ImageView backView) {
                finish();
            }

            @Override
            public void onTitleClick(TextView titleView) {

            }

            @Override
            public void onSearchViewClick(EditText searchView) {

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
                if (TextUtils.isEmpty(content)) {
                    nameAdapter.clearAll();
                    nameAdapter.setLoadState(LoadState.LOAD_COMPLETE);
                } else {
                    nameAdapter.searchName(all, content);
                }
            }
        });
    }
}