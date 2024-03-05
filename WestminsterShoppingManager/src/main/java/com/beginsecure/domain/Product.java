package com.beginsecure.domain;

public abstract  class Product {
    private String productID;
    private int noOfItems;
    private int price;
    private  String productName;
    private String type;

    public Product(String type,String productID, int noOfItems, int price, String productName) {
        this.productID = productID;
        this.noOfItems = noOfItems;
        this.price = price;
        this.productName = productName;
        this.type=type;
    }

    public Product() {
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public int getNoOfItems() {
        return noOfItems;
    }


    public void setNoOfItems(int noOfItems) {
        this.noOfItems = noOfItems;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getProductName() {
        return productName;
    }
    public String getType(){
        return  type;
    }


    public void setProductName(String productName) {
        this.productName = productName;
    }
    public  void displayInfo(){
        System.out.println("Product ID: " + productID + " | Product Name: " + productName + " | Price: " + price + " | No of Items: " + noOfItems);
    };
    public  String forprint(){
        return productID+ productName+ price+ noOfItems;
    };
    public abstract String forInteface();


}
