package com.blesk.userservice.Service.Caches;

import com.blesk.userservice.Cache.Accounts;

import java.util.List;

public interface CachesService {

    Iterable<Accounts> createOrUpdatCache(List<Accounts> caches);

    Iterable<Accounts> getAllCache(Iterable<Long> ids);

    Accounts getCache(Long id);

    Boolean deleteCache(Long id);
}