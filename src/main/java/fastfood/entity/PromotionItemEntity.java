package fastfood.entity;

import javax.persistence.*;

@Entity
@Table(name = "PROMOTION_ITEM")
public class PromotionItemEntity extends  BasicEntity {
    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "PROMOTION_ITEM_SEQ", sequenceName = "PROMOTION_ITEM_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "PROMOTION_ITEM_SEQ")
    private  Long id;

    @Column(name = "is_actived", columnDefinition = "BOOLEAN DEFAULT false")
    private Boolean isActived;

    @Column(name = "percent")
    private Integer percent;

    @ManyToOne(optional = false)
    @JoinColumn(name = "item_id", foreignKey = @ForeignKey(name = "FK_ITEM_03"))
    private  ItemEntity item;

    @ManyToOne(optional = false)
    @JoinColumn(name = "promotion_id", foreignKey = @ForeignKey(name = "FK_PROMOTION_01"))
    private PromotionEntity promotion;

}
