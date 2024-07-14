package entity.entity;

import jakarta.persistence.*;

@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IDProduct", nullable = false)
    private Integer id;

    @Column(name = "NameProduct", length = 20)
    private String nameProduct;

    @Column(name = "Price")
    private Double price;

    @Column(name = "QuantityLeft")
    private Integer quantityLeft;

    @Column(name = "ImageProduct", length = 150)
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

    public String getNameProduct() {
        return nameProduct;
    }

    public void setNameProduct(String nameProduct) {
        this.nameProduct = nameProduct;
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