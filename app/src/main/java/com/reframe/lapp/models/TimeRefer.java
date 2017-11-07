
package com.reframe.lapp.models;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class TimeRefer {

    @SerializedName("beginCut")
    private Integer mBeginCut;
    @SerializedName("endCut")
    private Integer mEndCut;

    public Integer getBeginCut() {
        return mBeginCut;
    }

    public void setBeginCut(Integer beginCut) {
        mBeginCut = beginCut;
    }

    public Integer getEndCut() {
        return mEndCut;
    }

    public void setEndCut(Integer endCut) {
        mEndCut = endCut;
    }

}
