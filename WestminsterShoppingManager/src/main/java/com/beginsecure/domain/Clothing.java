package com.beginsecure.domain;

public class Clothing extends Product{
    private int size;
    private String color;

    public Clothing(String type,String productID, int noOfItems, int price, String productName, int size, String color) {
        super(type,productID, noOfItems, price, productName);
        this.size = size;
        this.color = color;
    }

    public Clothing(String type, String productID, int noOfItems, int price, String productName) {
        super(type, productID, noOfItems, price, productName);
    }

    public Clothing() {
        super();
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getType(){
        return  super.getType();
    }

    @Override
    public void displayInfo() {
        System.out.println("Clothing{" +
                "size=" + size +
                ", color='" + color + '\'' +
                ", productID='" + super.getProductID() + '\'' +
                ", noOfItems=" + super.getNoOfItems() +
                ", price=" + super.getPrice() +
                ", productName='" + super.getProductName()+ '\'' +
                '}');
    }
    public  String forprint(){
        return  super.getType()+" "+
                super.getProductID() + " "+
                super.getNoOfItems() + " "+
                super.getPrice() + " "+
                super.getProductName() + " "+
                size + " "+
                color + " ";
    };
    public String forInteface(){
        return size + " "+
                color + " ";
    };
}
