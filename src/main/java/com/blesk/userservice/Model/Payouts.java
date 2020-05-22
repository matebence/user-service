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
@Entity(name = "Payouts")
@Table(name = "payouts", uniqueConstraints = {@UniqueConstraint(name = "payout_id", columnNames = "payout_id")})
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, scope = Payouts.class)
@SQLDelete(sql = "UPDATE users SET is_deleted = TRUE, deleted_at = NOW() WHERE user_id = ?")
public class Payouts implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payout_id")
    private Long payoutId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users users;

    @NotNull(message = Messages.PAYOUTS_IBAN_NOT_NULL)
    @Column(name = "iban", nullable = false)
    private String iban;

    @NotNull(message = Messages.PAYOUTS_AMOUNT_NOT_NULL)
    @Range(min = 10, max = 99999, message = Messages.PAYOUTS_AMOUNT_RANGE)
    @Column(name = "amount", nullable = false)
    private Double amount;

    @Column(name = "accapted", nullable = false)
    private Boolean accapted;

    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted;

    @Column(name = "created_at", updatable = false, nullable = false)
    private Timestamp createdAt;

    @Column(name = "updated_at")
    private Timestamp updatedAt;

    @Column(name = "deleted_at", updatable = false)
    private Timestamp deletedAt;

    public Payouts(Users users, String iban, Double amount, Boolean accapted, Boolean isDeleted, Timestamp createdAt, Timestamp updatedAt, Timestamp deletedAt) {
        this.users = users;
        this.iban = iban;
        this.amount = amount;
        this.accapted = accapted;
        this.isDeleted = isDeleted;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
    }

    public Payouts(String iban, Double amount, Boolean isDeleted, Boolean accapted, Timestamp createdAt, Timestamp updatedAt, Timestamp deletedAt) {
        this.iban = iban;
        this.amount = amount;
        this.accapted = accapted;
        this.isDeleted = isDeleted;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
    }

    public Payouts(Users users, String iban, Double amount) {
        this.users = users;
        this.iban = iban;
        this.amount = amount;
    }

    public Payouts(String iban, Double amount) {
        this.iban = iban;
        this.amount = amount;
    }

    public Payouts() {
    }

    public Long getPayoutId() {
        return this.payoutId;
    }

    public void setPayoutId(Long payoutId) {
        this.payoutId = payoutId;
    }

    public Users getUsers() {
        return this.users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    public String getIban() {
        return this.iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public Double getAmount() {
        return this.amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Boolean getAccapted() {
        return this.accapted;
    }

    public void setAccapted(Boolean accapted) {
        this.accapted = accapted;
    }

    public Boolean getDeleted() {
        return this.isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        this.isDeleted = deleted;
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
        this.accapted = false;
        this.createdAt = new Timestamp(System.currentTimeMillis());
    }

    @PreUpdate
    protected void preUpdate() {
        this.updatedAt = new Timestamp(System.currentTimeMillis());
    }
}