package com.loibi93.jacketts.ui.view;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.loibi93.jacketts.R;

public class SearchBar extends FrameLayout implements TextView.OnEditorActionListener, TextWatcher, View.OnClickListener {
    private ImageView searchButton;
    private ProgressBar loadingIndicator;
    private EditText searchText;
    private ImageButton clearButton;
    private OnSearchListener onSearchListener = null;

    private InputMethodManager inputMethodManager;

    public SearchBar(@NonNull Context context) {
        super(context);
        init(context);
    }

    public SearchBar(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public SearchBar(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(@NonNull Context context) {
        inflate(context, R.layout.search_bar, this);

        searchButton = findViewById(R.id.search_bar_search_button);
        loadingIndicator = findViewById(R.id.search_bar_progress);
        searchText = findViewById(R.id.search_bar_text);
        clearButton = findViewById(R.id.search_bar_clear);

        inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);

        searchText.setOnEditorActionListener(this);
        searchText.addTextChangedListener(this);

        searchButton.setOnClickListener(this);
        clearButton.setOnClickListener(this);
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            initiateSearch(v.getText().toString());
            return true;
        }
        return false;
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (s.length() != 0) {
            clearButton.setVisibility(VISIBLE);
        } else {
            clearButton.setVisibility(GONE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.search_bar_clear:
                searchText.setText("");
                break;
            case R.id.search_bar_search_button:
                initiateSearch(searchText.getText().toString());
                break;
        }
    }

    public void setOnSearchListener(OnSearchListener onSearchListener) {
        this.onSearchListener = onSearchListener;
    }

    public void notifySearchComplete() {
        searchButton.setVisibility(VISIBLE);
        loadingIndicator.setVisibility(GONE);
        searchText.setInputType(EditorInfo.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
    }

    private void initiateSearch(String query) {
        if (query == null || query.length() == 0) {
            Toast.makeText(getContext(), R.string.no_search_term, Toast.LENGTH_SHORT).show();
            return;
        }
        searchButton.setVisibility(GONE);
        loadingIndicator.setVisibility(VISIBLE);
        searchText.setInputType(EditorInfo.IME_ACTION_NONE);

        inputMethodManager.hideSoftInputFromWindow(getWindowToken(), 0);

        if (onSearchListener != null) {
            onSearchListener.onSearch(query);
        }
    }

    public interface OnSearchListener {
        void onSearch(String query);
    }

    @Override
    public void afterTextChanged(Editable s) {
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }
}
