package com.blesk.userservice.Model;

import com.blesk.userservice.Value.Messages;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@DynamicInsert
@DynamicUpdate
@Entity(name = "Places")
@Table(name = "places", uniqueConstraints = {@UniqueConstraint(name = "place_id", columnNames = "place_id")})
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, scope = Places.class)
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
}
