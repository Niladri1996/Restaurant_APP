package com.example.restaurantmanagement;

public class Review_Model {
    String OrderId,CustName,CustAddress,CustPhone,CustReview,CustRating;

    public Review_Model() {
    }

    public Review_Model(String orderId, String custName, String custAddress, String custPhone, String custReview, String custRating) {
        OrderId = orderId;
        CustName = custName;
        CustAddress = custAddress;
        CustPhone = custPhone;
        CustReview = custReview;
        CustRating = custRating;
    }

    public String getOrderId() {
        return OrderId;
    }

    public void setOrderId(String orderId) {
        OrderId = orderId;
    }

    public String getCustName() {
        return CustName;
    }

    public void setCustName(String custName) {
        CustName = custName;
    }

    public String getCustAddress() {
        return CustAddress;
    }

    public void setCustAddress(String custAddress) {
        CustAddress = custAddress;
    }

    public String getCustPhone() {
        return CustPhone;
    }

    public void setCustPhone(String custPhone) {
        CustPhone = custPhone;
    }

    public String getCustReview() {
        return CustReview;
    }

    public void setCustReview(String custReview) {
        CustReview = custReview;
    }

    public String getCustRating() {
        return CustRating;
    }

    public void setCustRating(String custRating) {
        CustRating = custRating;
    }
}
