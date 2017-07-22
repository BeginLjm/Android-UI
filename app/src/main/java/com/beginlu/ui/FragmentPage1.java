package com.beginlu.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.beginlu.qqlistitem.QQListItem;
import com.beginlu.qqlistitem.QQListItemButton;

import java.util.LinkedList;

public class FragmentPage1 extends Fragment {
    private View mView;
    private RecyclerView mRecyclerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_page1, container, false);
        mRecyclerView = (RecyclerView) mView.findViewById(R.id.recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(new MyAdapter());
        return mView;
    }

    class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

        LinkedList<QQListItemButton> itemButtons;

        public MyAdapter() {
            itemButtons = new LinkedList<>();
            itemButtons.add(new QQListItemButton(0xFFC8C7CE, 0xFFFFFFFF, 100, "置顶", 40, false));
            itemButtons.add(new QQListItemButton(0xFFFF9C37, 0xFFFFFFFF, 200, "标为未读", 30, false));
            itemButtons.add(new QQListItemButton(0xFFFF3739, 0xFFFFFFFF, 100, "删除", 40, false));
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new MyViewHolder(LayoutInflater.from(getActivity()).inflate(R.layout.listitem, parent, false));
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            holder.qqListItem.setButtons(itemButtons);
            holder.qqListItem.setOnClickListener(new QQListItem.OnClickListener() {
                @Override
                public void onButtonClick(int position, QQListItemButton button) {
                    Toast.makeText(getActivity(), button.getText(), Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onClick() {
                    Toast.makeText(getActivity(), "Click", Toast.LENGTH_SHORT).show();
                }
            });
        }

        @Override
        public int getItemCount() {
            return 20;
        }

        class MyViewHolder extends RecyclerView.ViewHolder {

            QQListItem qqListItem;

            public MyViewHolder(View itemView) {
                super(itemView);
                qqListItem = (QQListItem) itemView;
            }
        }
    }
}
