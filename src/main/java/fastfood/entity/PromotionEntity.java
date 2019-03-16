package fastfood.entity;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "PROMOTION")
public class PromotionEntity extends  BasicEntity{
    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "PROMOTION_SEQ", sequenceName = "PROMOTION_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "PROMOTION_SEQ")
    private  Long id;

    @Column(name = "is_actived", columnDefinition = "BOOLEAN DEFAULT false")
    private Boolean isActived;

    @Column(name = "name")
    private String name;

    @Column(name = "start_at")
    private Date startAt;

    @Column(name = "end_at")
    private Date endAt;

    @OneToMany(mappedBy = "promotion")
    private List<PromotionItemEntity> listPromotionItems;

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

    public Date getStartAt() {
        return startAt;
    }

    public void setStartAt(Date startAt) {
        this.startAt = startAt;
    }

    public Date getEndAt() {
        return endAt;
    }

    public void setEndAt(Date endAt) {
        this.endAt = endAt;
    }

    public List<PromotionItemEntity> getListPromotionItems() {
        return listPromotionItems;
    }

    public void setListPromotionItems(List<PromotionItemEntity> listPromotionItems) {
        this.listPromotionItems = listPromotionItems;
    }
}
