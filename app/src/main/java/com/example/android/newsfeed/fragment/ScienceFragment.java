package com.example.android.newsfeed.fragment;

import android.content.Loader;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;

import com.example.android.newsfeed.News;
import com.example.android.newsfeed.NewsLoader;
import com.example.android.newsfeed.R;
import com.example.android.newsfeed.utils.Constants;

import java.util.List;

/**
 * The ScienceFragment is a {@link BaseArticlesFragment} subclass that
 * reuses methods of the parent class by passing the specific type of article to be fetched.
 */
public class ScienceFragment extends BaseArticlesFragment {

    private static final String LOG_TAG = ScienceFragment.class.getName();

    @Override
    public Loader<List<News>> onCreateLoader(int i, Bundle bundle) {
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getActivity());

        // getString retrieves a String value from the preferences. The second parameter is the
        // default value for this preference.
        String numOfItems = sharedPrefs.getString(
                getString(R.string.settings_number_of_items_key),
                getString(R.string.settings_number_of_items_default));

        // Get the information from SharedPreferences and check for the value associated with the key
        String orderBy = sharedPrefs.getString(
                getString(R.string.settings_order_by_key),
                getString(R.string.settings_order_by_default));

        // Get the orderDate information from SharedPreferences and check for the value associated with the key
        String orderDate = sharedPrefs.getString(
                getString(R.string.settings_order_date_key),
                getString(R.string.settings_order_date_default));

        // Get the fromDate information from SharedPreferences and check for the value associated with the key
        String fromDate = sharedPrefs.getString(
                getString(R.string.settings_from_date_key),
                getString(R.string.settings_from_date_default));

        // Parse breaks apart the URI string that is passed into its parameter
        Uri baseUri = Uri.parse(Constants.NEWS_REQUEST_URL);

        // buildUpon prepares the baseUri that we just parsed so we can add query parameters to it
        Uri.Builder uriBuilder = baseUri.buildUpon();

        // Append query parameter and its value. (e.g. the 'show-tag=contributor')
        uriBuilder.appendQueryParameter(getString(R.string.q), "");
        uriBuilder.appendQueryParameter(getString(R.string.section), getString(R.string.science));
        uriBuilder.appendQueryParameter(getString(R.string.order_by), orderBy);
        uriBuilder.appendQueryParameter(getString(R.string.page_size), numOfItems);
        uriBuilder.appendQueryParameter(getString(R.string.order_date), orderDate);
        uriBuilder.appendQueryParameter(getString(R.string.from_date), fromDate);
        uriBuilder.appendQueryParameter(getString(R.string.show_fields), getString(R.string.thumbnail_and_trail_text));
        uriBuilder.appendQueryParameter(getString(R.string.format),getString(R.string.json));
        uriBuilder.appendQueryParameter(getString(R.string.show_tags), getString(R.string.contributor));
        uriBuilder.appendQueryParameter(getString(R.string.api_key), getString(R.string.test));

        Log.e(LOG_TAG,uriBuilder.toString());

        // Create a new loader for the given URL
        return new NewsLoader(getActivity(), uriBuilder.toString());
    }
}
