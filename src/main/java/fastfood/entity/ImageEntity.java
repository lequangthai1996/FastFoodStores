package fastfood.entity;

import javax.persistence.*;

@Entity
@Table(name = "IMAGE")
public class ImageEntity extends BasicEntity {
    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "IMAGE_SEQ", sequenceName = "IMAGE_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "IMAGE_SEQ")
    private  Long id;

    @Column(name = "is_actived", columnDefinition = "BOOLEAN DEFAULT false")
    private Boolean isActived;

    @Column(name = "url")
    private String url;

    @ManyToOne(optional = false)
    @JoinColumn(name = "item_id", foreignKey = @ForeignKey(name = "FK_ITEM_04"))
    private ItemEntity item;

}

