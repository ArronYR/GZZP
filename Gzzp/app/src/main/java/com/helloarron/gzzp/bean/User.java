package com.helloarron.gzzp.bean;

public class User {

    static User instance;

    public int type = 1;

    public static User getInstance() {
        if (instance == null) {
            instance = new User();
        }

        return instance;
    }

    public boolean isLogin;

    public boolean isLogin() {
        return isLogin;
    }

    public void setLogin(boolean isLogin) {
        this.isLogin = isLogin;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

}
