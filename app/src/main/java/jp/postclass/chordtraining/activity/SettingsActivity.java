package jp.postclass.chordtraining.activity;


import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.preference.RingtonePreference;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.MenuItem;

import java.util.List;

import jp.postclass.chordtraining.Exception.ApplicationRuntimeException;
import jp.postclass.chordtraining.R;
import jp.postclass.chordtraining.common.Constants;
import jp.postclass.chordtraining.common.UtCommon;

/**
 * A {@link PreferenceActivity} that presents a set of application settings. On
 * handset devices, settings are presented as a single list. On tablets,
 * settings are split by category, with category headers shown to the left of
 * the list of settings.
 * <p>
 * See <a href="http://developer.android.com/design/patterns/settings.html">
 * Android Design: Settings</a> for design guidelines and the <a
 * href="http://developer.android.com/guide/topics/ui/settings.html">Settings
 * API Guide</a> for more information on developing a Settings UI.
 */
public class SettingsActivity extends AppCompatPreferenceActivity {
    /**
     * A preference value change listener that updates the preference's summary
     * to reflect its new value.
     */
    private static Preference.OnPreferenceChangeListener sBindPreferenceSummaryToValueListener = new Preference.OnPreferenceChangeListener() {
        @Override
        public boolean onPreferenceChange(Preference preference, Object value) {
            String stringValue = value.toString();

            if (preference instanceof ListPreference) {
                // For list preferences, look up the correct display value in
                // the preference's 'entries' list.
                ListPreference listPreference = (ListPreference) preference;
                int index = listPreference.findIndexOfValue(stringValue);

                // Set the summary to reflect the new value.
                preference.setSummary(
                        index >= 0
                                ? listPreference.getEntries()[index]
                                : null);

            } else if (preference instanceof RingtonePreference) {
                // For ringtone preferences, look up the correct display value
                // using RingtoneManager.
                if (TextUtils.isEmpty(stringValue)) {
                    // Empty values correspond to 'silent' (no ringtone).
                    preference.setSummary(R.string.pref_ringtone_silent);

                } else {
                    Ringtone ringtone = RingtoneManager.getRingtone(
                            preference.getContext(), Uri.parse(stringValue));

                    if (ringtone == null) {
                        // Clear the summary if there was a lookup error.
                        preference.setSummary(null);
                    } else {
                        // Set the summary to reflect the new ringtone display
                        // name.
                        String name = ringtone.getTitle(preference.getContext());
                        preference.setSummary(name);
                    }
                }

            } else {
                // For all other preferences, set the summary to the value's
                // simple string representation.
                preference.setSummary(stringValue);
            }
            return true;
        }
    };

    /**
     * Helper method to determine if the device has an extra-large screen. For
     * example, 10" tablets are extra-large.
     */
    private static boolean isXLargeTablet(Context context) {
        return (context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_XLARGE;
    }

    /**
     * Binds a preference's summary to its value. More specifically, when the
     * preference's value is changed, its summary (line of text below the
     * preference title) is updated to reflect the value. The summary is also
     * immediately updated upon calling this method. The exact display format is
     * dependent on the type of preference.
     *
     * @see #sBindPreferenceSummaryToValueListener
     */
    private static void bindPreferenceSummaryToValue(Preference preference) {
        // Set the listener to watch for value changes.
        preference.setOnPreferenceChangeListener(sBindPreferenceSummaryToValueListener);

        // Trigger the listener immediately with the preference's
        // current value.
        sBindPreferenceSummaryToValueListener.onPreferenceChange(preference,
                PreferenceManager
                        .getDefaultSharedPreferences(preference.getContext())
                        .getString(preference.getKey(), ""));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupActionBar();
    }

    /**
     * Set up the {@link android.app.ActionBar}, if the API is available.
     */
    private void setupActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            // Show the Up button in the action bar.
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
//            if (!super.onMenuItemSelected(featureId, item)) {
//                NavUtils.navigateUpFromSameTask(this);
//            }

            finish();
            return true;
        }
        return super.onMenuItemSelected(featureId, item);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean onIsMultiPane() {
        return isXLargeTablet(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public void onBuildHeaders(List<Header> target) {
        loadHeadersFromResource(R.xml.pref_headers, target);
    }

    /**
     * This method stops fragment injection in malicious applications.
     * Make sure to deny any unknown fragments here.
     */
    protected boolean isValidFragment(String fragmentName) {
        return PreferenceFragment.class.getName().equals(fragmentName)
                || CommonPreferenceFragment.class.getName().equals(fragmentName)
                || ChordRootPreferenceFragment.class.getName().equals(fragmentName)
                || ChordDegreePreferenceFragment.class.getName().equals(fragmentName)
                || TonePreferenceFragment.class.getName().equals(fragmentName);
    }


    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static class CommonPreferenceFragment extends PreferenceFragment {
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.pref_common);
            setHasOptionsMenu(true);

            bindPreferenceSummaryToValue(findPreference(Constants.PREF_COMMON_COUNTDOWN_SECOND));
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            int id = item.getItemId();
            if (id == android.R.id.home) {
                startActivity(new Intent(getActivity(), SettingsActivity.class));
                return true;
            }
            return super.onOptionsItemSelected(item);
        }
    }

