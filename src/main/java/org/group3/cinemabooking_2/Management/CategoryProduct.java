package org.group3.cinemabooking_2.Management;

public class CategoryProduct {
    private int idCategory;
    private String productName;

    // Constructor, getters and setters

    public CategoryProduct(int idCategory, String productName) {
        this.idCategory = idCategory;
        this.productName = productName;
    }

    public int getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(int idCategory) {
        this.idCategory = idCategory;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }
}
