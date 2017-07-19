
package com.reframe.lapp.models;

import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class EvolutionData {

    @SerializedName("chart")
    private Chart mChart;
    @SerializedName("name")
    private String mName;
    @SerializedName("tries")
    private Long mTries;
    @SerializedName("value")
    private Long mValue;

    public Chart getChart() {
        return mChart;
    }

    public void setChart(Chart chart) {
        mChart = chart;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public Long getTries() {
        return mTries;
    }

    public void setTries(Long tries) {
        mTries = tries;
    }

    public Long getValue() {
        return mValue;
    }

    public void setValue(Long value) {
        mValue = value;
    }

}
