package com.blesk.userservice.Service.Caches;

import com.blesk.userservice.Model.Caches;
import com.blesk.userservice.Repository.Cache.CachesCrudRepository;
import com.blesk.userservice.Value.Messages;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.LockModeType;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class CachesServiceImpl implements CachesService {

    private CachesCrudRepository cachesCrudRepository;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public CachesServiceImpl(CachesCrudRepository cachesCrudRepository) {
        this.cachesCrudRepository = cachesCrudRepository;
    }

    @Override
    @Transactional
    @Lock(value = LockModeType.WRITE)
    public Iterable<Caches> createOrUpdatCache(List<Caches> caches) {
        List<Caches> result = new ArrayList<>();
        try {
            for (Caches cache : caches) {
                result.add(this.cachesCrudRepository.save(cache));
            }
        } catch (DataAccessException ex) {
            this.logger.info(Messages.CREATE_CACHE);
        }
        return result;
    }


    @Override
    @Transactional
    @Lock(value = LockModeType.READ)
    public Iterable<Caches> getAllCache(Iterable<Long> ids) {
        try {
            Iterable<Caches> caches = this.cachesCrudRepository.findAllById(ids);
            if (caches == null) this.logger.info(Messages.NOT_FOUND_CACHE);
            return caches;
        }catch (DataAccessException ex){
            this.logger.info(Messages.FIND_CACHE);
        }
        return Collections.emptyList();
    }

    @Override
    @Transactional
    @Lock(value = LockModeType.READ)
    public Caches getCache(Long id) {
        try {
            Optional optional = this.cachesCrudRepository.findById(id);
            if (!optional.isPresent()) this.logger.info(Messages.NOT_FOUND_CACHE);
            return (Caches) optional.get();
        } catch (DataAccessException ex) {
            this.logger.info(Messages.FIND_CACHE);
        }
        return null;
    }

    @Override
    @Transactional
    @Lock(value = LockModeType.WRITE)
    public Boolean deleteCache(Long id) {
        try {
            this.cachesCrudRepository.delete(this.getCache(id));
            return true;
        } catch (DataAccessException ex) {
            this.logger.info(Messages.DELETE_CACHE);
        }
        return false;
    }
}