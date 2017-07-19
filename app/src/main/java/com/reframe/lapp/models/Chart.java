
package com.reframe.lapp.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import javax.annotation.Generated;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class Chart {

    @SerializedName("data")
    private List<DatumChart> mData;
    @SerializedName("ideal")
    private Long mIdeal;
    @SerializedName("type")
    private String mType;

    public List<DatumChart> getData() {
        return mData;
    }

    public void setData(List<DatumChart> data) {
        mData = data;
    }

    public Long getIdeal() {
        return mIdeal;
    }

    public void setIdeal(Long ideal) {
        mIdeal = ideal;
    }

    public String getType() {
        return mType;
    }

    public void setType(String type) {
        mType = type;
    }

}
