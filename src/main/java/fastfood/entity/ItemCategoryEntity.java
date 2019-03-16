package fastfood.entity;

import javax.persistence.*;

@Entity
@Table(name = "ITEM_CATEGORY")
public class ItemCategoryEntity extends  BasicEntity{
    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "ITEM_CATEGORY_SEQ", sequenceName = "ITEM_CATEGORY_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "ITEM_CATEGORY_SEQ")
    private  Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "category_id", foreignKey = @ForeignKey(name = "FK_CATEGORY_01"))
    private CategoryEntity category;

    @ManyToOne(optional = false)
    @JoinColumn(name = "item_id", foreignKey = @ForeignKey(name = "FK_ITEM_02"))
    private ItemEntity item;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CategoryEntity getCategory() {
        return category;
    }

    public void setCategory(CategoryEntity category) {
        this.category = category;
    }

    public ItemEntity getItem() {
        return item;
    }

    public void setItem(ItemEntity item) {
        this.item = item;
    }
}
