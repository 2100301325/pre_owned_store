package com.example.ggapplication.data;

import com.google.gson.annotations.SerializedName;

public class User {
    @SerializedName("id")
    public static int id;
    public static int getId() {
        return id;
    }
    public static void setId(int id) {
       User.id = id;
    }
    @SerializedName("money")
    public static int money;

    public static int getMoney() {
        return money;
    }

    public static void setMoney(int money) {
        User.money = money;
    }
}
