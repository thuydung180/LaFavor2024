package com.nhom5.models;

public class Cart {
//    int orderId;
    String productName;
    double productPrice;
//    String productImage;
    int productQuantity;

    public Cart(String productName, double productPrice, int productQuantity) {
//        this.orderId = orderId;
//        this.productId = productId;
        this.productName = productName;
        this.productPrice = productPrice;
//        this.productImage = productImage;
        this.productQuantity = productQuantity;
    }



//    public int getOrderId() {
//        return orderId;
//    }

//    public void setOrderId(int orderId) {
//        this.orderId = orderId;
//    }

//    public int getProductId() {
//        return productId;
//    }
//
//    public void setProductId(int productId) {
//        this.productId = productId;
//    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

//    public String getProductDescription() {
//        return productDescription;
//    }
//
//    public void setProductDescription(String productDescription) {
//        this.productDescription = productDescription;
//    }

    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }

//    public String getProductImage() {
//        return productImage;
//    }
//
//    public void setProductImage(String productImage) {
//        this.productImage = productImage;
//    }

    public int getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(int productQuantity) {
        this.productQuantity = productQuantity;
    }
}
