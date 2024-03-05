package com.beginsecure.domain;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.*;
import java.util.List;


public class ShoppingCart extends AbstractTableModel  {

    public int type ;
    private ShoppingCart frame;
    public JTable mainTable;
    public  JComboBox<String> dropdown;
    public JTable cartTable;
    public String[] cartColumnNames = {"Product", "Quantity", "Price"};
    public Object[][] cartData = null;
    public  JFrame cart;
    private DefaultTableModel cartTableModel = new DefaultTableModel(cartData, cartColumnNames);
    private ArrayList<Product> products;
    private String[] columnNames = {"Product ID", "Name","Category","Price","Info"};
    public JLabel idValue, categoryValue, nameValue, sizeValue, colorValue, itemsValue, details,priceValue,sizeLabel,colorLabel;
    public JLabel totalField,secondDiscountField,finalTotalField,firstDiscountField;
private static ShoppingCart shoppingCart;
    private static boolean isCreated = false;
    public static synchronized boolean getInstance()
    {
        if(!isCreated)
        {
            shoppingCart = new ShoppingCart();
            isCreated = true;
            return true;
        }
        else return false;
    }

    //this is for storing the main customer interface
    public void shoppingCart(ArrayList<Product> products) {

        this.products = products;
        readFromFile();
        frame = this;

        cart = new JFrame("Shopping Cart");
        cart.setLayout(new BorderLayout());
        opensecondframe();
        JFrame table = new JFrame();
        table.setTitle ("Westminster Shopping Centre");
        // set the default close operation
        table.setDefaultCloseOperation (JFrame.DISPOSE_ON_CLOSE);
        // set the size of the frame
        table.setSize (800, 700);
        // set the layout of the frame to border layout
        table.setLayout (new BorderLayout());
        JPanel paneln = new JPanel ();
        paneln.setBorder(new EmptyBorder(10, 0, 10, 0));
        JLabel titleLabel = new JLabel ("Select Product Category", SwingConstants.CENTER);

        paneln.add (titleLabel);

        String[] items = {"All","Electronics", "Clothing"};
        dropdown = new JComboBox<>(items);


        dropdown.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedCategory =  (String) dropdown.getSelectedItem();


                if (selectedCategory.equals("Electronics")){
                    type=1;

                }
                else if (selectedCategory.equals("Clothing")){
                    type=2;

                }
                else if (selectedCategory.equals("All")){
                    type=0;
                }
                runIfCondition();
            }
        });

        dropdown.setMaximumSize(new Dimension(Short.MAX_VALUE, dropdown.getPreferredSize().height));
        paneln.add (dropdown);

        JLabel cartLabel = new JLabel ("  " + " ");
        cartLabel.setBorder(new EmptyBorder(0, 250, 0, 0));
        paneln.add (cartLabel);
        JButton cartButton = new JButton ("Shopping Cart");

        cartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cart.setVisible(true);
            }
        });
        paneln.add (cartButton);
        table.add(paneln, BorderLayout.NORTH);

        JPanel panelc = new JPanel ();
        panelc.setPreferredSize(new BoxLayout(panelc, BoxLayout.Y_AXIS).maximumLayoutSize(panelc));
        panelc.setLayout(new FlowLayout());

        mainTable = new JTable(frame);

        //this is for changing the color of the text in the main table when the no of items is less than 3
        for (int i = 0; i < 5; i++) {
            mainTable.getColumnModel().getColumn(i).setCellRenderer(new DefaultTableCellRenderer() {
                Color originalColor = null;
                @Override
                public Component getTableCellRendererComponent(JTable table, Object value,
                                                               boolean isSelected, boolean hasFocus,
                                                               int row, int column) {
                    Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                    int s=0;
                    if(!products.isEmpty()){

                        s=products.get(row).getNoOfItems();
                    }
                    if (s <3) {
                        c.setForeground(Color.RED);
                    } else {
                        c.setForeground(Color.BLUE);
                    }
                    return c;
                }
            });
        }

        JScrollPane cartTableScrollPane = new JScrollPane(mainTable);
        mainTable.setRowHeight(30);
        mainTable.setDefaultRenderer(Object.class, new CustomTableCellRenderer());
        mainTable.getSelectionModel().addListSelectionListener(new TableSelectionListener());

        // create a scroll pane for the table

        cartTableScrollPane.setPreferredSize(new Dimension(700, 300));

        JPanel panels = new JPanel (new BorderLayout());
        panels.setBorder(new EmptyBorder(0, 100, 20, 0));
        JPanel infoPanel = new JPanel();
        // set the layout of the panel to grid layout
        infoPanel.setLayout(new GridLayout(8, 2));
        // create labels for the product information
        JLabel selectedproduct = new JLabel("Selected Product-");
        JLabel idLabel = new JLabel("Product ID:");
        JLabel categoryLabel = new JLabel("Category:");
        JLabel nameLabel = new JLabel("Name:");
        sizeLabel = new JLabel("None");
        colorLabel = new JLabel("None");
        JLabel itemsLabel = new JLabel("Items Available:");
        JLabel priceLabel = new JLabel("Price:");

        idValue = new JLabel("None");
        details = new JLabel("None");
        categoryValue = new JLabel("None");
        nameValue = new JLabel("None");
        sizeValue = new JLabel("None");
        colorValue = new JLabel("None");
        itemsValue = new JLabel("None");
        priceValue = new JLabel("None");

        // Set the borders for the labels to add some padding
        selectedproduct.setBorder(new EmptyBorder(10, 10, 0, 0));
        idLabel.setBorder(new EmptyBorder(10, 10, 0, 0));
        categoryLabel.setBorder(new EmptyBorder(10, 10, 0, 0));
        nameLabel.setBorder(new EmptyBorder(10, 10, 0, 0));
        sizeLabel.setBorder(new EmptyBorder(10, 10, 0, 0));
        colorLabel.setBorder(new EmptyBorder(10, 10, 0, 0));
        itemsLabel.setBorder(new EmptyBorder(10, 10, 0, 0));
        priceLabel.setBorder(new EmptyBorder(10, 10, 0, 0));

        idValue.setBorder(new EmptyBorder(10, 10, 0, 0));
        details.setBorder(new EmptyBorder(10, 10, 0, 0));
        categoryValue.setBorder(new EmptyBorder(10, 10, 0, 0));
        nameValue.setBorder(new EmptyBorder(10, 10, 0, 0));
        sizeValue.setBorder(new EmptyBorder(10, 10, 0, 0));
        colorValue.setBorder(new EmptyBorder(10, 10, 0, 0));
        itemsValue.setBorder(new EmptyBorder(10, 10, 0, 0));
        priceValue.setBorder(new EmptyBorder(10, 10, 0, 0));

        // Add the labels to the panel
        infoPanel.add(selectedproduct);
        infoPanel.add(details);
        infoPanel.add(idLabel);
        infoPanel.add(idValue);
        infoPanel.add(categoryLabel);
        infoPanel.add(categoryValue);
        infoPanel.add(nameLabel);
        infoPanel.add(nameValue);
        infoPanel.add(sizeLabel);
        infoPanel.add(sizeValue);
        infoPanel.add(colorLabel);
        infoPanel.add(colorValue);
        infoPanel.add(itemsLabel);
        infoPanel.add(itemsValue);
        infoPanel.add(priceLabel);
        infoPanel.add(priceValue);

        Font font = new Font("Verdana", Font.BOLD, 12);
        selectedproduct.setFont(font);
        details.setFont(font);
        Font lightFont = new Font("Verdana", Font.PLAIN, 12);
        idLabel.setFont(lightFont);
        idValue.setFont(lightFont);

        categoryLabel.setFont(lightFont);
        categoryValue.setFont(lightFont);
        nameLabel.setFont(lightFont);
        nameValue.setFont(lightFont);
        sizeLabel.setFont(lightFont);
        sizeValue.setFont(lightFont);
        colorLabel.setFont(lightFont);
        colorValue.setFont(lightFont);
        itemsLabel.setFont(lightFont);
        itemsValue.setFont(lightFont);

        // Create a panel for the whitespace
        JPanel whitespacePanel = new JPanel();
        whitespacePanel.setPreferredSize(new Dimension(500, 100));

        // Create a button for adding the product to the cart
        JButton addButton = new JButton("Add to Shopping Cart");

        AddToCart add = new AddToCart();
        addButton.addActionListener(add);


        // Set the horizontal alignment of the button
        addButton.setHorizontalAlignment(SwingConstants.CENTER);
        panels.add(infoPanel, BorderLayout.WEST);
        JPanel panelsb = new JPanel (new FlowLayout());
        panelsb.setBorder(new EmptyBorder(20, 0, 0, 0));
        panelsb.add(addButton);
        panels.add(panelsb, BorderLayout.SOUTH);
        // Add the whitespacePanel to the center to take up the remaining space
        panels.add(whitespacePanel, BorderLayout.EAST);
        addButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        addButton.setMaximumSize(new Dimension(Short.MAX_VALUE, addButton.getPreferredSize().height));
        panelc.add (cartTableScrollPane);
        table.add (panelc, BorderLayout.CENTER);
        table.add (panels, BorderLayout.SOUTH);
        // make the frame visible
        table.setVisible (true);
        table.setLocationRelativeTo(null); // Center the frame on the screen
    }

    @Override
    public int getRowCount() {
        return products.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Object temp = null;

        if (columnIndex == 0) {
            temp = products.get(rowIndex).getProductID();
        } else if (columnIndex == 1) {
            temp = products.get(rowIndex).getProductName();
        } else if (columnIndex == 2) {
            temp = products.get(rowIndex).getType();
        } else if (columnIndex == 3) {
            temp = products.get(rowIndex).getPrice();
        } else if (columnIndex == 4) {
            temp = products.get(rowIndex).forInteface();
        }
        return temp;
    }
    public void addProduct(Product product) {
        products.add(product);
    }

    public void removeProduct(Product product) {
        products.remove(product);
    }


    public void runIfCondition(){//based on the selection of the dropdown menu relevant type of product is loaded
        if (type == 1) {
            products.clear();
            readElectronicsFromFile();

        } else if (type == 2) {
            products.clear();
            readClothingFromFile();
        } else if (type == 0) {
            products.clear();
            readFromFile();

        } else {
            System.out.println("Invalid input for if condition");
        }

// Only call readFromFile() if none of the specific category conditions are met
        if (type != 1 && type != 2 && type != 0) {
            products.clear();
            readFromFile();
        }
        fireTableDataChanged();//this is for refreshing the table to add the new products
    }

    //this is for reading both electronics and clothing from the json files
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
                for (String key : electronicsMap.keySet()) {
                    addProduct(electronicsMap.get(key));
                }
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
                for (String key : clothingMap.keySet()) {
                    addProduct(clothingMap.get(key));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }


    //this is for rendering the table
    private  class CustomTableCellRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                       boolean hasFocus, int row, int column) {
            JLabel label = new JLabel();
            if (value != null) {
                label.setText(value.toString());
            } else {
                label.setText("null value");
            }

            label.setHorizontalAlignment(SwingConstants.CENTER);
            return label;
        }
    }
    public class AddToCart implements ActionListener { //this is for adding the products to the cart Jtable
        @Override
        public void actionPerformed(ActionEvent e) {
            int selectedRow = mainTable.getSelectedRow();
            String selectedRowString = String.valueOf(selectedRow);


            if (selectedRow != -1) {
                int columnCount = mainTable.getColumnCount();
                // Get values from the selected row in mainTable
                String productID = mainTable.getValueAt(selectedRow, 0).toString();
                String productName = mainTable.getValueAt(selectedRow, 1).toString();
                String category = mainTable.getValueAt(selectedRow, 2).toString();
                String price = mainTable.getValueAt(selectedRow, 3).toString() + "€";
                String info = mainTable.getValueAt(selectedRow, 4).toString();

                String noOfItems = String.valueOf(products.get(selectedRow).getNoOfItems());
                WestminsterShoppingManager westminsterShoppingManager = new WestminsterShoppingManager();


                updateTable();

                boolean productExists = false;
                int cartTableRow = cartTable.getRowCount();
                for (int i = 0; i < cartTableRow; i++) {
                    // Check if product exists based on both name and category
                    if (cartTable.getValueAt(i, 0).toString().equals(productID+" \n"+productName + " \n" + category+" \n" +info)) {
                        productExists = true;
                        int count = Integer.parseInt(cartTable.getValueAt(i, 1).toString());
                        cartTable.setValueAt(count + 1, i, 1);
                        break;
                    }
                }
                try {
                    if (!productExists) {
                        String pricetoadd = mainTable.getValueAt(selectedRow, 3).toString() + "€";
                        cartTableModel.addRow(new Object[]{productID+" \n"+productName + " \n" + category+" \n" +info, 1, pricetoadd});
                    }
                }catch (Exception exception){
                    System.out.println("Row is deleted");
                }

                int cartrow = cartTable.getSelectedRow();

                //this is for calculating the total price and discounts
                User user = new User();
                int usercount = user.currentUser.getCount();

                int cartTableRows = cartTable.getRowCount();

                System.out.println("Total" + total());
                int total = total();
                int noOfItemsn =  cartTable.getRowCount();
                int electronicscount = 0;
                int clothingcount = 0;
                for (int i = 0; i < cartTableRows; i++) {
                    String[] categoryArray = cartTable.getValueAt(i, 0).toString().split("\n");
                    String[] quantityArray = cartTable.getValueAt(i, 1).toString().split(" ");
                    int quantity = Integer.parseInt(quantityArray[0]);
                    String categorytype = categoryArray[2];
                    System.out.println("Category type: " + categorytype + " | Quantity: " + quantity);
                    for (int j = 0; j < quantity; j++) {
                        if (categorytype.equals("Electronics ")) {
                            electronicscount++;
                        } else if (categorytype.equals("Clothing ")) {
                            clothingcount++;
                        }else System.out.println("Not equal");
                    }

                }
                boolean discountbool = false;
                if(electronicscount>2 || clothingcount>2){

                    discountbool = true;
                }


                totalField.setText(" "+total + "€");
                finalTotalField.setText(" "+total + "€");
                if (discountbool){
                    int discount = (int) (total * 0.2);

                    int finaltotal = total - discount;
                    secondDiscountField.setText(" "+discount + "€");

                    if (usercount==1){
                        int discount2 = (int) (total * 0.1);

                        int finaltotal2 = finaltotal - discount2;
                        firstDiscountField.setText(" "+discount2 + "€");
                        finalTotalField.setText(" "+finaltotal2 + "€");
                    }
                    else if (usercount>1){
                        firstDiscountField.setText(" "+0 + "€");
                        finalTotalField.setText(" "+finaltotal + "€");
                    }
                }
                else if (noOfItemsn>0){
                    secondDiscountField.setText(" "+0 + "€");
                    if (usercount==1){
                        int discount2 = (int) (total * 0.1);

                        int finaltotal2 = total - discount2;
                        firstDiscountField.setText(" "+discount2 + "€");
                        finalTotalField.setText(" "+finaltotal2 + "€");
                    }else if (usercount>1){
                        firstDiscountField.setText(" "+0 + "€");
                    }
                }

            } else {
                System.out.println("No row selected");
            }
        }
    }
    public int total() {
        int total = 0;
        for (int i = 0; i < cartTable.getRowCount(); i++) {
            int quantity = Integer.parseInt(cartTable.getValueAt(i, 1).toString()); // Get quantity from 2nd column

            String[] priceArray = cartTable.getValueAt(i, 2).toString().split("€"); // Extract price
            int price = Integer.parseInt(priceArray[0]); // Extract price value

            int productTotal = quantity * price; // Calculate product total

            total += productTotal; // Add product total to overall total
        }

        return total; // Return the final total
    }

    public void updateTable() {//this is for updating the main table when the products are added to the cart
        WestminsterShoppingManager westminsterShoppingManager = new WestminsterShoppingManager();
        westminsterShoppingManager.readFromFile();
        String productID = mainTable.getValueAt(mainTable.getSelectedRow(), 0).toString();
        westminsterShoppingManager.removeItem(productID);
        String noOfItems = String.valueOf(products.get(mainTable.getSelectedRow()).getNoOfItems());

        changeCount() ;
        int noOfItemsn = products.get(mainTable.getSelectedRow()).getNoOfItems();

        System.out.println(noOfItemsn);

        if (noOfItemsn<=0){//this is for removing the product from the main table when the no of items is 0

            LimitedSizeHashMap<String, Product> inventory = westminsterShoppingManager.getInventory();
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
                westminsterShoppingManager.removeProductFromInventory(productID);


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

                        FileWriter electronicsWriter = new FileWriter(electronicsFile, false);
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

                        for (String key : electronics.keySet()) {
                            System.out.println(electronics.get(key).toString());
                        }
                        for (String key : clothing.keySet()) {
                            System.out.println(clothing.get(key).toString());
                        }

                        FileWriter clothingWriter = new FileWriter(clothingFile, false);
                        String clothingJson = gson.toJson(clothing);
                        clothingWriter.write(clothingJson);
                        clothingWriter.close();
                        System.out.println(electronics+"\n"+"Electronics");
                        System.out.println(clothing+"\n"+"Clothing");
                    }
                } catch (IOException e) {
                    System.out.println("Error occurred while saving files");
                    e.printStackTrace();
                }

                System.out.println("Updated inventory");
