
package com.reframe.lapp.models;

import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class Response {

    @SerializedName("Bucket")
    private String mBucket;
    @SerializedName("ETag")
    private String mETag;
    @SerializedName("Key")
    private String mKey;
    @SerializedName("Location")
    private String mLocation;

    public String getBucket() {
        return mBucket;
    }

    public void setBucket(String bucket) {
        mBucket = bucket;
    }

    public String getETag() {
        return mETag;
    }

    public void setETag(String eTag) {
        mETag = eTag;
    }

    public String getKey() {
        return mKey;
    }

    public void setKey(String key) {
        mKey = key;
    }

    public String getLocation() {
        return mLocation;
    }

    public void setLocation(String location) {
        mLocation = location;
    }

}
