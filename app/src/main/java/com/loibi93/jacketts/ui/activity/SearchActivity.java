package com.loibi93.jacketts.ui.activity;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.loibi93.jacketts.R;
import com.loibi93.jacketts.ui.adapter.SectionsPagerAdapter;
import com.loibi93.jacketts.ui.view.SearchBar;

public class SearchActivity extends AppCompatActivity implements SearchBar.OnSearchListener {
    private SearchBar searchBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tab_layout);
        tabs.setupWithViewPager(viewPager);

        searchBar = findViewById(R.id.search_bar);
        searchBar.setOnSearchListener(this);
    }

    @Override
    public void onSearch(String query) {
        Toast.makeText(this, "Searching for: " + query, Toast.LENGTH_SHORT).show();
        (new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        searchBar.notifySearchComplete();
                    }
                });
            }
        })).start();
    }
}