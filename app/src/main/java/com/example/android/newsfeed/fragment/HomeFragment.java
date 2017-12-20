package com.example.android.newsfeed.fragment;

import android.content.Loader;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.newsfeed.News;

import java.util.List;

/**
 * The HomeFragment is a {@link BaseArticlesFragment} subclass that
 * reuses methods of the parent class
 */
public class HomeFragment extends BaseArticlesFragment {

    public static final String LOG_TAG = HomeFragment.class.getName();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public Loader<List<News>> onCreateLoader(int i, Bundle bundle) {
        return super.onCreateLoader(i, bundle);
    }

    @Override
    public void onLoadFinished(Loader<List<News>> loader, List<News> newsData) {
        super.onLoadFinished(loader, newsData);
    }

    @Override
    public void onLoaderReset(Loader<List<News>> loader) {
        super.onLoaderReset(loader);
    }

}
