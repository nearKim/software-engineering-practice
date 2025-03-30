package messy_filtering.solution.dynamic_specification.specification

enum class LogicalOperator {
    AND,
    OR,
    NOT,
}

interface Specification<T> {
    fun isSatisfiedBy(candidate: T): Boolean
}

class SpecificationImpl<T>(
    private val predicate: (T) -> Boolean,
) : Specification<T> {
    override fun isSatisfiedBy(candidate: T): Boolean = predicate(candidate)

    companion object {
        fun <T> simple(condition: (T) -> Boolean): Specification<T> = SpecificationImpl(condition)

        fun <T> composite(
            operator: LogicalOperator,
            vararg specs: Specification<T>,
        ): Specification<T> {
            val test: (T) -> Boolean =
                when (operator) {
                    LogicalOperator.AND -> { candidate -> specs.all { it.isSatisfiedBy(candidate) } }
                    LogicalOperator.OR -> { candidate -> specs.any { it.isSatisfiedBy(candidate) } }
                    LogicalOperator.NOT -> {
                        if (specs.size != 1) {
                            throw IllegalArgumentException("NOT operator requires exactly one specification")
                        }
                        { candidate -> !specs.first().isSatisfiedBy(candidate) }
                    }
                }
            return SpecificationImpl(test)
        }
    }
}
