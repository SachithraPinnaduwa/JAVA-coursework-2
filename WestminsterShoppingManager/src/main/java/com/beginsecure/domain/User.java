package com.beginsecure.domain;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

// this class is for user login and registration and saving the no of times the user logged in
public class User {
    private String userName;
    private  String password;
    public   int count;
    public HashMap<String,User> users = new HashMap<>();
    public  boolean countinue = true;

    public static User currentUser;
    public User(String userName, String password ) {//constructor for use in add account
        this.userName = userName;
        this.password = password;
        this.count=0;

    }
    public User(String userName, String password,int c) {//constructor for use in read from file
        this.userName = userName;
        this.password = password;
        this.count=c;

    }

    public User() {
        this.users = new HashMap<>();
    }//default constructor for use in other classes

    public static void main(String[] args) {

    }
    public void menu(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("1. Create an account");
        System.out.println("2. Log In");
        System.out.println("3. Delete your account");
        System.out.println("4. Save accounts in a file");
        System.out.println("5. Load accounts from a file");
        System.out.println("6. Enter GUI");
        System.out.println("7. Display users");
        System.out.println("8. Quit program ");
        String input = scanner.nextLine();

        switch (input) {
            case "1":
                addaccount();
                break;
            case "2":
                readFromFile();
                login();
                break;
            case "3":
                removeaccount();
                break;
            case "4":
                savetofile();
                break;
            case "5":
                readFromFile();
                break;
            case "6":
                boolean cart =ShoppingCart.getInstance();
                if(cart){
                    if (currentUser==null){
                        System.out.println("Please login");
                        File file = new File("users.json");
                        if (!file.exists()) {
                            System.out.println("No users found in the file.");
                            return;
                        }
                        readFromFile();
                        while (currentUser==null){
                            login();
                        }

                    }
                    ShoppingCart shoppingCart = new ShoppingCart();
                    shoppingCart.shoppingCart(new ArrayList<>());
                    System.out.println("Enter GUI");
                }


                break;
            case "7":
                displayUsers();
                break;
            case "8":
                savetofile();
                System.out.println("Going back to main menu");
                countinue=false;
                break;
            default:
                System.out.println("Invalid input");
                break;
        }
    }



    public void changeCount() {
        if (currentUser == null) {
            System.out.println("Please login first.");
            return;
        }

        // Check if specific user is provided
        String usernamecount = currentUser.getUserName();
        if (usernamecount != null) {
            currentUser = this;
            System.out.println( usernamecount);
            currentUser.setCount(currentUser.getCount()+1);
            if (!users.containsKey(usernamecount)) {

                return;
            }
            int newCount =  users.get(usernamecount).getCount();
            users.get(usernamecount).setCount(newCount);

        } else {
            System.out.println("user is null");
        }
        // ... log update based on username or current user
    }


    public void login(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("1.Enter your user name");
        String name = scanner.nextLine();
        Scanner scanner2 = new Scanner(System.in);
        System.out.println("1.Enter your password");
        String pass = scanner2.nextLine();

        if(users.containsKey(name)){
            if(users.get(name).getPassword().equals(pass)){
                System.out.println("Login successful");
                currentUser = users.get(name);
            }else{
                System.out.println("Wrong password");
            }
        }else{
            System.out.println("Wrong user name");
        }
    }
    public   void addaccount(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("1.Enter your user name");
        String name = scanner.nextLine();
        Scanner scanner2 = new Scanner(System.in);
        System.out.println("1.Enter your password");
        String pass = scanner2.nextLine();
        User user = new User(name,pass);

        users.put(name,user);

        Gson gson = new Gson();
        try (FileWriter writer = new FileWriter("users.json", true)) { // Use JSON file extension
            if (users.isEmpty()) {
                System.out.println("User list is empty");
                return;
            }

            // Convert the entire "users" HashMap to a JSON string
            String jsonString = gson.toJson(users);
            writer.write(jsonString);
            writer.flush(); // Ensure data is written to file

            System.out.println("Users saved successfully to users.json");
        } catch (IOException e) {
            System.out.println("Error occurred while saving users: " + e.getMessage());
        }

    }
    public void removeaccount(){

        Scanner scannerr = new Scanner(System.in);
        System.out.println("1.Enter your user name");
        String name = scannerr.nextLine();
        Scanner scanner2r = new Scanner(System.in);
        System.out.println("1.Enter your password");
        String pass = scanner2r.nextLine();

        if (users.get(name).getUserName().equals(name) && users.get(name).getPassword().equals(pass)){
            users.remove(name);
            System.out.println("account removed successfully");
        }else {
            System.out.println("username or password is incorrect");
        }

        for (String key : users.keySet()) {

            System.out.println(users.get(key).getUserName());

        }

    }
    public void savetofile() {
        Gson gson = new Gson();
        try (FileWriter writer = new FileWriter("users.json", false)) { // Use JSON file extension
            if (users.isEmpty()) {
                System.out.println("User list is empty");
                return;
            }


            // Convert the entire "users" HashMap to a JSON string
            String jsonString = gson.toJson(users);
            writer.write(jsonString);
            writer.close(); // Ensure data is written to file

            System.out.println("Users saved successfully to users.json");
        } catch (IOException e) {
            System.out.println("Error occurred while saving users: " + e.getMessage());
        }
    }

    public void readFromFile() {
        Gson gson = new Gson();
        try (JsonReader reader = new JsonReader(new FileReader("users.json"))) {
            reader.setLenient(true);

            // Use TypeToken to define the map type
            Map<String, User> usersMap = gson.fromJson(reader, new TypeToken<HashMap<String, User>>() {}.getType());
            users.putAll(usersMap); // Update the existing "users" HashMap
            System.out.println("Users loaded successfully from users.json");
        } catch (IOException e) {
            System.out.println("Error occurred while loading users: " + e.getMessage());
        }

        // Print user information if any
        if (!users.isEmpty()) {
            displayUsers();
        } else {
            System.out.println("No users found in the file.");
        }
    }

    public void displayUsers(){
        if (!(currentUser==null)) {
            System.out.println("Current user");
            System.out.println(users.get(currentUser.getUserName()).getUserName());
        }
        System.out.println("All users");
        for (String key : users.keySet()) {
            //print user name only
            System.out.println(users.get(key).getUserName());
        }

    }
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }
    public int getCount(){
        return count;
    }
    public void setCount(int count){
        this.count=count;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return userName +" "+ password +" "+ count ;
    }


}
