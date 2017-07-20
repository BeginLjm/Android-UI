package com.beginlu.ui;

import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
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
import com.beginlu.luloader.LuLoader;
import com.beginlu.lumusicplay.LuMusicPlay;
import com.beginlu.lutabtitle.LuTabTitle;
import com.beginlu.qqlistitem.QQListItem;
import com.beginlu.qqlistitem.QQListItemButton;

import java.util.LinkedList;

public class MainActivity extends AppCompatActivity {

    private LuTabTitle mLuTabTitle;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mLuTabTitle = (LuTabTitle) findViewById(R.id.lu_tab_title);
        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        mViewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
        mLuTabTitle.setupWithViewPager(mViewPager);
        mLuTabTitle.setOnAddClickListener(new LuTabTitle.OnAddClickListener() {
            @Override
            public void onAddClick() {
                Toast.makeText(MainActivity.this, "ADD", Toast.LENGTH_SHORT).show();
            }
        });
    }

    class MyPagerAdapter extends FragmentPagerAdapter {

        String[] titles = {"Page1", "Page2", "Page3", "Page4", "Page5", "Page6", "Page7", "Page8"};
        Fragment[] fragments = {new FragmentPage1(), new FragmentPage2(), new FragmentPage3(), new FragmentPage4(), new FragmentPage5(), new FragmentPage6(), new FragmentPage7(), new FragmentPage8()};

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return fragments.length;
        }

        @Override
        public Fragment getItem(int position) {
            return fragments[position];
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }
    }


}
