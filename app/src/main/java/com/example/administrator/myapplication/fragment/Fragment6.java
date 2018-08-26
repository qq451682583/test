package com.example.administrator.myapplication.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.myapplication.R;
import com.zw.yzk.component.statistics.statistics.ClickStatistics;

public class Fragment6 extends BaseFragment {

    private RecyclerView list;

    @Override
    String getTitle() {
        return this.getClass().getSimpleName();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_6;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = super.onCreateView(inflater, container, savedInstanceState);
        initView(root);


        return root;
    }

    private void initView(View root) {
        if (root == null) {
            return;
        }
        list = root.findViewById(R.id.list);
        list.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        list.setAdapter(new MyAdapter());
    }

    private static class MyAdapter extends RecyclerView.Adapter<MyAdapter.Holder> {

        @NonNull
        @Override
        public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            TextView textView = new TextView(parent.getContext());
            return new Holder(textView);
        }

        @Override
        public void onBindViewHolder(@NonNull Holder holder, int position) {
            ((TextView) holder.itemView).setText("ITEM: " + position);

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ClickStatistics clickStatistics = new ClickStatistics(v);
                    clickStatistics.clickEnd();
                }
            });
        }

        @Override
        public int getItemCount() {
            return 300;
        }

        static class Holder extends RecyclerView.ViewHolder {

            Holder(View itemView) {
                super(itemView);
            }
        }
    }
}
