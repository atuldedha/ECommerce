package com.example.e_commerce.cart_fragment;

public class CartItemModel {

    public static final int CART_ITEM =0;
    public static final int TOTAL_AMOUNT=1;

    private int type;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    ////////cart item


    private String productID;
    private String productImage;
    private String productTitle;
    private String productPrice;
    private String productCuttedPrice;

    private long noOfFreeCoupons;
    private long productQuantity;
    private long offersApplied;
    private long couponsApplied;
    private boolean inStock;

    public CartItemModel(int type, String productID, String productImage, String productTitle, String productPrice, String productCuttedPrice, long noOfFreeCoupons,
                         long productQuantity, long offersApplied, long couponsApplied, boolean inStock) {
        this.type = type;
        this.productID = productID;
        this.productImage = productImage;
        this.productTitle = productTitle;
        this.productPrice = productPrice;
        this.productCuttedPrice = productCuttedPrice;
        this.noOfFreeCoupons = noOfFreeCoupons;
        this.productQuantity = productQuantity;
        this.offersApplied = offersApplied;
        this.couponsApplied = couponsApplied;
        this.inStock = inStock;

    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public static int getCartItem() {
        return CART_ITEM;
    }


    public String getProductTitle() {
        return productTitle;
    }

    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductCuttedPrice() {
        return productCuttedPrice;
    }

    public void setProductCuttedPrice(String productCuttedPrice) {
        this.productCuttedPrice = productCuttedPrice;
    }

    public long getNoOfFreeCoupons() {
        return noOfFreeCoupons;
    }

    public void setNoOfFreeCoupons(long noOfFreeCoupons) {
        this.noOfFreeCoupons = noOfFreeCoupons;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public long getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(long productQuantity) {
        this.productQuantity = productQuantity;
    }

    public long getOffersApplied() {
        return offersApplied;
    }

    public void setOffersApplied(long offersApplied) {
        this.offersApplied = offersApplied;
    }

    public long getCouponsApplied() {
        return couponsApplied;
    }

    public void setCouponsApplied(long couponsApplied) {
        this.couponsApplied = couponsApplied;
    }

    public boolean isInStock() {
        return inStock;
    }

    public void setInStock(boolean inStock) {
        this.inStock = inStock;
    }

    ////////cart item

    ///////cart total

    public CartItemModel(int type) {
        this.type = type;
    }


     ///////cart total


}
