package com.example.android.newsfeed.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.newsfeed.News;
import com.example.android.newsfeed.R;
import com.example.android.newsfeed.adapter.NewsAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sj on 12/7/2017.
 */

public class HomeFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        // Find a reference to the {@link RecyclerView} in the layout
        RecyclerView recyclerView = rootView.findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setHasFixedSize(true);

        // Set the layoutManager on the {@link RecyclerView}
        recyclerView.setLayoutManager(layoutManager);

        // Create a list of news
        List<News> news = new ArrayList<>();

        // Add news to the list of news
        news.add(new News("Paul Keating says assisted dying 'unacceptable' as " +
                "Victoria debates law", "https://www.theguardian.com/society/2017/oct" +
                "/19/paul-keating-says-assisted-dying-unacceptable-as-victoria-debates-law"));
        news.add(new News("Universities are part of the solution to dysfunctional " +
                "Brexit debates", "https://www.theguardian.com/science/occams-corner" +
                "/2017/nov/06/universities-are-part-of-the-solution-to-dysfunctional-brexit-debates"));

        // Make the recyclerView use the NewsAdapter
        recyclerView.setAdapter(new NewsAdapter(getActivity(), news));
        return rootView;
    }
}
