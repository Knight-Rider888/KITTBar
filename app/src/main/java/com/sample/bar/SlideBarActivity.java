package com.sample.bar;

import android.os.Bundle;
import android.widget.TextView;

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
import knight.rider.kitt.bar.listener.OnTouchSideListener;

public class SlideBarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slide_bar);


        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this) {
            @Override
            public void smoothScrollToPosition(RecyclerView recyclerView, RecyclerView.State state, int position) {
                // 滚动指定位置到顶端
                TopLinearSmoothScroller scroller = new TopLinearSmoothScroller(recyclerView.getContext());
                scroller.setTargetPosition(position);
                startSmoothScroll(scroller);
            }
        });

        Adapter adapter = new Adapter(this);
        recyclerView.setAdapter(adapter);
        List<ContactBean> data = Data.getData();
        adapter.addData(data);

        KittSideBar sideBar = (KittSideBar) findViewById(R.id.side);

        TextView session = (TextView) findViewById(R.id.sessionTv);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                // 触摸侧边栏滑动，调用smoothScrollToPosition，虽然列表可能未滚动，但是此处可监听，而未滚动时 onScrolled 是监听不到的
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    // 强制更改为顶部Item的位置
                    LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                    int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();
                    String initial = adapter.getData(firstVisibleItemPosition).getPhonebook_label();
                    sideBar.setChooseLetter(initial);
                    session.setText(initial);
                }

            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                // 列表滚动同时变更侧边栏选中字母
                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();
                String initial = adapter.getData(firstVisibleItemPosition).getPhonebook_label();
                sideBar.setChooseLetter(initial);
                session.setText(initial);

            }
        });

        // 字符区间
        Map<String, Integer> map = new HashMap<>();
        // 当前数据源所有的字符
        Set<String> characters = new HashSet<>();

        for (int i = 0; i < data.size(); i++) {

            ContactBean bean = data.get(i);

            if (characters.add(bean.getPhonebook_label()))
                // 添加字符成功（利用set集合不重复性），记录添加字符的第一条记录的index
                map.put(bean.getPhonebook_label(), i);
        }


        sideBar.setOnTouchSideListener(new OnTouchSideListener() {
            @Override
            public void onTouch(char upperLetter, char lowerLetter, String upperLetterStr, String lowerLetterStr) {
                Integer integer = map.get(upperLetterStr);
                if (integer != null) {
                    // 触摸调用滚动到指定某字母开头的第一个item
                    recyclerView.smoothScrollToPosition(integer);
                }
            }

        });
        sideBar.setContainLetters2(characters, data.get(0).getPhonebook_label());

    }


}