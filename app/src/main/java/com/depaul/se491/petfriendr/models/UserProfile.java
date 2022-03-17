package com.depaul.se491.petfriendr.models;

public class UserProfile {

    private String petName;
    private String userName;
    private String userId;
    private String photo;
    private String profileMessage;
    private String comment;

    public UserProfile(){}

    public UserProfile(String petName, String userName, String userId, String photo,
                       String profileMessage, String comment){
        this.petName = petName;
        this.userName = userName;
        this.userId = userId;
        this.photo = photo;
        this.profileMessage = profileMessage;
    }

    public String getPetName() {
        return petName;
    }

    public String getPhoto() {
        return photo;
    }

    public String getProfileMessage() {
        return profileMessage;
    }

    public String getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }


}
