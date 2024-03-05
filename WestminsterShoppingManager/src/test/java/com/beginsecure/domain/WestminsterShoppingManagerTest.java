package com.beginsecure.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;



import java.io.*;

import java.util.Arrays;
import java.util.List;


import static org.junit.jupiter.api.Assertions.*;


class WestminsterShoppingManagerTest {
    WestminsterShoppingManager westminsterShoppingManager;

@BeforeEach
public void setup(){
    westminsterShoppingManager = new WestminsterShoppingManager();
}
    @Test
    void removeItem() {

        Electronics e = new Electronics("Electronics","3342",5,"Bill and hovel",6,6700,"Shaver");
        Clothing e2 = new Clothing("Clothing","567786",5,4000,"Shoe",30,"Black");
        westminsterShoppingManager.addProductToInventory(e.getProductID(),e);
        westminsterShoppingManager.addProductToInventory(e2.getProductID(),e2);
        westminsterShoppingManager.removeProductFromInventory(e.getProductID());
        assertEquals(1,westminsterShoppingManager.getInventory().size());
    }

    @Test
    void addItem() {

        Electronics e = new Electronics("Electronics","3342",5,"Bill and hovel",6,6700,"Shaver");
        westminsterShoppingManager.addProductToInventory(e.getProductID(),e);
        assertEquals(1,westminsterShoppingManager.getInventory().size());
    }



    @Test
    void saveToFile() throws IOException {

        Electronics e = new Electronics("Electronics","3342",5,"Bill and hovel",6,6700,"Shaver");
        westminsterShoppingManager.addProductToInventory(e.getProductID(),e);
        westminsterShoppingManager.saveToFile();
        String filePath = "Electronics.json"; // Replace with the actual file path
        assertTrue( fileExists(filePath));

    }
    private boolean fileExists(String filePath) {
        return new java.io.File(filePath).exists();
    }

    @Test
    void readFromFile() {

        westminsterShoppingManager.readFromFile();
//here the size should change depending on the number of items in the file
        assertEquals(2,westminsterShoppingManager.getInventory().size());
    }
    @Test
    void testforsorting() {
//this checks if the sorting works or not
        Electronics e = new Electronics("Electronics", "45", 5, "Bill and hovel", 6, 6700, "aaaa");
        Electronics e2 = new Electronics("Electronics", "3342", 5, "Bill and hovel", 6, 6700, "Shaver");
        westminsterShoppingManager.addProductToInventory(e.getProductID(), e);
        westminsterShoppingManager.addProductToInventory(e2.getProductID(), e2);

        // capture printed output
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));


        westminsterShoppingManager.displayInventory();


        String expectedOutput = "Electronics{warranty=5, brand='Bill and hovel', productID='3342', noOfItems=6, price=6700, productName='Shaver'}\nElectronics{warranty=5, brand='Bill and hovel', productID='45', noOfItems=6, price=6700, productName='aaaa'}";

        // Split the expected and actual output into lines
        List<String> expectedLines = Arrays.asList(expectedOutput.split("\\r?\\n"));
        List<String> actualLines = Arrays.asList(outContent.toString().trim().split("\\r?\\n"));

        //this checks if the number of lines are equal
        assertEquals(expectedLines.size(), actualLines.size());

        for (int i = 0; i < expectedLines.size(); i++) {
            assertEquals(expectedLines.get(i), actualLines.get(i));
        }

        // Reset System.out
        System.setOut(System.out);

    }


}