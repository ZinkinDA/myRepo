package ru.lab.fsm_mashine_2.FSM.Product;

public class Product {
    private int id;

    private String name;

    private int price;

    private String description;

    public Product(int id, String name,int price, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
    }

    public Product(String name, int price, String description) {
        this.name = name;
        this.price = price;
        this.description = description;
    }

    public Product() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
