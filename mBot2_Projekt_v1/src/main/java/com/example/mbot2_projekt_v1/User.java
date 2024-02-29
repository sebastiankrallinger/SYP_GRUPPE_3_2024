package com.example.mbot2_projekt_v1;

import java.util.ArrayList;
import java.util.List;

public class User {
    List<Mbot> mbots = new ArrayList<>();
    String username;

    public User(String username){
        this.username = username;
    }
}
