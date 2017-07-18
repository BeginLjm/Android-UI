package com.beginlu.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.Toast;

import com.beginlu.arcseekbar.ArcSeekBar;
import com.beginlu.qqlistitem.QQListItem;
import com.beginlu.qqlistitem.QQListItemButton;

import java.util.LinkedList;

public class MainActivity extends AppCompatActivity {

    private ArcSeekBar arcSeekBar;
    private LinkedList<QQListItemButton> mButtons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.arcSeekBar = (ArcSeekBar) findViewById(R.id.rounded_seek_bar);
        arcSeekBar.setOnArcSeekBarChangeListener(new ArcSeekBar.OnArcSeekBarChangeListener() {
            @Override
            public void onProgressChanged(ArcSeekBar arcSeekBar, float progress, boolean fromUser) {
                Log.d("Main", progress + "..." + fromUser);
            }

            @Override
            public void onStartTrackingTouch(ArcSeekBar arcSeekBar) {
                Log.d("Main", "start");
            }

            @Override
            public void onStopTrackingTouch(ArcSeekBar arcSeekBar) {
                Log.d("Main", "stop");
            }
        });

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.listView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new MyAdapter());

        mButtons = new LinkedList<>();
        QQListItemButton button1 = new QQListItemButton(0xFFC8C7CE, 0xFFFFFFFF, 100, "置顶", 40, false);
        mButtons.add(button1);

        QQListItemButton button2 = new QQListItemButton(0xFFFF9C37, 0xFFFFFFFF, 200, "标为未读", 30, false);
        mButtons.add(button2);

        QQListItemButton button3 = new QQListItemButton(0xFFFF3739, 0xFFFFFFFF, 100, "删除", 40, false);
        mButtons.add(button3);
    }

    class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new MyViewHolder(LayoutInflater.from(MainActivity.this).inflate(R.layout.listitem, parent, false));
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            holder.qqListItem.setButtons(mButtons);
            holder.qqListItem.setOnClickListener(new QQListItem.OnClickListener() {
                @Override
                public void OnClick(int position, QQListItemButton button) {
                    Toast.makeText(MainActivity.this, "Button" + position + button.getText(), Toast.LENGTH_SHORT).show();
                }
            });
        }

        @Override
        public int getItemCount() {
            return 5;
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
