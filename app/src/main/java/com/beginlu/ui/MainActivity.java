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

import com.beginlu.arcseekbar.ArcSeekBar;

public class MainActivity extends AppCompatActivity {

    private ArcSeekBar arcSeekBar;

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
    }

    class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new MyViewHolder(LayoutInflater.from(MainActivity.this).inflate(R.layout.listitem, parent, false));
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return 5;
        }

        class MyViewHolder extends RecyclerView.ViewHolder {

            public MyViewHolder(View itemView) {
                super(itemView);
            }
        }
    }


}
