package com.nhom5.models;

public class Product {
    int productId;
    String productName;
    String productCategory;
    double productPrice;
    double productComparePrice;
    String productDescription;
    float productRating;
    String productUrl;

    //Constructor


    public Product(int productId, String productName, String productCategory, double productPrice, double productComparePrice, String productDescription, float productRating, String productUrl) {
        this.productId = productId;
        this.productName = productName;
        this.productCategory = productCategory;
        this.productPrice = productPrice;
        this.productComparePrice = productComparePrice;
        this.productDescription = productDescription;
        this.productRating = productRating;
        this.productUrl = productUrl;
    }

    //Getter & Setter


    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(String productCategory) {
        this.productCategory = productCategory;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }

    public double getProductComparePrice() {
        return productComparePrice;
    }

    public void setProductComparePrice(double productComparePrice) {
        this.productComparePrice = productComparePrice;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public float getProductRating() {
        return productRating;
    }

    public void setProductRating(float productRating) {
        this.productRating = productRating;
    }

    public String getProductUrl() {
        return productUrl;
    }

    public void setProductUrl(String productUrl) {
        this.productUrl = productUrl;
    }
}
