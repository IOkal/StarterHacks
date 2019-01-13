package me.susieson.receiptsafe;

import org.parceler.Parcel;

import java.util.List;

@Parcel
class User {

    String userId;
    List<Receipt> receipts;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<Receipt> getReceipts() {
        return receipts;
    }

    public void setReceipts(List<Receipt> receipts) {
        this.receipts = receipts;
    }
}
