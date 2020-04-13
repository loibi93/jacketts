package com.loibi93.jacketts.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.loibi93.jacketts.R;
import com.loibi93.jacketts.data.http.HttpException;
import com.loibi93.jacketts.data.indexer.IndexerData;
import com.loibi93.jacketts.data.indexer.IndexerDto;
import com.loibi93.jacketts.ui.adapter.SectionsPagerAdapter;
import com.loibi93.jacketts.ui.model.JackettViewModel;
import com.loibi93.jacketts.ui.view.SearchBar;

import java.util.List;

import static com.loibi93.jacketts.ui.UiUtils.AnimationLength.MEDIUM;
import static com.loibi93.jacketts.ui.UiUtils.animateVisibilityChange;

public class SearchActivity extends AppCompatActivity {

    private JackettViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        viewModel = ViewModelProviders.of(this).get(JackettViewModel.class);
        IndexerData indexerData = viewModel.getIndexers();
        indexerData.getData().observe(this, this::onIndexers);
        indexerData.getErrorData().observe(this, this::onError);
        indexerData.update();
    }

    private void onIndexers(List<IndexerDto> indexers) {
        if (indexers == null) {
            return;
        }

        viewModel.initResultData(indexers);

        ViewPager viewPager = findViewById(R.id.view_pager);
        TabLayout tabs = findViewById(R.id.tab_layout);
        SearchBar searchBar = findViewById(R.id.search_bar);
        View appIcon = findViewById(R.id.app_icon);
        View loadingView = findViewById(R.id.tabLoadingView);

        searchBar.addOnSearchListener(viewModel);

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(indexers, getSupportFragmentManager());
        viewPager.setAdapter(sectionsPagerAdapter);
        viewPager.setOffscreenPageLimit(indexers.size());
        tabs.setupWithViewPager(viewPager);

        animateVisibilityChange(viewPager, View.VISIBLE, MEDIUM);
        animateVisibilityChange(tabs, View.VISIBLE, MEDIUM);
        animateVisibilityChange(searchBar, View.VISIBLE, MEDIUM);

        animateVisibilityChange(appIcon, View.GONE, MEDIUM);
        animateVisibilityChange(loadingView, View.GONE, MEDIUM);
    }

    private void onError(HttpException exception) {
        if (exception == null) {
            return;
        }
        // TODO: Show login probably
        Toast.makeText(this, exception.getMessage(), Toast.LENGTH_LONG).show();
    }
}