package com.nhom5.lafavor2024;

import java.util.List;

public class CustomerOrder {
    private String userId;
    private List<Cart> orderItems;
    private double totalAmount;

    public CustomerOrder() {
        // Default constructor required for calls to DataSnapshot.getValue(Order.class)
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<Cart> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<Cart> orderItems) {
        this.orderItems = orderItems;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }
}
