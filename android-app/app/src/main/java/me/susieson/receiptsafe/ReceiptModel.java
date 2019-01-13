package me.susieson.receiptsafe;

import java.util.ArrayList;

public class ReceiptModel {

    protected String merchantName;
    protected String merchantAddress;
    protected int day;
    protected int month;
    protected int year;

    protected String categoryName;
    protected float subtotalAmount;
    protected float taxAmount;
    protected float totalAmount;
    ArrayList<String> itemName = new ArrayList<String>();
    ArrayList<Float> amountPrice = new ArrayList<Float>();

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getMerchantAddress() {
        return merchantAddress;
    }

    public void setMerchantAddress(String merchantAddress) {
        this.merchantAddress = merchantAddress;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public ArrayList<String> getItemName() {
        return itemName;
    }

    public void setItemName(ArrayList<String> itemName) {
        this.itemName = itemName;
    }

    public ArrayList<Float> getAmountPrice() {
        return amountPrice;
    }

    public void setAmountPrice(ArrayList<Float> amountPrice) {
        this.amountPrice = amountPrice;
    }

    public float getSubtotalAmount() {
        return subtotalAmount;
    }

    public void setSubtotalAmount(float subtotalAmount) {
        this.subtotalAmount = subtotalAmount;
    }

    public float getTaxAmount() {
        return taxAmount;
    }

    public void setTaxAmount(float taxAmount) {
        this.taxAmount = taxAmount;
    }

    public float getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(float totalAmount) {
        this.totalAmount = totalAmount;
    }
}
