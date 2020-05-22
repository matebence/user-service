package com.blesk.userservice.Service.Accounts.Cache;

import com.blesk.userservice.Model.Cache.Accounts;

import java.util.List;

public interface CacheService {

    Iterable<Accounts> createOrUpdatCache(List<Accounts> caches);

    Iterable<Accounts> getAllCache(Iterable<Long> ids);

    Accounts getCache(Long id);

    Boolean deleteCache(Long id);
}