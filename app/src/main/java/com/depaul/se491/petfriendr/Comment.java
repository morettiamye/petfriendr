package com.depaul.se491.petfriendr;

import android.text.TextUtils;

import com.google.firebase.firestore.ServerTimestamp;
import com.google.firebase.auth.FirebaseUser;


import java.util.Date;

public class Comment {

    private String userId;
    private String userName;
    private String comment;
    private @ServerTimestamp Date timestamp;

    public Comment(){}

    public Comment(FirebaseUser user, double rating, String comment) {
        this.userId = user.getUid();
        this.userName = user.getDisplayName();
        if (TextUtils.isEmpty(this.userName)) {
            this.userName = user.getEmail();
        }

        this.comment = comment;
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

    public Date getTimestamp() {
        return timestamp;
    }
}
