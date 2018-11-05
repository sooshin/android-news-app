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

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;

import com.example.android.newsfeed.utils.Constants;

import static com.example.android.newsfeed.utils.Constants.API_KEY;
import static com.example.android.newsfeed.utils.Constants.API_KEY_PARAM;
import static com.example.android.newsfeed.utils.Constants.FORMAT;
import static com.example.android.newsfeed.utils.Constants.FORMAT_PARAM;
import static com.example.android.newsfeed.utils.Constants.FROM_DATE_PARAM;
import static com.example.android.newsfeed.utils.Constants.ORDER_BY_PARAM;
import static com.example.android.newsfeed.utils.Constants.ORDER_DATE_PARAM;
import static com.example.android.newsfeed.utils.Constants.PAGE_SIZE_PARAM;
import static com.example.android.newsfeed.utils.Constants.QUERY_PARAM;
import static com.example.android.newsfeed.utils.Constants.SECTION_PARAM;
import static com.example.android.newsfeed.utils.Constants.SHOW_FIELDS;
import static com.example.android.newsfeed.utils.Constants.SHOW_FIELDS_PARAM;
import static com.example.android.newsfeed.utils.Constants.SHOW_TAGS;
import static com.example.android.newsfeed.utils.Constants.SHOW_TAGS_PARAM;

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
        uriBuilder.appendQueryParameter(QUERY_PARAM, "");
        uriBuilder.appendQueryParameter(ORDER_BY_PARAM, orderBy);
        uriBuilder.appendQueryParameter(PAGE_SIZE_PARAM, numOfItems);
        uriBuilder.appendQueryParameter(ORDER_DATE_PARAM, orderDate);
        uriBuilder.appendQueryParameter(FROM_DATE_PARAM, fromDate);
        uriBuilder.appendQueryParameter(SHOW_FIELDS_PARAM, SHOW_FIELDS);
        uriBuilder.appendQueryParameter(FORMAT_PARAM, FORMAT);
        uriBuilder.appendQueryParameter(SHOW_TAGS_PARAM, SHOW_TAGS);
        uriBuilder.appendQueryParameter(API_KEY_PARAM, API_KEY); // Use your API key when API rate limit exceeded

        return uriBuilder;
    }

    /**
     * Returns String Url for query
     * @param context Context used to access getPreferredUri method
     * @param section News section
     */
    public static String getPreferredUrl(Context context, String section) {
        Uri.Builder uriBuilder = getPreferredUri(context);
        return uriBuilder.appendQueryParameter(SECTION_PARAM, section).toString();
    }
}
