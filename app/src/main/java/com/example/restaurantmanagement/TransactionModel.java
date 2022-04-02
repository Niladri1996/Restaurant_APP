package com.example.restaurantmanagement;

public class TransactionModel {
    String CardName,CardNo,CardDate,OrderId;

    public TransactionModel() {
    }

    public String getCardName() {
        return CardName;
    }

    public void setCardName(String cardName) {
        CardName = cardName;
    }

    public String getCardNo() {
        return CardNo;
    }

    public void setCardNo(String cardNo) {
        CardNo = cardNo;
    }

    public String getCardDate() {
        return CardDate;
    }

    public void setCardDate(String cardDate) {
        CardDate = cardDate;
    }

    public String getOrderId() {
        return OrderId;
    }

    public void setOrderId(String orderId) {
        OrderId = orderId;
    }

    public TransactionModel(String cardName, String cardNo, String cardDate, String orderId) {
        CardName = cardName;
        CardNo = cardNo;
        CardDate = cardDate;
        OrderId = orderId;
    }
}
