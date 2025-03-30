package messy_filtering.solution.query_object.peaa.vanilla

open class Criteria(
    private val sqlOperator: String,
    protected val field: String,
    protected val value: Any,
) {
    open fun generateSql(dataMap: DataMap): String = "${dataMap.getColumnForField(field)} $sqlOperator $value"

    companion object {
        fun greaterThan(
            fieldName: String,
            value: Int,
        ): Criteria = greaterThan(fieldName, value as Any)

        private fun greaterThan(
            fieldName: String,
            value: Any,
        ): Criteria = Criteria(" > ", fieldName, value)

        fun matches(
            fieldName: String,
            pattern: String,
        ): Criteria = MatchCriteria(fieldName, pattern)
    }
}

class MatchCriteria(
    field: String,
    pattern: String,
) : Criteria(" LIKE ", field, pattern) {
    override fun generateSql(dataMap: DataMap): String = "UPPER(${dataMap.getColumnForField(field)}) LIKE UPPER('$value')"
}
