package com.example.appr;

public class UserActivity {
    int _id;
    String _name;
    String _password;
    public UserActivity(){   }

    public UserActivity(int id, String a, String b){
        this._id=id;
        this._name = a;
        this._password = b;
    }
    public UserActivity(String a, String b){
        this._name = a;
        this._password = b;
    }

    public UserActivity(RegisterActivity register) {
    }

    public int getID(){
        return this._id;
    }

    public void setID(int id){
        this._id = id;
    }

    public String getName(){
        return this._name;
    }

    public void setName(String name){
        this._name = name;
    }

    public String getPw(){
        return this._password;
    }
    public void setPw(String passwd){
        this._password = passwd;
    }
}
