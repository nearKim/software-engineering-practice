package messy_filtering.solution.kotlin_dsl

import jakarta.persistence.criteria.*
import kotlin.reflect.KProperty1

infix fun <T, P> KProperty1<T, P>.eq(value: P): (CriteriaBuilder, Root<T>) -> Predicate =
    { cb, root -> cb.equal(root.get<P>(this.name), value) }

infix fun <T, P : Comparable<P>> KProperty1<T, P>.gt(value: P): (CriteriaBuilder, Root<T>) -> Predicate =
    { cb, root -> cb.greaterThan(root.get<P>(this.name), value) }

fun <T, P : Comparable<P>> asc(property: KProperty1<T, P>): (CriteriaBuilder, Root<T>) -> Order =
    { cb, root -> cb.asc(root.get<P>(property.name)) }

fun <T, P : Comparable<P>> desc(property: KProperty1<T, P>): (CriteriaBuilder, Root<T>) -> Order =
    { cb, root -> cb.desc(root.get<P>(property.name)) }

fun <T, R> Map<String, Join<*, *>>.getJoin(property: KProperty1<T, R>): Join<T, R> {
    val join = this[property.name] as? Join<T, R>
    return join ?: throw Exception("Join not found for ${property.name}")
}
