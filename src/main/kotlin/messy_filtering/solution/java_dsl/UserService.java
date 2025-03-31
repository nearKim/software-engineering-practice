package messy_filtering.solution.java_dsl;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import messy_filtering.solution.java_dsl.entities.Order;
import messy_filtering.solution.java_dsl.entities.User;

import java.util.List;

public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getActiveAdultUsers() {
        return userRepository.findByQuery(builder -> {
            builder.where((cb, root, joins) -> Predicates.eq(cb, root.get("isActive"), true));
            builder.where((cb, root, joins) -> Predicates.gt(cb, root.get("age"), 19));
            builder.orderBy((cb, root) -> Predicates.asc(cb, root.get("name")));
        }, 0, 10);
    }

    public List<User> getUsersWithShippedOrders() {
        return userRepository.findByQuery(builder -> {
            builder.join("orders", JoinType.INNER);
            builder.where((cb, root, joins) -> {
                Join<User, Order> orderJoin = (Join<User, Order>) joins.get("orders");
                return cb.equal(orderJoin.get("status"), "shipped");
            });
        }, 0, 20);
    }
}