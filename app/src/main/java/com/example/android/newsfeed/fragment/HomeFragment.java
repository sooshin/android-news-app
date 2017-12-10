package com.example.android.newsfeed.fragment;

import android.app.Fragment;
import android.app.LoaderManager;
import android.content.Loader;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.newsfeed.News;
import com.example.android.newsfeed.NewsLoader;
import com.example.android.newsfeed.R;
import com.example.android.newsfeed.adapter.NewsAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sj on 12/7/2017.
 */

public class HomeFragment extends Fragment
        implements LoaderManager.LoaderCallbacks<List<News>> {

    public static final String LOG_TAG = HomeFragment.class.getName();

    /** URL for news data from the Guardian data set */
    private static final String NEWS_REQUEST_URL =
            "http://content.guardianapis.com/search?q=debates&api-key=test";

    /** Constant value for the earthquake loader ID. */
    private static final int NEWS_LOADER_ID = 1;

    /** Adapter for the list of news */
    private  NewsAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        // Find a reference to the {@link RecyclerView} in the layout
        RecyclerView recyclerView = rootView.findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setHasFixedSize(true);

        // Set the layoutManager on the {@link RecyclerView}
        recyclerView.setLayoutManager(layoutManager);

        // Create a new adapter that takes an empty list of news as input
        mAdapter = new NewsAdapter(getActivity(), new ArrayList<News>());

        // Set the adapter on the {@link recyclerView}
        recyclerView.setAdapter(mAdapter);

        // Get a reference to the LoaderManager, in order to interact with loaders.
        LoaderManager loaderManager = getLoaderManager();

        // Initialize the loader with the NEWS_LOADER_ID
        loaderManager.initLoader(NEWS_LOADER_ID, null, this);

        return rootView;
    }

    @Override
    public Loader<List<News>> onCreateLoader(int i, Bundle bundle) {
        // Create a new loader for the given URL
        return new NewsLoader(getActivity(), NEWS_REQUEST_URL);
    }

    @Override
    public void onLoadFinished(Loader<List<News>> loader, List<News> newsData) {
        // Clear the adapter of previous news data
        mAdapter.clearAll();

        // If there is a valid list of {@link News}, then add them to the adapter's
        // data set. This will trigger the recyclerView to update.
        if (newsData != null && !newsData.isEmpty()) {
            mAdapter.addAll(newsData);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<News>> loader) {
        // Loader reset, so we can clear out our existing data.
        mAdapter.clearAll();
    }
}
