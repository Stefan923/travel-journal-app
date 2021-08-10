package me.stefan923.traveljournal.model;

import com.google.gson.annotations.SerializedName;

public class WeatherInfo {

    @SerializedName("description")
    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
