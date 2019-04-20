package fastfood.entity;

import org.hibernate.validator.constraints.EAN;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "UNIT")
public class UnitEntity extends  BasicEntity{
    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "UNIT_SEQ", sequenceName = "UNIT_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "UNIT_SEQ")
    private  Long id;

    @Column(name = "is_actived", columnDefinition = "BOOLEAN DEFAULT false")
    private Boolean isActived;

    @Column(name = "name")
    private String name;

    @Column(name = "syntax")
    private String syntax;

    @OneToMany(mappedBy = "unit", fetch = FetchType.LAZY)
    private List<ItemEntity> listItems;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public String getSyntax() {
        return syntax;
    }

    public void setSyntax(String syntax) {
        this.syntax = syntax;
    }

    public List<ItemEntity> getListItems() {
        return listItems;
    }

    public void setListItems(List<ItemEntity> listItems) {
        this.listItems = listItems;
    }
}

