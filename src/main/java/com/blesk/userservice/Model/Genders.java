package com.blesk.userservice.Model;

import com.blesk.userservice.Value.Messages;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SQLDelete;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.sql.Timestamp;

@DynamicInsert
@DynamicUpdate
@Entity(name = "Genders")
@Table(name = "genders", uniqueConstraints = {@UniqueConstraint(name = "gender_id", columnNames = "gender_id"), @UniqueConstraint(name = "gender_name", columnNames = "name")})
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, scope = Genders.class)
@SQLDelete(sql = "UPDATE genders SET is_deleted = TRUE, deleted_at = NOW() WHERE gender_id = ?")
public class Genders implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "gender_id")
    private Long genderId;

    @NotNull(message = Messages.GENDERS_NAME_NOT_NULL)
    @Size(min = 3, max = 20, message = Messages.GENDERS_NAME_SIZE)
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted;

    @Column(name = "created_at", updatable = false, nullable = false)
    private Timestamp createdAt;

    @Column(name = "updated_at")
    private Timestamp updatedAt;

    @Column(name = "deleted_at", updatable = false)
    private Timestamp deletedAt;

    public Genders(String name) {
        this.name = name;
    }

    public Genders(String name, Boolean isDeleted, Timestamp createdAt, Timestamp updatedAt, Timestamp deletedAt) {
        this.name = name;
        this.isDeleted = isDeleted;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
    }

    public Genders() {
    }

    public Long getGenderId() {
        return this.genderId;
    }

    public void setGenderId(Long genderId) {
        this.genderId = genderId;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
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