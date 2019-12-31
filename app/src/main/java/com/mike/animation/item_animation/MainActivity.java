package com.mike.animation.item_animation;


import android.view.View;
import android.widget.Button;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mike.animation.R;
import com.mike.animation.base.BaseActivity;
import com.mike.animation.base.BaseRecyAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


public class MainActivity extends BaseActivity {

    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;
    List<String> mDatas;
    BaseRecyAdapter baseRecyAdapter;

    @BindView(R.id.add)
    Button btnAdd;
    @BindView(R.id.remove)
    Button btnRemove;

    @OnClick(R.id.add)
    public void addClick() {
        mDatas.add(2, "this is " + mDatas.size() + " item");
        baseRecyAdapter.notifyItemInserted(2);
    }

    @OnClick(R.id.remove)
    public void removeClick() {
        if (mDatas.size() > 0) {
            mDatas.remove(0);
            baseRecyAdapter.notifyItemRemoved(0);
            //baseRecyAdapter.notifyDataSetChanged();
        }

    }


    @Override
    public int getContentViewId() {
        return R.layout.activity_item_animation;
    }

    @Override
    public void afterSetContentView() {
        mDatas = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            mDatas.add("this is " + i + " item");
        }
        baseRecyAdapter = new BaseRecyAdapter(recyclerView, mDatas) {
            @Override
            public View.OnClickListener setItemClickListener(ViewHolder holder, int position) {
                return null;
            }

            @Override
            public View.OnLongClickListener setItemLongClickListener(ViewHolder holder, int position) {
                return null;
            }
        };
        LinearLayoutManager llm = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(llm);
        recyclerView.setAdapter(baseRecyAdapter);
        RecyclerView.ItemAnimator scaleAnimator;
        //scaleAnimator = new ScaleAnimator();
        scaleAnimator = new FadeItemAnimator();
        scaleAnimator.setAddDuration(2000);
        scaleAnimator.setMoveDuration(2000);
        scaleAnimator.setRemoveDuration(2000);
        scaleAnimator.setChangeDuration(2000);
        recyclerView.setItemAnimator(scaleAnimator);
    }
}
