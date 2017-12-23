package com.example.android.newsfeed.fragment;

import android.content.Loader;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import com.example.android.newsfeed.News;
import com.example.android.newsfeed.NewsLoader;
import com.example.android.newsfeed.R;
import com.example.android.newsfeed.utils.Constants;

import java.util.List;

/**
 * The SportFragment is a {@link BaseArticlesFragment} subclass that
 * reuses methods of the parent class by passing the specific type of article to be fetched.
 */
public class SportFragment extends BaseArticlesFragment {

    private static final String LOG_TAG = SportFragment.class.getName();

    @Override
    public Loader<List<News>> onCreateLoader(int i, Bundle bundle) {
        // Parse breaks apart the URI string that is passed into its parameter
        Uri baseUri = Uri.parse(Constants.NEWS_REQUEST_URL);

        // buildUpon prepares the baseUri that we just parsed so we can add query parameters to it
        Uri.Builder uriBuilder = baseUri.buildUpon();

        // Append query parameter and its value. (e.g. the 'show-tag=contributor')
        uriBuilder.appendQueryParameter(getString(R.string.q), "");
        uriBuilder.appendQueryParameter(getString(R.string.order_by), getString(R.string.newest));
        uriBuilder.appendQueryParameter(getString(R.string.section), getString(R.string.sport));
        uriBuilder.appendQueryParameter(getString(R.string.show_fields), getString(R.string.thumbnail_and_trail_text));
        uriBuilder.appendQueryParameter(getString(R.string.format),getString(R.string.json));
        uriBuilder.appendQueryParameter(getString(R.string.show_tags), getString(R.string.contributor));
        uriBuilder.appendQueryParameter(getString(R.string.api_key), getString(R.string.test));

        Log.e(LOG_TAG,uriBuilder.toString());

        // Create a new loader for the given URL
        return new NewsLoader(getActivity(), uriBuilder.toString());
    }
}
