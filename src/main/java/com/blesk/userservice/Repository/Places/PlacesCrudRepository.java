package com.blesk.userservice.Repository.Places;

import com.blesk.userservice.Model.Places;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlacesCrudRepository extends CrudRepository<Places, Long> {
}