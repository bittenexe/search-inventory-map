package com.pluralsight;

import java.io.FileReader;
import java.io.BufferedReader;
import java.util.HashMap;
import java.util.Scanner;
import java.io.IOException;
public class StoreApp {


    public static HashMap<Integer, Product> inventory =
            new HashMap<Integer, Product>();

    public static void main(String[] args) {

        loadInventory();

        Scanner scanner = new Scanner(System.in);

        System.out.print("What item # are you interested in? ");
        int id = scanner.nextInt();

        Product matchedProduct = inventory.get(id);
        if (matchedProduct == null) {
            System.out.println("We don't carry that product");
            return;
        }
        System.out.printf("We carry %s and the price is $%.2f",
                matchedProduct.getName(), matchedProduct.getPrice());
    }

    public static void loadInventory() {
        try (BufferedReader reader = new BufferedReader(new FileReader("inventory.csv"))) {
            String line;
            boolean isFirstLine = true;

            while ((line = reader.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue;
                }

                String[] parts = line.split("\\|");
                if (parts.length >= 4) {
                    int id = Integer.parseInt(parts[0].trim());
                    String name = parts[1].trim();
                    double price = Double.parseDouble(parts[2].trim());

                    Product product = new Product(id, name, price);
                    inventory.put(id, product);
                }
            }
        } catch (IOException e) {
            System.out.println("Error. File would not load. " + e.getMessage());
        }
    }
}
