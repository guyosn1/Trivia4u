package com.example.triviavirsion2;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.ByteArrayOutputStream;

public class User extends Auth {
    String username;
    String picture;
    int score;

    public User(String email, String password, String username, int score, String picture) {
        super(email, password);
        this.username = username;
        this.score = score;
        this.picture = picture;
    }

    public User(String email, String password, String username, int score, Bitmap picture) {
        super(email, password);
        this.username = username;
        this.score = score;
        setPictureAsString(picture);
    }
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
