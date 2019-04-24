package fastfood.entity;

import javax.persistence.*;

@Entity
@Table(name = "PURCHASE_PACKAGE")
public class PurchasePackageEntity extends BasicEntity {
    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "PURCHASE_PACKAGE_SEQ", sequenceName = "PURCHASE_PACKAGE_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "PURCHASE_PACKAGE_SEQ")
    private Integer id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "PERIOD")
    private Integer period;

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

    public Integer getPeriod() {
        return period;
    }

    public void setPeriod(Integer period) {
        this.period = period;
    }
}
