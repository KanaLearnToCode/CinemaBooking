package entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class CategoryProduct {
    @Id
    @Column(name = "IDCategory", nullable = false, length = 10)
    private String iDCategory;

    @Column(name = "ProductName", length = 20)
    private String productName;

    public String getIDCategory() {
        return iDCategory;
    }

    public void setIDCategory(String iDCategory) {
        this.iDCategory = iDCategory;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

}