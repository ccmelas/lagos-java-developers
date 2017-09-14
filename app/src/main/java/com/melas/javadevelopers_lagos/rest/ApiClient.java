package com.melas.javadevelopers_lagos.rest;

import com.melas.javadevelopers_lagos.utilities.Configurations;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by melas on 5/1/17.
 */

public class ApiClient {
    private static Retrofit retrofit = null;

    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder().baseUrl(Configurations.GITHUB_API_URL).addConverterFactory(GsonConverterFactory.create()).build();
        }
        return retrofit;
    }
}
