package messy_filtering.solution.java_dsl;

import jakarta.persistence.criteria.*;

public class Predicates {
    public static <T> Predicate eq(CriteriaBuilder cb, Path<T> path, T value) {
        return cb.equal(path, value);
    }

    public static <T extends Comparable<T>> Predicate gt(CriteriaBuilder cb, Path<T> path, T value) {
        return cb.greaterThan(path, value);
    }

    public static <T extends Comparable<T>> Order asc(CriteriaBuilder cb, Path<T> path) {
        return cb.asc(path);
    }

    public static <T extends Comparable<T>> Order desc(CriteriaBuilder cb, Path<T> path) {
        return cb.desc(path);
    }
}