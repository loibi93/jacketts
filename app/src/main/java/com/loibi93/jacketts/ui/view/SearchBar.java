package com.loibi93.jacketts.ui.view;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MenuInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;

import com.loibi93.jacketts.R;

import java.util.HashSet;
import java.util.Set;

import static com.loibi93.jacketts.ui.UiUtils.AnimationLength.SHORT;
import static com.loibi93.jacketts.ui.UiUtils.switchViews;

public class SearchBar extends FrameLayout implements TextView.OnEditorActionListener, TextWatcher, View.OnClickListener {
    private EditText searchText;
    private ImageButton clearButton;
    private ImageButton optionsButton;
    private Set<OnSearchListener> onSearchListeners;
    private PopupMenu.OnMenuItemClickListener onMenuItemClickListener;

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

        onSearchListeners = new HashSet<>();

        ImageView settingsButton = findViewById(R.id.search_bar_settings_button);
        searchText = findViewById(R.id.search_bar_text);
        clearButton = findViewById(R.id.search_bar_clear);
        optionsButton = findViewById(R.id.search_bar_options);

        inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);

        searchText.setOnEditorActionListener(this);
        searchText.addTextChangedListener(this);

        settingsButton.setOnClickListener(this);
        clearButton.setOnClickListener(this);
        optionsButton.setOnClickListener(this);
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
            switchViews(optionsButton, clearButton, SHORT);
        } else {
            switchViews(clearButton, optionsButton, SHORT);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.search_bar_clear:
                searchText.setText("");
                break;
            case R.id.search_bar_settings_button:
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                break;
            case R.id.search_bar_options:
                showMenu(optionsButton);
                break;
        }
    }

    private void showMenu(View view) {
        PopupMenu menu = new PopupMenu(getContext(), view);
        MenuInflater inflater = menu.getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu.getMenu());
        menu.show();
        menu.setOnMenuItemClickListener(onMenuItemClickListener);
    }

    public void addOnSearchListener(OnSearchListener onSearchListener) {
        this.onSearchListeners.add(onSearchListener);
    }

    public void setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener listener) {
        this.onMenuItemClickListener = listener;
    }

    private void initiateSearch(String query) {
        if (query == null || query.length() == 0) {
            Toast.makeText(getContext(), R.string.no_search_term, Toast.LENGTH_SHORT).show();
            return;
        }

        inputMethodManager.hideSoftInputFromWindow(getWindowToken(), 0);

        for (OnSearchListener listener : onSearchListeners) {
            listener.onSearch(query);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    public interface OnSearchListener {
        void onSearch(String query);
    }
}
