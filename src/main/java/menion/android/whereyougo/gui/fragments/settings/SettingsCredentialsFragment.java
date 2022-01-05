package menion.android.whereyougo.gui.fragments.settings;

import android.os.Bundle;

import android.text.InputType;
import androidx.preference.EditTextPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import menion.android.whereyougo.R;
import menion.android.whereyougo.network.LoginTask;
import menion.android.whereyougo.preferences.Preferences;

public class SettingsCredentialsFragment extends PreferenceFragmentCompat {
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.whereyougo_preferences_credentials, rootKey);

        EditTextPreference gc_username = findPreference(Preferences.getKey(R.string.pref_KEY_S_GC_USERNAME));
        EditTextPreference gc_password = findPreference(Preferences.getKey(R.string.pref_KEY_S_GC_PASSWORD));
        Preference checkLogin = findPreference(Preferences.getKey(R.string.pref_KEY_S_GC_CHECK));
        if (checkLogin != null) checkLogin.setEnabled(!(Preferences.GC_USERNAME.isEmpty() || Preferences.GC_PASSWORD.isEmpty()));

        if (gc_username != null) {
            gc_username.setOnPreferenceChangeListener((preference, o) -> {
                Preferences.GC_USERNAME = (String) o;
                if (checkLogin != null) checkLogin.setEnabled(!(Preferences.GC_USERNAME.isEmpty() || Preferences.GC_PASSWORD.isEmpty()));
                return true;
            });
        }
        if (gc_password != null) {
            gc_password.setOnBindEditTextListener(editText -> editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD));
            gc_password.setOnPreferenceChangeListener((preference, o) -> {
                Preferences.GC_PASSWORD = (String) o;
                if (checkLogin != null) checkLogin.setEnabled(!(Preferences.GC_USERNAME.isEmpty() || Preferences.GC_PASSWORD.isEmpty()));
                return true;
            });
        }
        if (checkLogin != null) {
            checkLogin.setOnPreferenceClickListener(preference -> {
                if (!Preferences.GC_USERNAME.isEmpty() && !Preferences.GC_PASSWORD.isEmpty()) {
                    LoginTask loginTask = new LoginTask(requireView(), Preferences.GC_USERNAME, Preferences.GC_PASSWORD);
                    loginTask.execute();
                }
                return true;
            });
        }
    }
}
