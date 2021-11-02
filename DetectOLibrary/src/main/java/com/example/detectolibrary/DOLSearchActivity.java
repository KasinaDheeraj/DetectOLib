package com.example.detectolibrary;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class DOLSearchActivity extends AppCompatActivity {

    public static final String URL="Solution for no Text Detected";
    ViewPager viewPager;
    TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dolsearch);

        getSupportActionBar().hide();

        viewPager=(ViewPager) findViewById(R.id.viewpager);
        String[] titles={"Google Search","Youtube Search"};
        FragmentManager fm=getSupportFragmentManager();
        setupViewPager(fm,viewPager,titles);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }


    private void setupViewPager(FragmentManager fm,ViewPager viewPager,String[] titles) {
        MyAdapter adapter = new MyAdapter(fm,titles);

        String url= String.valueOf(getIntent().getExtras().get(URL));

        adapter.addFragment(new DOLGSearchFragment("https://www.google.com/search?q="+url),titles[0]);
        adapter.addFragment(new DOLYTSearchFragment("https://www.youtube.com/results?search_query="+url), titles[1]);

        viewPager.setAdapter(adapter);

    }


    public static class MyAdapter extends FragmentPagerAdapter {
        String[] titles;
        private final List<Fragment> FragmentList = new ArrayList<>();
        public MyAdapter(@NonNull FragmentManager fm, String[] titles) {
            super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
            this.titles=titles;
            ;
        }
        private void addFragment(Fragment fragment,String Title){
            FragmentList.add(fragment);
        }
        @NonNull
        @Override
        public Fragment getItem(int position) {
            return FragmentList.get(position);
        }

        @Override
        public int getCount() {
            return titles.length;
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }
    }
}