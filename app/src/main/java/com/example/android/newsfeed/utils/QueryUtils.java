/*
 * MIT License
 *
 * Copyright (c) 2018 Soojeong Shin
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.example.android.newsfeed.utils;

import android.text.TextUtils;
import android.util.Log;

import com.example.android.newsfeed.News;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Helper methods related to requesting and receiving news data from Guardian.
 */
public class QueryUtils {

    /** Tag for the log messages */
    private static final String LOG_TAG = QueryUtils.class.getSimpleName();

    /**
     * Create a private constructor because no one should ever create a {@link QueryUtils} object.
     */
    private QueryUtils() {
    }

    /**
     * Query the Guardian data set and return a list of {@link News} objects.
     */
    public static List<News> fetchNewsData(String requestUrl) {
        // Create URL object
        URL url = createUrl(requestUrl);

        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }

        // Extract relevant fields from the JSON response and create a list of {@link News}
        List<News> newsList = extractFeatureFromJSON(jsonResponse);

        // Return the list of {@link News}
        return newsList;
    }

    /**
     * Returns new URL object from the given string URL.
     */
    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Problem building the URL.", e);
        }
        return url;
    }

    /**
     * Make an HTTP request to the given URL and return a String as the response.
     */
    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        // If the URL is null, then return early.
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(Constants.READ_TIMEOUT /* milliseconds */);
            urlConnection.setConnectTimeout(Constants.CONNECT_TIMEOUT /* milliseconds */);
            urlConnection.setRequestMethod(Constants.REQUEST_METHOD_GET);
            urlConnection.connect();

            // If the request was successful (response code 200),
            // then read the input stream and parse the response.
            if (urlConnection.getResponseCode() == Constants.SUCCESS_RESPONSE_CODE) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the news JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                // Closing the input stream could throw an IOException, which is why
                // the makeHttpRequest(URL url) method signature specifies that an IOException
                // could be thrown.
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    /**
     * Convert the {@link InputStream} into a String which contains the
     * whole JSON response from the server.
     */
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    /**
     * Return a list of {@link News} objects that has been built up from
     * parsing the given JSON response.
     */
    private static List<News> extractFeatureFromJSON(String newsJSON) {
        // If the JSON string is empty or null, then return early.
        if (TextUtils.isEmpty(newsJSON)) {
            return null;
        }
        // Create an empty ArrayList that we can start adding news to
        List<News> newsList = new ArrayList<>();

        // Try to parse the JSON response string. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        try {
            // Create a JSONObject from the JSON response string
            JSONObject baseJsonResponse = new JSONObject(newsJSON);

            // Extract the JSONObject associated with the key called "response"
            JSONObject responseJsonObject = baseJsonResponse.getJSONObject(Constants.JSON_KEY_RESPONSE);

            // Extract the JSONArray associated with the key called "results"
            JSONArray resultsArray = responseJsonObject.getJSONArray(Constants.JSON_KEY_RESULTS);

            // For each element in the resultsArray, create a {@link News} object
            for (int i = 0; i < resultsArray.length(); i++) {

                // Get a single news at position i within the list of news
                JSONObject currentNews = resultsArray.getJSONObject(i);
                // For a given news, extract the value for the key called "webTitle"
                String webTitle = currentNews.getString(Constants.JSON_KEY_WEB_TITLE);
                // For a given news, extract the value for the key called "sectionName"
                String sectionName = currentNews.getString(Constants.JSON_KEY_SECTION_NAME);
                // For a given news, extract the value for the key called "webPublicationDate"
                String webPublicationDate = currentNews.getString(Constants.JSON_KEY_WEB_PUBLICATION_DATE);
                // For a given news, extract the value for the key called "webUrl"
                String webUrl = currentNews.getString(Constants.JSON_KEY_WEB_URL);

                // For a given news, if it contains the key called "tags", extract JSONArray
                // associated with the key "tags"
                String author = null;
                if (currentNews.has(Constants.JSON_KEY_TAGS)) {
                    // Extract the JSONArray associated with the key called "tags"
                    JSONArray tagsArray = currentNews.getJSONArray(Constants.JSON_KEY_TAGS);
                    if (tagsArray.length() != 0) {
                        // Extract the first JSONObject in the tagsArray
                        JSONObject firstTagsItem = tagsArray.getJSONObject(0);
                        // Extract the value for the key called "webTitle"
                        author = firstTagsItem.getString(Constants.JSON_KEY_WEB_TITLE);
                    }
                }

                // For a given news, if it contains the key called "fields", extract JSONObject
                // associated with the key "fields"
                String thumbnail = null;
                String trailText = null;
                if (currentNews.has(Constants.JSON_KEY_FIELDS)) {
                    // Extract the JSONObject associated with the key called "fields"
                    JSONObject fieldsObject = currentNews.getJSONObject(Constants.JSON_KEY_FIELDS);
                    // If there is the key called "thumbnail", extract the value for the key called "thumbnail"
                    if (fieldsObject.has(Constants.JSON_KEY_THUMBNAIL)) {
                        thumbnail = fieldsObject.getString(Constants.JSON_KEY_THUMBNAIL);
                    }
                    // If there is the key called "trailText", extract the value for the key called "trailText"
                    if (fieldsObject.has(Constants.JSON_KEY_TRAIL_TEXT)) {
                        trailText = fieldsObject.getString(Constants.JSON_KEY_TRAIL_TEXT);
                    }
                }

                // Create a new {@link News} object with the title and url from the JSON response.
                News news = new News(webTitle, sectionName, author, webPublicationDate, webUrl, thumbnail, trailText);

                // Add the new {@link News} to list of newsList.
                newsList.add(news);
            }
        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e(LOG_TAG, "Problem parsing the news JSON results", e);
        }

        // Return the list of news
        return newsList;
    }
}
