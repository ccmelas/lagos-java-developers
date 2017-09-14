package com.melas.javadevelopers_lagos.rest;

import com.melas.javadevelopers_lagos.models.ApiResponse;

import retrofit2.Call;

import retrofit2.http.GET;

import retrofit2.http.Query;

/**
 * Created by melas on 5/1/17.
 */

public interface ApiInterface {
    @GET("search/users")
    Call<ApiResponse> getDevelopers(@Query(value = "q", encoded = true) String query);
}
