package com.example.restaurantmanagement;

public class Order_Model {
    String timestamp,orderId,tableNo,totalcost,orderStatus;

    public Order_Model() {
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getTableNo() {
        return tableNo;
    }

    public void setTableNo(String tableNo) {
        this.tableNo = tableNo;
    }

    public String getTotalcost() {
        return totalcost;
    }

    public void setTotalcost(String totalcost) {
        this.totalcost = totalcost;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Order_Model(String timestamp, String orderId, String tableNo, String totalcost, String orderStatus) {
        this.timestamp = timestamp;
        this.orderId = orderId;
        this.tableNo = tableNo;
        this.totalcost = totalcost;
        this.orderStatus = orderStatus;
    }
}
