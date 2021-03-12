package com.example.e_commerce.horizontal_scroll;

public class HorizontalScrollModel {

    String productId;
    String productImage;
    String productName;
    String productProcessor;
    String productPrice;

    public HorizontalScrollModel(String productId,String productImage, String productName, String productProcessor, String productPrice) {

        this.productId=productId;
        this.productImage = productImage;
        this.productName = productName;
        this.productProcessor = productProcessor;
        this.productPrice = productPrice;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductProcessor() {
        return productProcessor;
    }

    public void setProductProcessor(String productProcessor) {
        this.productProcessor = productProcessor;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }
}
