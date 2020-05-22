package com.blesk.userservice.Repository.Accounts;

import com.blesk.userservice.Model.Cache.Accounts;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountsCrudRepository extends CrudRepository<Accounts, Long> {
}