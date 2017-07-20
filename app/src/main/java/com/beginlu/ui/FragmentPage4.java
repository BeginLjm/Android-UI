package com.beginlu.ui;

import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;

import com.beginlu.luloader.LuLoader;
import com.beginlu.lumusicplay.LuMusicPlay;

public class FragmentPage4 extends Fragment {

    private View mView;
    private LuMusicPlay mLuMusicPlay;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_page4, container, false);

        mLuMusicPlay = (LuMusicPlay) mView.findViewById(R.id.lu_music_play);
        mLuMusicPlay.start();

        return mView;
    }
}
