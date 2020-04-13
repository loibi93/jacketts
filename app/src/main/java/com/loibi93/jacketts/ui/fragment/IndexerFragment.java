package com.loibi93.jacketts.ui.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.loibi93.jacketts.R;
import com.loibi93.jacketts.data.http.HttpException;
import com.loibi93.jacketts.data.indexer.IndexerDto;
import com.loibi93.jacketts.data.searchresult.SearchResultData;
import com.loibi93.jacketts.data.searchresult.SearchResultItemDto;
import com.loibi93.jacketts.ui.adapter.SearchResultAdapter;
import com.loibi93.jacketts.ui.model.JackettViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static com.loibi93.jacketts.ui.UiUtils.AnimationLength.NONE;
import static com.loibi93.jacketts.ui.UiUtils.switchViews;

public class IndexerFragment extends Fragment {
    private static final String ARG_INDEXER_ID = "indexerId";

    private IndexerDto indexer;
    private View loadingView;
    private TextView messageView;
    private RecyclerView resultList;
    private SearchResultAdapter resultAdapter;

    public IndexerFragment() {
        super(R.layout.fragment_search);
    }

    public static IndexerFragment newInstance(IndexerDto indexer) {
        IndexerFragment fragment = new IndexerFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(ARG_INDEXER_ID, indexer);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle arguments = requireArguments();
        indexer = (IndexerDto) arguments.getSerializable(ARG_INDEXER_ID);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        loadingView = view.findViewById(R.id.loading_result);
        messageView = view.findViewById(R.id.message_view);
        resultList = view.findViewById(R.id.result_list);

        LinearLayoutManager resultLayoutManager = new LinearLayoutManager(getContext());
        resultList.setLayoutManager(resultLayoutManager);

        resultAdapter = new SearchResultAdapter(new ArrayList<>());
        resultList.setAdapter(resultAdapter);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        FragmentActivity activity = requireActivity();

        JackettViewModel viewModel = ViewModelProviders.of(activity).get(JackettViewModel.class);
        SearchResultData resultData = viewModel.getResultData(indexer.getId());

        resultData.getData().observe(this, this::onSearchResult);
        resultData.getErrorData().observe(this, this::onSearchError);
        resultData.getIsLoadingData().observe(this, this::onIsLoading);
    }

    private void onSearchResult(final List<SearchResultItemDto> results) {
        if (results == null) {
            return;
        }

        resultAdapter.clear();

        if (results.size() == 0) {
            messageView.setText(R.string.no_result);
            switchViews(getCurrentlyVisibleView(), messageView, NONE);
        } else {
            switchViews(getCurrentlyVisibleView(), resultList, NONE);
            resultAdapter.addAll(results);
        }
    }

    private void onSearchError(final HttpException exception) {
        if (exception == null) {
            return;
        }
        messageView.setText(String.format(Locale.ENGLISH, "Could not get Data from %s.", indexer.getName()));
        switchViews(getCurrentlyVisibleView(), messageView, NONE);
    }

    private void onIsLoading(boolean isLoading) {
        if (!isLoading) {
            return;
        }
        switchViews(getCurrentlyVisibleView(), loadingView, NONE);
    }

    private View getCurrentlyVisibleView() {
        if (messageView.getVisibility() == View.VISIBLE) {
            return messageView;
        } else if (loadingView.getVisibility() == View.VISIBLE) {
            return loadingView;
        } else {
            return resultList;
        }
    }
}