//                westminsterShoppingManager.displayInventory();
                System.out.println(westminsterShoppingManager.getInventory() + "\n"+ "No of items is 0 or less");


            } else {
                System.out.println("Invalid product ID");
            }


            removeItem(productID);
            System.out.println("No of items is 0 or less");

        }

        int selectedRow = mainTable.getSelectedRow();
        try {
            String noOfItemsupdated = String.valueOf(products.get(selectedRow).getNoOfItems());
            System.out.println(noOfItemsupdated);
            itemsValue.setText(noOfItemsupdated);
        }
        catch (Exception e){}
        westminsterShoppingManager.saveToFile();
        westminsterShoppingManager.readFromFile();
        itemsValue.setText(noOfItems);
        fireTableDataChanged();
    }
    public  int removeItem(String productID) {//this is for removing the product from the main table when the no of items is 0
        int index = -1;
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getProductID().equals(productID)) {
                index = i;
                break;
            }
        }
        if (index != -1) {
            products.remove(index);
        }
        return index;
    }
    public int changeCount() {
        int index = -1;
        for (int i = 0; i < products.size(); i++) {
            String productID = mainTable.getValueAt(mainTable.getSelectedRow(), 0).toString();
            if (products.get(i).getProductID().equals(productID)) {
                index = i;
                break;
            }
        }
        if (index != -1) {
            products.get(index).setNoOfItems(products.get(index).getNoOfItems()-1);
        }
        return index;
    }

    public void opensecondframe(){//this is for the cart Jframe

        User user = new User();//this is for changing the count of the customer
        User.currentUser.changeCount();



        cart.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel attributePanel = new JPanel();

        attributePanel.setLayout(new GridLayout(4, 2));
        attributePanel.setSize(500,50);
        JLabel totalLabel = new JLabel("Total:");

        totalField = new JLabel(" " + "€");
        JLabel firstDiscountLabel = new JLabel("First Purchase Discount (10%):");
        firstDiscountField = new JLabel(" " + "€");
        JLabel secondDiscountLabel = new JLabel("Three Items in the same Category Discount (20%):");
        secondDiscountField = new JLabel(" " + "€");
        JLabel finalTotalLabel = new JLabel("Final Total:");
        finalTotalField = new JLabel( "  " + "€");

        Font font = new Font("Calibre", Font.BOLD, 15);
        finalTotalField.setFont(font);
        finalTotalLabel.setFont(font);

        Font fontlight = new Font("Calibre", Font.PLAIN, 12);
        totalLabel.setFont(fontlight);
        totalField.setFont(fontlight);
        firstDiscountLabel.setFont(fontlight);
        firstDiscountField.setFont(fontlight);
        secondDiscountLabel.setFont(fontlight);
        secondDiscountField.setFont(fontlight);

        secondDiscountLabel.setMinimumSize(new Dimension(600, 20));


        attributePanel.add(totalLabel);
        attributePanel.add(totalField);
        attributePanel.add(firstDiscountLabel);
        attributePanel.add(firstDiscountField);
        attributePanel.add(secondDiscountLabel);
        attributePanel.add(secondDiscountField);
        attributePanel.add(finalTotalLabel);
        attributePanel.add(finalTotalField);

        totalLabel.setBorder(new EmptyBorder(0, 10, 0, 50));
        totalField.setBorder(new EmptyBorder(0, 50, 0, 0));
        firstDiscountLabel.setBorder(new EmptyBorder(10, 10, 0, 50));
        firstDiscountField.setBorder(new EmptyBorder(10, 50, 0, 0));
        secondDiscountLabel.setBorder(new EmptyBorder(10, 10, 0, 50));
        secondDiscountField.setBorder(new EmptyBorder(10, 50, 0, 0));
        finalTotalLabel.setBorder(new EmptyBorder(10, 10, 0, 50));
        finalTotalField.setBorder(new EmptyBorder(10, 50, 0, 0));
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.add(Box.createVerticalStrut(20));

        cartTableModel = new DefaultTableModel(cartData, cartColumnNames);
        cartTable = new JTable(cartTableModel);
        cartTable.setDefaultRenderer(Object.class, new CustomTableCellRenderer());
        cartTable.setRowHeight(80);
        centerPanel.add(cartTable.getTableHeader());
        centerPanel.add(cartTable);
        centerPanel.add(attributePanel);

//this is for properly displaying the cart Jframe when the products are added
        cartTableModel.addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                if (e.getType() == TableModelEvent.INSERT) {
                    int rowCount = cartTable.getRowCount();
                    int columnCount = cartTable.getColumnCount();
                    Dimension preferredSize = new Dimension(
                            200 * columnCount ,
                            80 * rowCount +300
                    );
                    cart.setMinimumSize(new Dimension(800, 300));
                    cart.setPreferredSize(preferredSize);
                    cart.pack();
                }
            }
        });


        // Add padding to the sides
        JPanel paddedPanel = new JPanel(new BorderLayout());
        paddedPanel.add(centerPanel, BorderLayout.CENTER);
        paddedPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 50)); // Padding on all sides

        cart.add(paddedPanel, BorderLayout.CENTER);

        cart.pack();
        cart.setLocationRelativeTo(null); // Center the frame on the screen
        cart.setVisible(false);
    }

    //this is when the table row is clicked the relevant information is displayed in the labels
    private class TableSelectionListener implements ListSelectionListener {
        @Override
        public void valueChanged(ListSelectionEvent e) {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = mainTable.getSelectedRow();

                if (selectedRow != -1) {
                    // Change the text of the label when a table row is clicked
                    String selectedID = mainTable.getValueAt(selectedRow, 0).toString();
                    String selectedName = mainTable.getValueAt(selectedRow, 1).toString();
                    String selectedCategory = mainTable.getValueAt(selectedRow, 2).toString();
                    String selectedPrice = mainTable.getValueAt(selectedRow, 3).toString();
                    String selectedInfo = mainTable.getValueAt(selectedRow, 4).toString();
                    String s="";
                    if (products.size() > 0) {
                        s = products.get(selectedRow).getNoOfItems() + "";
                    }


                    if (selectedCategory.equals("Electronics")){
                        String[] infoArray = selectedInfo.split(" ");
                        String warranty = infoArray[0];
                        String brand = infoArray[1];
                        sizeLabel.setText("Warranty:");
                        colorLabel.setText("Brand:");
                        sizeValue.setText(warranty);
                        colorValue.setText(brand);
                        itemsValue.setText(s);
                        idValue.setText(selectedID);
                        nameValue.setText(selectedName);
                        priceValue.setText(selectedPrice);
                        categoryValue.setText(selectedCategory);
                        details.setText("Electronics");
                    }
                    else if (selectedCategory.equals("Clothing")){
                        String[] infoArray = selectedInfo.split(" ");
                        String size = infoArray[0];
                        String color = infoArray[1];
                        sizeLabel.setText("Size:");
                        colorLabel.setText("Color:");
                        sizeValue.setText(size);
                        itemsValue.setText(s);
                        colorValue.setText(color);
                        idValue.setText(selectedID);
                        nameValue.setText(selectedName);
                        priceValue.setText(selectedPrice);
                        categoryValue.setText(selectedCategory);
                        details.setText("Clothing");
                    }


                }
            }
        }
    }


    //this is for reading the electronics from the file and adding it to the table
    public void readElectronicsFromFile() {
        ArrayList<Product> electronicsarray = new ArrayList<>();
        Gson gson = new Gson();
        String electronicsFile = "Electronics.json";
        if (new File(electronicsFile).exists()) {
            try (FileReader reader = new FileReader(electronicsFile)) {
                JsonReader jsonReader = new JsonReader(reader);
                // Use TypeToken to define the map type
                Map<String, Electronics> electronicsMap = gson.fromJson(jsonReader, new TypeToken<Map<String, Electronics>>() {}.getType());
                for (String key : electronicsMap.keySet()) {
                    electronicsarray.add(electronicsMap.get(key));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            products.clear();
            products.addAll(electronicsarray);
        }
    }

    //this is for reading the clothing from the file and adding it to the table
    public void  readClothingFromFile() {
        ArrayList<Product> clothingarray = new ArrayList<>();

        Gson gson = new Gson();
        String clothingFile = "Clothing.json";
        if (new File(clothingFile).exists()) {
            try (FileReader reader = new FileReader(clothingFile)) {
                JsonReader jsonReader = new JsonReader(reader);
                // Use TypeToken to define the map type
                Map<String, Clothing> clothingMap = gson.fromJson(jsonReader, new TypeToken<Map<String, Clothing>>() {}.getType());
                for (String key : clothingMap.keySet()) {
                    clothingarray.add(clothingMap.get(key));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        products.clear();
        products.addAll(clothingarray);

    }
}
