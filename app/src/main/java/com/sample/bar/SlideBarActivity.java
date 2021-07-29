package com.sample.bar;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sample.bar.adapter.Adapter;
import com.sample.bar.data.ContactBean;
import com.sample.bar.data.Data;
import com.sample.bar.scroll.TopLinearSmoothScroller;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import knight.rider.kitt.bar.KittSideBar;
import knight.rider.kitt.bar.listener.OnSelectLetterListener;

public class SlideBarActivity extends AppCompatActivity {

    private List<ContactBean> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slide_bar);


        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this) {
            @Override
            public void smoothScrollToPosition(RecyclerView recyclerView, RecyclerView.State state, int position) {
                TopLinearSmoothScroller scroller = new TopLinearSmoothScroller(recyclerView.getContext());
                scroller.setTargetPosition(position);
                startSmoothScroll(scroller);
            }
        });

        Adapter adapter = new Adapter(this);
        recyclerView.setAdapter(adapter);
        data = Data.getData();
        adapter.addData(data);

        KittSideBar sideBar = (KittSideBar) findViewById(R.id.side);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();
                char initial = adapter.getData(firstVisibleItemPosition).getInitial();
                sideBar.setChoosePosition(initial);
            }
        });

        // 字符区间
        Map<Character, int[]> map = new HashMap<>();
        Set<Character> characters = new HashSet<>();

        for (int i = 0; i < data.size(); i++) {
            ContactBean bean = data.get(i);
            if (bean.getName() == null) {
                char initial = bean.getInitial();
                int[] v = new int[2];
                map.put(initial, v);
                v[0] = i;
                characters.add(initial);
            }

            int[] ints = map.get(bean.getInitial());
            assert ints != null;
            ints[1] = i;
        }


        sideBar.setOnSelectLetterListener(new OnSelectLetterListener() {
            @Override
            public void onSelect(int pos, char letter) {
                int[] ints = map.get(letter);
                if (ints != null) {
                    recyclerView.smoothScrollToPosition(ints[0]);
                }

                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();
                char initial = adapter.getData(firstVisibleItemPosition).getInitial();
                sideBar.setChoosePosition(initial);

            }
        });
        sideBar.setContainLetters(characters, data.get(0).getInitial());

    }


}