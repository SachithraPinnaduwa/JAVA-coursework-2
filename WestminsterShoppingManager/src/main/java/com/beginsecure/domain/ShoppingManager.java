package com.beginsecure.domain;

public interface ShoppingManager {
    int maxnumberofproducts=50;
    public void displayMenu();
    public void addProductToInventory(String s,Product product);
    public  void removeProductFromInventory(String string);
    public void displayInventory();
}
