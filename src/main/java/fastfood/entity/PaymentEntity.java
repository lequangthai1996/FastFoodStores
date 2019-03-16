package fastfood.entity;

import sun.tools.tree.OrExpression;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "PAYMENT")
public class PaymentEntity extends BasicEntity {
    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "PAYMENT_SEQ", sequenceName = "PAYMENT_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "PAYMENT_SEQ")
    private Long id;

    @Column(name = "pay_email")
    private String payEmail;

    @Column(name = "transaction_amount")
    private Double transactionAmount;

    @Column(name = "transaction_time")
    private Date transactionTime;

    @OneToOne(optional = false)
    @JoinColumn(name = "order_id", foreignKey = @ForeignKey(name = "FK_ORDER_01"))
    private OrderEntity order;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPayEmail() {
        return payEmail;
    }

    public void setPayEmail(String payEmail) {
        this.payEmail = payEmail;
    }

    public Double getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(Double transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public Date getTransactionTime() {
        return transactionTime;
    }

    public void setTransactionTime(Date transactionTime) {
        this.transactionTime = transactionTime;
    }

    public OrderEntity getOrder() {
        return order;
    }

    public void setOrder(OrderEntity order) {
        this.order = order;
    }
}
