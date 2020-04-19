package com.loibi93.jacketts.preferences;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.fragment.app.DialogFragment;

import com.loibi93.jacketts.R;

import java.util.ArrayList;
import java.util.List;

public class RadioBoxPreference extends BaseDialogPreference {
    private List<String> options = null;

    public RadioBoxPreference(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    public RadioBoxPreference(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public RadioBoxPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RadioBoxPreference(Context context) {
        super(context);
        init();
    }

    private void init() {
        setDialogLayoutResource(R.layout.dialog_settings_radiobox);
        setPositiveButtonText(R.string.ok);
        setNegativeButtonText(R.string.cancel);
    }

    @Override
    public DialogFragment getDialog() {
        if (options == null) {
            throw new IllegalArgumentException("Options have not been set.");
        }
        return RadioBoxDialog.newInstance(key, options);
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }

    public static class RadioBoxDialog extends BaseDialogFragment {
        private RadioGroup radioGroup;
        private List<String> options;
        private List<RadioButton> buttons;

        private RadioBoxDialog(List<String> options) {
            this.options = options;
            this.buttons = new ArrayList<>(options.size());
        }

        private static RadioBoxDialog newInstance(PreferenceKey key, List<String> options) {
            RadioBoxDialog dialog = new RadioBoxDialog(options);
            Bundle bundle = new Bundle();
            bundle.putSerializable(ARG_KEY, key.name());
            dialog.setArguments(bundle);
            return dialog;
        }

        @Override
        protected View onCreateDialogView(Context context) {
            View dialog = super.onCreateDialogView(context);

            BaseDialogPreference preference = (BaseDialogPreference) getPreference();
            PreferenceKey key = preference.getPreferenceKey();
            String currentValue = PreferenceHandler.getStringPreference(key, null);

            radioGroup = dialog.findViewById(R.id.settings_radiobox);

            for (String option : options) {
                RadioButton button = new RadioButton(context);
                button.setText(option);
                buttons.add(button);
                radioGroup.addView(button);
                if (currentValue != null && option.contentEquals(currentValue)) {
                    radioGroup.check(button.getId());
                }
            }

            return dialog;
        }

        @Override
        protected Object getCurrentValue() {
            for (RadioButton button : buttons) {
                if (button.getId() == radioGroup.getCheckedRadioButtonId()) {
                    return button.getText();
                }
            }
            return null;
        }
    }
}
