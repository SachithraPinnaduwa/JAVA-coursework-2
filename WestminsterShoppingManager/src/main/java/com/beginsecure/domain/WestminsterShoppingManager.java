package com.beginsecure.domain;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import java.io.*;
import java.util.*;

public class WestminsterShoppingManager implements ShoppingManager {
    //there are three seperate hashmaps for ease of saving to the json file

    private LimitedSizeHashMap<String, Electronics> electronics;
    private LimitedSizeHashMap<String, Clothing> clothing;
    private LimitedSizeHashMap<String, Product> inventory ;

    public boolean cont = true;

    public WestminsterShoppingManager() {
        this.electronics = new LimitedSizeHashMap<String, Electronics>(maxnumberofproducts);
        this.clothing = new LimitedSizeHashMap<String, Clothing>(maxnumberofproducts);
        this.inventory = new LimitedSizeHashMap<String, Product>(maxnumberofproducts);

    }


//this method displays the menu
    @Override
    public void displayMenu() {

        Scanner scanner = new Scanner(System.in);
        System.out.println("1. Add a new item to the system");
        System.out.println("2. Delete an item from the system");
        System.out.println("3. Print the inventory list");
        System.out.println("4. Save items in a file");
        System.out.println("5. Load items from a file");
        System.out.println("6. Enter GUI");
        System.out.println("7. Exit");
        String input = scanner.nextLine();

        switch (input) {
            case "1":
                addItem();
                break;
            case "2":
                removeItem();
                break;
            case "3":
                displayInventory();
                break;
            case "4":
                saveToFile();
                break;
            case "5":
                readFromFile();
                break;
            case "6":
                User user = new User();
                user.menu();
                break;
            case "7":
                System.out.println("Going back to main menu");
                cont = false;
                break;
            default:
                System.out.println("Invalid input");
                break;
        }
    }

    @Override
    public void addProductToInventory(String s, Product product) {
        inventory.put(s, product);
    }

