package entity;

import jakarta.persistence.*;

@Entity
public class OrdersDetail {
    @Id
    @Column(name = "IDOrdersDetail", nullable = false, length = 10)
    private String iDOrdersDetail;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IDOrder")
    private Order iDOrder;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IDProduct")
    private Product iDProduct;

    @Column(name = "Quantity")
    private Integer quantity;

    @Column(name = "Price")
    private Double price;

    @Column(name = "Total")
    private Double total;

    public String getIDOrdersDetail() {
        return iDOrdersDetail;
    }

    public void setIDOrdersDetail(String iDOrdersDetail) {
        this.iDOrdersDetail = iDOrdersDetail;
    }

    public Order getIDOrder() {
        return iDOrder;
    }

    public void setIDOrder(Order iDOrder) {
        this.iDOrder = iDOrder;
    }

    public Product getIDProduct() {
        return iDProduct;
    }

    public void setIDProduct(Product iDProduct) {
        this.iDProduct = iDProduct;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

}