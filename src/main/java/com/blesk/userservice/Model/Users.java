package com.blesk.userservice.Model;

import com.blesk.userservice.Validator.Contains;
import com.blesk.userservice.Value.Messages;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.hibernate.annotations.*;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

@DynamicInsert
@DynamicUpdate
@Entity(name = "Users")
@Table(name = "users", uniqueConstraints = {@UniqueConstraint(name = "user_id", columnNames = "user_id"), @UniqueConstraint(name = "user_account_id", columnNames = "account_id"), @UniqueConstraint(name = "user_tel", columnNames = "tel")})
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, scope = Users.class)
@SQLDelete(sql = "UPDATE users SET is_deleted = TRUE, deleted_at = NOW() WHERE user_id = ?")
public class Users implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Transient
    @JsonIgnore
    private Boolean cached = false;

    @Transient
    @JsonProperty
    private String userName;

    @Transient
    @JsonProperty
    private String email;

    @JsonProperty
    @NotNull(message = Messages.USERS_FIRST_ACCOUNT_NOT_NULL)
    @Column(name = "account_id")
    private Long accountId;

    @Valid
    @OneToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}, fetch = FetchType.EAGER, mappedBy = "users")
    private Places places;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER, mappedBy = "users")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Set<Payments> payments = new HashSet<Payments>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER, mappedBy = "users")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Set<Payouts> payouts = new HashSet<Payouts>();

    @NotNull(message = Messages.USERS_FIRST_NAME_NOT_NULL)
    @Size(min = 3, max = 20, message = Messages.USERS_FIRST_NAME_SIZE)
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @NotNull(message = Messages.USERS_LAST_NAME_NOT_NULL)
    @Size(min = 3, max = 20, message = Messages.USERS_LAST_NAME_SIZE)
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Contains(message = Messages.USERS_GENDER_COTNAINS)
    @NotNull(message = Messages.USERS_GENDER_NOT_NULL)
    @Size(min = 3, max = 20, message = Messages.USERS_GENDER_SIZE)
    @Column(name = "gender", nullable = false)
    private String gender;

    @NotNull(message = Messages.USERS_BALANCE_NOT_NULL)
    @Range(min = 10, max = 99999, message = Messages.USERS_BALANCE_RANGE)
    @Column(name = "balance", nullable = false)
    private Double balance;

    @NotNull(message = Messages.USERS_TEL_NOT_NULL)
    @Size(min = 9, max = 15, message = Messages.USERS_TEL_SIZE)
    @Column(name = "tel", nullable = false)
    private String tel;

    @Column(name = "img")
    @Size(min = 5, max = 255, message = Messages.USERS_IMG_SIZE)
    private String img;

    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted;

    @Column(name = "created_at", updatable = false, nullable = false)
    private Timestamp createdAt;

    @Column(name = "updated_at")
    private Timestamp updatedAt;

    @Column(name = "deleted_at", updatable = false)
    private Timestamp deletedAt;

    public Users(Boolean cached, String userName, String email, Long accountId, Places places, String firstName, String lastName, String gender, Double balance, String tel, String img, Boolean isDeleted, Timestamp createdAt, Timestamp updatedAt, Timestamp deletedAt) {
        this.cached = cached;
        this.userName = userName;
        this.email = email;
        this.accountId = accountId;
        this.places = places;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.balance = balance;
        this.tel = tel;
        this.img = img;
        this.isDeleted = isDeleted;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
    }

    public Users(String userName, String email, Long accountId, Places places, String firstName, String lastName, String gender, Double balance, String tel, String img, Boolean isDeleted, Timestamp createdAt, Timestamp updatedAt, Timestamp deletedAt) {
        this.userName = userName;
        this.email = email;
        this.accountId = accountId;
        this.places = places;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.balance = balance;
        this.tel = tel;
        this.img = img;
        this.isDeleted = isDeleted;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
    }

    public Users(String userName, String email, Long accountId, Places places, String firstName, String lastName, String gender, Double balance, String tel, String img) {
        this.userName = userName;
        this.email = email;
        this.accountId = accountId;
        this.places = places;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.balance = balance;
        this.tel = tel;
        this.img = img;
    }

    public Users(Boolean cached, Long accountId, String userName, String email){
        this.cached = cached;
        this.accountId = accountId;
        this.userName = userName;
        this.email = email;
    }

    public Users() {
    }

    public Long getUserId() {
        return this.userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Boolean getCached() {
        return this.cached;
    }

    public void setCached(Boolean cached) {
        this.cached = cached;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getAccountId() {
        return this.accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public Places getPlaces() {
        return this.places;
    }

    public void setPlaces(Places places) {
        this.places = places;
        if (this.places != null)
            this.places.setUsers(this);
    }

    public void addPayment(Payments payments) {
        this.payments.add(payments);
        payments.setUsers(this);
    }

    public void removePayment(Payments payments) {
        this.payments.remove(payments);
        payments.setUsers(null);
    }

    public Set<Payments> getPayment() {
        return this.payments;
    }

    public void addPayout(Payouts payouts) {
        this.payouts.add(payouts);
        payouts.setUsers(this);
    }

    public void removePayout(Payouts payouts) {
        this.payouts.remove(payouts);
        payouts.setUsers(null);
    }

    public Set<Payouts> getPayout() {
        return this.payouts;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGender() {
        return this.gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Double getBalance() {
        return this.balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public String getTel() {
        return this.tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getImg() {
        return this.img;
    }

    public void setImg(String img) {
        this.img = img;
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
        this.createdAt = new Timestamp(System.currentTimeMillis());
    }

    @PreUpdate
    protected void preUpdate() {
        this.updatedAt = new Timestamp(System.currentTimeMillis());
    }
}