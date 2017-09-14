package com.melas.javadevelopers_lagos.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by melas on 9/14/17.
 */

public class ApiResponse {
    @SerializedName("items")
    public ArrayList<Developer> developers;

    public ApiResponse(ArrayList<Developer> developers) {
        this.developers = developers;
    }

    public ArrayList<Developer> getDevelopers() {
        return developers;
    }
}
