package fastfood.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@MappedSuperclass
public abstract class BasicEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "CREATED_BY")
    protected Long createdBy;

    @Column(name = "CREATED_DATE")
    protected Date createdDate;

    @Column(name = "UPDATED_BY")
    protected Long updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "UPDATED_DATE")
    protected Date updatedDate;

    @Column(name = "IS_DELETED", columnDefinition = "BOOLEAN DEFAULT false")
    protected Boolean isDeleted;

    public BasicEntity(Long createdBy, Date createdDate, Long updatedBy, Date updatedDate, Boolean isDeleted) {
        super();
        this.createdBy = createdBy;
        this.createdDate = createdDate;
        this.updatedBy = updatedBy;
        this.updatedDate = updatedDate;
        this.isDeleted = isDeleted;
    }

    public BasicEntity() {
        super();
    }

    @PrePersist
    protected void onCreate() {
        this.createdDate = new Date();
        this.updatedDate = new Date();
        this.isDeleted = false;
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedDate = new Date();
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Long getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }


}

