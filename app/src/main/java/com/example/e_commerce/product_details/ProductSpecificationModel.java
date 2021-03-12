package com.example.e_commerce.product_details;

public class ProductSpecificationModel {

    public static final int specificationTitle = 0;
    public static final int descriptionBody = 1;

    private int type;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    /////specification title
    private String title;

    public ProductSpecificationModel(int type, String title) {
        this.type = type;
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    /////specification ttle

    /////Specification Body
    private String productFeatureName;
    private String productFeatureDetail;

    public ProductSpecificationModel(int type, String productFeatureName, String productFeatureDetail) {
        this.type = type;
        this.productFeatureName = productFeatureName;
        this.productFeatureDetail = productFeatureDetail;
    }

    public String getProductFeatureName() {
        return productFeatureName;
    }

    public void setProductFeatureName(String productFeatureName) {
        this.productFeatureName = productFeatureName;
    }

    public String getProductFeatureDetail() {
        return productFeatureDetail;
    }

    public void setProductFeatureDetail(String productFeatureDetail) {
        this.productFeatureDetail = productFeatureDetail;
    }

    /////Specification Body

}
