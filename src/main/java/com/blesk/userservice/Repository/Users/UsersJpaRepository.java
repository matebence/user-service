package com.blesk.userservice.Repository.Users;

import com.blesk.userservice.Model.Redis.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersJpaRepository extends JpaRepository<Users, Long> {
}