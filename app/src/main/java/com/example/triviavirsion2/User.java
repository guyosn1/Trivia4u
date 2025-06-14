package com.example.triviavirsion2;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

public class User extends Auth {
    private String username;
    private String picture;
    private int score;
    private String uid;
    private Map<String,Boolean> friendRequest;

    private Map<String,Boolean> sentFriendRequest;
    public User()
    {
        super();
    }

    public User(String email, String password, String username, String picture) {
        super(email, password);
        this.username = username;
        this.score = 0;
        this.picture = picture;
        this.uid = "NO_UID";
        friendRequest = new HashMap<>();
        sentFriendRequest = new HashMap<>();

    }

    public User(String email, String password, String username, Bitmap picture) {
        super(email, password);
        this.username = username;
        this.score = 0;
        setPictureAsString(picture);
        this.uid = "NO_UID";
        friendRequest = new HashMap<>();
        sentFriendRequest = new HashMap<>();
    }

    public void addFriendRequest(String uid) {
        this.friendRequest.put(uid, true);
    }

    public void removeFriendRequest(String uid) {
        this.friendRequest.remove(uid);
    }

    public void addSentFriendRequest(String uid) {
        this.sentFriendRequest.put(uid, true);
    }

    public void removeSentFriendRequest(String uid) {
        this.sentFriendRequest.remove(uid);
    }

    public void setFriendRequest(Map<String, Boolean> map) {
        this.friendRequest = map;
    }

    public void setSentFriendRequest(Map<String, Boolean> map) {
        this.sentFriendRequest = map;
    }


    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getPicture() {
        return picture;
    }

    public String getUid() {
        return uid;
    }

    public String getUsername() {
        return username;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Converts a Bitmap image to a Base64-encoded PNG string and sets it as the profile picture.
     *
     * @param php Bitmap image to encode
     */
    public void setPictureAsString(Bitmap php) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        php.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        this.picture = Base64.encodeToString(byteArray, Base64.DEFAULT);
    }

    /**
     * Decodes the Base64-encoded profile picture string to a Bitmap.
     *
     * @return Bitmap decoded from profile picture string, or null if decoding fails
     */
    public Bitmap pictureToBitmap() {
        if (picture == null || picture.isEmpty()) return null;
        byte[] decodedString = Base64.decode(picture, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
    }
}
