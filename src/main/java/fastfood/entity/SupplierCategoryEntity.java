package fastfood.entity;

import javax.persistence.*;

@Entity
@Table(name = "SUPPLIER_CATEGORY")
public class SupplierCategoryEntity extends BasicEntity {

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SUPPLIER_CATEGORY_SEQ", sequenceName = "SUPPLIER_CATEGORY_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "SUPPLIER_CATEGORY_SEQ")
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "supplier_id", foreignKey = @ForeignKey(name = "FK_SUPPLIER_03"))
    private SupplierEntity supplier;

    @ManyToOne(optional = false)
    @JoinColumn(name = "category_id", foreignKey = @ForeignKey(name = "FK_CATEGORY_02"))
    private CategoryEntity category;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SupplierEntity getSupplier() {
        return supplier;
    }

    public void setSupplier(SupplierEntity supplier) {
        this.supplier = supplier;
    }

    public CategoryEntity getCategory() {
        return category;
    }

    public void setCategory(CategoryEntity category) {
        this.category = category;
    }
}
