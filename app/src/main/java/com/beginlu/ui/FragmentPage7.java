package com.beginlu.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class FragmentPage7 extends Fragment {

    private View mView;
    private RecyclerView mRecyclerView;
    private PKProgressBar mPkBar;
    private int left = 50;
    private int right = 80;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_page7, container, false);

        WheelPicker wheelPicker = (WheelPicker) mView.findViewById(R.id.wheel_view);
        return mView;
    }

    public static void main(String[] args) {
        long l = System.currentTimeMillis();
        int duration = 120;
        long startTime = 1517481938;
        long mTime = duration - ((System.currentTimeMillis() / 1000) - startTime);
        System.out.printf(mTime + "");
    }

}
