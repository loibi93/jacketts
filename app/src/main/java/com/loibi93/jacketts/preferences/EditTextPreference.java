package com.loibi93.jacketts.preferences;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;

import androidx.fragment.app.DialogFragment;

import com.loibi93.jacketts.R;

public class EditTextPreference extends BaseDialogPreference {
    private int inputType = -1;

    public EditTextPreference(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    public EditTextPreference(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public EditTextPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public EditTextPreference(Context context) {
        super(context);
        init();
    }

    private void init() {
        setDialogLayoutResource(R.layout.dialog_settings_text_input);
        setPositiveButtonText(R.string.ok);
        setNegativeButtonText(R.string.cancel);
    }

    @Override
    public DialogFragment getDialog() {
        return EditTextDialog.newInstance(key, inputType);
    }

    public void setInputType(int inputType) {
        this.inputType = inputType;
    }

    public static class EditTextDialog extends BaseDialogFragment {
        private EditText editText;
        private int inputType;

        private static EditTextDialog newInstance(PreferenceKey key, int inputType) {
            EditTextDialog dialog = new EditTextDialog(inputType);
            Bundle bundle = new Bundle();
            bundle.putSerializable(ARG_KEY, key.name());
            dialog.setArguments(bundle);
            return dialog;
        }

        private EditTextDialog(int inputType) {
            this.inputType = inputType;
        }

        @Override
        protected View onCreateDialogView(Context context) {
            View dialog = super.onCreateDialogView(context);

            BaseDialogPreference preference = (BaseDialogPreference) getPreference();
            PreferenceKey key = preference.getPreferenceKey();

            editText = dialog.findViewById(R.id.setting_edit_text);
            if (inputType != -1) {
                editText.setInputType(inputType);
            }

            String currentValue = PreferenceHandler.getStringPreference(key, null);
            if (currentValue != null) {
                editText.setText(currentValue);
            }

            return dialog;
        }

        @Override
        protected Object getCurrentValue() {
            return editText.getText().toString();
        }
    }
}
