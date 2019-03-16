package fastfood.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "SHIP")
public class ShipEntity implements Serializable {
    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SHIP_SEQ", sequenceName = "SHIP_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "SHIP_SEQ")
    private  Long id;

    @Column(name = "distance")
    private Double distance;

    @Column(name = "price")
    private Double price;

    @OneToMany(mappedBy = "ship")
    private List<OrderEntity> listOrders;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public List<OrderEntity> getListOrders() {
        return listOrders;
    }

    public void setListOrders(List<OrderEntity> listOrders) {
        this.listOrders = listOrders;
    }
}
