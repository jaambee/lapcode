
package com.reframe.lapp.models;

import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class DatumChart {

    @SerializedName("x")
    private Long mX;
    @SerializedName("y")
    private Long mY;

    public Long getX() {
        return mX;
    }

    public void setX(Long x) {
        mX = x;
    }

    public Long getY() {
        return mY;
    }

    public void setY(Long y) {
        mY = y;
    }

}
