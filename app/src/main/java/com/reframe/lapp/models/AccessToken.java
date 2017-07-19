package com.reframe.lapp.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Aldo on 22-11-2016.
 */

public class AccessToken implements Serializable {

    @SerializedName("token") public String token;

}
