package com.blesk.userservice.DAO;

import com.blesk.userservice.Value.Keys;
import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;
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
    public Boolean delete(String entity, String IdColumn, Long id) {
        Session session = this.entityManager.unwrap(Session.class);
        try {
            Query query = session.createSQLQuery(String.format("DELETE FROM %s WHERE %s = :%s", entity, IdColumn, IdColumn)).setParameter(IdColumn, id);
            query.executeUpdate();
        } catch (Exception e) {
            session.clear();
            session.close();
            return false;
        }
        return true;
    }

    @Override
    public T get(Class c, Long id) {
        Session session = this.entityManager.unwrap(Session.class);
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(c);
        Root<T> root = criteriaQuery.from(c);

        try {
            return session.createQuery(criteriaQuery.where(criteriaBuilder.equal(root.get("accountId"), id))).getSingleResult();
        } catch (Exception ex) {
            session.clear();
            session.close();
            return null;
        }
    }

    @Override
    public List<T> getAll(Class c, int pageNumber, int pageSize) {
        Session session = this.entityManager.unwrap(Session.class);
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();

        CriteriaQuery<Long> countCriteria = criteriaBuilder.createQuery(Long.class);
        countCriteria.select(criteriaBuilder.count(countCriteria.from(c)));
        Long total = this.entityManager.createQuery(countCriteria).getSingleResult();

        if (pageSize > total)
            pageSize = total.intValue();

        if ((pageNumber > 0) && (pageNumber < (Math.floor(total / pageSize))) ||
                (pageNumber == 0) && (pageNumber < (Math.floor(total / pageSize))) ||
                (pageNumber > 0) && (pageNumber == Math.floor(total / pageSize)) ||
                (pageNumber == 0) && (pageNumber == Math.floor(total / pageSize))) {

            CriteriaQuery criteriaQuery = criteriaBuilder.createQuery(c);
            Root<T> select = criteriaQuery.from(c);
            CriteriaQuery<T> entity = criteriaQuery.select(select).orderBy(criteriaBuilder.asc(select.get("createdAt")));

            Query typedQuery = session.createQuery(entity);
            typedQuery.setFirstResult(pageNumber);
            typedQuery.setMaxResults(pageSize);

            try {
                return typedQuery.getResultList();
            } catch (Exception ex) {
                session.clear();
                session.close();
                return null;
            }
        } else {
            return Collections.emptyList();
        }
    }

    @Override
    public T getItemByColumn(Class c, String column, String value) {
        Session session = this.entityManager.unwrap(Session.class);
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(c);
        Root<T> root = criteriaQuery.from(c);

        try {
            return session.createQuery(criteriaQuery.where(criteriaBuilder.equal(root.get(column), value))).getSingleResult();
        } catch (Exception ex) {
            session.clear();
            session.close();
            return null;
        }
    }

    @Override
    public Map<String, Object> searchBy(Class c, HashMap<String, HashMap<String, String>> criterias, int pageNumber) {
        final int PAGE_SIZE = 10;
        Session session = this.entityManager.unwrap(Session.class);
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(c);
        Root<T> root = criteriaQuery.from(c);

        List<Predicate> predicates = new ArrayList<Predicate>();
        CriteriaQuery<T> select = criteriaQuery.select(root);

        if (criterias.get(Keys.ORDER_BY) != null) {
            List<Order> orderList = new ArrayList();

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

        if (criterias.get(Keys.SEARCH) != null) {
            for (Object o : criterias.get(Keys.SEARCH).entrySet()) {
                Map.Entry pair = (Map.Entry) o;
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get(pair.getKey().toString())), "%" + pair.getValue().toString().toLowerCase() + "%"));
            }
            select.where(predicates.toArray(new Predicate[]{}));
        }

        TypedQuery<T> typedQuery = session.createQuery(select);
        if (criterias.get(Keys.PAGINATION) != null) {
            typedQuery.setFirstResult(pageNumber);
            typedQuery.setMaxResults(PAGE_SIZE);

            HashMap<String, Object> map = new HashMap<>();
            List<T> result = typedQuery.getResultList();

            int total = result.size();

            if ((pageNumber > 0) && (pageNumber < (Math.floor(total / PAGE_SIZE)))) {
                map.put("hasPrev", true);
                map.put("hasNext", true);
            } else if ((pageNumber == 0) && (pageNumber < (Math.floor(total / PAGE_SIZE)))) {
                map.put("hasPrev", false);
                map.put("hasNext", true);
            } else if ((pageNumber > 0) && (pageNumber == Math.floor(total / PAGE_SIZE))) {
                map.put("hasPrev", true);
                map.put("hasNext", false);
            } else if ((pageNumber == 0) && (pageNumber == Math.floor(total / PAGE_SIZE))) {
                map.put("hasPrev", false);
                map.put("hasNext", false);
            } else {
                return Collections.<String, Object>emptyMap();
            }

            map.put("results", result);
            return map;
        }

        try {
            HashMap<String, Object> map = new HashMap<>();
            map.put("results", typedQuery.getResultList());
            return map;
        } catch (Exception ex) {
            session.clear();
            session.close();
            return null;
        }
    }
}