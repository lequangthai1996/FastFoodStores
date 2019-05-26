package fastfood.build;

import fastfood.domain.CategoryVO;
import fastfood.domain.ItemVO;

import java.util.List;

public final class CategoryVOBuilder {
    private Integer id;
    private String name;
    private String description;
    private Integer levelCategory;
    private List<ItemVO> items;
    private Integer quantityItems;
    private Integer parentId;

    private CategoryVOBuilder() {
    }

    public static CategoryVOBuilder aCategoryVO() {
        return new CategoryVOBuilder();
    }

    public CategoryVOBuilder withId(Integer id) {
        this.id = id;
        return this;
    }

    public CategoryVOBuilder withParentId(Integer id) {
        this.parentId = id;
        return this;
    }

    public CategoryVOBuilder withName(String name) {
        this.name = name;
        return this;
    }
    public CategoryVOBuilder withQuantityItems(Integer quantityItems) {
        this.quantityItems = quantityItems;
        return this;
    }


    public CategoryVOBuilder withDescription(String description) {
        this.description = description;
        return this;
    }

    public CategoryVOBuilder withLevelCategory(Integer levelCategory) {
        this.levelCategory = levelCategory;
        return this;
    }

    public CategoryVOBuilder withItems(List<ItemVO> items) {
        this.items = items;
        return this;
    }

    public CategoryVO build() {
        CategoryVO categoryVO = new CategoryVO();
        categoryVO.setId(id);
        categoryVO.setName(name);
        categoryVO.setDescription(description);
        categoryVO.setLevelCategory(levelCategory);
        categoryVO.setItems(items);
        categoryVO.setQuantityItems(quantityItems);
        categoryVO.setParentId(parentId);
        return categoryVO;
    }
}
