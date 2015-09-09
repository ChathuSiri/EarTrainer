package com.example.chathura.eartrainer.logic;

public class User {
    private int id;
    private String userName;
    private String password;
    private float score;


    public void setPassword(String pass){
        this.password = pass;
    }
    public String getPassword(){
        return this.password;
    }

    public User(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public User() {
    }


    public void setId(int id){
        this.id = id;
    }
    public int getId(){
        return this.id;
    }
    public void setUserName(String userName){
        this.userName = userName;
    }

    public String getUserName(){
        return this.userName;
    }

    public void setScore(float score){
        this.score = score;

    }

    public float getScore(){
        return this.score;
    }
}
