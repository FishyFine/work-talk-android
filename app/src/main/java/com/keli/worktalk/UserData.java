package com.keli.worktalk;

public class UserData {
    private static UserData instance;

    public String username;
    public String password;
    public String token;

    public UserData(){

    }
    public synchronized static UserData getInstance(){
        if(instance == null){
            instance = new UserData();
        }
        return instance;
    }
}
