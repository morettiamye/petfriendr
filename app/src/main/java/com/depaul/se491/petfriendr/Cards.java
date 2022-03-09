package com.depaul.se491.petfriendr;

public class Cards {
    String thisUserID;
    String thisName;

    public Cards(String userID, String name){
        this.thisUserID = userID;
        this.thisName = name;

    }

    public String getThisUserID() {
        return thisUserID;
    }

    public void setThisUserID(String thisUserID) {
        this.thisUserID = thisUserID;
    }

    public String getThisName() {
        return thisName;
    }

    public void setThisName(String thisName) {
        this.thisName = thisName;
    }

}
