package com.depaul.se491.petfriendr.models;

import android.icu.text.SimpleDateFormat;
import android.text.TextUtils;

import com.google.firebase.auth.FirebaseUser;


import java.util.Date;

public class Comment {

    private String userId;
    private String userName;
    private String comment;
    private String timestamp;
    private  String receivedUserName;

    public Comment(){}

    public Comment(FirebaseUser user, String comment, String receivedUserName) {
        this.userId = user.getUid();
        this.userName = user.getDisplayName();
        if (TextUtils.isEmpty(this.userName)) {
            this.userName = user.getEmail();
        }

        this.comment = comment;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String millisInString  = dateFormat.format(new Date());
        this.timestamp = millisInString;
        this.receivedUserName = receivedUserName;
    }

    public String getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public String getComment() {
        return comment;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getReceivedUserName() {
        return receivedUserName;
    }
}
