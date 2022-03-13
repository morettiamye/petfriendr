package com.depaul.se491.petfriendr;

public class PetProfile {
    private String userName;
    private String petName;
    private String imageUrl;

    public PetProfile(String userName, String petName, String imageUrl) {
        this.userName = userName;
        this.petName = petName;
        this.imageUrl = imageUrl;
    }

    public String getUserName() { return userName; }
    public String getPetName() { return petName; }
}
