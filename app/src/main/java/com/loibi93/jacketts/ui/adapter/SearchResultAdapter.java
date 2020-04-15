package com.loibi93.jacketts.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.loibi93.jacketts.R;
import com.loibi93.jacketts.data.searchresult.SearchResultItemDto;

import java.util.List;

import static com.loibi93.jacketts.ui.UiUtils.humanReadableByteCount;

public class SearchResultAdapter extends RecyclerView.Adapter<SearchResultAdapter.SearchResultViewHolder> {
    private List<SearchResultItemDto> dataset;

    public SearchResultAdapter(List<SearchResultItemDto> dataset) {
        this.dataset = dataset;
    }

    @Override
    public SearchResultViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_result, parent, false);
        return new SearchResultViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SearchResultViewHolder holder, int position) {
        SearchResultItemDto item = dataset.get(position);
        holder.titleView.setText(item.getTitle());
        setText(humanReadableByteCount(item.getSize()), holder.sizeView, holder.sizeView);
        setText(item.getFilesHumanReadable(), holder.filesView, holder.filesContainer);
        setText(item.getCategoryDesc(), holder.categoryView, holder.categoryView);
        setText(item.getAgeHumanReadable(), holder.ageView, holder.ageView);
        holder.seedersView.setText(String.valueOf(item.getSeeders()));
        holder.leechersView.setText(String.valueOf(item.getPeers()));
    }

    private void setText(String text, TextView view, View container) {
        if (text == null || text.isEmpty()) {
            container.setVisibility(View.GONE);
        } else {
            container.setVisibility(View.VISIBLE);
            view.setText(text);
        }
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }

    public void clear() {
        dataset.clear();
        notifyDataSetChanged();
    }

    public void addAll(List<SearchResultItemDto> items) {
        dataset.addAll(items);
        notifyDataSetChanged();
    }

    public static class SearchResultViewHolder extends RecyclerView.ViewHolder {
        public TextView titleView;
        public TextView sizeView;
        public TextView filesView;
        public View filesContainer;
        public TextView categoryView;
        public TextView ageView;
        public TextView seedersView;
        public TextView leechersView;

        public SearchResultViewHolder(View view) {
            super(view);
            this.titleView = view.findViewById(R.id.title);
            this.sizeView = view.findViewById(R.id.size);
            this.filesView = view.findViewById(R.id.files);
            this.filesContainer = view.findViewById(R.id.filesContainer);
            this.categoryView = view.findViewById(R.id.category);
            this.ageView = view.findViewById(R.id.age);
            this.seedersView = view.findViewById(R.id.seeders);
            this.leechersView = view.findViewById(R.id.leechers);
        }
    }
}
