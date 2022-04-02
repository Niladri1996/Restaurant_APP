package com.example.restaurantmanagement;

public class Item_Model {
    String iemid,itemname,itemquantity,totalprice;

    public String getIemid() {
        return iemid;
    }

    public void setIemid(String iemid) {
        this.iemid = iemid;
    }

    public String getItemname() {
        return itemname;
    }

    public void setItemname(String itemname) {
        this.itemname = itemname;
    }

    public String getItemquantity() {
        return itemquantity;
    }

    public void setItemquantity(String itemquantity) {
        this.itemquantity = itemquantity;
    }

    public String getTotalprice() {
        return totalprice;
    }

    public void setTotalprice(String totalprice) {
        this.totalprice = totalprice;
    }

    public Item_Model() {
    }

    public Item_Model(String iemid, String itemname, String itemquantity, String totalprice) {
        this.iemid = iemid;
        this.itemname = itemname;
        this.itemquantity = itemquantity;
        this.totalprice = totalprice;
    }
}
