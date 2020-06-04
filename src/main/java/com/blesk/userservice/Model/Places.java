package com.blesk.userservice.Model;

import com.blesk.userservice.Value.Messages;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.sql.Timestamp;

@DynamicInsert
@DynamicUpdate
@Entity(name = "Places")
@Table(name = "places", uniqueConstraints = {@UniqueConstraint(name = "place_id", columnNames = "place_id")})
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, scope = Places.class)
@SQLDelete(sql = "UPDATE places SET is_deleted = TRUE, deleted_at = NOW() WHERE place_id = ?")
public class Places implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "place_id")
    private Long placeId;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Users users;

    @NotNull(message = Messages.PLACES_COUNTRY_NOT_NULL)
    @Size(min = 5, max = 64, message = Messages.PLACES_COUNTRY_SIZE)
    @Column(name = "country", nullable = false)
    private String country;

    @NotNull(message = Messages.PLACES_REGION_NOT_NULL)
    @Size(min = 5, max = 64, message = Messages.PLACES_REGION_SIZE)
    @Column(name = "region", nullable = false)
    private String region;

    @NotNull(message = Messages.PLACES_DISTRICT_NOT_NULL)
    @Size(min = 5, max = 64, message = Messages.PLACES_DISTRICT_SIZE)
    @Column(name = "district", nullable = false)
    private String district;

    @NotNull(message = Messages.PLACES_PLACE_NOT_NULL)
    @Size(min = 5, max = 32, message = Messages.PLACES_PLACE_SIZE)
    @Column(name = "place", nullable = false)
    private String place;

    @NotNull(message = Messages.PLACES_STREET_NOT_NULL)
    @Size(min = 5, max = 32, message = Messages.PLACES_STREET_SIZE)
    @Column(name = "street", nullable = false)
    private String street;

    @NotNull(message = Messages.PLACES_ZIP_NOT_NULL)
    @Column(name = "zip", nullable = false)
    private Integer zip;

    @NotNull(message = Messages.PLACES_CODE_NOT_NULL)
    @Size(min = 2, max = 2, message = Messages.PLACES_CODE_SIZE)
    @Column(name = "code", nullable = false)
    private String code;

    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted;

    @Column(name = "created_at", updatable = false, nullable = false)
    private Timestamp createdAt;

    @Column(name = "updated_at")
    private Timestamp updatedAt;

    @Column(name = "deleted_at", updatable = false)
    private Timestamp deletedAt;

    public Places(Users users, String country, String region, String district, String place, String street, Integer zip, String code, Boolean isDeleted, Timestamp createdAt, Timestamp updatedAt, Timestamp deletedAt) {
        this.users = users;
        this.country = country;
        this.region = region;
        this.district = district;
        this.place = place;
        this.street = street;
        this.zip = zip;
        this.code = code;
        this.isDeleted = isDeleted;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
    }

    public Places(Users users, String country, String region, String district, String place, String street, Integer zip, String code) {
        this.users = users;
        this.country = country;
        this.region = region;
        this.district = district;
        this.place = place;
        this.street = street;
        this.zip = zip;
        this.code = code;
    }

    public Places(String country, String region, String district, String place, String street, Integer zip, String code) {
        this.country = country;
        this.region = region;
        this.district = district;
        this.place = place;
        this.street = street;
        this.zip = zip;
        this.code = code;
    }

    public Places(Users users) {
        this.users = users;
    }

    public Places() {
    }

    public Long getPlaceId() {
        return this.placeId;
    }

    public void setPlaceId(Long placeId) {
        this.placeId = placeId;
    }

    public Users getUsers() {
        return this.users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    public String getCountry() {
        return this.country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getRegion() {
        return this.region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getDistrict() {
        return this.district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getPlace() {
        return this.place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getStreet() {
        return this.street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public Integer getZip() {
        return this.zip;
    }

    public void setZip(Integer zip) {
        this.zip = zip;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
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
        this.createdAt = new Timestamp(System.currentTimeMillis());
    }

    @PreUpdate
    protected void preUpdate() {
        this.updatedAt = new Timestamp(System.currentTimeMillis());
    }
}