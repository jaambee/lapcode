
package com.reframe.lapp.models;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class ProfessorScore {

    @SerializedName("evaluationId")
    private String mEvaluationId;
    @SerializedName("feedBack")
    private String mFeedBack;
    @SerializedName("rate")
    private Integer mRate;

    public String getEvaluationId() {
        return mEvaluationId;
    }

    public void setEvaluationId(String evaluationId) {
        mEvaluationId = evaluationId;
    }

    public String getFeedBack() {
        return mFeedBack;
    }

    public void setFeedBack(String feedBack) {
        mFeedBack = feedBack;
    }

    public Integer getRate() {
        return mRate;
    }

    public void setRate(Integer rate) {
        mRate = rate;
    }

}
