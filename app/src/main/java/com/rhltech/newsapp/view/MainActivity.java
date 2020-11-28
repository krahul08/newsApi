package com.rhltech.newsapp.view;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.rhltech.newsapp.R;
import com.rhltech.newsapp.model.Sources;
import com.rhltech.newsapp.model.SourcesResponse;
import com.rhltech.newsapp.view_model.NewsViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    NewsViewModel newsViewModel;
    private ViewPager viewPager;
    private ViewPagerAdapter viewPagerAdapter;
    private ArrayList<Sources> list = new ArrayList<>();
    private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        tabLayout.setupWithViewPager(viewPager);


        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setOffscreenPageLimit(1);
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        newsViewModel = ViewModelProviders.of(this).get(NewsViewModel.class);
        fetchSourcesData();
    }

    void fetchSourcesData() {
        List<Fragment> fragmentList = new ArrayList<>();
        List<String> sourceList = new ArrayList<>();
        newsViewModel.getResponseLiveData().observe(this, new Observer<SourcesResponse>() {
            @Override
            public void onChanged(SourcesResponse sourcesResponse) {
                progressBar.setVisibility(View.GONE);
                List<Sources> sources = sourcesResponse.getSources();
                for (int i = 0; i < sources.size(); i++) {
                    if (i == 10) {
                        break;
                    }
                    list.addAll(sources);
                    ArticlesFragment fragment = ArticlesFragment.newInstance(list.get(i).getId()
                            , list.get(i).getName());
                    fragmentList.add(fragment);
                    viewPager.setCurrentItem(i);
                    sourceList.add(list.get(i).getName());
                }
                viewPagerAdapter.setTabData(fragmentList, sourceList);
                viewPagerAdapter.notifyDataSetChanged();
            }
        });
    }
}