package com.blesk.userservice.Repository.Places;

import com.blesk.userservice.Model.Places;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlacesJpaRepository extends JpaRepository<Places, Long> {
}