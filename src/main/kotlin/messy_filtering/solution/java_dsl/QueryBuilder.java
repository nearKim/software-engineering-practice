package messy_filtering.solution.java_dsl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.*;
import jakarta.persistence.criteria.Predicate;

import java.util.*;

public class QueryBuilder<T> {
    private final EntityManager em;
    private final Class<T> entityClass;
    private final List<Map.Entry<String, JoinType>> joinSpecs = new ArrayList<>();
    private final List<Condition<T>> conditions = new ArrayList<>();
    private final List<OrderProvider<T>> orders = new ArrayList<>();

    public QueryBuilder(EntityManager em, Class<T> entityClass) {
        this.em = em;
        this.entityClass = entityClass;
    }

    public QueryBuilder<T> where(Condition<T> condition) {
        conditions.add(condition);
        return this;
    }

    public QueryBuilder<T> orderBy(OrderProvider<T> orderProvider) {
        orders.add(orderProvider);
        return this;
    }

    public QueryBuilder<T> join(String propertyName, JoinType joinType) {
        joinSpecs.add(new AbstractMap.SimpleEntry<>(propertyName, joinType));
        return this;
    }

    public CriteriaQuery<T> build() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<T> query = cb.createQuery(entityClass);
        Root<T> root = query.from(entityClass);
        Map<String, Join<?, ?>> joins = new HashMap<>();

        for (Map.Entry<String, JoinType> spec : joinSpecs) {
            Join<?, ?> join = root.join(spec.getKey(), spec.getValue());
            joins.put(spec.getKey(), join);
        }

        List<Predicate> predicates = new ArrayList<>();
        for (Condition<T> condition : conditions) {
            predicates.add(condition.apply(cb, root, joins));
        }
        if (!predicates.isEmpty()) {
            query.where(cb.and(predicates.toArray(new Predicate[0])));
        }

        List<Order> orderList = new ArrayList<>();
        for (OrderProvider<T> order : orders) {
            orderList.add(order.apply(cb, root));
        }
        if (!orderList.isEmpty()) {
            query.orderBy(orderList);
        }

        return query;
    }
}

@FunctionalInterface
interface Condition<T> {
    Predicate apply(CriteriaBuilder cb, Root<T> root, Map<String, Join<?, ?>> joins);
}

@FunctionalInterface
interface OrderProvider<T> {
    Order apply(CriteriaBuilder cb, Root<T> root);
}