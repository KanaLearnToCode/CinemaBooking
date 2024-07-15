package org.group3.cinemabooking_2.Management;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Product {
    private int idProduct;
    private String name;
    private float price;
    private Integer quantityLeft;
    private String imageProduct;
    private int idCategory;
    private ImageView imageView;

    // Constructor
    public Product(int idProduct, String name, float price, Integer quantityLeft, String imageProduct, int idCategory) {
        this.idProduct = idProduct;
        this.name = name;
        this.price = price;
        this.quantityLeft = quantityLeft;
        this.imageProduct = imageProduct;
        this.idCategory = idCategory;
        this.imageView = new ImageView(new Image(imageProduct));
        this.imageView.setFitHeight(50); // Set desired height
        this.imageView.setFitWidth(50);  // Set desired width
    }

    // Getters and Setters
    public int getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(int idProduct) {
        this.idProduct = idProduct;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public Integer getQuantityLeft() {
        return quantityLeft;
    }

    public void setQuantityLeft(Integer quantityLeft) {
        this.quantityLeft = quantityLeft;
    }

    public String getImageProduct() {
        return imageProduct;
    }

    public void setImageProduct(String imageProduct) {
        this.imageProduct = imageProduct;
    }

    public int getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(int idCategory) {
        this.idCategory = idCategory;
    }

    public ImageView getImageView() {
        return imageView;
    }
}
