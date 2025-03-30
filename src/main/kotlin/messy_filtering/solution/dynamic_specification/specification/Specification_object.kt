package messy_filtering.solution.dynamic_specification.specification_mangled

/**
 * val isEven = SimpleSpecification<Int> { it % 2 == 0 }
 * val isPositive = SimpleSpecification<Int> { it > 0 }
 * val spec = isEven.and(isPositive) // Combines conditions into a tree
 * println(spec.isSatisfiedBy(4)) // true
 * println(spec.isSatisfiedBy(-2)) // false
 */
sealed class Specification<T> {
    abstract fun isSatisfiedBy(candidate: T): Boolean

    fun and(other: Specification<T>): Specification<T> = AndSpecification(this, other)

    fun or(other: Specification<T>): Specification<T> = OrSpecification(this, other)

    fun not(): Specification<T> = NotSpecification(this)
}

data class SimpleSpecification<T>(
    private val condition: (T) -> Boolean,
) : Specification<T>() {
    override fun isSatisfiedBy(candidate: T): Boolean = condition(candidate)
}

data class AndSpecification<T>(
    private val left: Specification<T>,
    private val right: Specification<T>,
) : Specification<T>() {
    override fun isSatisfiedBy(candidate: T): Boolean = left.isSatisfiedBy(candidate) && right.isSatisfiedBy(candidate)
}

data class OrSpecification<T>(
    private val left: Specification<T>,
    private val right: Specification<T>,
) : Specification<T>() {
    override fun isSatisfiedBy(candidate: T): Boolean = left.isSatisfiedBy(candidate) || right.isSatisfiedBy(candidate)
}

data class NotSpecification<T>(
    private val spec: Specification<T>,
) : Specification<T>() {
    override fun isSatisfiedBy(candidate: T): Boolean = !spec.isSatisfiedBy(candidate)
}
