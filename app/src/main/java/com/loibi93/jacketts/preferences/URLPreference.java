package com.loibi93.jacketts.preferences;

import android.content.Context;
import android.os.Bundle;
import android.text.InputType;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import androidx.fragment.app.DialogFragment;

import com.loibi93.jacketts.R;

import java.util.Arrays;
import java.util.List;

import okhttp3.HttpUrl;

public class URLPreference extends BaseDialogPreference {
    public URLPreference(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    public URLPreference(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public URLPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public URLPreference(Context context) {
        super(context);
        init();
    }

    private void init() {
        setDialogLayoutResource(R.layout.dialog_settings_url);
        setPositiveButtonText(R.string.ok);
        setNegativeButtonText(R.string.cancel);
    }

    @Override
    public DialogFragment getDialog() {
        return URLDialog.newInstance(key);
    }

    public static class URLDialog extends BaseDialogFragment {
        private String selectedProtocol;
        private EditText baseUrlEditText;
        private EditText portEditText;

        private static URLDialog newInstance(PreferenceKey key) {
            URLDialog dialog = new URLDialog();
            Bundle bundle = new Bundle();
            bundle.putSerializable(ARG_KEY, key.name());
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

            portEditText.setInputType(InputType.TYPE_CLASS_NUMBER);

            URLPreference preference = (URLPreference) getPreference();
            String urlString = PreferenceHandler.getStringPreference(preference.getPreferenceKey(), null);
            if (urlString == null) {
                return dialog;
            }

            HttpUrl url = HttpUrl.parse(urlString);

            if (url == null) {
                return dialog;
            }

            String currentProtocol = url.scheme();
            String currentBaseUrl = url.host();
            int currentPort = url.port();

            protocolChooser.setSelection(protocolOptions.indexOf(currentProtocol));
            baseUrlEditText.setText(currentBaseUrl);
            portEditText.setText(String.valueOf(currentPort));

            return dialog;
        }

        @Override
        protected Object getCurrentValue() {
            String baseUrl = baseUrlEditText.getText().toString();
            int port = 443;
            try {
                port = Integer.parseInt(portEditText.getText().toString());
            } catch (NumberFormatException e) {
                Log.w(URLPreference.class.getSimpleName(), e);
            }
            return new HttpUrl.Builder()
                    .scheme(selectedProtocol)
                    .host(baseUrl)
                    .port(port)
                    .build()
                    .toString();
        }
    }
}
