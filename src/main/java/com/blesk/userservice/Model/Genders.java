package com.blesk.userservice.Model;

import com.blesk.userservice.Value.Messages;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@DynamicInsert
@DynamicUpdate
@Entity(name = "Genders")
@Table(name = "genders", uniqueConstraints = {@UniqueConstraint(name = "gender_id", columnNames = "gender_id"), @UniqueConstraint(name = "gender_name", columnNames = "name")})
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, scope = Genders.class)
public class Genders implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "gender_id")
    private Long genderId;

    @Version
    private Long version;

    @NotNull(message = Messages.GENDERS_NAME_NOT_NULL)
    @Size(min = 5, max = 20, message = Messages.GENDERS_NAME_SIZE)
    @Column(name = "name", nullable = false)
    private String name;

    public Genders(String name) {
        this.name = name;
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
}