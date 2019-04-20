package fastfood.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "CATEGORY")
public class CategoryEntity extends  BasicEntity{
    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "CATEGORY_SEQ", sequenceName = "CATEGORY_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "CATEGORY_SEQ")
    private  Integer id;

    @Column(name = "is_actived", columnDefinition = "BOOLEAN DEFAULT false")
    private Boolean isActived;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String desciption;

    @Column(name = "level")
    private Integer level;

    @Column(name = "parent_id")
    private Integer parentId;

    @OneToMany(mappedBy = "category")
    private List<ItemCategoryEntity> listItemCategories;

    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY)
    private List<SupplierCategoryEntity> listSupplierCategories;

    public List<SupplierCategoryEntity> getListSupplierCategories() {
        return listSupplierCategories;
    }

    public void setListSupplierCategories(List<SupplierCategoryEntity> listSupplierCategories) {
        this.listSupplierCategories = listSupplierCategories;
    }

    public List<ItemCategoryEntity> getListItemCategories() {
        return listItemCategories;
    }

    public void setListItemCategories(List<ItemCategoryEntity> listItemCategories) {
        this.listItemCategories = listItemCategories;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getActived() {
        return isActived;
    }

    public void setActived(Boolean actived) {
        isActived = actived;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesciption() {
        return desciption;
    }

    public void setDesciption(String desciption) {
        this.desciption = desciption;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }
}
