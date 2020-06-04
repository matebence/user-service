package com.blesk.userservice.DAO;

import com.blesk.userservice.Value.Keys;
import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import javax.persistence.criteria.*;
import java.util.*;

@Repository
public class DAOImpl<T> implements DAO<T> {

    @PersistenceContext
    protected EntityManager entityManager;

    @Override
    public T save(T t) {
        Session session = this.entityManager.unwrap(Session.class);
        try {
            session.save(t);
        } catch (ConstraintViolationException e) {
            throw e;
        } catch (Exception e) {
            session.clear();
            session.close();
            return null;
        }
        return t;
    }

    @Override
    public Boolean update(T t) {
        Session session = this.entityManager.unwrap(Session.class);
        try {
            session.update(t);
        } catch (ConstraintViolationException e) {
            throw e;
        } catch (Exception e) {
            session.clear();
            session.close();
            return false;
        }
        return true;
    }

    @Override
    public Boolean delete(T t) {
        Session session = this.entityManager.unwrap(Session.class);
        try {
            session.delete(t);
        } catch (Exception e) {
            session.clear();
            session.close();
            return false;
        }
        return true;
    }

    @Override
    public T get(Class<T> c, String column, Long id) {
        Session session = this.entityManager.unwrap(Session.class);
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(c);
        Root<T> root = criteriaQuery.from(c);

        try {
            return session.createQuery(criteriaQuery.where(criteriaBuilder.and(criteriaBuilder.equal(root.get("column"), id), criteriaBuilder.equal(root.get("isDeleted"), false)))).getSingleResult();
        } catch (Exception ex) {
            session.clear();
            session.close();
            return null;
        }
    }

    @Override
    public List<T> getAll(Class<T> c, int pageNumber, int pageSize) {
        Session session = this.entityManager.unwrap(Session.class);
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();

        try {
            CriteriaQuery<Long> countCriteria = criteriaBuilder.createQuery(Long.class);
            countCriteria.select(criteriaBuilder.count(countCriteria.from(c)));
            Long total = this.entityManager.createQuery(countCriteria).getSingleResult();

            CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(c);
            Root<T> select = criteriaQuery.from(c);
            CriteriaQuery<T> entity = criteriaQuery.select(select).where(criteriaBuilder.equal(select.get("isDeleted"), false)).orderBy(criteriaBuilder.asc(select.get("createdAt")));

            TypedQuery<T> typedQuery = session.createQuery(entity);
            typedQuery.setFirstResult(pageNumber);
            typedQuery.setMaxResults(pageSize);

            return new PageImpl<T>(typedQuery.getResultList(), pageable, total).getContent();
        } catch (Exception ex) {
            return Collections.emptyList();
        }
    }

    @Override
    public T getItemByColumn(Class<T> c, String column, String value) {
        Session session = this.entityManager.unwrap(Session.class);
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(c);
        Root<T> root = criteriaQuery.from(c);

        try {
            return session.createQuery(criteriaQuery.where(criteriaBuilder.and(criteriaBuilder.equal(root.get(column), value), criteriaBuilder.equal(root.get("isDeleted"), false)))).getSingleResult();
        } catch (Exception ex) {
            session.clear();
            session.close();
            return null;
        }
    }

    @Override
    public Map<String, Object> searchBy(Class<T> c, HashMap<String, HashMap<String, String>> criterias) {
        Session session = this.entityManager.unwrap(Session.class);
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        HashMap<String, Object> map = new HashMap<>(); PageImpl page = null;

        try {
            CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(c);
            Root<T> root = criteriaQuery.from(c);
            List<Predicate> predicates = new ArrayList<Predicate>();
            predicates.add(criteriaBuilder.equal(root.get("isDeleted"), false));
            CriteriaQuery<T> select = criteriaQuery.select(root);

            if (criterias.get(Keys.SEARCH) != null) {
                for (Object o : criterias.get(Keys.SEARCH).entrySet()) {
                    Map.Entry pair = (Map.Entry) o;
                    predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get(pair.getKey().toString())), "%" + pair.getValue().toString().toLowerCase() + "%"));
                }
                select.where(predicates.toArray(new Predicate[]{}));
            }
            if (criterias.get(Keys.ORDER_BY) != null) {
                List<Order> orderList = new ArrayList<>();

                for (Object o : criterias.get(Keys.ORDER_BY).entrySet()) {
                    Map.Entry pair = (Map.Entry) o;
                    if (pair.getValue().toString().toLowerCase().equals("asc")) {
                        orderList.add(criteriaBuilder.asc(root.get(pair.getKey().toString())));
                    } else if (pair.getValue().toString().toLowerCase().equals("desc")) {
                        orderList.add(criteriaBuilder.desc(root.get(pair.getKey().toString())));
                    }
                }
                select.orderBy(orderList);
            }
            if (criterias.get(Keys.PAGINATION) != null) {
                Pageable pageable = PageRequest.of(Integer.parseInt(criterias.get(Keys.PAGINATION).get(Keys.PAGE_NUMBER)), Integer.parseInt(criterias.get(Keys.PAGINATION).get(Keys.PAGE_SIZE)));
                TypedQuery<T> typedQuery = session.createQuery(select);
                long total = typedQuery.getResultList().size();
                typedQuery.setFirstResult(Integer.parseInt(criterias.get(Keys.PAGINATION).get(Keys.PAGE_NUMBER)));
                typedQuery.setMaxResults(Integer.parseInt(criterias.get(Keys.PAGINATION).get(Keys.PAGE_SIZE)));
                page = new PageImpl<T>(typedQuery.getResultList(), pageable, total);

                map.put("hasPrev", page.getNumber() > 0);
                map.put("hasNext", page.getNumber() < total - 1);
            }

            if (page == null) return null;
            map.put("results", page.getContent());
            return map;
        } catch (Exception e) {
            return Collections.<String, Object>emptyMap();
        }
    }
}