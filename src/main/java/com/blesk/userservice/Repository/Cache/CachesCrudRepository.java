package com.blesk.userservice.Repository.Cache;

import com.blesk.userservice.Model.Caches;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CachesCrudRepository extends CrudRepository<Caches, Long> {
}