package messy_filtering.solution.query_object.vanilla

class QueryObject<T>(
    private val klass: Class<T>,
) {
    private val criteria = mutableListOf<Criteria>()

    fun addCriteria(c: Criteria) {
        criteria.add(c)
    }

    fun execute(unitOfWork: UnitOfWork): Set<T> {
        val mapper = unitOfWork.getMapper(klass)
        val whereClause = generateWhereClause(mapper.dataMap)
        return mapper.findObjectsWhere(whereClause) as Set<T>
    }

    private fun generateWhereClause(dataMap: DataMap): String {
        val result = StringBuilder()
        for (c in criteria) {
            if (result.isNotEmpty()) {
                result.append(" AND ")
            }
            result.append(c.generateSql(dataMap))
        }
        return result.toString()
    }
}
