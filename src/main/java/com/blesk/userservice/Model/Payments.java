package com.blesk.userservice.Model;

import com.blesk.userservice.Value.Messages;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.sql.Timestamp;

@DynamicInsert
@DynamicUpdate
@Entity(name = "Payments")
@Table(name = "payments", uniqueConstraints = {@UniqueConstraint(name = "payment_id", columnNames = "payment_id"), @UniqueConstraint(name = "payment_credit_card", columnNames = "credit_card"), @UniqueConstraint(name = "payment_charge", columnNames = "charge"), @UniqueConstraint(name = "payment_refund", columnNames = "refund")})
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, scope = Payments.class)
@SQLDelete(sql = "UPDATE payments SET is_deleted = TRUE, deleted_at = NOW() WHERE payment_id = ?")
public class Payments implements Serializable {

    public enum Currency {
        EUR, USD;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id")
    private Long paymentId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users users;

    @NotNull(message = Messages.PAYMENTS_CREDIT_CARD_NOT_NULL)
    @Column(name = "credit_card", nullable = false)
    private String creditCard;

    @Transient
    private Integer expMonth;

    @Transient
    private Integer expYear;

    @Transient
    private String cvc;

    @Column(name = "charge", nullable = false)
    private String charge;

    @NotNull(message = Messages.PAYMENTS_AMOUNT_NOT_NULL)
    @Range(min = 10, max = 99999, message = Messages.PAYMENTS_AMOUNT_RANGE)
    @Column(name = "amount", nullable = false)
    private Double amount;

    @NotNull(message = Messages.PAYMENTS_CURRENCY_NOT_NULL)
    @Enumerated(EnumType.STRING)
    @Column(name = "currency", nullable = false)
    private Currency currency;

    @Column(name = "refunded", nullable = false)
    private Boolean refunded;

    @Column(name = "refund")
    private String refund;

    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted;

    @Column(name = "created_at", updatable = false, nullable = false)
    private Timestamp createdAt;

    @Column(name = "updated_at")
    private Timestamp updatedAt;

    @Column(name = "deleted_at", updatable = false)
    private Timestamp deletedAt;

    public Payments(Users users, String creditCard, Integer expMonth, Integer expYear, String cvc,  String charge, Double amount, Boolean refunded, String refund, Boolean isDeleted, Timestamp createdAt, Timestamp updatedAt, Timestamp deletedAt) {
        this.users = users;
        this.creditCard = creditCard;
        this.expMonth = expMonth;
        this.expYear = expYear;
        this.cvc = cvc;
        this.charge = charge;
        this.amount = amount;
        this.refunded = refunded;
        this.refund = refund;
        this.isDeleted = isDeleted;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
    }

    public Payments(Users users, String creditCard, String charge, Double amount, Currency currency, Boolean refunded, String refund, Boolean isDeleted, Timestamp createdAt, Timestamp updatedAt, Timestamp deletedAt) {
        this.users = users;
        this.creditCard = creditCard;
        this.charge = charge;
        this.amount = amount;
        this.currency = currency;
        this.refunded = refunded;
        this.refund = refund;
        this.isDeleted = isDeleted;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
    }

    public Payments(String creditCard, String charge, Double amount, Currency currency, Boolean refunded, String refund, Boolean isDeleted, Timestamp createdAt, Timestamp updatedAt, Timestamp deletedAt) {
        this.creditCard = creditCard;
        this.charge = charge;
        this.amount = amount;
        this.currency = currency;
        this.refunded = refunded;
        this.refund = refund;
        this.isDeleted = isDeleted;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
    }

    public Payments(String creditCard, String charge, Double amount, Currency currency, Boolean refunded, String refund) {
        this.creditCard = creditCard;
        this.charge = charge;
        this.amount = amount;
        this.currency = currency;
        this.refunded = refunded;
        this.refund = refund;
    }

    public Payments() {
    }

    public Long getPaymentId() {
        return this.paymentId;
    }

    public void setPaymentId(Long paymentId) {
        this.paymentId = paymentId;
    }

    public Users getUsers() {
        return this.users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    public String getCreditCard() {
        return this.creditCard;
    }

    public void setCreditCard(String creditCartd) {
        this.creditCard = creditCartd;
    }

    public Integer getExpMonth() {
        return this.expMonth;
    }

    public void setExpMonth(Integer expMonth) {
        this.expMonth = expMonth;
    }

    public Integer getExpYear() {
        return this.expYear;
    }

    public void setExpYear(Integer expYear) {
        this.expYear = expYear;
    }

    public String getCvc() {
        return this.cvc;
    }

    public void setCvc(String cvc) {
        this.cvc = cvc;
    }

    public String getCharge() {
        return this.charge;
    }

    public void setCharge(String charge) {
        this.charge = charge;
    }

    public Double getAmount() {
        return this.amount;
    }

    public Currency getCurrency() {
        return this.currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Boolean getRefunded() {
        return this.refunded;
    }

    public void setRefunded(Boolean refunded) {
        this.refunded = refunded;
    }

    public String getRefund() {
        return this.refund;
    }

    public void setRefund(String refund) {
        this.refund = refund;
    }

    public Boolean getDeleted() {
        return this.isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

    public Timestamp getCreatedAt() {
        return this.createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getUpdatedAt() {
        return this.updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Timestamp getDeletedAt() {
        return this.deletedAt;
    }

    public void setDeletedAt(Timestamp deletedAt) {
        this.deletedAt = deletedAt;
    }

    @PrePersist
    protected void prePersist() {
        this.isDeleted = false;
        this.refunded = false;
        this.createdAt = new Timestamp(System.currentTimeMillis());
    }

    @PreUpdate
    protected void preUpdate() {
        this.updatedAt = new Timestamp(System.currentTimeMillis());
    }
}