package com.beginsecure.domain;

public class Electronics extends Product {
    private int warranty;
    private  String brand;


    public Electronics(String type,String productID,int warranty, String brand, int noOfItems, int price, String productName) {
        super(type,productID, noOfItems, price, productName);
        this.warranty = warranty;
        this.brand = brand;


    }

    public Electronics() {
    }

    public int getWarranty() {
        return warranty;
    }

    public void setWarranty(int warranty) {
        this.warranty = warranty;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }
    public String getType(){
        return super.getType();
    }



    @Override
    public void displayInfo() {
        System.out.println("Electronics{" +
                "warranty=" + warranty +
                ", brand='" + brand + '\'' +
                ", productID='" + super.getProductID() + '\'' +
                ", noOfItems=" + super.getNoOfItems() +
                ", price=" + super.getPrice() +
                ", productName='" + super.getProductName() + '\'' +
                '}');
    }
    public  String forprint(){
        return  super.getType()+" "+
                super.getProductID() + " "+
                warranty +" "+
                brand+" "+
                super.getNoOfItems() +" "+
                super.getPrice() +" "+
                super.getProductName() +" ";
    };
    public String forInteface(){
        return warranty +" "+
                brand+" ";
    };
}
