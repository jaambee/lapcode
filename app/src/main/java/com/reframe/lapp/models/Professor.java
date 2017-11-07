
package com.reframe.lapp.models;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class Professor {

    @SerializedName("id")
    private String mId;
    @SerializedName("name")
    private String mName;
    @SerializedName("picture")
    private String mPicture;
    @SerializedName("specialty")
    private String mSpecialty;
    @SerializedName("evaluated")
    private Boolean mEvaluated;

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

    public String getPicture() {
        return mPicture;
    }

    public void setPicture(String picture) {
        mPicture = picture;
    }

    public String getSpecialty() {
        return mSpecialty;
    }

    public void setSpecialty(String specialty) {
        mSpecialty = specialty;
    }

    public Boolean getEvaluated() {
        return mEvaluated;
    }

    public void setEvaluated(Boolean evaluated) {
        mEvaluated = evaluated;
    }

}
