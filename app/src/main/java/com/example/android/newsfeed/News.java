package com.example.android.newsfeed;

/**
 * An {@link News} object contains information related to a single news.
 */

public class News {

    /** Title of the news */
    private String mTitle;

    /** Section name of the news*/
    private String mSection;

    /** Website URL of the news */
    private String mUrl;

    /**
     * Constructs a new {@link News} object.
     *
     * @param title is the title of the news
     * @param section is the section name of the news
     * @param url is the website URL to find more details about the news
     */
    public News(String title, String section, String url) {
        mTitle = title;
        mSection = section;
        mUrl = url;
    }

    /**
     * Returns the title of the news.
     */
    public String getTitle() {
        return mTitle;
    }

    /**
     * Returns the section name of the news.
     * @return
     */
    public String getSection() {
        return mSection;
    }

    /**
     * Returns the website URL to find more information about the earthquake.
     */
    public String getUrl() {
        return mUrl;
    }

}
