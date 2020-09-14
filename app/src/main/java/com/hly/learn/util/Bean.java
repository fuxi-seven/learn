package com.hly.learn.util;

public class Bean {

    String mPath;
    String mPublished;
    String mUpdated;
    String mTitle;
    String mSummary;
    String mAuthor;

    public void setPath(String path) {
        mPath = path;
    }

    public void setPublished(String published) {
        mPublished = published;
    }

    public void setUpdated(String updated) {
        mUpdated = updated;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public void setSummary(String summary) {
        mSummary = summary;
    }

    public void setAuthor(String author) {
        mAuthor = author;
    }

    public String toString() {
        return "path: " + mPath + "\n" + "published: " + mPublished + "\n"
                + "updated: " + mUpdated + "\n" + "title: " + mTitle + "\n"
                + "summary: " + mSummary + "\n" + "author: " + mAuthor + "\n";
    }
}
