package com.loibi93.jacketts.ui.view;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import androidx.preference.PreferenceDialogFragmentCompat;

import com.loibi93.jacketts.R;
import com.loibi93.jacketts.preferences.PreferenceHandler;
import com.loibi93.jacketts.preferences.PreferenceKey;
import com.loibi93.jacketts.preferences.URLPreference;

import java.util.Arrays;
import java.util.List;

public class URLDialog extends PreferenceDialogFragmentCompat {
    private String selectedProtocol;
    private EditText baseUrlEditText;
    private EditText portEditText;

    public static URLDialog newInstance(String key) {
        URLDialog dialog = new URLDialog();
        Bundle bundle = new Bundle();
        bundle.putSerializable(ARG_KEY, key);
        dialog.setArguments(bundle);
        return dialog;
    }

    @Override
    protected View onCreateDialogView(Context context) {
        View dialog = super.onCreateDialogView(context);

        List<String> protocolOptions = Arrays.asList("http", "https");
        Spinner protocolChooser = dialog.findViewById(R.id.protocol_chooser);
        SpinnerAdapter adapter = new ArrayAdapter<>(dialog.getContext(), android.R.layout.simple_list_item_1, protocolOptions);
        protocolChooser.setAdapter(adapter);
        protocolChooser.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedProtocol = protocolOptions.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        baseUrlEditText = dialog.findViewById(R.id.base_url_edit_text);
        portEditText = dialog.findViewById(R.id.port_edit_text);

        String currentProtocol = PreferenceHandler.getStringPreference(PreferenceKey.PROTOCOL, null);
        String currentBaseUrl = PreferenceHandler.getStringPreference(PreferenceKey.BASE_URL, null);
        int currentPort = PreferenceHandler.getIntPreference(PreferenceKey.PORT, -1);

        if (currentProtocol != null) {
            protocolChooser.setSelection(protocolOptions.indexOf(currentProtocol));
        }
        if (currentBaseUrl != null) {
            baseUrlEditText.setText(currentBaseUrl);
        }
        if (currentPort != -1) {
            portEditText.setText(String.valueOf(currentPort));
        }

        return dialog;
    }

    @Override
    public void onDialogClosed(boolean positiveResult) {
        if (!positiveResult) {
            return;
        }

        String baseUrl = baseUrlEditText.getText().toString();
        int port = Integer.parseInt(portEditText.getText().toString());

        URLPreference preference = (URLPreference) getPreference();
        preference.setProtocol(selectedProtocol);
        preference.setBaseUrl(baseUrl);
        preference.setPort(port);
    }
}
