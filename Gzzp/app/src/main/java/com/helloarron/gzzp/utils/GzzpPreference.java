package com.helloarron.gzzp.utils;

import android.annotation.SuppressLint;

import com.helloarron.dhroid.util.Preference;

/**
 * Created by arron on 2017/3/11.
 */

@SuppressLint("ParcelCreator")
public class GzzpPreference extends Preference {

    // 第一次登陆
    public int isFirst = 0;

    public String uid;
    public String name;
    public String email;
    public String password;
    public String token;
    public int langType = 1;

    public void clear() {
        this.uid = "";
        this.name = "";
        this.email = "";
        this.password = "";
        this.token = "";
        commit();
    }

    public int getIsFirst() {
        return isFirst;
    }

    public void setIsFirst(int isFirst) {
        this.isFirst = isFirst;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getLangType() {
        return langType;
    }

    public void setLangType(int langType) {
        this.langType = langType;
    }
}
