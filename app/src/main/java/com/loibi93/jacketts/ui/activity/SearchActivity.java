package com.loibi93.jacketts.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.TextView;

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
import static com.loibi93.jacketts.ui.UiUtils.AnimationLength.SHORT;
import static com.loibi93.jacketts.ui.UiUtils.animateVisibilityChange;
import static com.loibi93.jacketts.ui.UiUtils.switchViews;

public class SearchActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {
    public static boolean serverSettingsChanged = true;

    private JackettViewModel viewModel;
    private Button settingsButton;
    private ProgressBar loadingView;
    private ImageView iconView;
    private TextView messageView;
    private ViewPager viewPager;
    private TabLayout tabs;
    private SearchBar searchBar;

    private IndexerData indexerData;
    private boolean successfulInit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        loadingView = findViewById(R.id.tab_loading_view);
        settingsButton = findViewById(R.id.settings_button);
        iconView = findViewById(R.id.app_icon);
        messageView = findViewById(R.id.message_view);
        viewPager = findViewById(R.id.view_pager);
        tabs = findViewById(R.id.tab_layout);
        searchBar = findViewById(R.id.search_bar);

        settingsButton.setOnClickListener(v -> {
            Intent intent = new Intent(SearchActivity.this, SettingsActivity.class);
            startActivity(intent);
        });

        viewModel = ViewModelProviders.of(this).get(JackettViewModel.class);
        indexerData = viewModel.getIndexers();
        indexerData.getData().observe(this, this::onIndexers);
        indexerData.getErrorData().observe(this, this::onError);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (serverSettingsChanged) {
            successfulInit = false;
            serverSettingsChanged = false;
        }
        if (!successfulInit) {
            loadingView.setVisibility(View.VISIBLE);
            settingsButton.setVisibility(View.GONE);
            iconView.setVisibility(View.VISIBLE);
            messageView.setVisibility(View.GONE);
            viewPager.setVisibility(View.GONE);
            tabs.setVisibility(View.GONE);
            searchBar.setVisibility(View.GONE);
            indexerData.update();
        }
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_settings:
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                return true;
        }
        return false;
    }

    private void onIndexers(List<IndexerDto> indexers) {
        if (indexers == null) {
            return;
        }

        viewModel.initResultData(indexers);

        searchBar.addOnSearchListener(viewModel);
        searchBar.setOnMenuItemClickListener(this);

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(indexers, getSupportFragmentManager());
        viewPager.setAdapter(sectionsPagerAdapter);
        viewPager.setOffscreenPageLimit(indexers.size());
        tabs.setupWithViewPager(viewPager);

        animateVisibilityChange(viewPager, View.VISIBLE, MEDIUM);
        animateVisibilityChange(tabs, View.VISIBLE, MEDIUM);
        animateVisibilityChange(searchBar, View.VISIBLE, MEDIUM);

        animateVisibilityChange(iconView, View.GONE, MEDIUM);
        animateVisibilityChange(messageView, View.GONE, MEDIUM);
        animateVisibilityChange(loadingView, View.GONE, MEDIUM);

        successfulInit = true;
    }

    private void onError(HttpException exception) {
        if (exception == null) {
            return;
        }

        switchViews(loadingView, settingsButton, SHORT);
        switchViews(iconView, messageView, SHORT);

        int stringId;
        switch (exception.getCode()) {
            case 401:
                stringId = R.string.login_error_401;
                break;
            case 404:
                stringId = R.string.login_error_404;
                break;
            case 501:
                stringId = R.string.login_error_501;
                break;
            case 502:
                stringId = R.string.login_error_502;
                break;
            case 500:
            default:
                stringId = R.string.login_error_500;
        }

        messageView.setText(getString(stringId));

        successfulInit = false;
    }
}