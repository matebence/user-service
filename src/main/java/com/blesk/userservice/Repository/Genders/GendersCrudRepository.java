package com.blesk.userservice.Repository.Genders;

import com.blesk.userservice.Model.Genders;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GendersCrudRepository extends CrudRepository<Genders, Long> {
}