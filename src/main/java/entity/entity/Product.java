package entity.entity;

import jakarta.persistence.*;

import java.time.Instant;

@Entity
public class Product {
    @Id
    @Column(name = "IDProduct", nullable = false)
    private Integer id;

    @Column(name = "Name", length = 20)
    private String name;

    @Column(name = "Price")
    private Instant price;

    @Column(name = "Quantity")
    private Double quantity;

    @Column(name = "ImageProduct", length = 50)
    private String imageProduct;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IDCategory")
    private CategoryProduct iDCategory;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Instant getPrice() {
        return price;
    }

    public void setPrice(Instant price) {
        this.price = price;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public String getImageProduct() {
        return imageProduct;
    }

    public void setImageProduct(String imageProduct) {
        this.imageProduct = imageProduct;
    }

    public CategoryProduct getIDCategory() {
        return iDCategory;
    }

    public void setIDCategory(CategoryProduct iDCategory) {
        this.iDCategory = iDCategory;
    }

}