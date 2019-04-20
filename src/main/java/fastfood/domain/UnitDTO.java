package fastfood.domain;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.util.List;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class UnitDTO {
    private String id;
    private String name;
    private String syntax;
    private List<ItemDTO> listItems;

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

    public String getSyntax() {
        return syntax;
    }

    public void setSyntax(String syntax) {
        this.syntax = syntax;
    }

    public List<ItemDTO> getListItems() {
        return listItems;
    }

    public void setListItems(List<ItemDTO> listItems) {
        this.listItems = listItems;
    }
}
