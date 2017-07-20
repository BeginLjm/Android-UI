package com.beginlu.ui;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.beginlu.arcseekbar.ArcSeekBar;

public class FragmentPage2 extends Fragment {

    private View mView;
    private ArcSeekBar mArcSeekBar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_page2, container, false);
        mArcSeekBar = (ArcSeekBar) mView.findViewById(R.id.arc_seek_bar);
        mArcSeekBar.setOnArcSeekBarChangeListener(new ArcSeekBar.OnArcSeekBarChangeListener() {
            @Override
            public void onProgressChanged(ArcSeekBar arcSeekBar, float progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(ArcSeekBar arcSeekBar) {

            }

            @Override
            public void onStopTrackingTouch(ArcSeekBar arcSeekBar) {

            }
        });
        return mView;
    }
}
