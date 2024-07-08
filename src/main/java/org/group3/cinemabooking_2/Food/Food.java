package org.group3.cinemabooking_2.Food;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Food {
    private int idProduct;
    private ImageView imageProduct;
    private String name;
    private Integer quantityLeft;
    private float price;

    public Food() {}

    public Food(int idProduct, String imgPath, String name, Integer quantityLeft, float price) {
        this.idProduct = idProduct;
        this.imageProduct = new ImageView(new Image(imgPath));
        this.imageProduct.setFitHeight(100);
        this.imageProduct.setFitWidth(200);
        this.name = name;
        this.quantityLeft = quantityLeft;
        this.price = price;
    }

    public int getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(int idProduct) {
        this.idProduct = idProduct;
    }

    public ImageView getImageProduct() {
        return imageProduct;
    }

    public void setImageProduct(ImageView imageProduct) {
        this.imageProduct = imageProduct;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getQuantityLeft() {
        return quantityLeft;
    }

    public void setQuantityLeft(Integer quantityLeft) {
        this.quantityLeft = quantityLeft;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Food{" +
                "idProduct=" + idProduct +
                ", imageProduct=" + imageProduct +
                ", name='" + name + '\'' +
                ", quantityLeft=" + quantityLeft +
                ", price=" + price +
                '}';
    }
}
