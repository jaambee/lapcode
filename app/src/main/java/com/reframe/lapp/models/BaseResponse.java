
package com.reframe.lapp.models;

import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class BaseResponse {

    @SerializedName("data")
    private Object mData;
    @SerializedName("message")
    private String mMessage;
    @SerializedName("name")
    private String mName;
    @SerializedName("type")
    private String mType;

    public Object getData() {
        return mData;
    }

    public void setData(Object data) {
        mData = data;
    }

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getType() {
        return mType;
    }

    public void setType(String type) {
        mType = type;
    }

}
