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
import android.preference.PreferenceCategory;
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

        private PreferenceCategory categoryMajor;
        private PreferenceCategory categoryMinor;

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.pref_tone);
            setHasOptionsMenu(true);
            bindPreferenceSummaryToValue(findPreference(Constants.PREF_TONE_KEYMODE));

            categoryMajor = (PreferenceCategory) findPreference(Constants.PREF_TONE_CATEGORY_MAJOR);
            categoryMinor = (PreferenceCategory) findPreference(Constants.PREF_TONE_CATEGORY_MINOR);

            findPreference(Constants.PREF_TONE_KEYMODE).setOnPreferenceChangeListener(keymodeChangeListener);

            findPreference(Constants.PREF_TONE_Csh).setOnPreferenceChangeListener(includeChangeListener);
            findPreference(Constants.PREF_TONE_Fsh).setOnPreferenceChangeListener(includeChangeListener);
            findPreference(Constants.PREF_TONE_B).setOnPreferenceChangeListener(includeChangeListener);
            findPreference(Constants.PREF_TONE_E).setOnPreferenceChangeListener(includeChangeListener);
            findPreference(Constants.PREF_TONE_A).setOnPreferenceChangeListener(includeChangeListener);
            findPreference(Constants.PREF_TONE_D).setOnPreferenceChangeListener(includeChangeListener);
            findPreference(Constants.PREF_TONE_G).setOnPreferenceChangeListener(includeChangeListener);
            findPreference(Constants.PREF_TONE_C).setOnPreferenceChangeListener(includeChangeListener);
            findPreference(Constants.PREF_TONE_F).setOnPreferenceChangeListener(includeChangeListener);
            findPreference(Constants.PREF_TONE_Bb).setOnPreferenceChangeListener(includeChangeListener);
            findPreference(Constants.PREF_TONE_Eb).setOnPreferenceChangeListener(includeChangeListener);
            findPreference(Constants.PREF_TONE_Ab).setOnPreferenceChangeListener(includeChangeListener);
            findPreference(Constants.PREF_TONE_Db).setOnPreferenceChangeListener(includeChangeListener);
            findPreference(Constants.PREF_TONE_Gb).setOnPreferenceChangeListener(includeChangeListener);
            findPreference(Constants.PREF_TONE_Cb).setOnPreferenceChangeListener(includeChangeListener);
            findPreference(Constants.PREF_TONE_Ashm).setOnPreferenceChangeListener(includeChangeListener);
            findPreference(Constants.PREF_TONE_Dshm).setOnPreferenceChangeListener(includeChangeListener);
            findPreference(Constants.PREF_TONE_Gshm).setOnPreferenceChangeListener(includeChangeListener);
            findPreference(Constants.PREF_TONE_Cshm).setOnPreferenceChangeListener(includeChangeListener);
            findPreference(Constants.PREF_TONE_Fshm).setOnPreferenceChangeListener(includeChangeListener);
            findPreference(Constants.PREF_TONE_Bm).setOnPreferenceChangeListener(includeChangeListener);
            findPreference(Constants.PREF_TONE_Em).setOnPreferenceChangeListener(includeChangeListener);
            findPreference(Constants.PREF_TONE_Am).setOnPreferenceChangeListener(includeChangeListener);
            findPreference(Constants.PREF_TONE_Dm).setOnPreferenceChangeListener(includeChangeListener);
            findPreference(Constants.PREF_TONE_Gm).setOnPreferenceChangeListener(includeChangeListener);
            findPreference(Constants.PREF_TONE_Cm).setOnPreferenceChangeListener(includeChangeListener);
            findPreference(Constants.PREF_TONE_Fm).setOnPreferenceChangeListener(includeChangeListener);
            findPreference(Constants.PREF_TONE_Bbm).setOnPreferenceChangeListener(includeChangeListener);
            findPreference(Constants.PREF_TONE_Ebm).setOnPreferenceChangeListener(includeChangeListener);
            findPreference(Constants.PREF_TONE_Abm).setOnPreferenceChangeListener(includeChangeListener);

            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
            changeKeyMode(preferences.getString(Constants.PREF_TONE_KEYMODE, Constants.KEYMODE_MAJOR));
        }

        private void changeKeyMode(String keymode) {
            if (Constants.KEYMODE_MAJOR.equals(keymode)) {
                getPreferenceScreen().addPreference(categoryMajor);
                getPreferenceScreen().removePreference(categoryMinor);
            } else {
                getPreferenceScreen().addPreference(categoryMinor);
                getPreferenceScreen().removePreference(categoryMajor);
            }
        }


        private Preference.OnPreferenceChangeListener includeChangeListener = new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {

                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());

                boolean keyCsh = preferences.getBoolean(Constants.PREF_TONE_Csh, true);
                boolean keyFsh = preferences.getBoolean(Constants.PREF_TONE_Fsh, true);
                boolean keyB = preferences.getBoolean(Constants.PREF_TONE_B, true);
                boolean keyE = preferences.getBoolean(Constants.PREF_TONE_E, true);
                boolean keyA = preferences.getBoolean(Constants.PREF_TONE_A, true);
                boolean keyD = preferences.getBoolean(Constants.PREF_TONE_D, true);
                boolean keyG = preferences.getBoolean(Constants.PREF_TONE_G, true);
                boolean keyC = preferences.getBoolean(Constants.PREF_TONE_C, true);
                boolean keyF = preferences.getBoolean(Constants.PREF_TONE_F, true);
                boolean keyBb = preferences.getBoolean(Constants.PREF_TONE_Bb, true);
                boolean keyEb = preferences.getBoolean(Constants.PREF_TONE_Eb, true);
                boolean keyAb = preferences.getBoolean(Constants.PREF_TONE_Ab, true);
                boolean keyDb = preferences.getBoolean(Constants.PREF_TONE_Db, true);
                boolean keyGb = preferences.getBoolean(Constants.PREF_TONE_Gb, true);
                boolean keyCb = preferences.getBoolean(Constants.PREF_TONE_Cb, true);

                boolean keyAshm = preferences.getBoolean(Constants.PREF_TONE_Ashm, true);
                boolean keyDshm = preferences.getBoolean(Constants.PREF_TONE_Dshm, true);
                boolean keyGshm = preferences.getBoolean(Constants.PREF_TONE_Gshm, true);
                boolean keyCshm = preferences.getBoolean(Constants.PREF_TONE_Cshm, true);
                boolean keyFshm = preferences.getBoolean(Constants.PREF_TONE_Fshm, true);
                boolean keyBm = preferences.getBoolean(Constants.PREF_TONE_Bm, true);
                boolean keyEm = preferences.getBoolean(Constants.PREF_TONE_Em, true);
                boolean keyAm = preferences.getBoolean(Constants.PREF_TONE_Am, true);
                boolean keyDm = preferences.getBoolean(Constants.PREF_TONE_Dm, true);
                boolean keyGm = preferences.getBoolean(Constants.PREF_TONE_Gm, true);
                boolean keyCm = preferences.getBoolean(Constants.PREF_TONE_Cm, true);
                boolean keyFm = preferences.getBoolean(Constants.PREF_TONE_Fm, true);
                boolean keyBbm = preferences.getBoolean(Constants.PREF_TONE_Bbm, true);
                boolean keyEbm = preferences.getBoolean(Constants.PREF_TONE_Ebm, true);
                boolean keyAbm = preferences.getBoolean(Constants.PREF_TONE_Abm, true);

                switch (preference.getKey()) {
                    case Constants.PREF_TONE_Csh:
                        keyCsh = (boolean) newValue;
                        break;
                    case Constants.PREF_TONE_Fsh:
                        keyFsh = (boolean) newValue;
                        break;
                    case Constants.PREF_TONE_B:
                        keyB = (boolean) newValue;
                        break;
                    case Constants.PREF_TONE_E:
                        keyE = (boolean) newValue;
                        break;
                    case Constants.PREF_TONE_A:
                        keyA = (boolean) newValue;
                        break;
                    case Constants.PREF_TONE_D:
                        keyD = (boolean) newValue;
                        break;
                    case Constants.PREF_TONE_G:
                        keyG = (boolean) newValue;
                        break;
                    case Constants.PREF_TONE_C:
                        keyC = (boolean) newValue;
                        break;
                    case Constants.PREF_TONE_F:
                        keyF = (boolean) newValue;
                        break;
                    case Constants.PREF_TONE_Bb:
                        keyBb = (boolean) newValue;
                        break;
                    case Constants.PREF_TONE_Eb:
                        keyEb = (boolean) newValue;
                        break;
                    case Constants.PREF_TONE_Ab:
                        keyAb = (boolean) newValue;
                        break;
                    case Constants.PREF_TONE_Db:
                        keyDb = (boolean) newValue;
                        break;
                    case Constants.PREF_TONE_Gb:
                        keyGb = (boolean) newValue;
                        break;
                    case Constants.PREF_TONE_Cb:
                        keyCb = (boolean) newValue;
                        break;
                    case Constants.PREF_TONE_Ashm:
                        keyAshm = (boolean) newValue;
                        break;
                    case Constants.PREF_TONE_Dshm:
                        keyDshm = (boolean) newValue;
                        break;
                    case Constants.PREF_TONE_Gshm:
                        keyGshm = (boolean) newValue;
                        break;
                    case Constants.PREF_TONE_Cshm:
                        keyCshm = (boolean) newValue;
                        break;
                    case Constants.PREF_TONE_Fshm:
                        keyFshm = (boolean) newValue;
                        break;
                    case Constants.PREF_TONE_Bm:
                        keyBm = (boolean) newValue;
                        break;
                    case Constants.PREF_TONE_Em:
                        keyEm = (boolean) newValue;
                        break;
                    case Constants.PREF_TONE_Am:
                        keyAm = (boolean) newValue;
                        break;
                    case Constants.PREF_TONE_Dm:
                        keyDm = (boolean) newValue;
                        break;
                    case Constants.PREF_TONE_Gm:
                        keyGm = (boolean) newValue;
                        break;
                    case Constants.PREF_TONE_Cm:
                        keyCm = (boolean) newValue;
                        break;
                    case Constants.PREF_TONE_Fm:
                        keyFm = (boolean) newValue;
                        break;
                    case Constants.PREF_TONE_Bbm:
                        keyBbm = (boolean) newValue;
                        break;
                    case Constants.PREF_TONE_Ebm:
                        keyEbm = (boolean) newValue;
                        break;
                    case Constants.PREF_TONE_Abm:
                        keyAbm = (boolean) newValue;
                        break;
                }

                if (Constants.KEYMODE_MAJOR.equals(preferences.getString(Constants.PREF_TONE_KEYMODE, Constants.KEYMODE_MAJOR))) {
                    return validateRequiredAnyFlag(getContext(), keyCsh, keyFsh, keyB, keyE, keyA, keyD, keyG, keyC, keyF, keyBb, keyEb, keyAb, keyDb, keyGb, keyCb);
                } else {
                    return validateRequiredAnyFlag(getContext(), keyAshm, keyDshm, keyGshm, keyCshm, keyFshm, keyBm, keyEm, keyAm, keyDm, keyGm, keyCm, keyFm, keyBbm, keyEbm, keyAbm);
                }
            }
        };

        private Preference.OnPreferenceChangeListener keymodeChangeListener =
                new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                changeKeyMode((String) newValue);

                return sBindPreferenceSummaryToValueListener.onPreferenceChange(preference, newValue);
            }
        };

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

        private PreferenceCategory categoryVariation;

        private Preference.OnPreferenceChangeListener rootChangeListener =
                new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {

                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
                boolean rootA = preferences.getBoolean(Constants.PREF_CHORD_ROOT_A, false);
                boolean rootAm = preferences.getBoolean(Constants.PREF_CHORD_ROOT_Am, false);
                boolean rootB = preferences.getBoolean(Constants.PREF_CHORD_ROOT_B, false);
                boolean rootBm = preferences.getBoolean(Constants.PREF_CHORD_ROOT_Bm, false);
                boolean rootC = preferences.getBoolean(Constants.PREF_CHORD_ROOT_C, false);
                boolean rootCm = preferences.getBoolean(Constants.PREF_CHORD_ROOT_Cm, false);
                boolean rootD = preferences.getBoolean(Constants.PREF_CHORD_ROOT_D, false);
                boolean rootDm = preferences.getBoolean(Constants.PREF_CHORD_ROOT_Dm, false);
                boolean rootE = preferences.getBoolean(Constants.PREF_CHORD_ROOT_E, false);
                boolean rootEm = preferences.getBoolean(Constants.PREF_CHORD_ROOT_Em, false);
                boolean rootF = preferences.getBoolean(Constants.PREF_CHORD_ROOT_F, false);
                boolean rootFm = preferences.getBoolean(Constants.PREF_CHORD_ROOT_Fm, false);
                boolean rootG = preferences.getBoolean(Constants.PREF_CHORD_ROOT_G, false);
                boolean rootGm = preferences.getBoolean(Constants.PREF_CHORD_ROOT_Gm, false);

                switch (preference.getKey()) {
                    case Constants.PREF_CHORD_ROOT_A:
                        rootA = (boolean) newValue;
                        break;
                    case Constants.PREF_CHORD_ROOT_Am:
                        rootAm = (boolean) newValue;
                        break;
                    case Constants.PREF_CHORD_ROOT_B:
                        rootB = (boolean) newValue;
                        break;
                    case Constants.PREF_CHORD_ROOT_Bm:
                        rootBm = (boolean) newValue;
                        break;
                    case Constants.PREF_CHORD_ROOT_C:
                        rootC = (boolean) newValue;
                        break;
                    case Constants.PREF_CHORD_ROOT_Cm:
                        rootCm = (boolean) newValue;
                        break;
                    case Constants.PREF_CHORD_ROOT_D:
                        rootD = (boolean) newValue;
                        break;
                    case Constants.PREF_CHORD_ROOT_Dm:
                        rootDm = (boolean) newValue;
                        break;
                    case Constants.PREF_CHORD_ROOT_E:
                        rootE = (boolean) newValue;
                        break;
                    case Constants.PREF_CHORD_ROOT_Em:
                        rootEm = (boolean) newValue;
                        break;
                    case Constants.PREF_CHORD_ROOT_F:
                        rootF = (boolean) newValue;
                        break;
                    case Constants.PREF_CHORD_ROOT_Fm:
                        rootFm = (boolean) newValue;
                        break;
                    case Constants.PREF_CHORD_ROOT_G:
                        rootG = (boolean) newValue;
                        break;
                    case Constants.PREF_CHORD_ROOT_Gm:
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

            findPreference(Constants.PREF_CHORD_ROOT_A).setOnPreferenceChangeListener(rootChangeListener);
            findPreference(Constants.PREF_CHORD_ROOT_Am).setOnPreferenceChangeListener(rootChangeListener);
            findPreference(Constants.PREF_CHORD_ROOT_B).setOnPreferenceChangeListener(rootChangeListener);
            findPreference(Constants.PREF_CHORD_ROOT_Bm).setOnPreferenceChangeListener(rootChangeListener);
            findPreference(Constants.PREF_CHORD_ROOT_C).setOnPreferenceChangeListener(rootChangeListener);
            findPreference(Constants.PREF_CHORD_ROOT_Cm).setOnPreferenceChangeListener(rootChangeListener);
            findPreference(Constants.PREF_CHORD_ROOT_D).setOnPreferenceChangeListener(rootChangeListener);
            findPreference(Constants.PREF_CHORD_ROOT_Dm).setOnPreferenceChangeListener(rootChangeListener);
            findPreference(Constants.PREF_CHORD_ROOT_E).setOnPreferenceChangeListener(rootChangeListener);
            findPreference(Constants.PREF_CHORD_ROOT_Em).setOnPreferenceChangeListener(rootChangeListener);
            findPreference(Constants.PREF_CHORD_ROOT_F).setOnPreferenceChangeListener(rootChangeListener);
            findPreference(Constants.PREF_CHORD_ROOT_Fm).setOnPreferenceChangeListener(rootChangeListener);
            findPreference(Constants.PREF_CHORD_ROOT_G).setOnPreferenceChangeListener(rootChangeListener);
            findPreference(Constants.PREF_CHORD_ROOT_Gm).setOnPreferenceChangeListener(rootChangeListener);

            categoryVariation = (PreferenceCategory) findPreference("pref.chord.root.category.variation");
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

            bindPreferenceSummaryToValue(findPreference(Constants.PREF_CHORD_KEY));
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

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static class ScalePreferenceFragment extends PreferenceFragment {

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.pref_scale);
            setHasOptionsMenu(true);
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
