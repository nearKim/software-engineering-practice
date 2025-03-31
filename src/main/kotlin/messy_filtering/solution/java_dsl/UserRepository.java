package messy_filtering.solution.java_dsl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaQuery;
import messy_filtering.solution.java_dsl.entities.User;

import java.util.List;
import java.util.function.Consumer;

public class UserRepository {
    private final EntityManager em;

    public UserRepository(EntityManager em) {
        this.em = em;
    }

    public List<User> findByQuery(Consumer<QueryBuilder<User>> block, int page, int size) {
        QueryBuilder<User> builder = new QueryBuilder<>(em, User.class);
        block.accept(builder);
        CriteriaQuery<User> query = builder.build();
        TypedQuery<User> typedQuery = em.createQuery(query);
        typedQuery.setFirstResult(page * size);
        typedQuery.setMaxResults(size);
        return typedQuery.getResultList();
    }
}