package fastfood.entity;

import javax.persistence.*;

@Entity
@Table(name="ROLE")
public class RoleEntity extends  BasicEntity {
    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "ROLE_SEQ", sequenceName = "ROLE_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "ROLE_SEQ")
    private  Long id;

    @Column(name="name")
    private String name;

    @Column(name="description")
    private String description;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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
}
