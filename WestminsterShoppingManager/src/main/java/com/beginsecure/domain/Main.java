package com.beginsecure.domain;

import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        WestminsterShoppingManager westminsterShoppingManager = new WestminsterShoppingManager();
        User user = new User();


        while (true){
            System.out.println("Are you a customer or an admin?");
            System.out.println("1. Customer");
            System.out.println("2. Admin");
            System.out.println("3. Quit program");
            Scanner scanner = new Scanner(System.in);
            String input = scanner.nextLine();
            switch (input) {
                case "1":
                    do {
                        user.menu();
                    }while (user.countinue);

                case "2":
                    do {
                        westminsterShoppingManager.displayMenu();
                    }
                    while (westminsterShoppingManager.cont);

                case " ":

                    break;
                case "3":
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid input");
                    break;
            }
        }

    }

}
