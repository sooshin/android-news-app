package com.example.android.newsfeed;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;

import com.example.android.newsfeed.utils.Constants;

public final class NewsPreferences {

    /**
     * Get Uri.Builder based on stored SharedPreferences.
     * @param context Context used to access SharedPreferences
     * @return Uri.Builder
     */
    public static Uri.Builder getPreferredUri(Context context) {
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);

        // getString retrieves a String value from the preferences. The second parameter is the
        // default value for this preference.
        String numOfItems = sharedPrefs.getString(
                context.getString(R.string.settings_number_of_items_key),
                context.getString(R.string.settings_number_of_items_default));

        // Get the information from SharedPreferences and check for the value associated with the key
        String orderBy = sharedPrefs.getString(
                context.getString(R.string.settings_order_by_key),
                context.getString(R.string.settings_order_by_default));

        // Get the orderDate information from SharedPreferences and check for the value associated with the key
        String orderDate = sharedPrefs.getString(
                context.getString(R.string.settings_order_date_key),
                context.getString(R.string.settings_order_date_default));

        // Get the fromDate information from SharedPreferences and check for the value associated with the key
        String fromDate = sharedPrefs.getString(
                context.getString(R.string.settings_from_date_key),
                context.getString(R.string.settings_from_date_default));

        // Parse breaks apart the URI string that is passed into its parameter
        Uri baseUri = Uri.parse(Constants.NEWS_REQUEST_URL);

        // buildUpon prepares the baseUri that we just parsed so we can add query parameters to it
        Uri.Builder uriBuilder = baseUri.buildUpon();

        // Append query parameter and its value. (e.g. the 'show-tag=contributor')
        uriBuilder.appendQueryParameter(context.getString(R.string.q), "");
        uriBuilder.appendQueryParameter(context.getString(R.string.order_by), orderBy);
        uriBuilder.appendQueryParameter(context.getString(R.string.page_size), numOfItems);
        uriBuilder.appendQueryParameter(context.getString(R.string.order_date), orderDate);
        uriBuilder.appendQueryParameter(context.getString(R.string.from_date), fromDate);
        uriBuilder.appendQueryParameter(context.getString(R.string.show_fields), context.getString(R.string.thumbnail_and_trail_text));
        uriBuilder.appendQueryParameter(context.getString(R.string.format),context.getString(R.string.json));
        uriBuilder.appendQueryParameter(context.getString(R.string.show_tags), context.getString(R.string.contributor));
        uriBuilder.appendQueryParameter(context.getString(R.string.api_key), context.getString(R.string.test)); // Use your API key when API rate limit exceeded

        return uriBuilder;
    }

    /**
     * Returns String Url for query
     * @param context Context used to access getPreferredUri method
     * @param section News section
     */
    public static String getPreferredUrl(Context context, String section) {
        Uri.Builder uriBuilder = getPreferredUri(context);
        return uriBuilder.appendQueryParameter(context.getString(R.string.section), section).toString();
    }
}
