package com.blesk.userservice.Service.Caches;

import com.blesk.userservice.Cache.Accounts;
import com.blesk.userservice.Repository.Cache.AccountsCrudRepository;
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

    private AccountsCrudRepository accountsCrudRepository;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public CachesServiceImpl(AccountsCrudRepository accountsCrudRepository) {
        this.accountsCrudRepository = accountsCrudRepository;
    }

    @Override
    @Transactional
    @Lock(value = LockModeType.WRITE)
    public Iterable<Accounts> createOrUpdatCache(List<Accounts> caches) {
        List<Accounts> result = new ArrayList<>();
        try {
            for (Accounts cache : caches) {
                result.add(this.accountsCrudRepository.save(cache));
            }
        } catch (DataAccessException ex) {
            this.logger.info(Messages.CREATE_CACHE);
        }
        return result;
    }


    @Override
    @Transactional
    @Lock(value = LockModeType.READ)
    public Iterable<Accounts> getAllCache(Iterable<Long> ids) {
        try {
            Iterable<Accounts> caches = this.accountsCrudRepository.findAllById(ids);
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
    public Accounts getCache(Long id) {
        try {
            Optional optional = this.accountsCrudRepository.findById(id);
            if (!optional.isPresent()) this.logger.info(Messages.NOT_FOUND_CACHE);
            return (Accounts) optional.get();
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
            this.accountsCrudRepository.delete(this.getCache(id));
            return true;
        } catch (DataAccessException ex) {
            this.logger.info(Messages.DELETE_CACHE);
        }
        return false;
    }
}