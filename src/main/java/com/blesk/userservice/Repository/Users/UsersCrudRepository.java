package com.blesk.userservice.Repository.Users;

import com.blesk.userservice.Model.Users;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersCrudRepository extends CrudRepository<Users, Long> {
}