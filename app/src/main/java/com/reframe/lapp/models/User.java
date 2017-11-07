
package com.reframe.lapp.models;

import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class User {

    @SerializedName("birthDate")
    private String mAge;
    @SerializedName("country")
    private String mCountry;
    @SerializedName("createdAt")
    private String mCreatedAt;
    @SerializedName("email")
    private String mEmail;
    @SerializedName("institution")
    private String mInstitution;
    @SerializedName("name")
    private String mName;
    @SerializedName("password")
    private String mPassword;
    @SerializedName("picture")
    private String mPicture;
    @SerializedName("residenceYear")
    private String mResidenceYear;
    @SerializedName("role")
    private String mRole;
    @SerializedName("salt")
    private String mSalt;
    @SerializedName("sex")
    private String mSex;
    @SerializedName("specialty")
    private String mSpecialty;
    @SerializedName("updatedAt")
    private String mUpdatedAt;
    @SerializedName("_id")
    private String m_id;
    @SerializedName("level")
    private Level mLevel;



    public static final String PROFESSOR = "PROFESSOR";

    public static final String STUDENT = "STUDENT";

    public String getAge() {
        return mAge;
    }

    public void setAge(String age) {
        mAge = age;
    }

    public String getCountry() {
        return mCountry;
    }

    public void setCountry(String country) {
        mCountry = country;
    }

    public String getCreatedAt() {
        return mCreatedAt;
    }

    public void setCreatedAt(String createdAt) {
        mCreatedAt = createdAt;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        mEmail = email;
    }

    public String getInstitution() {
        return mInstitution;
    }

    public void setInstitution(String institution) {
        mInstitution = institution;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getPassword() {
        return mPassword;
    }

    public void setPassword(String password) {
        mPassword = password;
    }

    public String getPicture() {
        return mPicture;
    }

    public void setPicture(String picture) {
        mPicture = picture;
    }

    public String getResidenceYear() {
        return mResidenceYear;
    }

    public void setResidenceYear(String residenceYear) {
        mResidenceYear = residenceYear;
    }

    public String getRole() {
        return mRole;
    }

    public void setRole(String role) {
        mRole = role;
    }

    public String getSalt() {
        return mSalt;
    }

    public void setSalt(String salt) {
        mSalt = salt;
    }

    public String getSex() {
        return mSex;
    }

    public void setSex(String sex) {
        mSex = sex;
    }

    public String getSpecialty() {
        return mSpecialty;
    }

    public void setSpecialty(String specialty) {
        mSpecialty = specialty;
    }

    public String getUpdatedAt() {
        return mUpdatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        mUpdatedAt = updatedAt;
    }

    public String get_id() {
        return m_id;
    }

    public void set_id(String _id) {
        m_id = _id;
    }

    public Level getLevel() {
        return mLevel;
    }

    public void setLevel(Level level) {
        mLevel = level;
    }

}
