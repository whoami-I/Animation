package com.mike.animation.home;


import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.mike.animation.R;
import com.mike.animation.base.BaseActivity;
import com.mike.animation.scene.SceneActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


public class MainActivity extends BaseActivity {

    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;

    List<ItemDataBean> mDataList = new ArrayList<>();

    @Override
    public int getContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    public void afterSetContentView() {
        initDataList();
        LinearLayoutManager llm = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(llm);
        HomeListAdapter homeListAdapter = new HomeListAdapter(this, mDataList);
        recyclerView.setAdapter(homeListAdapter);
    }

    private void initDataList() {
        mDataList.add(new ItemDataBean("aniamtion",
                com.mike.animation.test.MainActivity.class, ColorGenerator.getInstance().getColor()));
        mDataList.add(new ItemDataBean("Scene",
                SceneActivity.class, ColorGenerator.getInstance().getColor()));
        mDataList.add(new ItemDataBean("Item Animation",
                com.mike.animation.item_animation.MainActivity.class, ColorGenerator.getInstance().getColor()));
    }
}
