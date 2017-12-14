package com.example.android.newsfeed.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.android.newsfeed.News;
import com.example.android.newsfeed.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * A {@link NewsAdapter} can provide a card item layout for each news in the data source
 * ( a list of {@link News} objects).
 */

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {
    private Context mContext;
    private List<News> mNewsList;

    /**
     * Constructs a new {@link NewsAdapter}
     * @param context of the app
     * @param newsList is the list of news, which is the data source of the adapter
     */
    public NewsAdapter(Context context, List<News> newsList) {
        mContext = context;
        mNewsList = newsList;
    }

    @Override
    public NewsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_card_item, null);
        return new ViewHolder(v);
    }

    @Override
    public int getItemCount() {
        return mNewsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView titleTextView;
        private TextView sectionTextView;
        private TextView authorTextView;
        private TextView dateTextView;
        private ImageView thumbnailImageView;
        private ImageView shareImageView;
        private TextView trailTextView;
        private CardView cardView;

        ViewHolder(View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.title_card);
            sectionTextView = itemView.findViewById(R.id.section_card);
            authorTextView = itemView.findViewById(R.id.author_card);
            dateTextView = itemView.findViewById(R.id.date_card);
            thumbnailImageView = itemView.findViewById(R.id.thumbnail_image_card);
            shareImageView = itemView.findViewById(R.id.share_image_card);
            trailTextView = itemView.findViewById(R.id.trail_text_card);
            cardView = itemView.findViewById(R.id.card_view);
        }
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // Find the current news that was clicked on
        final News currentNews = mNewsList.get(position);

        holder.titleTextView.setText(currentNews.getTitle());
        holder.sectionTextView.setText(currentNews.getSection());
        holder.authorTextView.setText(currentNews.getAuthor());

        // Format the date string and set the formatted date string on the textView
        //holder.dateTextView.setText(formatDate(currentNews.getDate()));

        holder.dateTextView.setText(getTimeDifference(formatDate(currentNews.getDate())));

        // Get string of the trailTextHTML and convert Html text to plain text
        // and set the plain text on the textView
        String trailTextHTML = currentNews.getTrailTextHtml();
        holder.trailTextView.setText(Html.fromHtml(Html.fromHtml(trailTextHTML).toString()));

        // Set an OnClickListener to open a website with more information about the selected article
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Convert the String URL into a URI object (to pass into the Intent constructor)
                Uri newsUri = Uri.parse(currentNews.getUrl());

                // Create a new intent to view the news URI
                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, newsUri);

                // Send the intent to launch a new activity
                mContext.startActivity(websiteIntent);
            }
        });

        Glide.with(mContext.getApplicationContext())
                .load(currentNews.getThumbnail())
                .into(holder.thumbnailImageView);

        holder.shareImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shareData(currentNews);
            }
        });
    }

    private void shareData(News news) {
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT,
                news.getTitle() + " : " + news.getUrl());
        mContext.startActivity(Intent.createChooser(sharingIntent, "Share Article"));
    }

    /**
     *  Clear all data (a list of {@link News} objects)
     */
    public void clearAll() {
        mNewsList.clear();
        notifyDataSetChanged();
    }

    /**
     * Add  a list of {@link News}
     * @param newsList is the list of news, which is the data source of the adapter
     */
    public void addAll(List<News> newsList) {
        mNewsList.clear();
        mNewsList.addAll(newsList);
        notifyDataSetChanged();
    }

    /**
     * Convert date and time in UTC into a more readable representation
     *
     * @param dateString is the web publication date of the news
     * @return the formatted date string (i.e "2:15 AM Jan 1, 2000") from a String dateString
     */
    private String formatDate(String dateString) {
        // Parse the dateString into a Date object
        SimpleDateFormat simpleDateFormat =
                new SimpleDateFormat("yyyy-MM-dd'T'kk:mm:ss'Z'");
        Date dateObject = null;
        try {
            dateObject = simpleDateFormat.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        // Initialize a SimpleDateFormat instance and configure it to provide a more readable
        // representation according to the given format.
        simpleDateFormat = new SimpleDateFormat("h:mm a MMM d, yyyy ");
        return simpleDateFormat.format(dateObject);
    }

    private static long getDateInMillis(String dateString) {
        SimpleDateFormat simpleDateFormat =
                new SimpleDateFormat("h:mm a MMM d, yyyy ");
        long dateInMillis = 0;
        Date dateObject;
        try {
            dateObject = simpleDateFormat.parse(dateString);
            dateInMillis = dateObject.getTime();
            return dateInMillis;
        } catch (ParseException e) {
            Log.e("Problem parsing date", e.getMessage());
            e.printStackTrace();
        }
        return 0;
    }

    private CharSequence getTimeDifference(String dateString) {
        long currentTime = System.currentTimeMillis();
        long publicationTime = getDateInMillis(dateString);
        return DateUtils.getRelativeTimeSpanString(publicationTime, currentTime,
                DateUtils.SECOND_IN_MILLIS);
    }
}
