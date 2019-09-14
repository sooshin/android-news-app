/*
 * MIT License
 *
 * Copyright (c) 2018 Soojeong Shin
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.example.android.newsfeed;

import android.app.DatePickerDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import androidx.appcompat.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.DatePicker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * The SettingsActivity is the activity that appears when a settings icon is clicked on.
 */

public class SettingsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);

        // Navigate with the app icon in the action bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    /**
     * The NewsPreferenceFragment implements the Preference.OnPreferenceChangeListener interface
     * to set up to listen for any Preference changes made by the user.
     * And the NewsPreferenceFragment also implements the DatePickerDialog.OnDateSetListener to
     * receive a callback when the user has finished selecting a date.
     */
    public static class NewsPreferenceFragment extends PreferenceFragment
            implements Preference.OnPreferenceChangeListener, DatePickerDialog.OnDateSetListener {

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.settings_main);

            // Find the preference for number of items
            Preference numOfItems = findPreference(getString(R.string.settings_number_of_items_key));
            // bind the current preference value to be displayed
            bindPreferenceSummaryToValue(numOfItems);

            // Find the "order by" Preference object according to its key
            Preference orderBy = findPreference(getString(R.string.settings_order_by_key));
            // Update the summary so that it displays the current value stored in SharedPreferences
            bindPreferenceSummaryToValue(orderBy);

            // Find the "order date" Preference object according to its key
            Preference orderDate = findPreference(getString(R.string.settings_order_date_key));
            // Update the summary so that it displays the current value stored in SharedPreferences
            bindPreferenceSummaryToValue(orderDate);

            // Find the "color theme" Preference object according to its key
            Preference colorTheme = findPreference(getString(R.string.settings_color_key));
            // Update the summary so that it displays the current value stored in SharedPreferences
            bindPreferenceSummaryToValue(colorTheme);

            // Find the "text size" Preference object according to its key
            Preference textSize = findPreference(getString(R.string.settings_text_size_key));
            // Update the summary so that it displays the current value stored in SharedPreferences
            bindPreferenceSummaryToValue(textSize);

            // Find the "from date" Preference object according to its key
            Preference fromDate = findPreference(getString(R.string.settings_from_date_key));
            setOnPreferenceClick(fromDate);
            // bind the current preference value to be displayed
            bindPreferenceSummaryToValue(fromDate);
        }

        /**
         * This method is called when the user has clicked a Preference.
         */
        private void setOnPreferenceClick(Preference preference) {
            preference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {
                    String key = preference.getKey();
                    if (key.equalsIgnoreCase(getString(R.string.settings_from_date_key))) {
                        showDatePicker();
                    }
                    return false;
                }
            });
        }

        /**
         * Show the current date as the default date in the picker
         */
        private void showDatePicker() {
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
            new DatePickerDialog(getActivity(),
                   this, year, month, dayOfMonth).show();
        }

        @Override
        public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
            // Since it starts counting the months from 0, add one to the month value.
            month = month + 1;
            // Date the user selected
            String selectedDate = year + "-" + month + "-" + dayOfMonth;
            // Convert selected date string(i.e. "2017-2-1" into formatted date string(i.e. "2017-02-01")
            String formattedDate = formatDate(selectedDate);

            // Storing selected date
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString(getString(R.string.settings_from_date_key), formattedDate).apply();

            // Update the displayed preference summary after it has been changed
            Preference fromDatePreference = findPreference(getString(R.string.settings_from_date_key));
            bindPreferenceSummaryToValue(fromDatePreference);
        }

        /**
         * This method is called when the user has changed a Preference.
         * Update the displayed preference summary (the UI) after it has been changed.
         * @param preference the changed Preference
         * @param value the new value of the Preference
         * @return True to update the state of the Preference with the new value
         */
        @Override
        public boolean onPreferenceChange(Preference preference, Object value) {
            String stringValue = value.toString();
            // Update the summary of a ListPreference using the label
            if (preference instanceof ListPreference) {
                ListPreference listPreference = (ListPreference) preference;
                int prefIndex = listPreference.findIndexOfValue(stringValue);
                if (prefIndex >= 0) {
                    CharSequence[] labels = listPreference.getEntries();
                    preference.setSummary(labels[prefIndex]);
                }
            } else {
                preference.setSummary(stringValue);
            }
            return true;
        }

        /**
         * Set this fragment as the OnPreferenceChangeListener and
         * bind the value that is in SharedPreferences to what will show up in the preference summary
         */
        private void bindPreferenceSummaryToValue(Preference preference) {
            // Set the current NewsPreferenceFragment instance to listen for changes to the preference
            // we pass in using
            preference.setOnPreferenceChangeListener(this);

            // Read the current value of the preference stored in the SharedPreferences on the device,
            // and display that in the preference summary
            SharedPreferences preferences =
                    PreferenceManager.getDefaultSharedPreferences(preference.getContext());
            String preferenceString = preferences.getString(preference.getKey(), "");
            onPreferenceChange(preference, preferenceString);
        }

        /**
         * Convert selected date string(i.e. "2017-2-1" into formatted date string(i.e. "2017-02-01")
         *
         * @param dateString is the selected date from the DatePicker
         * @return the formatted date string
         */
        private String formatDate(String dateString) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-M-d");
            Date dateObject = null;
            try {
                dateObject = simpleDateFormat.parse(dateString);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            SimpleDateFormat df= new SimpleDateFormat("yyyy-MM-dd");
            return df.format(dateObject);
        }
    }

    // Go back to the MainActivity when up button in action bar is clicked on.
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
