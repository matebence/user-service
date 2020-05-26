package com.blesk.userservice.DAO.Payouts;

import com.blesk.userservice.DAO.DAOImpl;
import com.blesk.userservice.Model.Payouts;
import com.blesk.userservice.Value.Keys;
import org.hibernate.Session;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.*;

@Repository
public class PayoutsDAOImpl extends DAOImpl<Payouts> implements PayoutsDAO {

    @Override
    public Boolean softDelete(Payouts payouts) {
        Session session = this.entityManager.unwrap(Session.class);
        try {
            session.delete(payouts);
        } catch (Exception e) {
            session.clear();
            session.close();
            return false;
        }
        return true;
    }

    @Override
    public Payouts getItemByColumn(String column, String value) {
        Session session = this.entityManager.unwrap(Session.class);
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Payouts> criteriaQuery = criteriaBuilder.createQuery(Payouts.class);
        Root<Payouts> root = criteriaQuery.from(Payouts.class);

        try {
            return session.createQuery(criteriaQuery.where(criteriaBuilder.and(criteriaBuilder.equal(root.get(column), value), criteriaBuilder.equal(root.get("isDeleted"), false)))).getSingleResult();
        } catch (Exception ex) {
            session.clear();
            session.close();
            return null;
        }
    }

    @Override
    public List<Payouts> getAll(int pageNumber, int pageSize) {
        Session session = this.entityManager.unwrap(Session.class);
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();

        try {
            CriteriaQuery<Long> countCriteria = criteriaBuilder.createQuery(Long.class);
            countCriteria.select(criteriaBuilder.count(countCriteria.from(Payouts.class)));
            Long total = this.entityManager.createQuery(countCriteria).getSingleResult();

            CriteriaQuery<Payouts> criteriaQuery = criteriaBuilder.createQuery(Payouts.class);
            Root<Payouts> select = criteriaQuery.from(Payouts.class);
            CriteriaQuery<Payouts> entity = criteriaQuery.select(select).where(criteriaBuilder.equal(select.get("isDeleted"), false)).orderBy(criteriaBuilder.asc(select.get("createdAt")));

            TypedQuery<Payouts> typedQuery = session.createQuery(entity);
            typedQuery.setFirstResult(pageNumber);
            typedQuery.setMaxResults(pageSize);

            return new PageImpl<Payouts>(typedQuery.getResultList(), pageable, total).getContent();
        } catch (Exception ex) {
            return Collections.emptyList();
        }
    }

    @Override
    public Map<String, Object> searchBy(HashMap<String, HashMap<String, String>> criterias) {
        Session session = this.entityManager.unwrap(Session.class);
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        HashMap<String, Object> map = new HashMap<>(); PageImpl page = null;

        try {
            CriteriaQuery<Payouts> criteriaQuery = criteriaBuilder.createQuery(Payouts.class);
            Root<Payouts> root = criteriaQuery.from(Payouts.class);
            List<Predicate> predicates = new ArrayList<Predicate>();
            predicates.add(criteriaBuilder.equal(root.get("isDeleted"), false));
            CriteriaQuery<Payouts> select = criteriaQuery.select(root);

            if (criterias.get(Keys.SEARCH) != null) {
                for (Object o : criterias.get(Keys.SEARCH).entrySet()) {
                    Map.Entry pair = (Map.Entry) o;
                    predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get(pair.getKey().toString())), "%" + pair.getValue().toString().toLowerCase() + "%"));
                }
                select.where(predicates.toArray(new Predicate[]{}));
            }
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
            if (criterias.get(Keys.PAGINATION) != null) {
                Pageable pageable = PageRequest.of(Integer.parseInt(criterias.get(Keys.PAGINATION).get(Keys.PAGE_NUMBER)), Integer.parseInt(criterias.get(Keys.PAGINATION).get(Keys.PAGE_SIZE)));
                TypedQuery<Payouts> typedQuery = session.createQuery(select);
                long total = typedQuery.getResultList().size();
                typedQuery.setFirstResult(Integer.parseInt(criterias.get(Keys.PAGINATION).get(Keys.PAGE_NUMBER)));
                typedQuery.setMaxResults(Integer.parseInt(criterias.get(Keys.PAGINATION).get(Keys.PAGE_SIZE)));
                page = new PageImpl<Payouts>(typedQuery.getResultList(), pageable, total);

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