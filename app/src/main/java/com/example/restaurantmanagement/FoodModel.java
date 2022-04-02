package com.example.restaurantmanagement;

public class FoodModel {

   String FoodId,FoodName,FoodPrice,Timestamp,FoodImage;
   int count=0;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public FoodModel() {
    }

    public String getFoodId() {
        return FoodId;
    }

    public void setFoodId(String foodId) {
        FoodId = foodId;
    }

    public String getFoodName() {
        return FoodName;
    }

    public void setFoodName(String foodName) {
        FoodName = foodName;
    }

    public String getFoodPrice() {
        return FoodPrice;
    }

    public void setFoodPrice(String foodPrice) {
        FoodPrice = foodPrice;
    }

    public String getTimestamp() {
        return Timestamp;
    }

    public void setTimestamp(String timestamp) {
        Timestamp = timestamp;
    }

    public String getFoodImage() {
        return FoodImage;
    }

    public void setFoodImage(String foodImage) {
        FoodImage = foodImage;
    }

    public FoodModel(String foodId, String foodName, String foodPrice, String timestamp, String foodImage) {
        FoodId = foodId;
        FoodName = foodName;
        FoodPrice = foodPrice;
        Timestamp = timestamp;
        FoodImage = foodImage;
    }
}
