package com.beginlu.lumusicplay;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

/**
 * Created by lujm on 17/7/21.
 */

public class LuMusic extends RelativeLayout {
    private final LuMusicPlay mLuMusicPlay;
    private final LuMusicNext mLuMusicNext;
    private final LuMusicPrevious mLuMusicPrevious;

    public LuMusic(Context context) {
        this(context, null);
    }

    public LuMusic(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LuMusic(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        View view = View.inflate(context, R.layout.lu_music_layout, null);
        view.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        this.addView(view);
        mLuMusicPlay = (LuMusicPlay) view.findViewById(R.id.lu_music_play);
        mLuMusicNext = (LuMusicNext) view.findViewById(R.id.lu_music_next);
        mLuMusicPrevious = (LuMusicPrevious) view.findViewById(R.id.lu_music_previous);
        mLuMusicPlay.start();
        mLuMusicPlay.setOnPlayTouchEvent(new LuMusicPlay.OnPlayTouchEvent() {
            @Override
            public void next(float offset) {
                Log.d("MusicPlay", offset + "");
                if (mLuMusicNext.getVisibility() == GONE)
                    mLuMusicNext.setVisibility(VISIBLE);
                mLuMusicNext.setOffSet(offset);
            }

            @Override
            public void previous(float offset) {
                Log.d("MusicPlay", offset + "");
                if (mLuMusicPrevious.getVisibility() == GONE)
                    mLuMusicPrevious.setVisibility(VISIBLE);
                mLuMusicPrevious.setOffSet(offset);
            }

            @Override
            public void stop() {
                if (mLuMusicNext.getVisibility() == VISIBLE)
                    mLuMusicNext.stop();
                if (mLuMusicPrevious.getVisibility() == VISIBLE)
                    mLuMusicPrevious.stop();
            }
        });
    }
}
