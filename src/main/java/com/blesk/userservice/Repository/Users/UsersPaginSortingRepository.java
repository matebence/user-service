package com.blesk.userservice.Repository.Users;

import com.blesk.userservice.Model.Redis.Users;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersPaginSortingRepository extends PagingAndSortingRepository<Users, Long> {
}