    /**
     * This fragment shows general preferences only. It is used when the
     * activity is showing a two-pane settings UI.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static class TonePreferenceFragment extends PreferenceFragment {

        private Preference.OnPreferenceChangeListener includeChangeListener = new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {

                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
                boolean high = preferences.getBoolean(Constants.PREF_TONE_INCLUDE_HIGH, false);
                boolean middle = preferences.getBoolean(Constants.PREF_TONE_INCLUDE_MIDDLE, false);
                boolean low = preferences.getBoolean(Constants.PREF_TONE_INCLUDE_LOW, false);

                switch (preference.getKey()) {
                    case Constants.PREF_TONE_INCLUDE_HIGH:
                        high = (boolean) newValue;
                        break;
                    case Constants.PREF_TONE_INCLUDE_MIDDLE:
                        middle = (boolean) newValue;
                        break;
                    case Constants.PREF_TONE_INCLUDE_LOW:
                        low = (boolean) newValue;
                        break;
                    default:
                        throw new ApplicationRuntimeException(preference.getKey());
                }

                return validateRequiredAnyFlag(getContext(), high, middle, low);
            }
        };


        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.pref_tone);
            setHasOptionsMenu(true);
            bindPreferenceSummaryToValue(findPreference(Constants.PREF_TONE_KEYMODE));

            findPreference(Constants.PREF_TONE_INCLUDE_HIGH).setOnPreferenceChangeListener(includeChangeListener);
            findPreference(Constants.PREF_TONE_INCLUDE_MIDDLE).setOnPreferenceChangeListener(includeChangeListener);
            findPreference(Constants.PREF_TONE_INCLUDE_LOW).setOnPreferenceChangeListener(includeChangeListener);
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            int id = item.getItemId();
            if (id == android.R.id.home) {
                startActivity(new Intent(getActivity(), SettingsActivity.class));
                return true;
            }
            return super.onOptionsItemSelected(item);
        }
    }



    /**
     * This fragment shows general preferences only. It is used when the
     * activity is showing a two-pane settings UI.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static class ChordRootPreferenceFragment extends PreferenceFragment {

        private Preference.OnPreferenceChangeListener rootChangeListener = new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {

                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
                boolean rootA = preferences.getBoolean(Constants.PREF_CODE_ROOT_A, false);
                boolean rootAm = preferences.getBoolean(Constants.PREF_CODE_ROOT_Am, false);
                boolean rootB = preferences.getBoolean(Constants.PREF_CODE_ROOT_B, false);
                boolean rootBm = preferences.getBoolean(Constants.PREF_CODE_ROOT_Bm, false);
                boolean rootC = preferences.getBoolean(Constants.PREF_CODE_ROOT_C, false);
                boolean rootCm = preferences.getBoolean(Constants.PREF_CODE_ROOT_Cm, false);
                boolean rootD = preferences.getBoolean(Constants.PREF_CODE_ROOT_D, false);
                boolean rootDm = preferences.getBoolean(Constants.PREF_CODE_ROOT_Dm, false);
                boolean rootE = preferences.getBoolean(Constants.PREF_CODE_ROOT_E, false);
                boolean rootEm = preferences.getBoolean(Constants.PREF_CODE_ROOT_Em, false);
                boolean rootF = preferences.getBoolean(Constants.PREF_CODE_ROOT_F, false);
                boolean rootFm = preferences.getBoolean(Constants.PREF_CODE_ROOT_Fm, false);
                boolean rootG = preferences.getBoolean(Constants.PREF_CODE_ROOT_G, false);
                boolean rootGm = preferences.getBoolean(Constants.PREF_CODE_ROOT_Gm, false);

                switch (preference.getKey()) {
                    case Constants.PREF_CODE_ROOT_A:
                        rootA = (boolean) newValue;
                        break;
                    case Constants.PREF_CODE_ROOT_Am:
                        rootAm = (boolean) newValue;
                        break;
                    case Constants.PREF_CODE_ROOT_B:
                        rootB = (boolean) newValue;
                        break;
                    case Constants.PREF_CODE_ROOT_Bm:
                        rootBm = (boolean) newValue;
                        break;
                    case Constants.PREF_CODE_ROOT_C:
                        rootC = (boolean) newValue;
                        break;
                    case Constants.PREF_CODE_ROOT_Cm:
                        rootCm = (boolean) newValue;
                        break;
                    case Constants.PREF_CODE_ROOT_D:
                        rootD = (boolean) newValue;
                        break;
                    case Constants.PREF_CODE_ROOT_Dm:
                        rootDm = (boolean) newValue;
                        break;
                    case Constants.PREF_CODE_ROOT_E:
                        rootE = (boolean) newValue;
                        break;
                    case Constants.PREF_CODE_ROOT_Em:
                        rootEm = (boolean) newValue;
                        break;
                    case Constants.PREF_CODE_ROOT_F:
                        rootF = (boolean) newValue;
                        break;
                    case Constants.PREF_CODE_ROOT_Fm:
                        rootFm = (boolean) newValue;
                        break;
                    case Constants.PREF_CODE_ROOT_G:
                        rootG = (boolean) newValue;
                        break;
                    case Constants.PREF_CODE_ROOT_Gm:
                        rootGm = (boolean) newValue;
                        break;

                    default:
                        throw new ApplicationRuntimeException(preference.getKey());
                }

                return validateRequiredAnyFlag(getContext(), rootA, rootAm, rootB, rootBm, rootC, rootCm, rootD, rootDm, rootE, rootEm, rootF, rootFm, rootG, rootGm);
            }
        };

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.pref_chord_root);
            setHasOptionsMenu(true);

            findPreference(Constants.PREF_CODE_ROOT_A).setOnPreferenceChangeListener(rootChangeListener);
            findPreference(Constants.PREF_CODE_ROOT_Am).setOnPreferenceChangeListener(rootChangeListener);
            findPreference(Constants.PREF_CODE_ROOT_B).setOnPreferenceChangeListener(rootChangeListener);
            findPreference(Constants.PREF_CODE_ROOT_Bm).setOnPreferenceChangeListener(rootChangeListener);
            findPreference(Constants.PREF_CODE_ROOT_C).setOnPreferenceChangeListener(rootChangeListener);
            findPreference(Constants.PREF_CODE_ROOT_Cm).setOnPreferenceChangeListener(rootChangeListener);
            findPreference(Constants.PREF_CODE_ROOT_D).setOnPreferenceChangeListener(rootChangeListener);
            findPreference(Constants.PREF_CODE_ROOT_Dm).setOnPreferenceChangeListener(rootChangeListener);
            findPreference(Constants.PREF_CODE_ROOT_E).setOnPreferenceChangeListener(rootChangeListener);
            findPreference(Constants.PREF_CODE_ROOT_Em).setOnPreferenceChangeListener(rootChangeListener);
            findPreference(Constants.PREF_CODE_ROOT_F).setOnPreferenceChangeListener(rootChangeListener);
            findPreference(Constants.PREF_CODE_ROOT_Fm).setOnPreferenceChangeListener(rootChangeListener);
            findPreference(Constants.PREF_CODE_ROOT_G).setOnPreferenceChangeListener(rootChangeListener);
            findPreference(Constants.PREF_CODE_ROOT_Gm).setOnPreferenceChangeListener(rootChangeListener);
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            int id = item.getItemId();
            if (id == android.R.id.home) {
                startActivity(new Intent(getActivity(), SettingsActivity.class));
                return true;
            }
            return super.onOptionsItemSelected(item);
        }
    }



    /**
     * This fragment shows general preferences only. It is used when the
     * activity is showing a two-pane settings UI.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static class ChordDegreePreferenceFragment extends PreferenceFragment {

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.pref_chord_degree);
            setHasOptionsMenu(true);

            bindPreferenceSummaryToValue(findPreference(Constants.PREF_CODE_KEY));
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            int id = item.getItemId();
            if (id == android.R.id.home) {
                startActivity(new Intent(getActivity(), SettingsActivity.class));
                return true;
            }
            return super.onOptionsItemSelected(item);
        }
    }


    protected static boolean validateRequiredAnyFlag(Context context, boolean... booleans) {

        if (UtCommon.isTrueAny(booleans)) {
            return true;
        } else {
            AlertDialog.Builder dlg = new AlertDialog.Builder(context);
            dlg.setTitle(R.string.common_error_input_title);
            dlg.setMessage(R.string.common_error_input_required_any_flag);
            dlg.setPositiveButton(
                    R.string.common_positive_button,
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    }
            );
            dlg.create().show();
            return false;
        }
    }
}
