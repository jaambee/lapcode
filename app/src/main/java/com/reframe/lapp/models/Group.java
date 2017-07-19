
package com.reframe.lapp.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import javax.annotation.Generated;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class Group {

    @SerializedName("available")
    private Boolean mAvailable;
    @SerializedName("exercises")
    private List<Exercise> mExercises;
    @SerializedName("id")
    private String mId;
    @SerializedName("name")
    private String mName;

    public Boolean getAvailable() {
        return mAvailable;
    }

    public void setAvailable(Boolean available) {
        mAvailable = available;
    }

    public List<Exercise> getExercises() {
        return mExercises;
    }

    public void setExercises(List<Exercise> exercises) {
        mExercises = exercises;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

}
