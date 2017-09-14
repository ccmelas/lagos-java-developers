package com.melas.javadevelopers_lagos.models;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by melas on 9/14/17.
 */

public class Developer implements Parcelable {
    @SerializedName("avatar_url")
    private String avatar;
    @SerializedName("login")
    private String username;
    @SerializedName("html_url")
    private String githubURL;

    public Developer(String avatar, String username, String githubURL) {
        this.avatar = avatar;
        this.username = username;
        this.githubURL = githubURL;
    }

    protected Developer(Parcel in) {
        avatar = in.readString();
        username = in.readString();
        githubURL = in.readString();
    }

    public static final Creator<Developer> CREATOR = new Creator<Developer>() {
        @Override
        public Developer createFromParcel(Parcel in) {
            return new Developer(in);
        }

        @Override
        public Developer[] newArray(int size) {
            return new Developer[size];
        }
    };

    public String getAvatar() {
        return avatar;
    }

    public String getUsername() {
        return username;
    }

    public String getGithubURL() {
        return githubURL;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(avatar);
        parcel.writeString(username);
        parcel.writeString(githubURL);
    }

}
