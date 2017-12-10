package com.example.android.newsfeed.fragment;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.newsfeed.News;
import com.example.android.newsfeed.R;
import com.example.android.newsfeed.adapter.NewsAdapter;
import com.example.android.newsfeed.utils.QueryUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sj on 12/7/2017.
 */

public class HomeFragment extends Fragment {

    public static final String LOG_TAG = HomeFragment.class.getName();

    /** URL for news data from the Guardian data set */
    private static final String NEWS_REQUEST_URL =
            "http://content.guardianapis.com/search?q=debates&api-key=test";

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

        // Start the AsyncTask to fetch the news data
        NewsAsyncTask task = new NewsAsyncTask();
        task.execute(NEWS_REQUEST_URL);

        return rootView;
    }

    private class NewsAsyncTask extends AsyncTask<String, Void, List<News>> {

        @Override
        protected List<News> doInBackground(String... urls) {
            // Don't perform the request if there are no URLs, or the first URL is null
            if (urls.length < 1 || urls[0] == null) {
                return null;
            }

            List<News> result = QueryUtils.fetchNewsData(urls[0]);
            return result;
        }

        @Override
        protected void onPostExecute(List<News> data) {
            // Clear the adapter of previous news data
            mAdapter.clearAll();

            // If there is a valid list of {@link News}, then add them to the adapter's
            // data set. This will trigger the recyclerView to update.
            if (data != null && !data.isEmpty()) {
                mAdapter.addAll(data);
            }
        }
    }
}
