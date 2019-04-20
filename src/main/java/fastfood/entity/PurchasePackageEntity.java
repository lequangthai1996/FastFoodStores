package fastfood.entity;

import javax.persistence.*;

@Entity
@Table(name = "PURCHASE_PACKAGE")
public class PurchasePackageEntity {
    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "PURCHASE_PACKAGE_SEQ", sequenceName = "PURCHASE_PACKAGE_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "PURCHASE_PACKAGE_SEQ")
    private String id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "PERIOD")
    private String period;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }
}
