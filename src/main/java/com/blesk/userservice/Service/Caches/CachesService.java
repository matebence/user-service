package com.blesk.userservice.Service.Caches;

import com.blesk.userservice.Model.Caches;

import java.util.List;

public interface CachesService {

    Iterable<Caches> createOrUpdatCache(List<Caches> caches);

    Iterable<Caches> getAllCache(Iterable<Long> ids);

    Caches getCache(Long id);

    Boolean deleteCache(Long id);
}