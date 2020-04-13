package com.loibi93.jacketts.ui.adapter;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.loibi93.jacketts.data.indexer.IndexerDto;
import com.loibi93.jacketts.ui.fragment.IndexerFragment;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class SectionsPagerAdapter extends FragmentPagerAdapter {
    private List<IndexerDto> indexers;

    public SectionsPagerAdapter(List<IndexerDto> indexers, FragmentManager fm) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.indexers = indexers;
    }

    @NotNull
    @Override
    public Fragment getItem(int position) {
        return IndexerFragment.newInstance(indexers.get(position));
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return indexers.get(position).getName();
    }

    @Override
    public int getCount() {
        return indexers.size();
    }
}