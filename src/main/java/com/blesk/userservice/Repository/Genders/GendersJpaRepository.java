package com.blesk.userservice.Repository.Genders;

import com.blesk.userservice.Model.Genders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GendersJpaRepository extends JpaRepository<Genders, Long> {
}