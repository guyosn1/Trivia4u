package com.example.triviavirsion2;

public class FriendRequest {


    private String senderUid;
    private String senderEmail;
    private String receiverUid;
    private String key;

    public FriendRequest(){

    }

    public FriendRequest(String senderUid, String receiverUid, String senderEmail) {
        this.senderUid = senderUid;
        this.receiverUid = receiverUid;
        this.senderEmail = senderEmail;
        key = senderUid + "_" + receiverUid;
    }


    public String getSenderEmail() {
        return senderEmail;
    }

    public String getReceiverUid() {
        return receiverUid;
    }

    public String getSenderUid() {
        return senderUid;
    }

    public void setReceiverUid(String receiverUid) {
        this.receiverUid = receiverUid;
    }

    public void setSenderEmail(String senderEmail) {
        this.senderEmail = senderEmail;
    }

    public void setSenderUid(String senderUid) {
        this.senderUid = senderUid;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