    @Override
    public void removeProductFromInventory(String string) {
        inventory.remove(string);
    }
    public void removeItem(String productID){
        inventory.get(productID).setNoOfItems(inventory.get(productID).getNoOfItems()-1);
    }
    public LimitedSizeHashMap<String, Product> getInventory(){

        return inventory;
    }


//this method adds the item to the inventory
    public void addItem() {
        System.out.println("Enter the product ID");
        Scanner ID = new Scanner(System.in);
        String productID = ID.nextLine();

        for (String key : inventory.keySet()) {
            if (key.equals(productID)) {
                System.out.println("Product ID already exists. Do you want to add more items to the product? \n 1. Yes \n 2. No");
                Scanner sc = new Scanner(System.in);
                String choice = sc.nextLine();

                if (choice.equals("1")) {
                    System.out.println("Enter the number of items you want to add");
                    Scanner scanner = new Scanner(System.in);

                    if (!scanner.hasNextInt()) {
                        System.out.println("Invalid input. Please enter a valid integer.");
                        scanner.next(); // remove the invalid input to avoid an infinite loop
                    } else {
                        int noOfItems = scanner.nextInt();

                        if (noOfItems < 0) {
                            System.out.println("Negative integer. Please enter a valid integer.");
                            return;
                        }

                        inventory.get(productID).setNoOfItems(inventory.get(productID).getNoOfItems() + noOfItems);
                    }
                } else if (choice.equals("2")) {
                    return;
                } else {
                    System.out.println("Invalid input");
                    return;
                }
                return;
            }
        }

        int noOfItems = 0;
        System.out.println("Enter the number of items");
        Scanner scanner = new Scanner(System.in);

        if (!scanner.hasNextInt()) {
            System.out.println("Invalid input. Please enter a valid integer.");
            return;
        }

        noOfItems = scanner.nextInt();

        if (noOfItems < 0) {
            System.out.println("Negative integer. Please enter a valid integer.");
            return;
        }

        System.out.println("Number of items: " + noOfItems);




        int price = 0;
        while (true) {
            System.out.println("Enter the price");
            Scanner prc = new Scanner(System.in);
            if (!prc.hasNextInt() && !prc.hasNextInt()) {

                System.out.println("Invalid input. Please enter a valid integer.");
                prc.next(); // remove the invalid input to avoid an infinite loop

            } else {

                price = prc.nextInt();
                System.out.println("price: " + price);
                break; // Exit the loop when valid input is received
            }
        }

        String productName = "";
        while (true) {
            System.out.println("Enter the product name");
            Scanner name = new Scanner(System.in);
            productName = name.nextLine();
            if (productName.matches("-?\\d+")) {
                System.out.println("You entered an integer value: " + productName);

            } else {
                System.out.println("product name: " + productName);
                break;
            }
        }

        int type = 0;
        while (true) {
            System.out.println("Enter the type of item you want to add \n 1. Electronics \n 2. Clothing");
            Scanner in = new Scanner(System.in);
            if(!in.hasNextInt()){
                System.out.println("Invalid input. Please enter a valid integer.");

            }else{
                type = in.nextInt();
                if (type == 1 || type == 2) {

                    System.out.println("choice: " + type);
                    break; // Exit the loop when valid input is received
                } else {

                    System.out.println("Invalid input. Please enter a valid integer.");


                }
            }

        }
//checks if the type is Electronics or Clothing
        if (type == 1) {
            String type1 = "Electronics";
            String brand = "";
            while (true) {
                System.out.println("Enter the brand");
                Scanner br = new Scanner(System.in);
                brand = br.nextLine();
                if (brand.matches("-?\\d+")) {
                    System.out.println("You entered an integer value: " + brand);

                } else {
                    System.out.println("brand name: " + brand);
                    break;
                }
            }

            int warranty = 0;
            while (true) {
                System.out.println("Enter the warranty");
                Scanner war = new Scanner(System.in);
                if (!war.hasNextInt() && !war.hasNextInt()) {

                    System.out.println("Invalid input. Please enter a valid integer.");
                    war.next(); // remove the invalid input to avoid an infinite loop

                } else {
                    warranty = war.nextInt();

                    System.out.println("warranty period: " + warranty);
                    break; // Exit the loop when valid input is received
                }
            }

            Electronics electronicsobj = new Electronics(type1, productID, warranty, brand, noOfItems, price, productName);

            addProductToInventory(productID, electronicsobj);
            electronics.put(productID, electronicsobj);
            displayInventory();


        } else if (type == 2) {
            String type2 = "Clothing";
            int size = 0;
            while (true) {
                System.out.println("Enter the size");
                Scanner sc = new Scanner(System.in);
                if (!sc.hasNextInt() && !sc.hasNextInt()) {
                    System.out.println("Invalid input. Please enter a valid integer.");
                    sc.next(); // remove the invalid input to avoid an infinite loop

                } else {
                    size = sc.nextInt();

                    System.out.println("size: " + size);
                    break; // Exit the loop when valid input is received
                }
            }

            String color = "";
            while (true) {
                System.out.println("Enter the color");
                Scanner clr = new Scanner(System.in);
                color = clr.nextLine();
                if (color.matches("-?\\d+")) {
                    System.out.println("You entered an integer value: " + color);

                } else {
                    System.out.println("product name: " + color);
                    break;
                }
            }

            Clothing clothingobj = new Clothing(type2, productID, noOfItems, price, productName, size, color);

            addProductToInventory(productID, clothingobj);

            clothing.put(productID, clothingobj);
            displayInventory();

        } else {
            System.out.println("Invalid input");
        }

    }

//this method removes the item from the inventory
    public void removeItem() {
        System.out.println("Enter the product ID");
        Scanner ID = new Scanner(System.in);
        String productID = ID.nextLine();
        if (inventory.containsKey(productID)) {
            Product temp = inventory.get(productID);
            System.out.println("Product ID: " + productID +
                    " | Product Name: "
                    + temp.getProductName() +
                    " | Price: " +
                    temp.getPrice() +
                    " | No of Items: " +
                    temp.getNoOfItems());
            System.out.println("Product removed \n");
            removeProductFromInventory(productID);
            System.out.println(inventory.size() + " products in inventory");

        } else {
            System.out.println("Invalid product ID");
        }


    }

