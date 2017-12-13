package com.example.android.newsfeed;

/**
 * An {@link News} object contains information related to a single news.
 */

public class News {

    /** Title of the news */
    private String mTitle;

    /** Section name of the news*/
    private String mSection;

    /** Author name in the article */
    private String mAuthor;

    /** Web publication date of the news */
    private String mDate;

    /** Website URL of the news */
    private String mUrl;

    /** Thumbnail of the article */
    private String mThumbnail;

    /**
     * Constructs a new {@link News} object.
     *
     * @param title is the title of the news
     * @param section is the section name of the news
     * @param author is author name in article
     * @param date is the web publication date of the news
     * @param url is the website URL to find more details about the news
     */
    public News(String title, String section, String author, String date, String url, String thumbnail) {
        mTitle = title;
        mSection = section;
        mAuthor = author;
        mDate = date;
        mUrl = url;
        mThumbnail = thumbnail;
    }

    /**
     * Returns the title of the news.
     */
    public String getTitle() {
        return mTitle;
    }

    /**
     * Returns the section name of the news.
     */
    public String getSection() {
        return mSection;
    }

    /**
     * Returns the author name of the article.
     */
    public String getAuthor() {
        return mAuthor;
    }
    /**
     * Returns the web publication date of the news.
     */
    public String getDate() {
        return mDate;
    }

    /**
     * Returns the website URL to find more information about the earthquake.
     */
    public String getUrl() {
        return mUrl;
    }

    /**
     * Returns the thumbnail of the article
     */
    public String getThumbnail() {
        return mThumbnail;
    }

}
