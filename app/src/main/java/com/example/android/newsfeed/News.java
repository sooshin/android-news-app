package com.example.android.newsfeed;

/**
 * An {@link News} object contains information related to a single news.
 */

public class News {

    /** Title of the article */
    private String mTitle;

    /** Section name of the article*/
    private String mSection;

    /** Author name in the article */
    private String mAuthor;

    /** Web publication date of the article */
    private String mDate;

    /** Website URL of the article */
    private String mUrl;

    /** Thumbnail of the article */
    private String mThumbnail;

    /** TrailText of the article with string type Html */
    private String mTrailTextHtml;

    /**
     * Constructs a new {@link News} object.
     *
     * @param title is the title of the article
     * @param section is the section name of the article
     * @param author is author name in article
     * @param date is the web publication date of the article
     * @param url is the website URL to find more details about the article
     * @param thumbnail is the thumbnail of the article
     * @param trailText is trail text of the article with string type Html
     */
    public News(String title, String section, String author, String date, String url, String thumbnail, String trailText) {
        mTitle = title;
        mSection = section;
        mAuthor = author;
        mDate = date;
        mUrl = url;
        mThumbnail = thumbnail;
        mTrailTextHtml = trailText;
    }

    /**
     * Returns the title of the article
     */
    public String getTitle() {
        return mTitle;
    }

    /**
     * Returns the section name of the article.
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
     * Returns the web publication date of the article.
     */
    public String getDate() {
        return mDate;
    }

    /**
     * Returns the website URL to find more information about the news.
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

    /**
     * Returns the TrailText of the article with string type Html
     */
    public String getTrailTextHtml() {
        return mTrailTextHtml;
    }
}
