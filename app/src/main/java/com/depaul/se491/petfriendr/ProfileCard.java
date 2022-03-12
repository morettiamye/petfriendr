/*
        Use this class to code the profile card that will display when populated on SeePetsActivity
        Card should contain: photo (left) and username + pet name (right)
*/

package com.depaul.se491.petfriendr;

public class ProfileCard {
    String thisUserID;
    String thisName;

    public ProfileCard(String userID, String name){
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
