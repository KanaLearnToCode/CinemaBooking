package entity.entity;

import jakarta.persistence.*;

@Entity
public class Product {
    @Id
    @Column(name = "IDProduct", nullable = false)
    private Integer id;

    @Column(name = "Name", length = 20)
    private String name;

    @Column(name = "Price")
    private Double price;

    @Column(name = "QuantityLeft")
    private Integer quantityLeft;

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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
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

    public CategoryProduct getIDCategory() {
        return iDCategory;
    }

    public void setIDCategory(CategoryProduct iDCategory) {
        this.iDCategory = iDCategory;
    }

}