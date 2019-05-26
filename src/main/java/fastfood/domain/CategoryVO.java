package fastfood.domain;

import java.util.List;

public class CategoryVO {
    private Integer id;
    private String name;
    private String description;
    private Integer levelCategory;
    private List<ItemVO> items;
    private Integer quantityItems;
    private Integer parentId;

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Integer getQuantityItems() {
        return quantityItems;
    }

    public void setQuantityItems(Integer quantityItems) {
        this.quantityItems = quantityItems;
    }

    public List<ItemVO> getItems() {
        return items;
    }

    public void setItems(List<ItemVO> items) {
        this.items = items;
    }

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getLevelCategory() {
        return levelCategory;
    }

    public void setLevelCategory(Integer levelCategory) {
        this.levelCategory = levelCategory;
    }
}