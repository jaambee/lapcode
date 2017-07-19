
package com.reframe.lapp.models;

import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class Datum {

    @SerializedName("name")
    private String mName;
    @SerializedName("points")
    private Long mPoints;

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public Long getPoints() {
        return mPoints;
    }

    public void setPoints(Long points) {
        mPoints = points;
    }

}
