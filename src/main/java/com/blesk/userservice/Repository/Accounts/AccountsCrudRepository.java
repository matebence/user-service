package com.blesk.userservice.Repository.Cache;

import com.blesk.userservice.Cache.Accounts;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountsCrudRepository extends CrudRepository<Accounts, Long> {
}