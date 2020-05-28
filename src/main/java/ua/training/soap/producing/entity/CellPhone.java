package ua.training.soap.producing.entity;

import javax.persistence.*;

@Entity
@Table(name = "cell_phone")
public class CellPhone {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "brand_name")
    private String brandName;

    @Column(name = "model")
    private String model;

    @Column(name = "price")
    private Double price;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "operating_system")
    private OperatingSystem operatingSystem;

    public CellPhone() {
    }

    public CellPhone(Long id, String brandName, String model, Double price, OperatingSystem operatingSystem) {
        this.id = id;
        this.brandName = brandName;
        this.model = model;
        this.price = price;
        this.operatingSystem = operatingSystem;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public OperatingSystem getOperatingSystem() {
        return operatingSystem;
    }

    public void setOperatingSystem(OperatingSystem operatingSystem) {
        this.operatingSystem = operatingSystem;
    }

}
