package com.example.android.newsfeed.utils;

/**
 * Created by sj on 12/19/2017.
 */

public class Constants {

    /**
     * Create a private constructor because no one should ever create a {@link Constants} object.
     */
    private Constants() {
    }

    /**  Extract the key associated with the JSONObject */
    public static final String JSON_KEY_RESPONSE = "response";
    public static final String JSON_KEY_RESULTS = "results";
    public static final String JSON_KEY_WEB_TITLE = "webTitle";
    public static final String JSON_KEY_SECTION_NAME = "sectionName";
    public static final String JSON_KEY_WEB_PUBLICATION_DATE = "webPublicationDate";
    public static final String JSON_KEY_WEB_URL = "webUrl";
    public static final String JSON_KEY_TAGS = "tags";
    public static final String JSON_KEY_FIELDS = "fields";
    public static final String JSON_KEY_THUMBNAIL = "thumbnail";
    public static final String JSON_KEY_TRAIL_TEXT = "trailText";

    /** URL for news data from the guardian data set */
    public static final String NEWS_REQUEST_URL = "https://content.guardianapis.com/search";
}
