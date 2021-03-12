package com.example.e_commerce.my_order;

public class MyOrderItemModel {

    private int orderedProductImage;
    private String orderedProductTitle;
    private String orderDeliveryStatus;
    private int rating;

    public int getRating() {

        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public MyOrderItemModel(int orderedProductImage, int rating, String orderedProductTitle, String orderDeliveryStatus) {
        this.orderedProductImage = orderedProductImage;
        this.rating=rating;
        this.orderedProductTitle = orderedProductTitle;
        this.orderDeliveryStatus = orderDeliveryStatus;
    }

    public int getOrderedProductImage() {
        return orderedProductImage;
    }

    public void setOrderedProductImage(int orderedProductImage) {
        this.orderedProductImage = orderedProductImage;
    }

    public String getOrderedProductTitle() {
        return orderedProductTitle;
    }

    public void setOrderedProductTitle(String orderedProductTitle) {
        this.orderedProductTitle = orderedProductTitle;
    }

    public String getOrderDeliveryStatus() {
        return orderDeliveryStatus;
    }

    public void setOrderDeliveryStatus(String orderDeliveryStatus) {
        this.orderDeliveryStatus = orderDeliveryStatus;
    }
}