    //this method displays the inventory after sorting it according to the product name
    @Override
    public void displayInventory() {

        ArrayList<Product> list = new ArrayList<Product>();

        for (String key : inventory.keySet()) {

            list.add(inventory.get(key));
        }
        if (list.isEmpty()) {
            System.out.println("Inventory is empty");

        }
//        In this example, the Collections.sort() method is used to sort the ArrayList based on a custom comparator.
//        The comparator compares the product names using the compareTo method
        Collections.sort(list, new Comparator<Product>() {
            @Override
            public int compare(Product p1, Product p2) {
                return p1.getProductName().compareTo(p2.getProductName());
            }
        });
        for (Product key : list) {
            key.displayInfo();

        }

    }

    public void saveToFile() {
        Gson gson = new Gson();

        try {
            // Check if inventory is empty
            if (inventory.isEmpty()) {
                System.out.println("Inventory is empty");
            } else {
                // Separate Hashmaps for Electronics and Clothing
                HashMap<String, Electronics> electronics = new HashMap<>();
                HashMap<String, Clothing> clothing = new HashMap<>();

                // Populate respective Hashmaps based on product types
                for (String key : inventory.keySet()) {
                    Product product = inventory.get(key);
                    if (product instanceof Electronics) {
                        electronics.put(key, (Electronics) product);
                    } else if (product instanceof Clothing) {
                        clothing.put(key, (Clothing) product);
                    }
                }

                // Write Electronics data to JSON file
                File electronicsFile = new File("Electronics.json");
                if (electronicsFile.createNewFile()) {
                    System.out.println("File created: " + electronicsFile.getName());
                } else {
                    System.out.println("File already exists. Overwriting...");
                }

                FileWriter electronicsWriter = new FileWriter(electronicsFile);
                String electronicsJson = gson.toJson(electronics);
                electronicsWriter.write(electronicsJson);
                electronicsWriter.close();

                // Write Clothing data to JSON file
                File clothingFile = new File("Clothing.json");
                if (clothingFile.createNewFile()) {
                    System.out.println("File created: " + clothingFile.getName());
                } else {
                    System.out.println("File already exists. Overwriting...");
                }

                FileWriter clothingWriter = new FileWriter(clothingFile);
                String clothingJson = gson.toJson(clothing);
                clothingWriter.write(clothingJson);
                clothingWriter.close();
            }
        } catch (IOException e) {
            System.out.println("Error occurred while saving files");
            e.printStackTrace();
        }
    }


    public void readFromFile() {
        Gson gson = new Gson();
        String electronicsFile = "Electronics.json";
        String clothingFile = "Clothing.json";



        // Read Electronics data
        if (new File(electronicsFile).exists()) {
            try (FileReader reader = new FileReader(electronicsFile)) {
                JsonReader jsonReader = new JsonReader(reader);
                // Use TypeToken to define the map type
                Map<String, Electronics> electronicsMap = gson.fromJson(jsonReader, new TypeToken<Map<String, Electronics>>() {}.getType());
                inventory.putAll(electronicsMap); // Add electronics to inventory
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // Read Clothing data
        if (new File(clothingFile).exists()) {
            try (FileReader reader = new FileReader(clothingFile)) {
                JsonReader jsonReader = new JsonReader(reader);
                // Use TypeToken to define the map type
                Map<String, Clothing> clothingMap = gson.fromJson(jsonReader, new TypeToken<Map<String, Clothing>>() {}.getType());
                inventory.putAll(clothingMap); // Add clothing to inventory
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // Check if inventory is populated
        if (inventory.isEmpty()) {
            System.out.println("No products found in inventory files.");
        } else {
            System.out.println("Inventory successfully loaded.");
        }
    }



}




